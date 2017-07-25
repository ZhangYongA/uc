package com.y.uc.exception;

/**
 * Created by zhangyong on 2017/7/21.
 */
public class PasswordErrorException extends ServiceException {

    public PasswordErrorException() {
    }

    public PasswordErrorException(String message) {
        super(message);
    }
}
