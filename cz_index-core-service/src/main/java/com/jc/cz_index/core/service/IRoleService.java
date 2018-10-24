/**
 * 2016/9/26 21:27:44 Jack Liu created.
 */

package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.model.Role;

/**
 * 角色表兼顾权限的表示 Service 接口 Created by Jack Liu on 2016/09/26.
 */
public interface IRoleService extends IBaseService<Role> {

    /**
     * 
     * 描述：获取用户角色
     * 
     * @param authId
     * @return
     * @author yangyongchuan 2016年9月26日 下午10:49:42
     * @version 1.0
     */
    public List<Role> findUserRoles(Long authId);



    /**
     * 
     * 描述：获取菜单需要的权限
     * 
     * @param id
     * @return
     * @author yangyongchuan 2016年9月27日 上午9:33:41
     * @version 1.0
     */
    public List<Role> findByMenuForAuthority(Long menuId);



    /**
     * 
     * 描述：添加角色
     * 
     * @param role
     * @author yangyongchuan 2016年10月16日 下午12:56:24
     * @version 1.0
     */
    public void addRole(Role role);



    /**
     * 
     * 描述：编辑角色
     * 
     * @param role
     * @author yangyongchuan 2016年10月16日 下午12:56:39
     * @version 1.0
     */
    public void updateRole(Role role) throws BaseException;

}
