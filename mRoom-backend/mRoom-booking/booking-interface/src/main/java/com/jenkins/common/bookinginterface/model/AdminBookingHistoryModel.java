package com.jenkins.common.bookinginterface.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JenkinsZhang
 * @date 2020/4/19
 */
public class AdminBookingHistoryModel implements Serializable {

    private int recordId;
    private Date startTime;
    private Date endTime;
    private Date creationTime;
    private int status;
    private String roomName;
    private String roomAddress;
    private String bookerEmail;

    public String getRoomAddress() {
        return roomAddress;
    }

    @Override
    public String toString() {
        return "AdminBookingHistoryModel{" +
                "recordId=" + recordId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", creationTime=" + creationTime +
                ", status=" + status +
                ", roomName='" + roomName + '\'' +
                ", roomAddress='" + roomAddress + '\'' +
                ", bookerEmail='" + bookerEmail + '\'' +
                '}';
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

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

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getBookerEmail() {
        return bookerEmail;
    }

    public void setBookerEmail(String bookerEmail) {
        this.bookerEmail = bookerEmail;
    }

}
