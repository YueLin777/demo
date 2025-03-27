package com.mind.contract.config;

import com.mind.contract.dao.UserDao;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.UserService;
import com.mind.contract.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：long
 * @date ：Created in 2024/8/15 0015 11:38
 * @description：请求拦截器
 */

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 从请求头中取出token，并判断其是否存在和合法
         */
        String token = request.getHeader("token");
        if (token != null && TokenUtils.verify(token)) {
            if (userDao.queryUserByUsername(TokenUtils.getUsername(token))==null){
                throw new BusinessException("用户不存在");
            }
            return true;
        }else {
            throw new BusinessException("请先登录");
        }
    }
    /***
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     @param request
     @param response
     @param handler
     @param modelAndView
     @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /***
     * 整个请求结束之后被调用，也就是在DispatchServlet渲染了对应的视图之后执行（主要用于进行资源清理工作）
     @param request
     @param response
     @param handler
     @param ex
     @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
