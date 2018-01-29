package com.jf.dao.impl;

import com.jf.dao.WebDao;
import com.jf.dao.impl.base.BaseDaoImpl;
import com.jf.entity.Web;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


public class WebDaoImpl extends BaseDaoImpl<Web> implements WebDao {

    public WebDaoImpl(EntityManager entityManager) {
        super(Web.class, entityManager);
    }
}
