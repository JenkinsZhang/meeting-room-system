package com.jenkins.common.bookingservice.mapper;

import com.jenkins.common.roomInterface.entity.Room;
import com.jenkins.common.userinterface.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/4/21
 */

@Mapper
@Component
public interface TestMapper {

    List<User> getAllUsers();

    List<Room> getAllRooms();

}
