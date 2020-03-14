package com.jenkins.common.bookingservice.service;

import com.jenkins.common.bookinginterface.entity.BookingRecord;
import com.jenkins.common.bookingservice.mapper.BookingRecordMapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author JenkinsZhang
 * @date 2020/3/11
 *
 */
@Service
public class BookingService {

    public static final int EMAIL_FAILED = -1;
    public static final int TIME_CONFLICT = 0;
    private BookingRecordMapper bookingRecordMapper;


    @Autowired
    public BookingService(BookingRecordMapper bookingRecordMapper) {
        this.bookingRecordMapper = bookingRecordMapper;
    }

    public int addBookingRecord(BookingRecord bookingRecord)
    {
        String email = bookingRecord.getBooker_email();
        if (email==null){
            return EMAIL_FAILED;
        }
        String pattern = "^[A-Za-z0-9]+@([A-Za-z0-9]+\\.){1,2}[A-Za-z0-9]+$";
        if(!Pattern.matches(pattern,email))
        {
            return EMAIL_FAILED;
        }
        Date now = DateTime.now().toDate();
        bookingRecord.setCreation_time(now);
        return bookingRecordMapper.insertBookingRecord(bookingRecord);
    }

    public List<BookingRecord> bookingHistory(String bookerEmail)
    {
        List<BookingRecord> bookingRecords = bookingRecordMapper.selectBookingRecordByUserEmail(bookerEmail);
        bookingRecords.sort(((o1, o2) -> {
             if(o1.getStart_time().before(o2.getStart_time())){
                 return 1;
             };
            return 0;
        }));
        return bookingRecords;
    }
}
