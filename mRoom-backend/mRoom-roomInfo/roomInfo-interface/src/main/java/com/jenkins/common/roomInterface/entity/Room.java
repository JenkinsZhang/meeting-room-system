package com.jenkins.common.roomInterface.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author JenkinsZhang
 * @date 2020/3/11
 * 
 */
public class Room implements Serializable {

    private int roomId;
    private String roomName;
    private String address;
    private int maxPeople;
    private int projection;
    private int airConditioner;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public int getProjection() {
        return projection;
    }

    public void setProjection(int projection) {
        this.projection = projection;
    }

    public int getAirConditioner() {
        return airConditioner;
    }

    public void setAirConditioner(int airConditioner) {
        this.airConditioner = airConditioner;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", address='" + address + '\'' +
                ", maxPeople=" + maxPeople +
                ", projection=" + projection +
                ", airConditioner=" + airConditioner +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
