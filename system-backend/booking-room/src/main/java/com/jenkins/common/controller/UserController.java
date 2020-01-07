package com.jenkins.common.controller;


import com.jenkins.common.mapper.RoomInformationMapper;
import com.jenkins.common.mapper.UserMapper;
import com.jenkins.common.model.ResultVo;
import com.jenkins.common.model.RoomInformation;
import com.jenkins.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RoomInformationMapper roomInformationMapper;

    @GetMapping("/user/{id}")
    public ResultVo getUserById(@PathVariable(name = "id") int id) {
        return ResultVo.ok("success", userMapper.selectUserById(id));
    }

    @GetMapping("/room_information/{id}")
    public ResultVo getRoomInformationById(@PathVariable(name = "id") int id) {
        return ResultVo.ok("success", roomInformationMapper.selectRoomInformationById(id));
    }

}
