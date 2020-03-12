package com.jenkins.common.roomService.controller;

import com.jenkins.common.components.model.ResultVo;
import com.jenkins.common.roomInterface.entity.Room;
import com.jenkins.common.roomService.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/3/12
 *
 */

@RestController
public class RoomController {

    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("")
    public ResultVo getRooms()
    {
        List<Room> rooms = roomService.getRooms();
        return ResultVo.ok("OK!",rooms);
    }
}
