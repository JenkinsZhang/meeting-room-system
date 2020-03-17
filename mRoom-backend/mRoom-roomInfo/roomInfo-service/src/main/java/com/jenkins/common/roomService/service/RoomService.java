package com.jenkins.common.roomService.service;

import com.jenkins.common.roomInterface.entity.Room;
import com.jenkins.common.roomService.mapper.RoomMapper;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
        Room roomDetail = (Room)valueOperations.get("room_detail_" + roomId);
        System.out.println("Redis中的记录:" + roomDetail);
        if(roomDetail == null)
        {
            int i = 0;
            Room roomDetailById = roomMapper.getRoomDetailById(roomId);
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
}
