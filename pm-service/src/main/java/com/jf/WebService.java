package com.jf;

import com.jf.base.BaseService;
import com.jf.dao.WebDao;
import com.jf.dao.base.BaseDao;
import com.jf.entity.Web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebService extends BaseService<Web> {
    @Autowired
    protected WebDao webDao;

    @Override
    protected BaseDao getDao() {
        return webDao;
    }
}
