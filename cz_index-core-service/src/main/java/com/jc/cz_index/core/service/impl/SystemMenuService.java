/**
 * 2016/9/26 21:31:48 Jack Liu created.
 */

package com.jc.cz_index.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.Log4jUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.SysConfig;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.ISystemMenuDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.SystemMenu;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.ISystemMenuService;

/**
 * 系统菜单 Service 实现 Created by Jack Liu on 2016/09/26.
 */
@Service
public class SystemMenuService extends BaseService<SystemMenu> implements ISystemMenuService {
    private Log            log = Log4jUtils.getLog(SystemMenuService.class);

    @Autowired
    private ISystemMenuDao systemMenuDao;



    @Override
    public IDataProvider<SystemMenu, Long> getModelDao() {
        return this.systemMenuDao;
    }



    /**
     * 
     * 描述：获取用户可操作菜单
     * 
     * @param systemUser
     * @return
     * @author yangyongchuan 2016年10月10日 下午3:48:58
     * @version 1.0
     */
    public List<SystemMenu> queryMenu(SystemUser systemUser) {
        List<SystemMenu> menuList = null;
        // 获取菜单
        List<SystemMenu> systemMenus = systemMenuDao.queryMenuByAuthId(systemUser.getAuthId());
        // 按照目录拼装菜单
        if (!CollectionsUtils.isNull(systemMenus)) {
            // 后台管理系统菜单root id
            Long menuRootId = Long.valueOf(SysConfig.getValue(ContentUtils.MENU_ROOT_ID));
            menuList = new ArrayList<SystemMenu>();
            for (SystemMenu systemMenu1 : systemMenus) {
                if (null != systemMenu1 && null != systemMenu1.getParentId() && systemMenu1.getParentId() == menuRootId) {
                    if (!menuList.contains(systemMenu1)) {
                        menuList.add(systemMenu1);
                    }
                }
            }
            SystemMenu parentMenu = new SystemMenu();
            for (SystemMenu systemMenu2 : systemMenus) {
                if (null != systemMenu2 && null != systemMenu2.getParentId() && systemMenu2.getParentId() > menuRootId) {
                    // 二级菜单
                    parentMenu.setId(systemMenu2.getParentId());
                    int index = menuList.indexOf(parentMenu);
                    if (index < 0 || null == menuList.get(index)) {
                        continue;
                    }
                    menuList.get(index).getSubMenuList().add(systemMenu2);
                }
            }
        }
        return menuList;
    }



    @Override
    public void addMenu(SystemMenu systemMenu) throws BaseException {
        // 登录用户
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        Date currentDate = DateUtils.getCurrentDate();
        systemMenu.setCreator(loginUser.getId());
        systemMenu.setCreateDate(currentDate);
        // 菜单编号
        systemMenu.setSn(generateMenuSn(systemMenu.getParentId()));
        systemMenuDao.insertObject(systemMenu);
    }



    @Override
    public void updateMenu(SystemMenu systemMenu) throws BaseException {
        SystemMenu systemMenuFormDB = systemMenuDao.getBaseObject(systemMenu.getId());
        if (null == systemMenuFormDB) {
            throw new BaseException("菜单不存在");
        }
        // 登录用户
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        Date currentDate = DateUtils.getCurrentDate();
        // 复制数据
        BeanUtils.copyPropertiesIgnoreNull(systemMenu, systemMenuFormDB);
        systemMenuFormDB.setUpdator(loginUser.getId());
        systemMenuFormDB.setUpdateDate(currentDate);
        // 菜单编号
        systemMenuDao.updateObject(systemMenuFormDB);
    }



    /**
     * 
     * 描述：获取菜单编号
     * 
     * @param parentId
     * @return
     * @throws BaseException
     * @author yangyongchuan 2016年10月17日 下午3:23:37
     * @version 1.0
     */
    public String generateMenuSn(Long parentId) throws BaseException {
        String sn = null;
        SystemMenu parentMenu = systemMenuDao.getBaseObject(parentId);
        if (null == parentMenu) {
            log.error("菜单不存在,id=" + parentId);
            throw new BaseException("父菜单不存在");
        }
        sn = parentMenu.getSn();
        List<SystemMenu> subMenuList = systemMenuDao.getMenuByParentId(parentId);
        if (CollectionsUtils.isNotNull(subMenuList)) {
            sn += StringUtils.add0("" + (subMenuList.size() + 1), 2);
        } else {
            sn += "01";
        }
        return sn;
    }



    @Override
    public SystemMenu checkMenuBySn(SystemUser systemUser, String sn) {
        QueryParams params = new QueryParams();
        params.addParameter("user_auth_id", systemUser.getAuthId());
        params.addParameter("menu_sn", sn);
        SystemMenu systemMenu = systemMenuDao.checkMenuBySn(params);
        return systemMenu;
    }
}
