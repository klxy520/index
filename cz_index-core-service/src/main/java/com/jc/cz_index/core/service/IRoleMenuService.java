/**
 * 2016/9/26 21:27:44 Jack Liu created.
 */

package com.jc.cz_index.core.service;

import com.jc.cz_index.model.RoleMenu;

/**
 * 权限与菜单 Service 接口 Created by Jack Liu on 2016/09/26.
 */
public interface IRoleMenuService extends IBaseService<RoleMenu> {
    /**
     * 
     * 描述：设置角色菜单
     * 
     * @param roleId
     * @param menuIdStr
     * @author yangyongchuan 2016年10月18日 下午3:55:54
     * @version 1.0
     */
    public void setRoleMenu(Long roleId, String menuIdStr);

}
