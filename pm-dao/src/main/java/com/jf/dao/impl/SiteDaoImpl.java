package com.jf.dao.impl;

import com.jf.dao.SiteDao;
import com.jf.dao.impl.base.BaseDaoImpl;
import com.jf.entity.Site;

import javax.persistence.EntityManager;

public class SiteDaoImpl extends BaseDaoImpl<Site> implements SiteDao {
    public SiteDaoImpl( EntityManager entityManager) {
        super(Site.class,entityManager);
    }
}
