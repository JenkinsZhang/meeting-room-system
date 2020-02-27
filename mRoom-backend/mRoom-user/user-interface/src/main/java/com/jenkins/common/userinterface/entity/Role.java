package com.jenkins.common.userinterface.entity;

import java.io.Serializable;

public class Role implements Serializable {

    private int id;
    private String role_name;
    private String role_nameZh;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRole_nameZh() {
        return role_nameZh;
    }

    public void setRole_nameZh(String role_nameZh) {
        this.role_nameZh = role_nameZh;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role_name='" + role_name + '\'' +
                ", role_nameZh='" + role_nameZh + '\'' +
                '}';
    }
}
