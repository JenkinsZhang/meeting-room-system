package com.jenkins.common.bookingservice.controller;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jenkins.common.authinterface.model.UserInfo;
import com.jenkins.common.bookinginterface.entity.Attender;
import com.jenkins.common.bookinginterface.entity.BookingRecord;
import com.jenkins.common.bookinginterface.model.AdminBookingHistoryModel;
import com.jenkins.common.bookinginterface.model.BookingHistoryModel;
import com.jenkins.common.bookinginterface.model.CalendarEventsModel;
import com.jenkins.common.bookingservice.client.AuthClient;
import com.jenkins.common.bookingservice.client.BookingClient;
import com.jenkins.common.bookingservice.mapper.BookingRecordMapper;
import com.jenkins.common.bookingservice.service.BookingService;
import com.jenkins.common.components.model.ResultVo;
import com.jenkins.common.roomInterface.model.RoomOverview;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
public class BookingController {

    private BookingService bookingService;
    private AuthClient authClient;

    @Autowired
    public BookingController(BookingService bookingService, AuthClient authClient) {
        this.authClient = authClient;
        this.bookingService = bookingService;
    }

    @PostMapping("/")
    public ResultVo booking(@RequestBody BookingRecord bookingRecord) {

        Date endTime = bookingRecord.getEndTime();
        Date startTime = bookingRecord.getStartTime();
        if (endTime.before(startTime)) {
            return ResultVo.error("Incorrect time! Please do not attempt to dump garbage data!");
        }
        int result = bookingService.addBookingRecord(bookingRecord);
        if (result == BookingService.EMAIL_FAILED) {
            return ResultVo.error("Incorrect email! Please do not attempt to dump garbage data!");
        }
        if (result == BookingService.TIME_CONFLICT) {
            return ResultVo.error("Booking failed! The Time you chose has been booked!");
        }
        return ResultVo.ok("Booking succeeded!", result);
    }

    @PostMapping("/attender")
    public ResultVo addAttender(@RequestParam("recordId") int recordId, @RequestParam("attenders") String attenders) {
        int code = bookingService.addAttender(recordId, attenders);
        return code == 1 ? ResultVo.ok("OK!") : ResultVo.error("Failed!");
    }

    @GetMapping("/attender")
    public ResultVo getAttender(@RequestParam("recordId") int recordId) {
        Attender attenders = bookingService.getAttenders(recordId);
        return ResultVo.ok("Ok", attenders);
    }

    @PutMapping("/attender")
    public ResultVo updateAttender(@RequestParam("recordId") int recordId, @RequestParam("attenders") String attenders) {
        int code = bookingService.updateAttender(recordId, attenders);
        return code == 1 ? ResultVo.ok("OK!") : ResultVo.error("Failed!");
    }

    @GetMapping("/history/{bookerEmail}/{page}/{size}")
    public ResultVo getHistory(@PathVariable("bookerEmail") String bookerEmail,
                               @PathVariable("page") int page,
                               @PathVariable("size") int size,
                               @RequestParam("filters") int[] filters,
                               @RequestParam("date") String date) {
        if (filters == null || filters.length == 0) {
            filters = new int[]{-1, 0, 1};
        }
        Date newDate = new Date();
        if (date == null || date.equals("") || date.length() == 0) {
            newDate = null;
        } else {
            newDate = DateTime.parse(date).toDate();
        }
        List<BookingHistoryModel> bookingHistoryModels = bookingService.bookingHistory(bookerEmail, page, size, filters, newDate);
        if (bookingHistoryModels == null || bookingHistoryModels.size() == 0) {
            return ResultVo.error("No booking history!");
        }
        return ResultVo.ok("success!", bookingHistoryModels);
    }

    @GetMapping("/history/count/{bookerEmail}")
    public ResultVo countHistory(@PathVariable("bookerEmail") String bookerEmail,
                                 @RequestParam("filters") int[] filters, @RequestParam("date") String date) {
        if (filters == null || filters.length == 0) {
            filters = new int[]{-1, 0, 1};
        }
        int countHistory = bookingService.countHistory(bookerEmail, filters, date);
        if (countHistory == 0) {
            return ResultVo.error("No booking history!");
        }
        return ResultVo.ok("OK!", countHistory);
    }

    @GetMapping("/admin/history/{page}/{size}")
    public ResultVo getAllBookingRecords(@RequestParam("filters") int[] filters, @RequestParam("date") String date,
                                         @RequestParam("bookerEmail") String bookerEmail, @PathVariable("page") int page,
                                         @PathVariable("size") int size) {
        List<AdminBookingHistoryModel> adminBookingHistoryModelList = bookingService.bookingRecords(bookerEmail, page, size, filters, date);
        return ResultVo.ok("OK!", adminBookingHistoryModelList);
    }

    @GetMapping("/admin/history/count/")
    public ResultVo countAllHistory(@RequestParam("filters") int[] filters, @RequestParam("date") String date,
                                    @RequestParam("bookerEmail") String bookerEmail) {
        if (filters == null || filters.length == 0) {
            filters = new int[]{-1, 0, 1};
        }
        int countHistory = bookingService.countHistoryAll(bookerEmail, filters, date);
        if (countHistory == 0) {
            return ResultVo.error("No booking history!");
        }
        return ResultVo.ok("OK!", countHistory);
    }

    @PostMapping("/history/edit")
    public ResultVo editHistory(@RequestBody BookingRecord bookingRecord) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("access-token");
        Object data = authClient.verifyToken(token).getData();
        UserInfo userInfo = JSON.parseObject(JSON.toJSONString(data), UserInfo.class);
        if (userInfo.getEmail() != bookingRecord.getBookerEmail() && userInfo.getRoleID() == 1) {
            return ResultVo.error("Not authorized!");
        }

        if (bookingRecord == null) {
            return ResultVo.error("No post data!");
        }
        int i = bookingService.updateBookingRecord(bookingRecord);
        if (i == BookingService.DELETE_FAILED) {
            return ResultVo.error("Cannot find the record!");
        }
        if (i == BookingService.INSERT_FAILED) {
            return ResultVo.error("The time is not available! Please change!");
        }
        if (i == 0) {
            return ResultVo.error("Backend Error! Please contact the administrator!");
        }
        return ResultVo.ok("OK");
    }

    @GetMapping("/history/{recordId}/cancel")
    public ResultVo cancelRecord(@PathVariable("recordId") int recordId) {
        int update = bookingService.updateStatus(-1, recordId);
        if (update != 1) {
            return ResultVo.error("Cancel Error! Please try again later!");
        }
        return ResultVo.ok("OK!");
    }

    @GetMapping("/history/{recordId}/complete")
    public ResultVo completeRecord(@PathVariable("recordId") int recordId) {
        int update = bookingService.updateStatus(1, recordId);
        if (update != 1) {
            return ResultVo.error("Complete Error! Please try again later!");
        }
        return ResultVo.ok("OK!");
    }

    @GetMapping("/calendar/events")
    public ResultVo getCalendarEvents(@RequestParam("startTime") String startTime,
                                      @RequestParam("endTime") String endTime) {
        Date parsedStartTime = DateTime.parse(startTime).toDate();
        Date parsedEndTime = DateTime.parse(endTime).toDate();
        List<CalendarEventsModel> calendarEvents = bookingService.getCalendarEvents(parsedStartTime, parsedEndTime);
        return calendarEvents.size() > 0 ? ResultVo.ok("OK", calendarEvents) : ResultVo.error("Failed!");

    }

    @PutMapping("/history/autoComplete/{bookerEmail}")
    public ResultVo autoComplete(@PathVariable("bookerEmail") String bookerEmail) {
        int count = bookingService.autoComplete(bookerEmail);
        return count == 0 ? ResultVo.error("No Records To Be Completed!") : ResultVo.ok("Auto Complete Success!");
    }

    @PutMapping("/admin/history/autoComplete")
    public ResultVo autoCompleteAll() {
        int count = bookingService.autoCompleteAll();
        return count == 0 ? ResultVo.error("No Records To Be Completed!") : ResultVo.ok("Auto Complete Success!");
    }

    @GetMapping("/advance")
    public ResultVo advanceBookingSearch(@RequestParam("startTime") String startTime,
                                         @RequestParam("endTime") String endTime,
                                         @RequestParam("capacity") int capacity,
                                         @RequestParam("airConditioner") int airConditioner,
                                         @RequestParam("projection") int projection) {
        List<RoomOverview> roomOverviews = bookingService.advanceBooking(startTime, endTime, capacity, airConditioner, projection);
        return ResultVo.ok("OK!", roomOverviews);
    }

    @PostMapping("/notify")
    public ResultVo notifyAttenders(@RequestParam("recordId") int recordId,
                                    @RequestParam("type") String type,
                                    @RequestParam("startTime") String startTime,
                                    @RequestParam("endTime") String endTime,
                                    @RequestParam("roomName") String roomName,
                                    @RequestParam("roomAddress") String address,
                                    @RequestParam("subject") String subject) {
        int code = bookingService.notifyAttenders(recordId, type, startTime, endTime, roomName, address,subject);
        return code == 0 ? ResultVo.error("Emailing Failed!") : ResultVo.ok("Email Sent!");
    }

    @PostMapping("/notify/update")
    public ResultVo notifyAttendersUpdate(@RequestParam("recordId") int recordId,
                                          @RequestParam("newStartTime") String newStartTime,
                                          @RequestParam("newEndTime") String newEndTime,
                                          @RequestParam("oldStartTime") String oldStartTime,
                                          @RequestParam("oldEndTime") String oldEndTime,
                                          @RequestParam("oldRoomName") String oldRoomName,
                                          @RequestParam("oldRoomAddress") String oldRoomAddress,
                                          @RequestParam("newRoomName") String newRoomName,
                                          @RequestParam("newRoomAddress") String newRoomAddress,
                                          @RequestParam("subject") String subject) {
        int code = bookingService.notifyAttendersUpdate(recordId, oldStartTime,newStartTime,oldEndTime,newEndTime,oldRoomName,newRoomName,oldRoomAddress,newRoomAddress,subject);
        return code == 0 ? ResultVo.error("Emailing Failed!") : ResultVo.ok("Email Sent!");
    }
}
