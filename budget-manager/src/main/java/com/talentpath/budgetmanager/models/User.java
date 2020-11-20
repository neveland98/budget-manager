package com.talentpath.budgetmanager.models;

public class User {
    Integer userId;
    String username;
    String password;//todo: figure out what to do with this to make it secure

    public User() {}
    public User(User toCopy) {
        this.userId = toCopy.userId;
        this.username = toCopy.username;
        this.password = toCopy.password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
