package com.jenkins.common.bookinginterface.entity;

import java.io.Serializable;
import java.util.Date;

public class BookingRecord implements Serializable {

    private int record_id;
    private int room_id;
    private Date start_time;
    private Date end_time;
    private Date creation_time;
    private String booker_email;
    private int status;

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public Date getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Date creation_time) {
        this.creation_time = creation_time;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
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



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBooker_email() {
        return booker_email;
    }

    public void setBooker_email(String booker_email) {
        this.booker_email = booker_email;
    }

    @Override
    public String toString() {
        return "BookingRecord{" +
                "record_id=" + record_id +
                ", room_id=" + room_id +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", creation_time=" + creation_time +
                ", booker_email='" + booker_email + '\'' +
                ", status=" + status +
                '}';
    }
}
