package com.jf;

import com.jf.base.BaseService;
import com.jf.dao.ProjectDao;
import com.jf.dao.base.BaseDao;
import com.jf.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends BaseService<Project> {
    @Autowired
    protected ProjectDao projectDao;

    @Override
    protected BaseDao<Project> getDao() {
        return projectDao;
    }
}
