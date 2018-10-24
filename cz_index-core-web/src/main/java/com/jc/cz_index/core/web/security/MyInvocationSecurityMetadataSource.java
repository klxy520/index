package com.jc.cz_index.core.web.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;
import org.springframework.stereotype.Service;

import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.Log4jUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.SysConfig;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Role;
import com.jc.cz_index.model.SystemMenu;
import com.jc.cz_index.core.service.IRoleService;
import com.jc.cz_index.core.service.ISystemMenuService;

/**
 * 
 * 描述：资源源数据定义，即定义某一资源可以被哪些角色访问
 * 
 * @author yangyongchuan 2016年9月12日 下午5:09:03
 * @version 1.0
 */
@Service("securityMetadataSource")
public class MyInvocationSecurityMetadataSource implements MySecurityMetadataSource, FilterInvocationSecurityMetadataSource {
    private static final Set<String>                              HTTP_METHODS = new HashSet<String>(
            Arrays.asList(new String[] { "DELETE", "GET", "HEAD", "OPTIONS", "POST", "PUT", "TRACE" }));
    private Log                                                   log;

    @Autowired
    private IRoleService                                          roleService;

    @Autowired
    private ISystemMenuService                                    menuService;
    private UrlMatcher                                            urlMatcher;
    private Map<String, Map<Object, Collection<ConfigAttribute>>> httpMethodMap;
    private Map<String, Map<Object, Collection<ConfigAttribute>>> httpMethodMapTemp;



    public MyInvocationSecurityMetadataSource() {
        this.log = Log4jUtils.getLog(super.getClass());

        this.urlMatcher = new AntUrlPathMatcher();

        this.httpMethodMap = new HashMap<String, Map<Object, Collection<ConfigAttribute>>>();
        this.httpMethodMapTemp = new HashMap<String, Map<Object, Collection<ConfigAttribute>>>();
    }



    @PostConstruct
    public synchronized void initAllConfigAttributes() {
        this.httpMethodMapTemp.clear();
        loadResourceDefine();
        this.httpMethodMap = this.httpMethodMapTemp;
    }



    private void loadResourceDefine() {
        QueryParams params = new QueryParams();
        params.addParameter("id", Long.valueOf(SysConfig.getValue(ContentUtils.MENU_ROOT_ID)));
        params.addParameter("status", 0);
        SystemMenu menu = this.menuService.queryOneBase(params);
        loadMenuAuthority(menu);
    }



    private void loadMenuAuthority(SystemMenu menu) {
        if (null == menu) {
            return;
        }
        Collection<ConfigAttribute> attrs = new ArrayList<ConfigAttribute>();
        if (StringUtils.isNotBlank(menu.getTargetUrl())) {
            List<Role> roles = this.roleService.findByMenuForAuthority(menu.getId());
            if (null != roles) {

                for (Role role : roles) {
                    ConfigAttribute ca = new SecurityConfig(role.getMark());
                    attrs.add(ca);
                    String method = null;
                    if (null != menu.getMethod()) {
                        method = SystemMenu.HttpMethodType.getHttpMethodType(String.valueOf(menu.getMethod())).getDesc();
                    }
                    addSecureUrl(menu.getTargetUrl(), method, attrs);
                }
            }

        }
        QueryParams params = new QueryParams();
        params.addParameter("parentId", menu.getId());
        params.addParameter("status", 0);
        params.addOrderBy("sn", true);
        List<SystemMenu> menus = this.menuService.queryBaseList(params);
        if (null != menus)
            for (SystemMenu childMenu : menus) {
                loadMenuAuthority(childMenu);
            }
    }



    private void addSecureUrl(String pattern, String method, Collection<ConfigAttribute> attrs) {
        Map<Object, Collection<ConfigAttribute>> mapToUse = getRequestMapForHttpMethod(method);
        mapToUse.put(this.urlMatcher.compile(pattern), attrs);
        if (this.log.isDebugEnabled())
            this.log.debug(new StringBuilder().append("Added URL pattern: ").append(pattern).append("; attributes: ").append(attrs)
                    .append((method == null) ? "" : new StringBuilder().append(" for HTTP method '").append(method).append("'").toString())
                    .toString());
    }



    private Map<Object, Collection<ConfigAttribute>> getRequestMapForHttpMethod(String method) {
        if ((method != null) && (!(HTTP_METHODS.contains(method)))) {
            throw new IllegalArgumentException(
                    new StringBuilder().append("Unrecognised HTTP method: '").append(method).append("'").toString());
        }

        Map<Object, Collection<ConfigAttribute>> methodRequestMap = (Map<Object, Collection<ConfigAttribute>>) this.httpMethodMapTemp
                .get(method);

        if (methodRequestMap == null) {
            methodRequestMap = new LinkedHashMap<Object, Collection<ConfigAttribute>>();
            this.httpMethodMapTemp.put(method, methodRequestMap);
        }

        return methodRequestMap;
    }



    /**
     * 
     * 描述：获取请求资源需要的角色
     * 
     * @param url
     * @param method
     * @return
     * @author yangyongchuan 2016年9月28日 下午5:26:53
     * @version 1.0
     */
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if ((object == null) || (!(supports(object.getClass())))) {
            throw new IllegalArgumentException("Object must be a FilterInvocation");
        }

        String url = ((FilterInvocation) object).getRequestUrl();
        String method = ((FilterInvocation) object).getHttpRequest().getMethod();

        return lookupAttributes(url, method);
    }



    /**
     * 
     * 描述：获取请求资源需要的角色
     * 
     * @param url
     * @param method
     * @return
     * @author yangyongchuan 2016年9月28日 下午5:26:53
     * @version 1.0
     */
    public final Collection<ConfigAttribute> lookupAttributes(String url, String method) {
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }

        if ((url.endsWith("/")) && (url.length() > 1)) {
            url = url.substring(0, url.length() - 1);
        }

        if (this.urlMatcher.requiresLowerCaseUrl()) {
            url = url.toLowerCase();

            if (this.log.isDebugEnabled()) {
                this.log.debug(new StringBuilder().append("Converted URL to lowercase, from: '").append(url).append("'; to: '").append(url)
                        .append("'").toString());
            }

        }

        Collection<ConfigAttribute> attributes = extractMatchingAttributes(url,
                (Map<Object, Collection<ConfigAttribute>>) this.httpMethodMap.get(method));

        if (attributes == null) {
            attributes = extractMatchingAttributes(url, (Map<Object, Collection<ConfigAttribute>>) this.httpMethodMap.get(null));
        }

        return attributes;
    }



    private Collection<ConfigAttribute> extractMatchingAttributes(String url, Map<Object, Collection<ConfigAttribute>> map) {
        if (map == null) {
            return null;
        }

        boolean debug = this.log.isDebugEnabled();

        for (Entry<Object, Collection<ConfigAttribute>> entry : map.entrySet()) {
            Object p = entry.getKey();
            boolean matched = this.urlMatcher.pathMatchesUrl(this.urlMatcher.compile(entry.getKey().toString()), url);

            if (debug) {
                this.log.debug(new StringBuilder().append("Candidate is: '").append(url).append("'; pattern is ").append(p)
                        .append("; matched=").append(matched).toString());
            }

            if (matched) {
                return ((Collection<ConfigAttribute>) entry.getValue());
            }
        }
        return null;
    }



    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

        for (Entry<String, Map<Object, Collection<ConfigAttribute>>> entry : this.httpMethodMap.entrySet()) {
            for (Collection<ConfigAttribute> attrs : ((Map<Object, Collection<ConfigAttribute>>) entry.getValue()).values()) {
                allAttributes.addAll(attrs);
            }
        }

        return allAttributes;
    }



    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}