package com.jf.entity;

import com.jf.constant.EnumConstant;
import com.jf.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
@Table
@Entity(name = "site")
public class Site extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 接口名
     */
    @Column(nullable = false, length = 30)
    private String name;
    /**
     * 服务状态
     */
    @Enumerated
    private EnumConstant.ServerStatus status = EnumConstant.ServerStatus.ENABLE;
    /**
     *排序
     */
    @Column(nullable = false,length = 2)
    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumConstant.ServerStatus getStatus() {
        return status;
    }

    public void setStatus(EnumConstant.ServerStatus status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
