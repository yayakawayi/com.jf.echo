package com.jf;

import com.jf.base.BaseService;
import com.jf.dao.UserDao;
import com.jf.dao.base.BaseDao;
import com.jf.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User> {
    @Autowired
    private UserDao userDao;

    @Override
    protected BaseDao<User> getDao() {
        return userDao;
    }
}
