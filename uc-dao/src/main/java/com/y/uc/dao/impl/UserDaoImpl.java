package com.y.uc.dao.impl;

import com.google.common.collect.ImmutableMap;
import com.y.uc.dao.UserDao;
import com.y.uc.model.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangyong on 2017/7/18.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public User get(int id) {
        return sqlSession.selectOne("UserDao.getById", id);
    }

    @Override
    public User get(String email, String password) {
        ImmutableMap<String, Object> param = ImmutableMap.of("email", email, "password", password);
        return sqlSession.selectOne("UserDao.getByEmailAndPassword",param);
    }

    @Override
    public User getByEmail(String email) {
        return sqlSession.selectOne("UserDao.getByEmail", email);
    }

    @Override
    public User getByToken(String token) {
        return sqlSession.selectOne("UserDao.getByToken", token);
    }

    @Override
    public int save(User user) {
        return sqlSession.insert("UserDao.save", user);
    }

    @Override
    public int updatePassword(int id, String password) {
        ImmutableMap<String, Object> param = ImmutableMap.of("id", id, "password", password);
        return sqlSession.update("UserDao.updatePassword", param);
    }
}
