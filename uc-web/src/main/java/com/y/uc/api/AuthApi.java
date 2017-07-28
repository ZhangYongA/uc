package com.y.uc.api;

import com.y.uc.constant.Constant;
import com.y.uc.constant.ExceptionCode;
import com.y.uc.exception.EmailAlreadyExistsException;
import com.y.uc.exception.EncryptionPasswordException;
import com.y.uc.exception.PasswordErrorException;
import com.y.uc.exception.UserNotExistsException;
import com.y.uc.model.dto.LoginParam;
import com.y.uc.model.dto.Register;
import com.y.uc.model.dto.SimpleUser;
import com.y.uc.service.UserService;
import com.y.uc.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static com.y.uc.constant.ExceptionCode.EMAIL_ALREADY_EXISTS;
import static com.y.uc.constant.ExceptionCode.PASSWORD_ERROR;
import static com.y.uc.constant.ExceptionCode.SERVER_ERROR;
import static com.y.uc.constant.ExceptionCode.USER_NOT_EXISTS;

/**
 * Created by zhangyong on 2017/7/21.
 */
@RestController
public class AuthApi {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Response<?> save(@RequestBody(required = false) Register register) {
        SimpleUser user;
        try {
            user = userService.register(register);
        } catch (EmailAlreadyExistsException e) {
            user = userService.get(register.getEmail());
            return Response.error(EMAIL_ALREADY_EXISTS.getCode(), EMAIL_ALREADY_EXISTS.getMsg(), user);
        } catch (EncryptionPasswordException e) {
            return Response.error(SERVER_ERROR.getCode(), SERVER_ERROR.getMsg());
        }
        return Response.ok(user);
    }

    @PostMapping("/login")
    public Response<?> login(@RequestBody LoginParam loginParam, HttpServletResponse response) {
        SimpleUser user;
        try {
            user = userService.login(loginParam);
        } catch (EncryptionPasswordException e) {
            return Response.error(SERVER_ERROR.getCode(), SERVER_ERROR.getMsg());
        } catch (UserNotExistsException e) {
            return Response.error(USER_NOT_EXISTS.getCode(), USER_NOT_EXISTS.getMsg());
        } catch (PasswordErrorException e) {
            return Response.error(PASSWORD_ERROR.getCode(), PASSWORD_ERROR.getMsg());
        }
        Cookie cookie = new Cookie(Constant.Cookie.TOKEN, user.getToken());
        cookie.setDomain("localhost");
        cookie.setPath("/");
        response.addCookie(cookie);
        return Response.ok(user);
    }

}
