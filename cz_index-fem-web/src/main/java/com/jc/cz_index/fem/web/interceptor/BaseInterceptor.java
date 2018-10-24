package com.jc.cz_index.fem.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * base拦截器
 *
 * @author: 杨永川
 * @version: 1.0, 2015年12月21日
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = Logger.getLogger(BaseInterceptor.class);



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("请求url:" + request.getRequestURI() + " 处理开始");
        return super.preHandle(request, response, handler);
    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        logger.debug("请求url:" + request.getRequestURI() + " 处理结束");
        super.postHandle(request, response, handler, modelAndView);
    }

}
