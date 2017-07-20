package com.y.uc.service.impl;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.y.uc.config.RedisConfig;
import com.y.uc.config.beanself.BeanSelfAware;
import com.y.uc.dao.UserDao;
import com.y.uc.domain.User;
import com.y.uc.dto.Register;
import com.y.uc.dto.SimpleUser;
import com.y.uc.exception.EmailAlreadyExistsException;
import com.y.uc.exception.EncryptionPasswordException;
import com.y.uc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

import static com.y.uc.util.SecurityUtil.encryption;

/**
 * Created by zhangyong on 2017/7/18.
 */
@Service
public class UserServiceImpl implements UserService, BeanSelfAware {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    private UserService selfProxy;

    @Override
    @Cacheable(value = RedisConfig.USER_NAMESPACE, key = "#id.toString()")
    public SimpleUser get(int id) {
        User user = userDao.get(id);
        if (user == null) {
            return null;
        }
        return new SimpleUser(user);
    }

    @Override
    @Cacheable(value = RedisConfig.USER_NAMESPACE, key = "#email")
    public SimpleUser get(String email) {
        if (Strings.isNullOrEmpty(email))
            throw new IllegalArgumentException("参数 `email` 不能为空！");
        User user = userDao.get(email);
        return new SimpleUser(user);
    }

    @Override
    public SimpleUser register(Register register) throws EmailAlreadyExistsException, EncryptionPasswordException {
        if (register == null || Strings.isNullOrEmpty(register.getEmail())
                || Strings.isNullOrEmpty(register.getName())
                || Strings.isNullOrEmpty(register.getPassword())) {
            throw new IllegalArgumentException("注册信息不完整！");
        }
        User user = userDao.get(register.getEmail());
        if (user != null) {
            throw new EmailAlreadyExistsException();
        }
        String password;
        try {
            password = encryption(register.getPassword());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("密码加密失败，失败信息：{}", Throwables.getStackTraceAsString(e));
            throw new EncryptionPasswordException("密码加密失败！");
        }
        user = new User.Builder().name(register.getName()).email(register.getEmail()).password(password).build();
        userDao.save(user);
        return selfProxy.get(user.getId());
    }

    @Override
    public void setSelf(Object proxyBean) {
        this.selfProxy = (UserService) proxyBean;
    }
}
