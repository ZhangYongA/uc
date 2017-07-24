package com.y.uc.api;

import com.google.common.base.Throwables;
import com.y.uc.constant.ExceptionCode;
import com.y.uc.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by zhangyong on 2017/7/18.
 */
@RestController
@ControllerAdvice
public class RestExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public Response illegalArgumentExceptionHandler(HttpServletRequest request, Exception e) throws Exception{
        LOGGER.error("---DefaultException Handler---Host {} invokes url {} ERROR: {}"
                , request.getRemoteHost(), request.getRequestURL(), Throwables.getStackTraceAsString(e));
        return Response.error(ExceptionCode.ILLEGAL_ARGUMENTS, e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response defaultHandler(HttpServletRequest request, Exception e) throws Exception {
        LOGGER.error("---DefaultException Handler---Host {} invokes url {} ERROR: {}"
                , request.getRemoteHost(), request.getRequestURL(), Throwables.getStackTraceAsString(e));
        return Response.error(ExceptionCode.SERVER_ERROR, e.getMessage());
    }

}
