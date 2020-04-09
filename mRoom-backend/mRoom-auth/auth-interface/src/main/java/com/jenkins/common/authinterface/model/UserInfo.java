package com.jenkins.common.authinterface.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class UserInfo implements Serializable {

    private String email;

    private String username;

    private int roleID;

    private String roleName;

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

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", roleID=" + roleID +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
