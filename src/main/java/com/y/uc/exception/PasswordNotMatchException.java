package com.y.uc.exception;

/**
 * Created by zhangyong on 2017/7/21.
 */
public class PasswordNotMatchException extends ServiceException {

    public PasswordNotMatchException() {
    }

    public PasswordNotMatchException(String message) {
        super(message);
    }
}
