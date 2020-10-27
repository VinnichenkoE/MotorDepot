package com.vinnichenko.motorDepot.entity;

import java.io.Serializable;

public class UserSessionData implements Serializable {
    int id;
    String name;
    User.Role role;

    public UserSessionData() {
    }

    public UserSessionData(int id, String name, User.Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}
