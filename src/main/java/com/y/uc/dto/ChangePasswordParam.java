package com.y.uc.dto;

import lombok.Data;

/**
 * Created by zhangyong on 2017/7/21.
 */
@Data
public class ChangePasswordParam {
    private int id;
    private String oldPassword;
    private String newPassword;
}