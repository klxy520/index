/**
 * 2016/9/26 21:27:48 Jack Liu created.
 */

package com.jc.cz_index.core.service;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.model.SystemUserRole;

/**
 * 系统用户角色 Service 接口 Created by Jack Liu on 2016/09/26.
 */
public interface ISystemUserRoleService extends IBaseService<SystemUserRole> {

    /**
     * 
     * 描述：用户授权
     * 
     * @param systemUserAuthId
     * @param roleIds
     * @author yangyongchuan 2016年10月18日 下午1:13:18
     * @version 1.0
     */
    public void setUsrRole(Long systemUserAuthId, String roleIdStr) throws BaseException;

}
