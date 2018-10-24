package com.jc.cz_index.core.web.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;
import org.springframework.stereotype.Service;

import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.Log4jUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Role;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.model.SystemUserAuth;
import com.jc.cz_index.model.UserLoginFail;
import com.jc.cz_index.core.service.IRoleService;
import com.jc.cz_index.core.service.ISystemUserAuthService;
import com.jc.cz_index.core.service.ISystemUserService;
import com.jc.cz_index.core.service.IUserLoginFailService;

/**
 * 
 * 描述：登录认证成功处理器，主要控制一些特殊地址认证成功后的走向
 * 
 * @author yangyongchuan 2016年9月28日 下午6:21:20
 * @version 1.0
 */
@Service("authenticationSuccessHandler")
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    protected final Log                      logger;
    private static final String              DEFAULT_TARGET_ATTRIBUTE = "targetUrl";
    private RequestCache                     requestCache;
    private UrlMatcher                       urlMatcher;
    private String                           returnBackUrl;
    private List<String>                     returnBackUrls;
    private String                           returnDefaultUrl;
    private List<String>                     returnDefaultUrls;
    private String                           targetUrlAttribute;
    private List<AuthenticationSuccessEvent> successEvents;

    @Autowired
    private IRoleService                     roleService;

    @Autowired
    private ISystemUserAuthService           systemUserAuthService;

    @Autowired
    private ISystemUserService               systemUserService;

    @Autowired
    private IUserLoginFailService            userLoginFailService;



    public AuthenticationSuccessHandler() {
        this.logger = Log4jUtils.getLog(super.getClass());

        this.requestCache = new HttpSessionRequestCache();

        this.urlMatcher = new AntUrlPathMatcher();

        this.targetUrlAttribute = DEFAULT_TARGET_ATTRIBUTE;
    }



    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        UserLoginFail userLogin = new UserLoginFail();
        List<UserLoginFail> userLoginFailsList = new ArrayList<UserLoginFail>();
        // 获取用户
        QueryParams params = new QueryParams();
        params.addParameter("loginName", authentication.getName());
        List<SystemUserAuth> systemUserAuthList = this.systemUserAuthService.queryBaseList(params);
        SystemUserAuth systemUserAuth = systemUserAuthList.get(0);
        systemUserAuth.setLoginDate(new Date());
        this.systemUserAuthService.doUpdateModel(systemUserAuth);
        // 获取用户角色
        List<Role> roles = this.roleService.findUserRoles(systemUserAuth.getId());
        systemUserAuth.setRoles(roles);

        // 修改错误次数
        userLoginFailsList = userLoginFailService.queryBaseList(params);
        if (userLoginFailsList.size() != 0) {
            userLogin = userLoginFailsList.get(0);
            userLogin.setFailTimes(0);
            userLoginFailService.doUpdateModel(userLogin);
        }

        // 获取登录用户的基本信息,存入session
        params.clear();
        params.addParameter("authId", systemUserAuth.getId());
        SystemUser systemUser = this.systemUserService.queryOneDetail(params);
        request.getSession().setAttribute(ContentUtils.SESSION_USER, systemUser);
        request.getSession().removeAttribute(ContentUtils.SESSION_USER_AREA);
        request.getSession().removeAttribute(ContentUtils.SESSION_USER_ORG);
        //判断是否为业务管理员
        if(systemUser.getAdministrativeDivision()!=null){//区域机构
            request.getSession().setAttribute(ContentUtils.SESSION_USER_AREA, systemUser.getAdministrativeDivision().getName());
        }
        if(systemUser.getAdministrativeManagement()!=null){//行政机构
            request.getSession().setAttribute(ContentUtils.SESSION_USER_ORG, systemUser.getAdministrativeManagement().getAdministrativeName());
        }
        invokeSuccessEvent(request, response, authentication);

        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        boolean isAjax = new Boolean(request.getParameter("ajax")).booleanValue();
        String targetUrl = "/";

        if (savedRequest == null) {
            if (isAjax) {
                targetUrl = determineTargetUrl(request, response);
                request.getSession().setAttribute(this.targetUrlAttribute, targetUrl);
                response.getWriter().write(targetUrl);
                response.getWriter().close();
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }

            return;
        }

        String url = ((DefaultSavedRequest) savedRequest).getServletPath();

        if (matchesReturnBackUrl(url)) {
            clearAuthenticationAttributes(request);

            targetUrl = savedRequest.getRedirectUrl();

            if (isAjax) {
                request.getSession().setAttribute(this.targetUrlAttribute, targetUrl);
                response.getWriter().write(targetUrl);
                response.getWriter().close();
            } else {
                this.logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
                getRedirectStrategy().sendRedirect(request, response, targetUrl);
            }

            return;
        }

        if ((isAlwaysUseDefaultTargetUrl()) || (org.springframework.util.StringUtils.hasText(request.getParameter(getTargetUrlParameter())))
                || (matchesReturnDefaultUrl(url))) {
            this.requestCache.removeRequest(request, response);

            if (isAjax) {
                targetUrl = determineTargetUrl(request, response);
                request.getSession().setAttribute(this.targetUrlAttribute, targetUrl);
                response.getWriter().write(targetUrl);
                response.getWriter().close();
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }

            return;
        }

        clearAuthenticationAttributes(request);

        targetUrl = savedRequest.getRedirectUrl();

        if (isAjax) {
            request.getSession().setAttribute(this.targetUrlAttribute, targetUrl);
            response.getWriter().write(targetUrl);
            response.getWriter().close();
        } else {
            this.logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
    }



    private void invokeSuccessEvent(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if ((null != this.successEvents) && (!(this.successEvents.isEmpty())))
            for (AuthenticationSuccessEvent event : this.successEvents)
                try {
                    event.onSuccess(request, response, authentication);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
    }



    private boolean matchesReturnBackUrl(String url) {
        List<String> urls = getReturnBackUrls();

        return matchesUrl(urls, url);
    }



    private boolean matchesReturnDefaultUrl(String url) {
        List<String> urls = getReturnDefaultUrls();

        return matchesUrl(urls, url);
    }



    private boolean matchesUrl(List<String> urls, String url) {
        if (null != urls) {
            int firstQuestionMarkIndex = url.indexOf("?");
            if (firstQuestionMarkIndex != -1) {
                url = url.substring(0, firstQuestionMarkIndex);
            }

            if ((url.endsWith("/")) && (url.length() > 1)) {
                url = url.substring(0, url.length() - 1);
            }

            if (this.urlMatcher.requiresLowerCaseUrl()) {
                url = url.toLowerCase();
            }

            for (String p : urls) {
                boolean matched = this.urlMatcher.pathMatchesUrl(this.urlMatcher.compile(p), url);

                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Candidate returnBackUrl is: '" + url + "'; pattern is " + p + "; matched=" + matched);
                }
                if (matched) {
                    return matched;
                }
            }
        }

        return false;
    }



    public String getDefaultTarget() {
        return getDefaultTargetUrl();
    }



    private List<String> getReturnBackUrls() {
        if ((StringUtils.isNotBlank(this.returnBackUrl)) && (null == this.returnBackUrls)) {
            String[] urls = this.returnBackUrl.trim().split(",");
            this.returnBackUrls = Arrays.asList(urls);
        }
        return this.returnBackUrls;
    }



    private List<String> getReturnDefaultUrls() {
        if ((StringUtils.isNotBlank(this.returnDefaultUrl)) && (null == this.returnDefaultUrls)) {
            String[] urls = this.returnDefaultUrl.trim().split(",");
            this.returnDefaultUrls = Arrays.asList(urls);
        }
        return this.returnDefaultUrls;
    }



    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }



    public String getReturnBackUrl() {
        return this.returnBackUrl;
    }



    public void setReturnBackUrl(String returnBackUrl) {
        this.returnBackUrl = returnBackUrl;
    }



    public String getReturnDefaultUrl() {
        return this.returnDefaultUrl;
    }



    public void setReturnDefaultUrl(String returnDefaultUrl) {
        this.returnDefaultUrl = returnDefaultUrl;
    }



    public String getTargetUrlAttribute() {
        return this.targetUrlAttribute;
    }



    public void setTargetUrlAttribute(String targetUrlAttribute) {
        this.targetUrlAttribute = targetUrlAttribute;
    }



    public List<AuthenticationSuccessEvent> getSuccessEvents() {
        return this.successEvents;
    }



    public void setSuccessEvents(List<AuthenticationSuccessEvent> successEvents) {
        this.successEvents = successEvents;
    }
}