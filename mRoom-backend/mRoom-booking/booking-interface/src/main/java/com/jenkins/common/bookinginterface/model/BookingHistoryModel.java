package com.jenkins.common.bookinginterface.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JenkinsZhang
 * @date 2020/3/14
 */


public class BookingHistoryModel implements Serializable {

    private int recordId;
    private Date startTime;
    private Date endTime;
    private Date creatTime;
    private int Status;
    private String roomName;
    private String roomAddress;
    private int maxPeople;
    private int projection;
    private int airConditioner;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomAddress() {
        return roomAddress;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
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
        return "BookingHistoryModel{" +
                "recordId=" + recordId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", creatTime=" + creatTime +
                ", Status=" + Status +
                ", roomName='" + roomName + '\'' +
                ", roomAddress='" + roomAddress + '\'' +
                ", maxPeople=" + maxPeople +
                ", projection=" + projection +
                ", airConditioner=" + airConditioner +
                '}';
    }
}
