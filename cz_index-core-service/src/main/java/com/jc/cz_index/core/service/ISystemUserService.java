/**
 * 2016/9/26 21:27:46 Jack Liu created.
 */

package com.jc.cz_index.core.service;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.model.SystemUser;

/**
 * 系统用户信息 Service 接口 Created by Jack Liu on 2016/09/26.
 */
public interface ISystemUserService extends IBaseService<SystemUser> {

    /**
     * 
     * 描述：添加系统用户
     * 
     * @param systemUser
     * @author yangyongchuan 2016年10月14日 上午11:22:28
     * @version 1.0
     */

    public void addUser(SystemUser systemUser);



    /**
     * 
     * 描述：编辑系统用户
     * 
     * @param systemUser
     * @author yangyongchuan 2016年10月14日 下午3:59:38
     * @version 1.0
     */
    public void updateUser(SystemUser systemUser) throws BaseException;

}
