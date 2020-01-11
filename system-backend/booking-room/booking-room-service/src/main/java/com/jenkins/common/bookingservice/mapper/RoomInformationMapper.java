package com.jenkins.common.bookingservice.mapper;

import com.jenkins.common.bookingservice.model.RoomInformation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoomInformationMapper {

    public List<RoomInformation> selectRoomInformationById(@Param("room_id") int room_id);
}
