package com.jenkins.common.bookingservice.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jenkins.common.bookinginterface.entity.BookingRecord;
import com.jenkins.common.bookinginterface.model.BookingHistoryModel;
import com.jenkins.common.bookinginterface.model.CalendarEventsModel;
import com.jenkins.common.bookingservice.client.BookingClient;
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

        Date endTime = bookingRecord.getEndTime();
        Date startTime = bookingRecord.getStartTime();
        if(endTime.before(startTime))
        {
            return ResultVo.error("Incorrect time! Please do not attempt to dump garbage data!");
        }
        int result = bookingService.addBookingRecord(bookingRecord);
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

    @GetMapping("/history/{bookerEmail}/{page}/{size}")
    public ResultVo getHistory(@PathVariable("bookerEmail") String bookerEmail,
                               @PathVariable("page") int page,
                               @PathVariable("size") int size,
                               @RequestParam("filters") int[] filters)
    {
        if(filters == null || filters.length == 0)
        {
            filters = new int[]{-1, 0, 1};
        }
        List<BookingHistoryModel> bookingHistoryModels = bookingService.bookingHistory(bookerEmail, page, size,filters);
        if(bookingHistoryModels == null || bookingHistoryModels.size() ==0)
        {
            return ResultVo.error("No booking history!");
        }
        return ResultVo.ok("success!",bookingHistoryModels);
    }

    @GetMapping("/history/count/{bookerEmail}")
    public ResultVo countHistory(@PathVariable("bookerEmail") String bookerEmail,
                                 @RequestParam("filters") int[] filters)
    {
        if(filters == null || filters.length == 0)
        {
            filters = new int[]{-1, 0, 1};
        }
        int countHistory = bookingService.countHistory(bookerEmail,filters);
        if(countHistory == 0)
        {
            return ResultVo.error("No booking history!");
        }
        return ResultVo.ok("OK!",countHistory);
    }

    @PostMapping("/history/edit")
    public ResultVo editHistory(@RequestBody BookingRecord bookingRecord)
    {
        if(bookingRecord == null)
        {
            return ResultVo.error("No post data!");
        }
        int i = bookingService.updateBookingRecord(bookingRecord);
        if (i == BookingService.DELETE_FAILED)
        {
            return  ResultVo.error("Cannot find the record!");
        }
        if(i == BookingService.INSERT_FAILED)
        {
            return ResultVo.error("The time is not available! Please change!");
        }
        if(i == 0)
        {
            return ResultVo.error("Backend Error! Please contact the administrator!");
        }
        return ResultVo.ok("OK");
    }

    @GetMapping("/history/{recordId}/cancel")
    public ResultVo cancelRecord(@PathVariable("recordId") int recordId)
    {
        int update = bookingService.updateStatus(-1, recordId);
        if(update != 1)
        {
            return ResultVo.error("Cancel Error! Please try again later!");
        }
        return ResultVo.ok("OK!");
    }

    @GetMapping("/history/{recordId}/complete")
    public ResultVo completeRecord(@PathVariable("recordId") int recordId)
    {
        int update = bookingService.updateStatus(1, recordId);
        if(update != 1)
        {
            return ResultVo.error("Complete Error! Please try again later!");
        }
        return ResultVo.ok("OK!");
    }

    @GetMapping("/calendar/events")
    public ResultVo getCalendarEvents(@RequestParam("startTime") String startTime,
                                      @RequestParam("endTime") String endTime){
        Date parsedStartTime = DateTime.parse(startTime).toDate();
        Date parsedEndTime = DateTime.parse(endTime).toDate();
        List<CalendarEventsModel> calendarEvents = bookingService.getCalendarEvents(parsedStartTime, parsedEndTime);
        return calendarEvents.size() > 0 ? ResultVo.ok("OK",calendarEvents) : ResultVo.error("Failed!");

    }

}
