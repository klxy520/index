package com.jc.cz_index.core.web.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.jc.cz_index.common.utils.Log4jUtils;

/**
 * 描述：登录成功后保存用户信息
 * 
 * @author lurf 2013-4-28
 * @version 2.0.0
 * @since 2.0.0
 */
@Component("saveUserInfoForLogined")
public class SaveUserInfoForLogined implements AuthenticationSuccessEvent {

    private Log log = Log4jUtils.getLog(SaveUserInfoForLogined.class);



    /*
     * (non-Javadoc)
     * 
     * @see
     * com.anze.base.spring.security.AuthenticationSuccessEvent#onSuccess(javax
     * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @Override
    public void onSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws Exception {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String ip = details.getRemoteAddress();
        String sessionId = details.getSessionId();
        Collection<GrantedAuthority> authoritys = authentication.getAuthorities();
        log.debug("=====用户信息====== name:" + name + ", password:" + password + ", ip:" + ip + ", sessionId:" + sessionId + ", roles:"
                + authoritys.toString());

    }

}
