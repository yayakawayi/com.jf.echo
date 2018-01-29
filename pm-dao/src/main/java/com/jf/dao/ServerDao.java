package com.jf.dao;
import com.jf.dao.base.BaseDao;
import com.jf.entity.Server;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerDao extends BaseDao<Server> {

}
