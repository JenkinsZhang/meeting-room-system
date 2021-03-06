package com.jenkins.common.roomService.mapper;


import com.jenkins.common.roomInterface.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    List<Room> selectAllRoomDetails();

    List<Room> selectRoomDetails();

    int updateRoomSelective(@Param("room") Room room);

    int insertRoom(@Param("room") Room room);

    int deleteRoomById(int roomId);
}
