/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jf.shiro.captcha;

import com.jf.json.OperateStatus;
import com.jf.shiro.SecurityHelper;
import com.jf.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：验证码检查
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00      2017/4/27 16:22      陈飞(fly)                    新建
 * <br>
 * *************************************************************************************************<br>
 */
public class CaptchaCheckFilter extends AccessControlFilter {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaCheckFilter.class);

    /**
     * 是否开启验证码支持
     */
    private boolean captchaEbabled = true;

    /**
     * 前台提交的验证码参数名
     */
    private String captchaParam = "captcha";

    /**
     * 验证码验证失败后存储到的属性名
     */
    private String failureKeyAttribute = "captchaFailure";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //1、设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码
        request.setAttribute("captchaEbabled", captchaEbabled);

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        //2、判断验证码是否禁用 或不是表单提交（允许访问）
        if (captchaEbabled == false || !"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return true;
        }
        //3、此时是表单提交，验证验证码是否正确
        String captcha = httpServletRequest.getParameter(captchaParam);
        if (StringUtils.isNotBlank(captcha)) {
            captcha = captcha.toUpperCase();
        }
        HttpSession session = httpServletRequest.getSession();
        String sysCaptcha = session.getAttribute(SecurityHelper.CAPTCHA_KEY).toString();
        if (sysCaptcha.equals(captcha)) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        OperateStatus status = OperateStatus.defaultFailure();
        status.setData("验证码错误");
        status.setMsg("验证码错误");
        try {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            if (!SecurityHelper.isAsync(servletRequest)) {// 不是ajax请求
                request.setAttribute("LoginFailure", status);
                saveRequestAndRedirectToLogin(request, response);
            } else {
                servletResponse.setCharacterEncoding("UTF-8");
                servletResponse.setContentType("application/json; charset=utf-8");
                PrintWriter out = servletResponse.getWriter();
                out.println(JsonUtil.genJson(status));
                out.flush();
                out.close();
            }
        } catch (IOException ioe) {
            logger.error(ioe.getMessage(), ioe);
        }
        return false;
    }


}
