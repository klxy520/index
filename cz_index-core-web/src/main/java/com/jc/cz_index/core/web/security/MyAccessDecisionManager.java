package com.jc.cz_index.core.web.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * 
 * 描述：访问决策器，决定某个用户具有的角色，是否有足够的权限去访问某个资源
 * 
 * @author yangyongchuan 2016年9月12日 下午5:09:23
 * @version 1.0
 */
@Service("accessDecisionManager")
public class MyAccessDecisionManager implements AccessDecisionManager {
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        if (configAttributes == null) {
            return;
        }

        for (ConfigAttribute ca : configAttributes) {
            String needRole = ((SecurityConfig) ca).getAttribute();

            for (GrantedAuthority ga : authentication.getAuthorities())
                if (needRole.trim().equals(ga.getAuthority().trim()))
                    return;
        }
        throw new AccessDeniedException("[" + authentication.getName() + "] 无权限访问 [" + object + "] 需要 " + configAttributes + " 权限");
    }



    public boolean supports(ConfigAttribute attribute) {
        return true;
    }



    public boolean supports(Class<?> clazz) {
        return true;
    }
}