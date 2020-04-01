package com.jenkins.common.roomInterface.model;

import java.io.Serializable;

/**
 * @author JenkinsZhang
 * @date 2020/3/29
 */
public class RoomResources  implements Serializable {

    private int id;
    private String title;
    private int capacity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "RoomResources{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
