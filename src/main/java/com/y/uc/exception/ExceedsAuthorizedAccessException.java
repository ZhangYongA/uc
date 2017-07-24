package com.y.uc.exception;

/**
 * Created by zhangyong on 2017/7/22.
 */
public class ExceedsAuthorizedAccessException extends ServiceException {

    public ExceedsAuthorizedAccessException() {
    }

    public ExceedsAuthorizedAccessException(String message) {
        super(message);
    }

}
