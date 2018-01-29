package com.jf.dao.impl;

import com.jf.dao.ProjectDao;
import com.jf.dao.impl.base.BaseDaoImpl;
import com.jf.entity.Project;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


public class ProjectDaoImpl extends BaseDaoImpl<Project> implements ProjectDao {

    public ProjectDaoImpl(EntityManager entityManager) {
        super(Project.class, entityManager);
    }
}
