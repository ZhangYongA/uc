package com.y.uc.api;


import com.y.uc.exception.EncryptionPasswordException;
import com.y.uc.exception.ExceedsAuthorizedAccessException;
import com.y.uc.exception.PasswordErrorException;
import com.y.uc.exception.UnLoginException;
import com.y.uc.exception.UserNotExistsException;
import com.y.uc.model.annotation.Login;
import com.y.uc.model.dto.ChangePasswordParam;
import com.y.uc.model.dto.SimpleUser;
import com.y.uc.service.UserService;
import com.y.uc.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.y.uc.constant.ExceptionCode.PASSWORD_ERROR;
import static com.y.uc.constant.ExceptionCode.SERVER_ERROR;
import static com.y.uc.constant.ExceptionCode.UN_LOGIN;
import static com.y.uc.constant.ExceptionCode.USER_NOT_EXISTS;
import static com.y.uc.constant.ExceptionCode.VIRES_CHANGE_PASSWORD;

/**
 * Created by zhangyong on 2017/7/18.
 */
@Login
@RestController
@RequestMapping("/users/")
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public Response<?> get(@PathVariable int id) {
        SimpleUser user = userService.get(id);
        return Response.ok(user);
    }

    @Login
    @PostMapping("/user/password")
    public Response<?> changePassword(@RequestBody ChangePasswordParam param) {
        try {
            userService.changePassword(param);
        } catch (ExceedsAuthorizedAccessException e) {
            return Response.error(VIRES_CHANGE_PASSWORD.getCode(), VIRES_CHANGE_PASSWORD.getMsg());
        } catch (UserNotExistsException e) {
            return Response.error(USER_NOT_EXISTS.getCode(), USER_NOT_EXISTS.getMsg());
        } catch (PasswordErrorException e) {
            return Response.error(PASSWORD_ERROR.getCode(), PASSWORD_ERROR.getMsg());
        } catch (EncryptionPasswordException e) {
            return Response.error(SERVER_ERROR.getCode(), SERVER_ERROR.getMsg());
        } catch (UnLoginException e) {
            return Response.error(UN_LOGIN.getCode(), UN_LOGIN.getMsg());
        }
        return Response.ok();
    }
}
