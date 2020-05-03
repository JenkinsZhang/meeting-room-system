package com.jenkins.common.bookingservice;

import com.jenkins.common.bookinginterface.entity.BookingRecord;
import com.jenkins.common.bookinginterface.model.AdminBookingHistoryModel;
import com.jenkins.common.bookinginterface.model.BookingHistoryModel;
import com.jenkins.common.bookingservice.mapper.BookingRecordMapper;
import com.jenkins.common.bookingservice.mapper.TestMapper;
import com.jenkins.common.bookingservice.service.BookingService;
import com.jenkins.common.roomInterface.entity.Room;
import com.jenkins.common.roomInterface.model.RoomOverview;
import com.jenkins.common.userinterface.entity.User;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author JenkinsZhang
 * @date 2020/3/14
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookingServiceTestMapper {

    @Autowired
    public BookingService bookingService;

    @Autowired
    public BookingRecordMapper bookingRecordMapper;

    @Autowired
    public TestMapper testMapper;

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

    @Test
    public void testQueryAvailable(){
        Date date1 = DateTime.parse("2020-11-06").toDate();
        Date date2 = DateTime.parse("2020-11-07").toDate();
        List<RoomOverview> roomOverviews = bookingRecordMapper.queryAvailable(date1, date2, 0, 0, 0);
        System.out.println(roomOverviews);
    }
    @Test
    public void testInsert(){
        System.out.println("Starting....");
        System.out.println("=============================================================");
        List<Room> allRooms = testMapper.getAllRooms();
        System.out.println(allRooms);
        List<User> allUsers = testMapper.getAllUsers();
        System.out.println(allUsers);
        int roomCount = allRooms.size();
        int userCount = allUsers.size();
        DateTime endDate = new DateTime("2020-6-1");
        DateTime startDate = endDate.minusYears(2);

        System.out.println("Inserting....");
        System.out.println("=============================================================");
        for(int i = 0;i<3000;i++)
        {
            int startTime = new Random().nextInt(13) + 9;
            int endTime = new Random().nextInt(22-startTime)+startTime;
            int startMinute = new Random().nextInt(2)*30;
            int endMinute = new Random().nextInt(2)*30;
            int day = new Random().nextInt(2*365+1);
            DateTime startTimeFinal = null;
            DateTime endTimeFinal = null;

            if(startTime == endTime)
            {
                if(endTime == 22)
                {
                    continue;
                }
                startTimeFinal = startDate.plusDays(day).plusHours(startTime);
                endTimeFinal = startDate.plusDays(day).plusHours(endTime).plusMinutes(30);

            }
            else{
                if(endTime != 22)
                {
                    startTimeFinal = startDate.plusDays(day).plusHours(startTime).plusMinutes(startMinute);
                    endTimeFinal = startDate.plusDays(day).plusHours(endTime).plusMinutes(endMinute);
                }
                else {
                    startTimeFinal = startDate.plusDays(day).plusHours(startTime).plusMinutes(startMinute);
                    endTimeFinal = startDate.plusDays(day).plusHours(endTime);
                }
            }
            BookingRecord bookingRecord = new BookingRecord();
            bookingRecord.setStartTime(startTimeFinal.toDate());
            bookingRecord.setEndTime(endTimeFinal.toDate());
            int roomRandom = new Random().nextInt(roomCount);
            int userRandom = new Random().nextInt(userCount);
            bookingRecord.setBookerEmail(allUsers.get(userRandom).getEmail());
            bookingRecord.setRoomId(allRooms.get(roomRandom).getRoomId());
            bookingRecord.setSubject("This is just a Test!!!!");
            int i1 = bookingService.addBookingRecord(bookingRecord);
        }
        System.out.println("Adding Complete!");
    }

    @Test
    public void testMail(){

    }


}
