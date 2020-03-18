package com.jenkins.common.roomService.mapper;


import com.jenkins.common.roomInterface.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jenkinszhang
 */
@Mapper
@Component
public interface RoomMapper {

    List<Room> selectAllRooms();

    String getRoomNameById(int roomId);

    Room getRoomDetailById(int roomId);

}
