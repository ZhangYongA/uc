package com.y.uc.service;

import com.y.uc.dto.ChangePasswordParam;
import com.y.uc.dto.LoginParam;
import com.y.uc.dto.Register;
import com.y.uc.dto.SimpleUser;
import com.y.uc.exception.EmailAlreadyExistsException;
import com.y.uc.exception.EncryptionPasswordException;
import com.y.uc.exception.ExceedsAuthorizedAccessException;
import com.y.uc.exception.PasswordNotMatchException;
import com.y.uc.exception.UnLoginException;
import com.y.uc.exception.UserNotExistsException;

/**
 * Created by zhangyong on 2017/7/18.
 */
public interface UserService {

    SimpleUser get(int id);

    SimpleUser get(String email);

    SimpleUser getByToken(String token);

    SimpleUser register(Register register)
            throws EmailAlreadyExistsException, EncryptionPasswordException;

    SimpleUser login(LoginParam loginParam)
            throws EncryptionPasswordException, UserNotExistsException, PasswordNotMatchException;

    void changePassword(ChangePasswordParam param)
            throws ExceedsAuthorizedAccessException, UserNotExistsException, PasswordNotMatchException, EncryptionPasswordException, UnLoginException;

}
