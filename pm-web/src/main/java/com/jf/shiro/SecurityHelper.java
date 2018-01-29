package com.jf.shiro;

import javax.servlet.http.HttpServletRequest;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00      2017/5/15 23:35      马超(Vision.Mac)                新建
 * <br>
 * *************************************************************************************************
 */
public final class SecurityHelper {
    public static final String ERROR_KEY_ATTRIBUTE_NAME = "shiroLoginFailure";
    /**
     * 会话中的验证码key
     */
    public static final String CAPTCHA_KEY = "SE_KEY_MM_CODE";
    /**
     * 前台提交的验证码参数名
     */
    public static final String CAPTCHA_PARAM = "captcha";

    /**
     * 是否是异步请求
     */
    public static boolean isAsync(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
