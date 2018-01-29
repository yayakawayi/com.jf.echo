package com.jf.entity;

import com.jf.constant.EnumConstant;
import com.jf.entity.base.BaseEntity;
import com.jf.entity.base.SimpleEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 服务表
 */
@Table
@Entity(name = "server")
public class Server extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(length = 30, nullable = false)
    private String name;
    /**
     * 备注
     */
    @Column(length = 100)
    private String remark;
    /**
     * 端口
     */
    @Column(length = 10, nullable = false, unique = true)
    private String port;
    /**
     * 所属项目
     */
    @ManyToOne
    private Project project;
    /**
     * 服务状态
     */
    @Enumerated
    private EnumConstant.ServerStatus status = EnumConstant.ServerStatus.ENABLE;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public EnumConstant.ServerStatus getStatus() {
        return status;
    }

    public void setStatus(EnumConstant.ServerStatus status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
