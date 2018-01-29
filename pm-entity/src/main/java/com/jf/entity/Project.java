package com.jf.entity;

import com.jf.entity.base.BaseEntity;
import javax.persistence.*;
/**
 * 項目表
 */
@Entity
@Table(name = "project")
public class Project extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(length = 30,nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
