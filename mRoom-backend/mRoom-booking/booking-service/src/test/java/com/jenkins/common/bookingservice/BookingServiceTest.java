package com.jenkins.common.bookingservice;

import com.jenkins.common.bookinginterface.entity.BookingRecord;
import com.jenkins.common.bookinginterface.model.BookingHistoryModel;
import com.jenkins.common.bookingservice.mapper.BookingRecordMapper;
import com.jenkins.common.bookingservice.service.BookingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
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
    public void test2(){
        int[] filters = {0,-1,1};
        List<BookingRecord> bookingRecords = bookingRecordMapper.selectBookingRecordByUserEmail("614758656@qq.com", 0, 10, filters);
        System.out.println(bookingRecords);
    }

}
