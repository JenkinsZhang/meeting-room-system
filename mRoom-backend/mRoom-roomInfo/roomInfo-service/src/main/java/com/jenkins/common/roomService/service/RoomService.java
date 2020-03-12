package com.jenkins.common.roomService.service;

import com.jenkins.common.roomInterface.entity.Room;
import com.jenkins.common.roomService.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/3/12
 *
 */

@Service
public class RoomService {

    private RoomMapper roomMapper;

    @Autowired
    public RoomService(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    public List<Room> getRooms(){
        return roomMapper.selectAllRooms();
    }
}
