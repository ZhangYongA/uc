package com.y.uc.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.y.uc.constant.ExceptionCode;
import com.y.uc.model.annotation.Login;
import com.y.uc.util.SpringUtil;
import com.y.uc.vo.Response;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static com.y.uc.constant.ExceptionCode.UN_LOGIN;
import static com.y.uc.util.AuthUtil.isLogin;

/**
 * 登录权限验证
 * Created by zhangyong on 2017/7/25.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Object bean = handlerMethod.getBean();
            Method method = handlerMethod.getMethod();
            Annotation classAnnotation = bean.getClass().getAnnotation(Login.class);
            Annotation methodAnnotation = method.getAnnotation(Login.class);
            if (classAnnotation != null || methodAnnotation != null) {
                if (!isLogin()) {
                    Response rsp = Response.error(UN_LOGIN.getCode(), UN_LOGIN.getMsg());
                    response.setHeader("content-type", "application/json;charset=utf-8");
                    OutputStream outputStream = response.getOutputStream();
                    ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
                    outputStream.write(objectMapper.writeValueAsBytes(rsp));
                    return false;
                }
            }
        }
        return true;
    }
}
