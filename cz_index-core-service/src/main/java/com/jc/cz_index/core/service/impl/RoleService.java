/**
 * 2016/9/26 21:27:44 Jack Liu created.
 */

package com.jc.cz_index.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IRoleDao;
import com.jc.cz_index.model.Role;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.IRoleService;

/**
 * 角色表兼顾权限的表示 Service 实现 Created by Jack Liu on 2016/09/26.
 */
@Service
public class RoleService extends BaseService<Role> implements IRoleService {

    @Autowired
    private IRoleDao roleDao;



    @Override
    public IDataProvider<Role, Long> getModelDao() {
        return this.roleDao;
    }



    @Override
    public List<Role> findUserRoles(Long authId) {
        List<Role> roles = this.roleDao.findEnabledRoleByAuthId(authId);
        return roles;
    }



    @Override
    public List<Role> findByMenuForAuthority(Long menuId) {
        List<Role> roles = this.roleDao.findByMenuForAuthority(menuId);
        return roles;
    }



    @Override
    public void addRole(Role role) {
        // 登录用户
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        role.setCreator(loginUser.getId());
        role.setCreateDate(DateUtils.getCurrentDate());
        roleDao.insertObject(role);
    }



    @Override
    public void updateRole(Role role) throws BaseException {
        Role roleFormDB = roleDao.getBaseObject(role.getId());
        if (null == roleFormDB) {
            throw new BaseException("该角色不存在");
        }
        // 登录用户
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        BeanUtils.copyPropertiesIgnoreNull(role, roleFormDB);
        roleFormDB.setUpdator(loginUser.getId());
        roleFormDB.setUpdateDate(DateUtils.getCurrentDate());
        roleFormDB.setDescription(role.getDescription());
        roleDao.updateObject(roleFormDB);
    }

}
