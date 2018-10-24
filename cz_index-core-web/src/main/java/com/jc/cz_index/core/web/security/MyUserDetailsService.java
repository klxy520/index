package com.jc.cz_index.core.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Role;
import com.jc.cz_index.model.SystemUserAuth;
import com.jc.cz_index.core.service.ISystemUserAuthService;
import com.jc.cz_index.core.service.impl.RoleService;

/**
 * 
 * 描述：获取用户数据库信息
 * 
 * @author yangyongchuan 2016年9月28日 下午6:34:04
 * @version 1.0
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private ISystemUserAuthService systemUserAuthService;

    @Autowired
    private RoleService            roleService;

    @Autowired
    private MessageSource          messageSource;



    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        // 获取用户
        QueryParams params = new QueryParams();
        params.addParameter("loginName", username);
        SystemUserAuth systemUserAuth = this.systemUserAuthService.queryOneBase(params);
        if (null == systemUserAuth) {
            throw new UsernameNotFoundException(
                    this.messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", null, "用户账号不存在！", null));
        }
        String password = systemUserAuth.getPassword();
        // 获取用户角色
        List<Role> roles = this.roleService.findUserRoles(systemUserAuth.getId());
        HttpServletRequest request = WebUtils.getRequest();
        validate(systemUserAuth, request);

        Collection<GrantedAuthorityImpl> auths = new ArrayList<GrantedAuthorityImpl>();
        if ((null != roles) && (!(roles.isEmpty()))) {
            for (Role role : roles) {
                auths.add(new GrantedAuthorityImpl(role.getMark()));
            }
        }

        return new org.springframework.security.core.userdetails.User(username, password, 0 == systemUserAuth.getStatus() ? true : false,
                true, true, true, auths);
    }



    protected void validate(SystemUserAuth systemUserAuth, HttpServletRequest request)
            throws UsernameNotFoundException, DataAccessException {
    }
}