package com.y.uc.constant;

/**
 * Created by zhangyong on 2017/7/18.
 */
public enum ExceptionCode {

    SUCCESS(0, "OK"),
    ILLEGAL_ARGUMENTS(1000002, "非法参数！"),
    EMAIL_ALREADY_EXISTS(1000003, "邮箱已注册！"),
    USER_NOT_EXISTS(1000004, "用户不存在！"),
    PASSWORD_ERROR(1000005, "密码不正确！"),
    VIRES_CHANGE_PASSWORD(1000006, "无权修改别人密码！"),
    UN_LOGIN(1000007, "请先登录！"),
    SERVER_ERROR(9999999, "服务器内部错误，请联系管理员！");

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
