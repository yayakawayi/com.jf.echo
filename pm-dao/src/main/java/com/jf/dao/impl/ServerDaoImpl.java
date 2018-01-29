package com.jf.dao.impl;

import com.jf.dao.ServerDao;
import com.jf.dao.impl.base.BaseDaoImpl;
import com.jf.entity.Server;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


public class ServerDaoImpl extends BaseDaoImpl<Server> implements ServerDao{
    public ServerDaoImpl( EntityManager entityManager) {
        super(Server.class, entityManager);
    }
}
