package com.jenkins.common.roomInterface.model;

import java.io.Serializable;

/**
 * @author JenkinsZhang
 * @date 2020/3/30
 */
public class RoomOverview implements Serializable {

    private int roomId;
    private String roomName;
    private String address;
    private int capacity;
    private int projection;
    private int airConditioner;
    private String imageURL;


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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "RoomOverview{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", address='" + address + '\'' +
                ", capacity=" + capacity +
                ", projection=" + projection +
                ", airConditioner=" + airConditioner +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
