package com.y.uc.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.y.uc.config.RedisConfig;
import com.y.uc.config.beanself.BeanSelfAware;
import com.y.uc.dao.UserDao;
import com.y.uc.domain.User;
import com.y.uc.dto.ChangePasswordParam;
import com.y.uc.dto.LoginParam;
import com.y.uc.dto.Principal;
import com.y.uc.dto.Register;
import com.y.uc.dto.SimpleUser;
import com.y.uc.exception.EmailAlreadyExistsException;
import com.y.uc.exception.EncryptionPasswordException;
import com.y.uc.exception.ExceedsAuthorizedAccessException;
import com.y.uc.exception.PasswordNotMatchException;
import com.y.uc.exception.UnLoginException;
import com.y.uc.exception.UserNotExistsException;
import com.y.uc.service.UserService;
import com.y.uc.util.PrincipalUtil;
import com.y.uc.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

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
        Preconditions.checkArgument(!Strings.isNullOrEmpty(email), "参数 `email` 不能为空！");
        User user = userDao.getByEmail(email);
        return new SimpleUser(user);
    }

    @Override
    public SimpleUser getByToken(String token) {
        if (Strings.isNullOrEmpty(token))
            return null;
        User user = userDao.getByToken(token);
        return user == null ? null : new SimpleUser(user);
    }

    @Override
    public SimpleUser register(Register register)
            throws EmailAlreadyExistsException, EncryptionPasswordException {
        Preconditions.checkArgument(register != null
                        && !Strings.isNullOrEmpty(register.getEmail())
                        && !Strings.isNullOrEmpty(register.getName())
                        && !Strings.isNullOrEmpty(register.getPassword())
                , "注册信息不完整！");
        User user = userDao.getByEmail(register.getEmail());
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
        user = new User.Builder().name(register.getName()).email(register.getEmail())
                .password(password)
                .token(UUID.randomUUID().toString())
                .build();
        userDao.save(user);
        return selfProxy.get(user.getId());
    }

    @Override
    public SimpleUser login(LoginParam loginParam)
            throws EncryptionPasswordException, UserNotExistsException, PasswordNotMatchException {
        Preconditions.checkArgument(loginParam != null
                        && !Strings.isNullOrEmpty(loginParam.getEmail())
                        && !Strings.isNullOrEmpty(loginParam.getPassword())
                , "登录信息不完整！");
        String pwd;
        try {
            pwd = SecurityUtil.encryption(loginParam.getPassword());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("密码加密失败，失败信息：{}", Throwables.getStackTraceAsString(e));
            throw new EncryptionPasswordException("密码加密失败！");
        }
        User user = userDao.getByEmail(loginParam.getEmail());
        if (user == null) {
            throw new UserNotExistsException("邮箱不存在！");
        }
        if (!user.getPassword().equals(pwd)) {
            throw new PasswordNotMatchException("密码不正确！");
        }
        return new SimpleUser(user);
    }

    @Override
    public void changePassword(ChangePasswordParam param)
            throws ExceedsAuthorizedAccessException, UserNotExistsException
            , PasswordNotMatchException, EncryptionPasswordException, UnLoginException {
        Preconditions.checkArgument(param != null
                        && !Strings.isNullOrEmpty(param.getOldPassword())
                        && !Strings.isNullOrEmpty(param.getNewPassword())
                , "参数不完整！");
        User user = userDao.get(param.getId());
        if (user == null) {
            throw new UserNotExistsException("用户不存在！");
        }
        Principal principal = PrincipalUtil.getPrincipal();
        if (principal == null){
            throw new UnLoginException("请先登录！");
        }
        if (principal.getId() != param.getId()) {
            LOGGER.info("越权修改他人密码，操作人 id：{}，被修改人 id：{}", principal.getId(), param.getId());
            throw new ExceedsAuthorizedAccessException("无权修改他人密码!");
        }
        String oldPwd;
        String newPwd;
        try {
            oldPwd = SecurityUtil.encryption(param.getOldPassword());
            newPwd = SecurityUtil.encryption(param.getNewPassword());
        } catch (UnsupportedEncodingException e) {
            throw new EncryptionPasswordException("密码加密失败！");
        }
        if (!user.getPassword().equals(oldPwd)) {
            throw new PasswordNotMatchException("旧密码错误！");
        }
        userDao.updatePassword(param.getId(), newPwd);
    }

    @Override
    public void setSelf(Object proxyBean) {
        this.selfProxy = (UserService) proxyBean;
    }
}
