package com.y.uc.dao;

import com.y.uc.domain.User;

/**
 * Created by zhangyong on 2017/7/18.
 */
public interface UserDao {

    User get(int id);

    User get(String email, String password);

    User getByEmail(String email);

    User getByToken(String token);

    int save(User user);

    int updatePassword(int id, String password);

}
