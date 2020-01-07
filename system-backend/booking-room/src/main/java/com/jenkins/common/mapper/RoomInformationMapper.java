package com.jenkins.common.mapper;

import com.jenkins.common.model.RoomInformation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoomInformationMapper {

    public List<RoomInformation> selectRoomInformationById(@Param("id") int id);
}
