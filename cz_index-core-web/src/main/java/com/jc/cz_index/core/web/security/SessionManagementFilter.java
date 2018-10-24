package com.jc.cz_index.core.web.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import com.jc.cz_index.common.utils.StringUtils;

/**
 * 
 * 描述：会话管理
 * 
 * @author yangyongchuan 2016年9月12日 下午4:54:43
 * @version 1.0
 */
public class SessionManagementFilter extends GenericFilterBean {
    static final String                     FILTER_APPLIED              = "__spring_security_session_mgmt_filter_applied";
    private final SecurityContextRepository securityContextRepository;
    private SessionAuthenticationStrategy   sessionStrategy             = new SessionFixationProtectionStrategy();
    private AuthenticationTrustResolver     authenticationTrustResolver = new AuthenticationTrustResolverImpl();
    private String                          invalidSessionUrl;
    private AuthenticationFailureHandler    failureHandler              = new SimpleUrlAuthenticationFailureHandler();
    private RedirectStrategy                redirectStrategy            = new DefaultRedirectStrategy();



    public SessionManagementFilter() {
        this.securityContextRepository = new HttpSessionSecurityContextRepository();
    }



    public SessionManagementFilter(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }



    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (request.getAttribute("__spring_security_session_mgmt_filter_applied") != null) {
            chain.doFilter(request, response);
            return;
        }

        request.setAttribute("__spring_security_session_mgmt_filter_applied", Boolean.TRUE);

        if (!(this.securityContextRepository.containsContext(request))) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if ((authentication != null) && (!(this.authenticationTrustResolver.isAnonymous(authentication)))) {
                try {
                    this.sessionStrategy.onAuthentication(authentication, request, response);
                } catch (SessionAuthenticationException e) {
                    this.logger.debug("SessionAuthenticationStrategy rejected the authentication object", e);
                    SecurityContextHolder.clearContext();
                    this.failureHandler.onAuthenticationFailure(request, response, e);

                    return;
                }

                this.securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
            } else if ((request.getRequestedSessionId() != null) && (!(request.isRequestedSessionIdValid()))) {
                this.logger.debug("Requested session ID" + request.getRequestedSessionId() + " is invalid.");

                if (StringUtils.isNotEmpty(this.invalidSessionUrl)) {
                    this.logger.debug("Starting new session (if required) and redirecting to '" + this.invalidSessionUrl + "'");
                    request.getSession();

                    RequestCache requestCache = new HttpSessionRequestCache();
                    requestCache.saveRequest(request, response);

                    this.redirectStrategy.sendRedirect(request, response, this.invalidSessionUrl);

                    return;
                }
            }

        }

        chain.doFilter(request, response);
    }



    public void setSessionAuthenticationStrategy(SessionAuthenticationStrategy sessionStrategy) {
        Assert.notNull(sessionStrategy, "authenticatedSessionStratedy must not be null");
        this.sessionStrategy = sessionStrategy;
    }



    public void setInvalidSessionUrl(String invalidSessionUrl) {
        this.invalidSessionUrl = invalidSessionUrl;
    }



    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }



    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}