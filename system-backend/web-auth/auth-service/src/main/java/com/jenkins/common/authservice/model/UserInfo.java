package com.jenkins.common.authservice.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class UserInfo implements Serializable {

    private int id;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
