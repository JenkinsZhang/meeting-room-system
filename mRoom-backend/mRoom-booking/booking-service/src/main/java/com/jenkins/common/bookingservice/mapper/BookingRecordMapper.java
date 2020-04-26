package com.jenkins.common.bookingservice.mapper;

import com.jenkins.common.bookinginterface.entity.BookingRecord;
import com.jenkins.common.bookinginterface.model.CalendarEventsModel;
import com.jenkins.common.roomInterface.model.RoomOverview;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author jenkinszhang
 * @date 2020/3/10
 */
@Component
public interface BookingRecordMapper {

    List<BookingRecord> selectBookingRecordByUserEmail(@Param("bookerEmail") String bookerEmail,
                                                       int page,int size,@Param("filters") int[] filters,
                                                       Date startTime,Date endTime);

    List<BookingRecord> getAllBookingRecords(@Param("bookerEmail") String bookerEmail,
                                             int page,int size, @Param("filters") int[] filters,
                                             Date startTime,Date endTime);

    int insertBookingRecord(@Param("bookingRecord") BookingRecord bookingRecord);

    int countRecordByEmail(String bookerEmail,@Param("filters") int[] filters,Date startTime,Date endTime);

    int countAllRecords(@Param("bookerEmail") String bookerEmail,int[] filters,Date startTime,Date endTime);

    int deleteRecordById(int recordId);

    int updateRecordId(int originalRecordId,int newRecordId);

    int updateStatusByRecordId(int status,int recordId);

    List<BookingRecord> getCalendarEvents(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    List<RoomOverview> queryAvailable(@Param("startTime") Date startTime, @Param("endTime") Date endTime, int capacity, int airConditioner, int projection);

    int autoComplete(String bookerEmail,Date now);
//    List<BookingRecord> getAll();
}
