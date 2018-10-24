/**
 * 2016/9/26 21:27:48 Jack Liu created.
 */

package com.jc.cz_index.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.ISystemUserRoleDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.model.SystemUserRole;
import com.jc.cz_index.core.service.ISystemUserRoleService;

/**
 * 系统用户角色 Service 实现 Created by Jack Liu on 2016/09/26.
 */
@Service
public class SystemUserRoleService extends BaseService<SystemUserRole> implements ISystemUserRoleService {

    @Autowired
    private ISystemUserRoleDao systemUserRoleDao;



    @Override
    public IDataProvider<SystemUserRole, Long> getModelDao() {
        return this.systemUserRoleDao;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setUsrRole(Long systemUserAuthId, String roleIdStr) throws BaseException {
        // 删除以前授权
        QueryParams params = new QueryParams();
        params.addParameter("authId", systemUserAuthId);
        systemUserRoleDao.deleteObjectByWhere(params);
        // 设置目前授权角色
        SystemUserRole systemUserRole = null;
        // 登录用户
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        Date currentDate = DateUtils.getCurrentDate();
        if (StringUtils.isNotEmpty(roleIdStr)) {
            List<String> roleIdList = StringUtils.getStringList(roleIdStr, ",");
            for (String roleId : roleIdList) {
                systemUserRole = new SystemUserRole();
                systemUserRole.setAuthId(systemUserAuthId);
                systemUserRole.setRoleId(StringUtils.StringToLong(roleId));
                systemUserRole.setCreator(loginUser.getId());
                systemUserRole.setCreateDate(currentDate);
                systemUserRoleDao.insertObject(systemUserRole);
            }
        }
    }

}
