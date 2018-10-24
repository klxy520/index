package com.jc.cz_index.core.web.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.web.filter.GenericFilterBean;

public class AnonymousAccessDeniedHandler extends GenericFilterBean {
    private AuthenticationTrustResolver authenticationTrustResolver;
    private ThrowableAnalyzer           throwableAnalyzer;
    private RedirectStrategy            redirectStrategy;
    private String                      redirectUrl;



    public AnonymousAccessDeniedHandler() {
        this.authenticationTrustResolver = new AuthenticationTrustResolverImpl();
        this.throwableAnalyzer = new DefaultThrowableAnalyzer();
        this.redirectStrategy = new DefaultRedirectStrategy();
    }



    public void afterPropertiesSet() {
    }



    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        try {
            chain.doFilter(request, response);

            if (this.logger.isDebugEnabled())
                this.logger.debug("Chain processed normally");
        } catch (IOException ex) {
            throw ex;
        } catch (Exception ex) {
            Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(ex);
            RuntimeException ase = (AuthenticationException) this.throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,
                    causeChain);

            if (ase == null) {
                ase = (AccessDeniedException) this.throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
            }

            if (ase != null) {
                handleException(request, response, chain, ase);
            } else {
                if (ex instanceof ServletException)
                    throw ((ServletException) ex);
                if (ex instanceof RuntimeException) {
                    throw ((RuntimeException) ex);
                }

                throw new RuntimeException(ex);
            }
        }
    }



    private void handleException(HttpServletRequest request, HttpServletResponse response, FilterChain chain, RuntimeException exception)
            throws IOException, ServletException {
        if ((exception instanceof AccessDeniedException)
                && (this.authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication()))) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Access is denied (user is anonymous); redirecting to authentication entry point", exception);
            }

            RequestCache requestCache = new HttpSessionRequestCache();
            requestCache.saveRequest(request, response);

            request.getRequestDispatcher(this.redirectUrl).forward(request, response);
        } else {
            throw exception;
        }
    }



    public String getRedirectUrl() {
        return this.redirectUrl;
    }



    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
        protected void initExtractorMap() {
            super.initExtractorMap();

            registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
                public Throwable extractCause(Throwable throwable) {
                    ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
                    return ((ServletException) throwable).getRootCause();
                }
            });
        }
    }
}