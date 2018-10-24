/**
 * 2016/9/26 21:27:47 Jack Liu created.
 */

package com.jc.cz_index.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.ISystemUserAuthDao;
import com.jc.cz_index.dao.IUserLoginFailDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.SystemUserAuth;
import com.jc.cz_index.model.UserLoginFail;
import com.jc.cz_index.core.service.ISystemUserAuthService;

/**
 * 系统用户认证信息 Service 实现 Created by Jack Liu on 2016/09/26.
 */
@Service
public class SystemUserAuthService extends BaseService<SystemUserAuth> implements ISystemUserAuthService {

    @Autowired
    private ISystemUserAuthDao systemUserAuthDao;

    @Autowired
    private IUserLoginFailDao  userLoginFailDao;


    @Override
    public IDataProvider<SystemUserAuth, Long> getModelDao() {
        return this.systemUserAuthDao;
    }



    @Override
    public void updateAuthStatus(Long systemUserAuthId, Integer dbFieldStatusDisable) {
        QueryParams params = new QueryParams();
        QueryParams params2 = new QueryParams();
        params.addParameter("id", systemUserAuthId);
        SystemUserAuth systemUserAuth = systemUserAuthDao.getBaseObject(systemUserAuthId);
        params.addParameter("status", dbFieldStatusDisable);
        systemUserAuthDao.updateObjectByFields(params);
        params2.put("loginName", systemUserAuth.getLoginName());
        List<UserLoginFail> list = userLoginFailDao.queryBaseList(params2);
        UserLoginFail userLoginFail = list.get(0);
        userLoginFail.setFailTimes(0);
        userLoginFailDao.updateObject(userLoginFail);
    }

}
