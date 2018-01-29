package com.jf.entity;

import com.jf.constant.EnumConstant;
import com.jf.entity.base.BaseEntity;

import javax.persistence.*;

@Table
@Entity(name = "user")
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 30)
    private String username;
    @Column(nullable = false, length = 64)
    private String password;
    /**
     * 头像
     */
    @Column
    private String img;
    /**
     * 性别
     */
    @Column(length = 2)
    private EnumConstant.UserSex sex;
    /**
     * 年龄
     */
    @Column(length = 4)
    private Integer age;
    /**
     * 备注，个性签名
     */
    @Column(length = 100)
    private String remark;
    /**
     * 在线状态
     */
    @Enumerated
    private EnumConstant.ServerStatus status = EnumConstant.ServerStatus.DISABLE;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public EnumConstant.UserSex getSex() {
        return sex;
    }

    public void setSex(EnumConstant.UserSex sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public EnumConstant.ServerStatus getStatus() {
        return status;
    }

    public void setStatus(EnumConstant.ServerStatus status) {
        this.status = status;
    }
}
