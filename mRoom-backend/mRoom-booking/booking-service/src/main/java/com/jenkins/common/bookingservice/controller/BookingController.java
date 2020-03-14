package com.jenkins.common.bookingservice.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jenkins.common.bookinginterface.entity.BookingRecord;
import com.jenkins.common.bookingservice.mapper.BookingRecordMapper;
import com.jenkins.common.bookingservice.service.BookingService;
import com.jenkins.common.components.model.ResultVo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/")
    public ResultVo booking(@RequestBody BookingRecord bookingRecord)
    {
        int result = bookingService.addBookingRecord(bookingRecord);
        Date end_time = bookingRecord.getEnd_time();
        Date start_time = bookingRecord.getStart_time();
        if(end_time.before(start_time))
        {
            return ResultVo.error("Incorrect time! Please do not attempt to dump garbage data!");
        }
        if(result == BookingService.EMAIL_FAILED)
        {
            return ResultVo.error("Incorrect email! Please do not attempt to dump garbage data!");
        }
        if(result == BookingService.TIME_CONFLICT)
        {
            return ResultVo.error("Booking failed! The Time you chose has been booked!");
        }
        return ResultVo.ok("Booking succeeded!");
    }

    @GetMapping("/history/{bookerEmail}")
    public ResultVo getHistory(@PathVariable("bookerEmail") String bookerEmail)
    {
        List<BookingRecord> bookingRecords = bookingService.bookingHistory(bookerEmail);
        return ResultVo.ok("success!",bookingRecords);
    }
}
