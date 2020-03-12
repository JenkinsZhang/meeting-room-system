package com.jenkins.common.bookingservice.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jenkins.common.bookinginterface.entity.BookingRecord;
import com.jenkins.common.bookingservice.mapper.BookingRecordMapper;
import com.jenkins.common.bookingservice.service.BookingService;
import com.jenkins.common.components.model.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        int i = bookingService.addBookingRecord(bookingRecord);
        if(i == 1)
        {
            return ResultVo.ok("Booking succeeded!");
        }
        return ResultVo.error("Booking failed!");
    }


}
