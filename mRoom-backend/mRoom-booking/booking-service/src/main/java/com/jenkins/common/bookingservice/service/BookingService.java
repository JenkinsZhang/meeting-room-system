package com.jenkins.common.bookingservice.service;

import com.jenkins.common.bookinginterface.entity.BookingRecord;
import com.jenkins.common.bookingservice.mapper.BookingRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JenkinsZhang
 * @date 2020/3/11
 *
 */
@Service
public class BookingService {

    private BookingRecordMapper bookingRecordMapper;

    @Autowired
    public BookingService(BookingRecordMapper bookingRecordMapper) {
        this.bookingRecordMapper = bookingRecordMapper;
    }

    public int addBookingRecord(BookingRecord bookingRecord)
    {
        if (bookingRecord.getBooker_email()==null){
            return 0;
        }
        return bookingRecordMapper.insertBookingRecord(bookingRecord);
    }
}
