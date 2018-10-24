package com.jc.cz_index.api.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 
 * 描述：异常总处理
 * 
 * @author yangyongchuan 2016年10月19日 下午5:38:39
 * @version 1.0
 */
public class MyExceptionResolver extends SimpleMappingExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = super.doResolveException(request, response, handler, ex);
        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (null != stackTrace && stackTrace.length > 0) {
            modelAndView.addObject("errorMsg", stackTrace[0]);
        }
        return modelAndView;
    }

}
