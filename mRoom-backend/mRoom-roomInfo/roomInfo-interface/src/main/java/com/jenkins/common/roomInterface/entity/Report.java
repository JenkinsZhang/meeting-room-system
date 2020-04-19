package com.jenkins.common.roomInterface.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JenkinsZhang
 * @date 2020/4/16
 */

public class Report implements Serializable {

    private int reportId;
    private int roomId;
    private String reporter;
    private String detail;
    private int status;
    private Date createTime;
    private Date completeTime;

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", roomId=" + roomId +
                ", reporter='" + reporter + '\'' +
                ", detail='" + detail + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", completeTime=" + completeTime +
                '}';
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
