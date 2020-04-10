package com.jenkins.common.roomService.service;

import com.jenkins.common.roomInterface.entity.Room;
import com.jenkins.common.roomInterface.model.RoomOverview;
import com.jenkins.common.roomInterface.model.RoomResources;
import com.jenkins.common.roomService.mapper.RoomMapper;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author JenkinsZhang
 * @date 2020/3/12
 *
 */

@Service
public class RoomService {


    private RoomMapper roomMapper;
    private RedisTemplate redisTemplate;

    @Autowired
    public RoomService(RoomMapper roomMapper,RedisTemplate redisTemplate) {
        this.roomMapper = roomMapper;
        this.redisTemplate = redisTemplate;
    }

    public List<Room> getRooms(){
        return roomMapper.selectAllRooms();
    }

    public String getRoomName(int roomId)
    {
        return roomMapper.getRoomNameById(roomId);
    }

    public Room getRoomDetail(int roomId) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Room roomDetail = (Room)valueOperations.get("room_detail_" + roomId);;
        System.out.println("Redis中的记录:" + roomDetail);
        if(roomDetail == null)
        {
            Room roomDetailById = roomMapper.getRoomDetailById(roomId);
            System.out.println("数据库查出来的记录：" +roomDetailById);
            if(roomDetailById == null)
            {
                return null;
            }
            else {
                valueOperations.set("room_detail_"+roomId,roomDetailById);
                return roomDetailById;
            }
        }else
        {
            return roomDetail;
        }
    }

    public List<RoomResources> getRoomResources(){
        List<Room> rooms = roomMapper.selectAllRooms();
        List<RoomResources>  roomResources = new ArrayList<>();
        for (Room room : rooms) {
            RoomResources  roomResource = new RoomResources();
            roomResource.setId(room.getRoomId());
            roomResource.setTitle(room.getRoomName());
            roomResource.setCapacity(room.getMaxPeople());
            roomResources.add(roomResource);

        }
        return roomResources;
    }

    public List<RoomOverview> getRoomOverview(){
        List<Room> rooms = roomMapper.selectAllRoomDetails();
        List<RoomOverview> roomOverviews = new ArrayList<>();
        for (Room room : rooms) {
            RoomOverview roomOverview = new RoomOverview();
            roomOverview.setRoomId(room.getRoomId());
            roomOverview.setAddress(room.getAddress());
            roomOverview.setAirConditioner(room.getAirConditioner());
            roomOverview.setProjection(room.getProjection());
            roomOverview.setCapacity(room.getMaxPeople());
            roomOverview.setRoomName(room.getRoomName());
            roomOverview.setImageURL(room.getImageUrl());
            roomOverviews.add(roomOverview);
        }
        return roomOverviews;

    }

    public int addRoom(MultipartFile file,Room room)
    {
        String roomName = room.getRoomName();
        if(roomName == null)
        {
            return 0;
        }
        String storePath = System.getProperty("user.dir") +
                File.separator + "src"+File.separator+"main" +
                File.separator + "resources" + File.separator + "static";

        return 1;
    }
}
