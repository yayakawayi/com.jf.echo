package com.jf.dao;

import com.jf.dao.base.BaseDao;
import com.jf.entity.Project;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDao extends BaseDao<Project> {
}
