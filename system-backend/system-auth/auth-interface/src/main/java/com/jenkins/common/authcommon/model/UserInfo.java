package com.jenkins.common.authcommon.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class UserInfo implements Serializable {

    private String username;

    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
