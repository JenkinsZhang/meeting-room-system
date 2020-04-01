package com.jenkins.common.roomInterface.api;


import com.jenkins.common.components.model.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface RoomApi {

    @GetMapping("")
    public ResultVo getRooms();

    @GetMapping("/{roomId}")
    public ResultVo getRoomName(@PathVariable("roomId") int roomId);

    @GetMapping("/detail/{roomId}")
    public ResultVo getRoomDetail(@PathVariable("roomId") int roomId);

    @GetMapping("/roomOverview")
    public ResultVo getRoomOverview();

    @GetMapping("/roomResources")
    public ResultVo getRoomResources();
}
