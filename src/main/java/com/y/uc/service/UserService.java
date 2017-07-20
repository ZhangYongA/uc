package com.y.uc.service;

import com.y.uc.dto.Register;
import com.y.uc.dto.SimpleUser;
import com.y.uc.exception.EmailAlreadyExistsException;
import com.y.uc.exception.EncryptionPasswordException;

/**
 * Created by zhangyong on 2017/7/18.
 */
public interface UserService {

    SimpleUser get(int id);

    SimpleUser get(String email);

    SimpleUser register(Register register) throws EmailAlreadyExistsException, EncryptionPasswordException;

}
