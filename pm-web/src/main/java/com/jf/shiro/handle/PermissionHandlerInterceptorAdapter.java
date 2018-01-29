package com.jf.shiro.handle;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PermissionHandlerInterceptorAdapter extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器");
        System.out.println(request.getRequestURI());
        if (request.getSession().getAttribute("User")==null){
            request.setAttribute("msg", "请先登录吧！");
            request.getRequestDispatcher("/user/msg").forward(request, response);
            return false;
        }
        return true;
    }
}
