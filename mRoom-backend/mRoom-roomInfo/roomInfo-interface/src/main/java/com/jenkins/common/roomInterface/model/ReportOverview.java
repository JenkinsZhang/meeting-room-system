package com.jenkins.common.roomInterface.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JenkinsZhang
 * @date 2020/4/16
 */

public class ReportOverview implements Serializable {

    private int reportId;
    private int roomId;
    private String roomName;
    private String reporter;
    private String address;
    private String detail;
    private String status;
    private Date createTime;
    private Date completeTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    @Override
    public String toString() {
        return "ReportOverview{" +
                "reportId=" + reportId +
                ", roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", reporter='" + reporter + '\'' +
                ", address='" + address + '\'' +
                ", detail='" + detail + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", completeTime=" + completeTime +
                '}';
    }
}
