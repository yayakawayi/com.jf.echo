package com.jf;
import com.jf.base.BaseService;
import com.jf.dao.ServerDao;
import com.jf.dao.base.BaseDao;
import com.jf.entity.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerService extends BaseService<Server> {
    @Autowired
    private ServerDao serverDao;
    @Override
    protected BaseDao<Server> getDao() {
        return serverDao;
    }
}
