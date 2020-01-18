package com.jenkins.common.components.model;

import java.io.Serializable;
import java.util.Date;

public class RoomInformation implements Serializable {

    private int record_id;
    private Date start_time;
    private Date end_time;
    private int booker_id;
    private int room_id;
    private int status;

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public int getBooker_id() {
        return booker_id;
    }

    public void setBooker_id(int booker_id) {
        this.booker_id = booker_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RoomInformation{" +
                "record_id=" + record_id +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", booker_id=" + booker_id +
                ", room_id=" + room_id +
                ", status=" + status +
                '}';
    }
}
