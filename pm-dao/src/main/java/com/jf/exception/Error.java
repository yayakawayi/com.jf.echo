package com.jf.exception;

import java.io.Serializable;

/**
 * 异常信息类
 *
 * @author fly
 */
public class Error implements Serializable {

    private String code;
    private String info;
    private String des;

    public Error() {
        super();
    }

    public Error(String code) {
        super();
        this.code = code;
    }

    public Error(String code, String info) {
        super();
        this.code = code;
        this.info = info;
    }

    public Error(String code, String info, String des) {
        super();
        this.code = code;
        this.info = info;
        this.des = des;
    }

    /**
     * 异常码
     */
    public String getCode() {
        return code;
    }

    /**
     * 异常码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 异常提示信息
     */
    public String getInfo() {
        return info;
    }

    /**
     * 异常提示信息
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * 异常描述
     */
    public String getDes() {
        return des;
    }

    /**
     * 异常描述
     */
    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "Error [code=" + code + ", info=" + info + ", des=" + des + "]";
    }

}
