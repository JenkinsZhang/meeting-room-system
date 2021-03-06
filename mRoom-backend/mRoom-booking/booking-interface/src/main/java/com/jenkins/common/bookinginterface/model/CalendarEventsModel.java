package com.jenkins.common.bookinginterface.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JenkinsZhang
 * @date 2020/3/29
 */
public class CalendarEventsModel implements Serializable {

    private int id;
    private int resourceId;
    private long start;
    private long end;
    private String bookerEmail;
    private String subject;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getBookerEmail() {
        return bookerEmail;
    }

    public void setBookerEmail(String bookerEmail) {
        this.bookerEmail = bookerEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "CalendarEventsModel{" +
                "id=" + id +
                ", resourceId=" + resourceId +
                ", start=" + start +
                ", end=" + end +
                ", bookerEmail='" + bookerEmail + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
