package com.jenkins.common.roomInterface.entity;

import java.io.Serializable;

/**
 * @author JenkinsZhang
 * @date 2020/3/11
 * 
 */
public class Room implements Serializable {

    private int room_id;
    private String room_name;
    private String address;
    private int max_people;
    private int projection;
    private int air_conditioner;
    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMax_people() {
        return max_people;
    }

    public void setMax_people(int max_people) {
        this.max_people = max_people;
    }

    public int getProjection() {
        return projection;
    }

    public void setProjection(int projection) {
        this.projection = projection;
    }

    public int getAir_conditioner() {
        return air_conditioner;
    }

    public void setAir_conditioner(int air_conditioner) {
        this.air_conditioner = air_conditioner;
    }

    @Override
    public String toString() {
        return "Room{" +
                "room_id=" + room_id +
                ", room_name='" + room_name + '\'' +
                ", address='" + address + '\'' +
                ", max_people=" + max_people +
                ", projection=" + projection +
                ", air_conditioner=" + air_conditioner +
                '}';
    }
}
