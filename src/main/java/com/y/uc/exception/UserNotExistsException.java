package com.y.uc.exception;

/**
 * Created by zhangyong on 2017/7/21.
 */
public class UserNotExistsException extends ServiceException {

    public UserNotExistsException() {
    }

    public UserNotExistsException(String message) {
        super(message);
    }
}
