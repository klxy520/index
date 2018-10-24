/**
 * 2016/9/26 21:27:47 Jack Liu created.
 */

package com.jc.cz_index.core.service;

import com.jc.cz_index.model.SystemUserAuth;

/**
 * 系统用户认证信息 Service 接口 Created by Jack Liu on 2016/09/26.
 */
public interface ISystemUserAuthService extends IBaseService<SystemUserAuth> {

    /**
     * 
     * 描述：修改状态（启用，禁用）
     * 
     * @param systemUserAuthId
     * @param dbFieldStatusDisable
     * @author yangyongchuan 2016年10月14日 下午5:50:11
     * @version 1.0
     */
    public void updateAuthStatus(Long systemUserAuthId, Integer dbFieldStatusDisable);

}
