package com.jenkins.common.bookingservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jenkins.common.bookinginterface.entity.BookingRecord;
import com.jenkins.common.bookinginterface.model.BookingHistoryModel;
import com.jenkins.common.bookingservice.client.BookingClient;
import com.jenkins.common.bookingservice.mapper.BookingRecordMapper;
import com.jenkins.common.components.model.ResultVo;
import com.jenkins.common.roomInterface.entity.Room;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author JenkinsZhang
 * @date 2020/3/11
 *
 */
@Service
public class BookingService {

    public static final int EMAIL_FAILED = -1;
    public static final int TIME_CONFLICT = 0;
    private BookingRecordMapper bookingRecordMapper;
    private BookingClient bookingClient;

    @Autowired
    public BookingService(BookingRecordMapper bookingRecordMapper,BookingClient bookingClient) {
        this.bookingRecordMapper = bookingRecordMapper;
        this.bookingClient = bookingClient;
    }

    public int addBookingRecord(BookingRecord bookingRecord)
    {
        String email = bookingRecord.getBooker_email();
        if (email==null){
            return EMAIL_FAILED;
        }
        String pattern = "^[A-Za-z0-9]+@([A-Za-z0-9]+\\.){1,2}[A-Za-z0-9]+$";
        if(!Pattern.matches(pattern,email))
        {
            return EMAIL_FAILED;
        }
        Date now = DateTime.now().toDate();
        bookingRecord.setCreation_time(now);
        return bookingRecordMapper.insertBookingRecord(bookingRecord);
    }

    public List<BookingHistoryModel> bookingHistory(String bookerEmail,int page,int page_size,int[] filters)
    {
        List<BookingRecord> bookingRecords = bookingRecordMapper.selectBookingRecordByUserEmail(bookerEmail,page-1,page_size,filters);
        bookingRecords.sort(((o1, o2) -> {
             if(o1.getStart_time().before(o2.getStart_time())){
                 return 1;
             };
            return 0;
        }));
        List<BookingHistoryModel> history = new ArrayList<>();
        for (BookingRecord bookingRecord : bookingRecords) {

            BookingHistoryModel bookingHistoryModel = new BookingHistoryModel();
            Object data = bookingClient.getRoomDetail(bookingRecord.getRoom_id()).getData();
            Room roomDetail = JSON.parseObject(JSON.toJSONString(data), Room.class);
            bookingHistoryModel.setStartTime(bookingRecord.getStart_time());
            bookingHistoryModel.setEndTime(bookingRecord.getEnd_time());
            bookingHistoryModel.setCreatTime(bookingRecord.getCreation_time());
            bookingHistoryModel.setRecordId(bookingRecord.getRecord_id());
            bookingHistoryModel.setRoomName(roomDetail.getRoom_name());
            bookingHistoryModel.setRoomAddress(roomDetail.getAddress());
            bookingHistoryModel.setMaxPeople(roomDetail.getMax_people());
            bookingHistoryModel.setAirConditioner(roomDetail.getAir_conditioner());
            bookingHistoryModel.setProjection(roomDetail.getProjection());
            bookingHistoryModel.setStatus(bookingRecord.getStatus());

            history.add(bookingHistoryModel);
        }
        return history;
    }

    public int countHistory(String bookerEmail,int[] filters)
    {
        return bookingRecordMapper.countRecordByEmail(bookerEmail,filters);
    }
}
