package com.jc.cz_index.core.web.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * 
 * 描述：安全拦截filter，必须包含authenticationManager,accessDecisionManager,
 * securityMetadataSource三个属性,我们的所有控制将在这三个类中实现，解释详见具体配置
 * 
 * @author yangyongchuan 2016年9月12日 下午5:01:51
 * @version 1.0
 */
public class SecurityInterceptorFilter extends AbstractSecurityInterceptor implements Filter {
    private FilterInvocationSecurityMetadataSource securityMetadataSource;



    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }



    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());

            super.afterInvocation(token, null);
        } finally {
            super.afterInvocation(token, null);
        }
    }



    public Class<? extends Object> getSecureObjectClass() {
        return FilterInvocation.class;
    }



    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }



    public void init(FilterConfig config) throws ServletException {
    }



    public void destroy() {
    }



    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return this.securityMetadataSource;
    }



    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }
}