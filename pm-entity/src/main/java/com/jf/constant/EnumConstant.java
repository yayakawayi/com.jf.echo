package com.jf.constant;

import java.io.Serializable;

/**
 * 枚举常量
 */
public class EnumConstant implements Serializable {
    /**
     * 服务状态
     */
    public enum ServerStatus{
        /**
         * 启用
         */
        ENABLE,
        /**
         * 禁用
         */
        DISABLE
    }

    /**
     * 用户性别
     */
    public enum UserSex{
        WOMAN,MAN
    }

}
