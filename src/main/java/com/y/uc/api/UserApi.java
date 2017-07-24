package com.y.uc.api;

import com.y.uc.constant.ExceptionCode;
import com.y.uc.dto.ChangePasswordParam;
import com.y.uc.dto.SimpleUser;
import com.y.uc.exception.EncryptionPasswordException;
import com.y.uc.exception.ExceedsAuthorizedAccessException;
import com.y.uc.exception.PasswordNotMatchException;
import com.y.uc.exception.UnLoginException;
import com.y.uc.exception.UserNotExistsException;
import com.y.uc.service.UserService;
import com.y.uc.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhangyong on 2017/7/18.
 */
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

    @PostMapping("/user/password")
    public Response<?> changePassword(@RequestBody ChangePasswordParam param) {
        try {
            userService.changePassword(param);
        } catch (ExceedsAuthorizedAccessException e) {
            return Response.error(ExceptionCode.VIRES_CHANGE_PASSWORD);
        } catch (UserNotExistsException e) {
            return Response.error(ExceptionCode.USER_NOT_EXISTS);
        } catch (PasswordNotMatchException e) {
            return Response.error(ExceptionCode.PASSWORD_NOT_MATCH);
        } catch (EncryptionPasswordException e) {
            return Response.error(ExceptionCode.SERVER_ERROR);
        } catch (UnLoginException e) {
            return Response.error(ExceptionCode.UN_LOGIN);
        }
        return Response.ok();
    }
}
