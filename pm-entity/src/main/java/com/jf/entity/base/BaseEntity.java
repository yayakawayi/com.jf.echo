package com.jf.entity.base;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class BaseEntity extends SimpleEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    protected Date createTime;
    /**
     * 上次修改时间
     */
    protected Date lastUpdateTime;
    /**
     * 创建人Id
     */
    protected String createUserId;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
}
