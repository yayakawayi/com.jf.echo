package com.jf.shiro;

import com.jf.json.OperateStatus;
import com.jf.util.JsonUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：
 * 表登录单认证
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
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomFormAuthenticationFilter.class);

    private static final String LOGIN_STATUS = "_LoginStatus";

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
                                     ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        OperateStatus status = OperateStatus.defaultSuccess();
        status.setMsg("登录成功");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 是否是ajax请求
        if (!SecurityHelper.isAsync(request)) {
            request.setAttribute(LOGIN_STATUS, status);
            issueSuccessRedirect(servletRequest, servletResponse);
        } else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
            writer.write(JsonUtil.genJson(status));
            writer.close();
        }
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        OperateStatus status = OperateStatus.defaultFailure();
        try {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            // 是否是ajax请求
            if (!SecurityHelper.isAsync(servletRequest)) {
                request.setAttribute(LOGIN_STATUS, status);
                saveRequestAndRedirectToLogin(request, response);
            } else {
                servletResponse.setCharacterEncoding("UTF-8");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
                writer.write(JsonUtil.genJson(status));
                writer.close();
            }
        } catch (IOException ioe) {
            LOGGER.error(ioe.getMessage(), ioe);
        }
        return false;
    }
}
