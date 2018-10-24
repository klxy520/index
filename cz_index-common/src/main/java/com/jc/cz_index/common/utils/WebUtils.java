package com.jc.cz_index.common.utils;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * 描述：WebUtils
 * 
 * @author yangyongchuan 2016年9月19日 下午1:30:51
 * @version 1.0
 */
public class WebUtils {
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != requestAttributes) {
            return requestAttributes.getRequest();
        }
        return null;
    }



    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != requestAttributes) {
            return requestAttributes.getResponse();
        }
        return null;
    }



    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        if (null != request) {
            return request.getSession(false);
        }
        return null;
    }



    public static ServletContext getContext() {
        HttpSession session = getSession();
        if (null != session) {
            return session.getServletContext();
        }
        return null;
    }



    public static Object getAttribute(String name) {
        Object value = null;

        HttpServletRequest request = getRequest();
        if (null != request) {
            value = request.getAttribute(name);
            if (null == value) {
                HttpSession session = getSession();
                if (null != session) {
                    value = session.getAttribute(name);
                    if (null == value) {
                        ServletContext context = getContext();
                        if (null != context) {
                            value = context.getAttribute(name);
                        }
                    }
                }
            }

        }

        return value;
    }



    /**
     * 
     * 描述：获取登录用户
     * 
     * @return
     * @author yangyongchuan 2016年10月14日 上午11:37:51
     * @version 1.0
     */
    public static Object getLoginUser() {
        return WebUtils.getSession().getAttribute(ContentUtils.SESSION_USER);
    }
    /**
     * @Title: urlIsReach   
     * @Description: TODO(判断远程地址是否可达，可达则返回true)   
     * @param: @param url
     * @param: @return       
     * @return: boolean      
     * @throws
     */
    public static boolean urlIsReach(String url) {  
        if (url==null) {  
            return false;  
        }  
        try {  
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();  
            if (HttpURLConnection.HTTP_OK==connection.getResponseCode()) {  
                return true;  
            }  
        } catch (Exception e) {  
            return false;  
        }  
        return false;  
    } 
}