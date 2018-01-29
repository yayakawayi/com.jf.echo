package com.jf.dao.impl;

import com.jf.dao.UserDao;
import com.jf.dao.impl.base.BaseDaoImpl;
import com.jf.entity.User;

import javax.persistence.EntityManager;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    public UserDaoImpl(EntityManager entityManager) {
        super(User.class, entityManager);
    }
}
