package com.y.uc.vo;

import com.y.uc.constant.ExceptionCode;
import lombok.Data;

/**
 * Created by zhangyong on 2017/7/18.
 */
@Data
public class Response<T> {

    private Response() {
    }

    private int code;
    private String msg;
    private T data;

    public static <T> Response<T> ok() {
        return ok(null);
    }

    public static <T> Response<T> ok(T data) {
        Response<T> response = new Response<>();
        response.setCode(ExceptionCode.SUCCESS.getCode());
        response.setMsg(ExceptionCode.SUCCESS.getMsg());
        response.setData(data);
        return response;
    }

    public static <T> Response<T> error(int code, String msg, T data) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> error(int code, String msg) {
        return error(code, msg, null);
    }
}
