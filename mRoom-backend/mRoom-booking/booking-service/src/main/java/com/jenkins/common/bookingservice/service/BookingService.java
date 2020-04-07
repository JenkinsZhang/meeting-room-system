package com.jenkins.common.bookingservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jenkins.common.bookinginterface.entity.BookingRecord;
import com.jenkins.common.bookinginterface.model.BookingHistoryModel;
import com.jenkins.common.bookinginterface.model.CalendarEventsModel;
import com.jenkins.common.bookingservice.client.BookingClient;
import com.jenkins.common.bookingservice.mapper.BookingRecordMapper;
import com.jenkins.common.components.model.ResultVo;
import com.jenkins.common.roomInterface.entity.Room;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * @author JenkinsZhang
 * @date 2020/3/11
 *
 */
@Service

public class BookingService {

    public static final int INSERT_FAILED = -3;
    public static final int DELETE_FAILED = -2;
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
        String email = bookingRecord.getBookerEmail();
        if (email==null){
            return EMAIL_FAILED;
        }
        String pattern = "^[A-Za-z0-9]+@([A-Za-z0-9]+\\.){1,2}[A-Za-z0-9]+$";
        if(!Pattern.matches(pattern,email))
        {
            return EMAIL_FAILED;
        }
        Date now = DateTime.now().toDate();
        bookingRecord.setCreationTime(now);
        bookingRecord.setStatus(0);
        return bookingRecordMapper.insertBookingRecord(bookingRecord);
    }

    public List<BookingHistoryModel> bookingHistory(String bookerEmail,int page,int size,int[] filters)
    {
        List<BookingRecord> bookingRecords = bookingRecordMapper.selectBookingRecordByUserEmail(bookerEmail,page-1,size,filters);

        List<BookingHistoryModel> history = new ArrayList<>();
        for (BookingRecord bookingRecord : bookingRecords) {

//            System.out.println(bookingRecord);
            BookingHistoryModel bookingHistoryModel = new BookingHistoryModel();
            Object data = bookingClient.getRoomDetail(bookingRecord.getRoomId()).getData();
            Room roomDetail = JSON.parseObject(JSON.toJSONString(data), Room.class);
            bookingHistoryModel.setStartTime(bookingRecord.getStartTime());
            bookingHistoryModel.setEndTime(bookingRecord.getEndTime());
            bookingHistoryModel.setCreationTime(bookingRecord.getCreationTime());
            bookingHistoryModel.setRecordId(bookingRecord.getRecordId());
            bookingHistoryModel.setRoomName(roomDetail.getRoomName());
            bookingHistoryModel.setRoomAddress(roomDetail.getAddress());
            bookingHistoryModel.setMaxPeople(roomDetail.getMaxPeople());
            bookingHistoryModel.setAirConditioner(roomDetail.getAirConditioner());
            bookingHistoryModel.setProjection(roomDetail.getProjection());
            bookingHistoryModel.setStatus(bookingRecord.getStatus());
            bookingHistoryModel.setBookerEmail(bookingRecord.getBookerEmail());

            history.add(bookingHistoryModel);
        }
        return history;
    }

    public int countHistory(String bookerEmail,int[] filters)
    {
        return bookingRecordMapper.countRecordByEmail(bookerEmail,filters);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public int updateBookingRecord(BookingRecord bookingRecord)
    {
        System.out.println("============更新中============");
        System.out.println(bookingRecord);
        int originalRecordId = bookingRecord.getRecordId();
        int delete = bookingRecordMapper.deleteRecordById(originalRecordId);
        if(delete != 1)
        {

            return DELETE_FAILED;
        }
        int insert = bookingRecordMapper.insertBookingRecord(bookingRecord);
        if(insert != 1)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return INSERT_FAILED;
        }
        int newRecordId = bookingRecord.getRecordId();
        int update = bookingRecordMapper.updateRecordId(originalRecordId, newRecordId);
        return update;
    }

    public int updateStatus(int status,int recordId)
    {
        return bookingRecordMapper.updateStatusByRecordId(status,recordId);
    }

    public List<CalendarEventsModel> getCalendarEvents(Date startTime,Date endTime){

        List<BookingRecord> calendarEvents = bookingRecordMapper.getCalendarEvents(startTime, endTime);
        System.out.println(calendarEvents);
        List<CalendarEventsModel> calendarEventsModels = new ArrayList<>();
        for (BookingRecord calendarEvent : calendarEvents) {
            CalendarEventsModel calendarEventsModel = new CalendarEventsModel();
            calendarEventsModel.setId(calendarEvent.getRecordId());
            calendarEventsModel.setResourceId(calendarEvent.getRoomId());
            calendarEventsModel.setStart(calendarEvent.getStartTime().getTime());
            calendarEventsModel.setEnd(calendarEvent.getEndTime().getTime());
            calendarEventsModels.add(calendarEventsModel);
        }
        return calendarEventsModels;
    }
}
