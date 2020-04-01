package com.jenkins.common.roomService.controller;

import com.jenkins.common.components.model.ResultVo;
import com.jenkins.common.roomInterface.entity.Room;
import com.jenkins.common.roomInterface.model.RoomOverview;
import com.jenkins.common.roomInterface.model.RoomResources;
import com.jenkins.common.roomService.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{roomId}")
    public ResultVo getRoomName(@PathVariable("roomId") int roomId)
    {
        String roomName = roomService.getRoomName(roomId);
        return ResultVo.ok("OK!",roomName);
    }

    @GetMapping("/detail/{roomId}")
    public ResultVo getRoomDetail(@PathVariable("roomId") int roomId){
        Room roomDetail = roomService.getRoomDetail(roomId);
        return roomDetail == null ? ResultVo.error("No room information!") : ResultVo.ok("OK",roomDetail);
    }

    @GetMapping("/roomResources")
    public ResultVo getRoomResources()
    {
        List<RoomResources> roomResources = roomService.getRoomResources();
        return roomResources.size() > 0 ? ResultVo.ok("OK",roomResources) : ResultVo.error("No room information");
    }

    @GetMapping("/roomOverview")
    public ResultVo getRoomOverview()
    {
        List<RoomOverview> roomOverview = roomService.getRoomOverview();
        return roomOverview.size() > 0 ? ResultVo.ok("OK",roomOverview) : ResultVo.error("No room information");
    }
}
