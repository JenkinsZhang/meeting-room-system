package com.jenkins.common.bookinginterface.entity;

import java.io.Serializable;
import java.util.Date;

public class BookingRecord implements Serializable {

    private int recordId;
    private int roomId;
    private Date startTime;
    private Date endTime;
    private Date creationTime;
    private String bookerEmail;
    private int status;
    private String subject;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomDd) {
        this.roomId = roomDd;
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

    public String getBookerEmail() {
        return bookerEmail;
    }

    public void setBookerEmail(String bookerEmail) {
        this.bookerEmail = bookerEmail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "BookingRecord{" +
                "recordId=" + recordId +
                ", roomId=" + roomId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", creationTime=" + creationTime +
                ", bookerEmail='" + bookerEmail + '\'' +
                ", status=" + status +
                ", subject='" + subject + '\'' +
                '}';
    }
}
