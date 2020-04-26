package com.jenkins.common.bookingservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jenkins.common.bookinginterface.entity.BookingRecord;
import com.jenkins.common.bookinginterface.model.AdminBookingHistoryModel;
import com.jenkins.common.bookinginterface.model.BookingHistoryModel;
import com.jenkins.common.bookinginterface.model.CalendarEventsModel;
import com.jenkins.common.bookingservice.client.BookingClient;
import com.jenkins.common.bookingservice.mapper.BookingRecordMapper;
import com.jenkins.common.components.model.ResultVo;
import com.jenkins.common.roomInterface.entity.Room;
import com.jenkins.common.roomInterface.model.RoomOverview;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public List<BookingHistoryModel> bookingHistory(String bookerEmail,int page,int size,int[] filters,Date date)
    {
        Date startTime = null;
        Date endTime = null;
        if(date != null)
        {
            startTime = new DateTime(date).toDate();
            endTime = new DateTime(startTime).plusDays(1).toDate();
        }
        List<BookingRecord> bookingRecords = bookingRecordMapper.selectBookingRecordByUserEmail(bookerEmail,page-1,size,filters,startTime,endTime);

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

    public List<AdminBookingHistoryModel> bookingRecords(String bookerEmail, int page, int size, int[] filters, String date)
    {
        if(bookerEmail == null || bookerEmail.length() == 0)
        {
            bookerEmail = null;
        }
        List<BookingRecord> allBookingRecords = null;
        if(date == null || date.length() == 0)
        {
            date = null;
            allBookingRecords = bookingRecordMapper.getAllBookingRecords(bookerEmail, page - 1, size, filters, null, null);
        }else {
            Date startTime = DateTime.parse(date).toDate();
            Date endTime = new DateTime(startTime).plusDays(1).toDate();
            allBookingRecords= bookingRecordMapper.getAllBookingRecords(bookerEmail,page-1,size,filters,startTime,endTime);
        }
        System.out.println(allBookingRecords);
        List<AdminBookingHistoryModel> adminBookingHistoryModelList = new ArrayList<>();
        for (BookingRecord bookingRecord : allBookingRecords) {
            AdminBookingHistoryModel adminBookingHistoryModel = new AdminBookingHistoryModel();
            Object data = bookingClient.getRoomDetail(bookingRecord.getRoomId()).getData();
            Room roomDetail = JSON.parseObject(JSON.toJSONString(data), Room.class);
            adminBookingHistoryModel.setBookerEmail(bookingRecord.getBookerEmail());
            adminBookingHistoryModel.setCreationTime(bookingRecord.getCreationTime());
            adminBookingHistoryModel.setStartTime(bookingRecord.getStartTime());
            adminBookingHistoryModel.setEndTime(bookingRecord.getEndTime());
            adminBookingHistoryModel.setRoomName(roomDetail.getRoomName());
            adminBookingHistoryModel.setStatus(bookingRecord.getStatus());
            adminBookingHistoryModel.setRecordId(bookingRecord.getRecordId());
            adminBookingHistoryModelList.add(adminBookingHistoryModel);
        }
        return adminBookingHistoryModelList;
    }

    public int countHistoryAll(String bookerEmail,int[] filters,String date)
    {
        if(bookerEmail == null || bookerEmail.length() == 0)
        {
            bookerEmail = null;
        }
        Date startTime = null;
        Date endTime = null;
        if(date != null && date.length()!=0 && date != "")
        {
            startTime = DateTime.parse(date).toDate();
            endTime = new DateTime(startTime).plusDays(1).toDate();
        }
        return bookingRecordMapper.countAllRecords(bookerEmail,filters,startTime,endTime);
    }

    public int countHistory(String bookerEmail,int[] filters,String date)
    {
        Date startTime = null;
        Date endTime = null;
        if(date != null && date.length()!=0 && date != "")
        {
            startTime = DateTime.parse(date).toDate();
            endTime = new DateTime(startTime).plusDays(1).toDate();
        }
        return bookingRecordMapper.countRecordByEmail(bookerEmail,filters,startTime,endTime);
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

    public int autoComplete(String bookerEmail)
    {
        Date now = DateTime.now().toDate();
        return  bookingRecordMapper.autoComplete(bookerEmail,now);
    }

    public int autoCompleteAll()
    {
        Date now = DateTime.now().toDate();
        return bookingRecordMapper.autoComplete(null,now);
    }

    public List<RoomOverview> advanceBooking(String startTime,String endTime,int capacity,int airConditioner,int projection) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            Date newStartTime = simpleDateFormat.parse(startTime);
            Date newEndTime = simpleDateFormat.parse(endTime);
            return bookingRecordMapper.queryAvailable(newStartTime,newEndTime,capacity,airConditioner,projection);
        } catch (Exception e)
        {
            System.out.println(e);
            return null;
        }


    }
}
