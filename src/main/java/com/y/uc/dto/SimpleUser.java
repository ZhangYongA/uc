package com.y.uc.dto;

import com.y.uc.domain.User;
import lombok.Data;

import java.util.Date;

/**
 * Created by zhangyong on 2017/7/20.
 */
@Data
public class SimpleUser {

    private int id;
    private String name;
    private String email;
    private String token;
    private Date registerTime;

    public SimpleUser() {
    }

    public SimpleUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.registerTime = user.getCreateTime();
        this.token = user.getToken();
    }

}
