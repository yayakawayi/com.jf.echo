package com.jf.json;

import java.io.Serializable;

/**
 * *************************************************************************************************
 * <p/>
 * 实现功能：前端返回结果类
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/4/5 16:27      陈飞(fly)                  新建
 * <p/>
 * *************************************************************************************************
 */
public class OperateStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String COMMON_SUCCESS_MSG = "操作成功";
    public static final String COMMON_FAILURE_MSG = "操作失败，请稍后重试";

    /**
     * 成功标志
     */
    private boolean success;
    /**
     * 消息
     */
    private String msg;

    /**
     * 返回数据
     */
    private Object data;


    public OperateStatus(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }


    public OperateStatus(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public static OperateStatus defaultSuccess() {
        return new OperateStatus(true, COMMON_SUCCESS_MSG);
    }

    public static OperateStatus defaultFailure() {
        return new OperateStatus(false, COMMON_FAILURE_MSG);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
