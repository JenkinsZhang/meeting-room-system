package com.jenkins.common.bookingservice.mapper;

import com.jenkins.common.bookinginterface.entity.BookingRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jenkinszhang
 * @date 2020/3/10
 */
@Component
public interface BookingRecordMapper {

    List<BookingRecord> selectBookingRecordByUserEmail(@Param("booker_email") String bookerEmail);

    int insertBookingRecord(@Param("booking_record") BookingRecord bookingRecord);
}
