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

    List<BookingRecord> selectBookingRecordByUserEmail(@Param("bookerEmail") String bookerEmail, int page,int pageSize,@Param("filters") int[] filters);

    int insertBookingRecord(@Param("bookingRecord") BookingRecord bookingRecord);

    int countRecordByEmail(String bookerEmail,@Param("filters") int[] filters);

    int deleteRecordById(int recordId);

    int updateRecordId(int originalRecordId,int newRecordId);

    int updateStatusByRecordId(int status,int recordId);
}
