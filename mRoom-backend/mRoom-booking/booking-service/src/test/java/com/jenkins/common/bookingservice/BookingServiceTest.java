package com.jenkins.common.bookingservice;

import com.jenkins.common.bookinginterface.entity.BookingRecord;
import com.jenkins.common.bookinginterface.model.AdminBookingHistoryModel;
import com.jenkins.common.bookinginterface.model.BookingHistoryModel;
import com.jenkins.common.bookingservice.mapper.BookingRecordMapper;
import com.jenkins.common.bookingservice.service.BookingService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/3/14
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookingServiceTest {

    @Autowired
    public BookingService bookingService;

    @Autowired
    public BookingRecordMapper bookingRecordMapper;


//    @Test
//    public void test1(){
//        List<BookingHistoryModel> bookingHistoryModels = bookingService.bookingHistory("614758656@qq.com", 1, 10);
//        System.out.println(bookingHistoryModels);
//        System.out.println(bookingHistoryModels.size());
//    }

    @Test
    public void testBookingHistory(){
        int[] filters = {0,-1,1};
        Date date = DateTime.now().toDate();
        List<BookingHistoryModel> bookingHistoryModels = bookingService.bookingHistory("614758656@qq.com", 1, 10, filters, date);
        System.out.println(bookingHistoryModels);
    }

    @Test
    public void testAllBookingRecords()
    {
        int[] filters = {0,-1,1};
        List<AdminBookingHistoryModel> adminBookingHistoryModelList = bookingService.bookingRecords("614758656@qq.com", 1, 100, filters, null);
        System.out.println(adminBookingHistoryModelList.size());
    }


    @Test
    public void testGetCalendarEvents(){
        String startTime = "2019-1-1";
        Date date = DateTime.parse(startTime).toDate();
        String endTime = "2022-1-1";
        Date date1 = DateTime.parse(endTime).toDate();

        List<BookingRecord> calendarEvents = bookingRecordMapper.getCalendarEvents(date, date1);
        System.out.println(calendarEvents);
    }

    @Test
    public void testAutoComplete(){
        int i = bookingService.autoComplete("614758656@qq.com");
        System.out.println(i);
    }






}
