package com.y.uc.constant;

/**
 * Created by zhangyong on 2017/7/18.
 */
public enum  ExceptionCode {

    SUCCESS(0, "OK"),
    LOGIN_EXCEPTION(1000001, "用户名或密码不正确！"),
    MISS_REQUEST_PARAM(1000002, "请求参数不完整！"),
    EMAIL_ALREADY_EXISTS(1000003, "邮箱已注册！"),
    SERVER_ERROR(999999, "服务器内部错误，请联系管理员！");

    private int code;
    private String msg;

    ExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
