package com.jf.dao;

import com.jf.dao.base.BaseDao;
import com.jf.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseDao<User> {
}
