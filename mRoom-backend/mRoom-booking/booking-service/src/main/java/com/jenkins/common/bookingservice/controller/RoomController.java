package com.jenkins.common.bookingservice.controller;


import com.jenkins.common.bookingservice.mapper.BookingRecordMapper;
import com.jenkins.common.components.model.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

    private  BookingRecordMapper bookingRecordMapper;

    @Autowired
    public RoomController(BookingRecordMapper bookingRecordMapper) {
        this.bookingRecordMapper = bookingRecordMapper;
    }

    @GetMapping("/room_information/{room_id}")
    public ResultVo getRoomInformationById(@PathVariable(name = "room_id") int room_id) {
        return ResultVo.ok("success", bookingRecordMapper.selectRoomInformationById(room_id));
    }

}
