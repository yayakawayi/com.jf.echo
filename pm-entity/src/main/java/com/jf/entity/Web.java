package com.jf.entity;

import com.jf.entity.base.BaseEntity;

import javax.persistence.*;

/**
 * 视图表
 */
@Table
@Entity(name = "web")
public class Web extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Column(length = 30, nullable = false)
    private String name;
    /**
     * 备注
     */
    @Column(length = 100)
    private String remark;
    /**
     * s端口:tomcat 监听的关闭端口.
     */
    @Column(length = 10, nullable = false, unique = true)
    private String serverPort;
    /**
     * c端口:使用8080监听浏览器发送的请求；使用8009 接受其他服务器转发过来的请求
     */
    @Column(length = 10, nullable = false, unique = true)
    private String connectorPort;
    /**
     * 所属项目
     */
    @ManyToOne
    private Project project;

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

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getConnectorPort() {
        return connectorPort;
    }

    public void setConnectorPort(String connectorPort) {
        this.connectorPort = connectorPort;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
