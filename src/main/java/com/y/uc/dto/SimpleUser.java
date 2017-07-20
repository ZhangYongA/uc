package com.y.uc.dto;

import com.y.uc.domain.User;

import java.util.Date;

/**
 * Created by zhangyong on 2017/7/20.
 */
public class SimpleUser {

    private int id;
    private String name;
    private String email;
    private Date registerTime;

    public SimpleUser() {
    }

    public SimpleUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.registerTime = user.getCreateTime();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
}
