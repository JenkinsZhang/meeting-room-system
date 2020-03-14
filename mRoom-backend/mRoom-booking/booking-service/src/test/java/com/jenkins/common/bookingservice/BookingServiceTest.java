package com.jenkins.common.bookingservice;

import com.jenkins.common.bookinginterface.entity.BookingRecord;
import com.jenkins.common.bookingservice.service.BookingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void test1(){
        List<BookingRecord> bookingRecords = bookingService.bookingHistory("614758656@qq.com");
        System.out.println(bookingRecords);
    }
}
