package com.whm.crm.settings.web.interceptor;

import com.alibaba.druid.sql.visitor.functions.Concat;
import com.whm.crm.commons.contants.Contants;
import com.whm.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 实现登录验证
 * @author 15718
 */
public class LoginInterceptor implements HandlerInterceptor {


    /**
     * 在执行控制器之前 进行拦截 实现登录验证
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果你尝试直接跳过登录  来到工作页面
        //     情况1: 以前登录过  登录信息保存在session中  并且session未过期  可以放行
        //     情况2: 没登录过  或 session已经过期  需要拦截
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        //此时说明符合情况2
        if(user == null){
            //重定向到登录页  保证地址栏与页面一直
            //动态获取  而不是写死response.sendRedirect("/crm");
            response.sendRedirect(request.getContextPath());
            //进行拦截
            return false;
        }
        //其它情况放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
