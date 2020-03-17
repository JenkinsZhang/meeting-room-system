package com.jenkins.common.roomService;

import com.google.inject.internal.cglib.core.$LocalVariablesSorter;
import com.jenkins.common.roomInterface.entity.Room;
import com.jenkins.common.roomService.service.RoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author JenkinsZhang
 * @date 2020/3/15
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Test
    public void test()
    {
        Room roomDetail = roomService.getRoomDetail(2);
        System.out.println(roomDetail);
    }
}
