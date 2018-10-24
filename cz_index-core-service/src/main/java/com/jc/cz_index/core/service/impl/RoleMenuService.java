/**
 * 2016/9/26 21:27:45 Jack Liu created.
 */

package com.jc.cz_index.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IRoleMenuDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.RoleMenu;
import com.jc.cz_index.core.service.IRoleMenuService;

/**
 * 权限与菜单 Service 实现 Created by Jack Liu on 2016/09/26.
 */
@Service
public class RoleMenuService extends BaseService<RoleMenu> implements IRoleMenuService {
    private static final Logger logger = Logger.getLogger(RoleMenuService.class);
    @Autowired
    private IRoleMenuDao        roleMenuDao;



    @Override
    public IDataProvider<RoleMenu, Long> getModelDao() {
        return this.roleMenuDao;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setRoleMenu(Long roleId, String menuIdStr) {
        long nowTime = DateUtils.getTime();
        // 删除以前授权
        QueryParams params = new QueryParams();
        params.addParameter("roleId", roleId);
        roleMenuDao.deleteObjectByWhere(params);
        // 设置目前授权角色
        RoleMenu roleMenu = null;
        // 登录用户
        // SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        // Date currentDate = DateUtils.getCurrentDate();
        if (StringUtils.isNotEmpty(menuIdStr)) {
            List<RoleMenu> roleMenuList = new ArrayList<RoleMenu>();
            List<String> roleIdList = StringUtils.getStringList(menuIdStr, ",");
            for (String menuId : roleIdList) {
                roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setSystemMenuId(StringUtils.StringToLong(menuId));
                // roleMenu.setCreator(loginUser.getId());
                // roleMenu.setCreateDate(currentDate);
                // roleMenuDao.insertObject(roleMenu);
                roleMenuList.add(roleMenu);
            }
            //RoleMenu[] roleMenuArray = new RoleMenu[roleMenuList.size()];
            //roleMenuList.toArray(roleMenuArray);
            roleMenuDao.insertList(roleMenuList);
        }

        logger.debug("--------------设置角色权限耗时：" + (DateUtils.getTime() - nowTime) + "毫秒");
    }

}
