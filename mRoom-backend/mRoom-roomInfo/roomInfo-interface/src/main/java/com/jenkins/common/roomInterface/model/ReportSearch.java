package com.jenkins.common.roomInterface.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JenkinsZhang
 * @date 2020/4/17
 */
public class ReportSearch implements Serializable {

    private Date startDate;
    private Date endDate;
    private String reporter;
    private int roomId;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "ReportSearch{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", reporter='" + reporter + '\'' +
                ", roomId=" + roomId +
                '}';
    }
}
