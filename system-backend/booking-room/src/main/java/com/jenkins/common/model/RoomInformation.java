package com.jenkins.common.model;

import java.io.Serializable;
import java.util.Date;

public class RoomInformation implements Serializable {

    private int id;
    private Date start_time;
    private Date end_time;
    private int booker;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getBooker() {
        return booker;
    }

    public void setBooker(int booker) {
        this.booker = booker;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "RoomInformation{" +
                "id=" + id +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", booker=" + booker +
                ", status=" + status +
                '}';
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
