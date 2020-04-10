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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

    @Test
    public void testAddRoom() throws IOException {
        File directory = new File("");
        String canonicalPath = directory.getCanonicalPath();
        System.out.println(canonicalPath);
        System.out.println(System.getProperty("user.dir"));

        String projectPath = System.getProperty("user.dir") + File.separator + "src"+File.separator+"main" + File.separator + "resources" + File.separator + "static";
        System.out.println(projectPath);
    }

}
