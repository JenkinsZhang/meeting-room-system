package com.jenkins.common.bookinginterface.entity;

import java.io.Serializable;

/**
 * @author JenkinsZhang
 * @date 29/04/2020
 */
public class Attender implements Serializable {

    private int id;
    private int recordId;
    private String attenders;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getAttenders() {
        return attenders;
    }

    public void setAttenders(String attenders) {
        this.attenders = attenders;
    }

    @Override
    public String toString() {
        return "Attender{" +
                "id=" + id +
                ", recordId=" + recordId +
                ", attenders='" + attenders + '\'' +
                '}';
    }
}
