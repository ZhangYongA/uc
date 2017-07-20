package com.y.uc.api;

import com.y.uc.constant.ExceptionCode;
import com.y.uc.domain.User;
import com.y.uc.dto.Register;
import com.y.uc.dto.SimpleUser;
import com.y.uc.exception.EmailAlreadyExistsException;
import com.y.uc.exception.EncryptionPasswordException;
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

    @PostMapping("/user")
    public Response<?> save(@RequestBody(required = false) Register register) {
        SimpleUser user;
        try {
            user = userService.register(register);
        } catch (EmailAlreadyExistsException e) {
            user = userService.get(register.getEmail());
            return Response.error(ExceptionCode.EMAIL_ALREADY_EXISTS, user);
        } catch (EncryptionPasswordException e) {
            return Response.error(ExceptionCode.SERVER_ERROR);
        }
        return Response.ok(user);
    }

}
