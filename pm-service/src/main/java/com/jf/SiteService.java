package com.jf;

import com.jf.base.BaseService;
import com.jf.dao.SiteDao;
import com.jf.dao.base.BaseDao;
import com.jf.entity.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SiteService extends BaseService<Site> {
    @Autowired
    private SiteDao siteDao;
    @Override
    protected BaseDao<Site> getDao() {
        return siteDao;
    }


}
