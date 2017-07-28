package com.y.uc.service;


import com.y.uc.exception.EmailAlreadyExistsException;
import com.y.uc.exception.EncryptionPasswordException;
import com.y.uc.exception.ExceedsAuthorizedAccessException;
import com.y.uc.exception.PasswordErrorException;
import com.y.uc.exception.UnLoginException;
import com.y.uc.exception.UserNotExistsException;
import com.y.uc.model.dto.ChangePasswordParam;
import com.y.uc.model.dto.LoginParam;
import com.y.uc.model.dto.Register;
import com.y.uc.model.dto.SimpleUser;

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
            throws EncryptionPasswordException, UserNotExistsException, PasswordErrorException;

    void changePassword(ChangePasswordParam param)
            throws ExceedsAuthorizedAccessException, UserNotExistsException, PasswordErrorException, EncryptionPasswordException, UnLoginException;

}
