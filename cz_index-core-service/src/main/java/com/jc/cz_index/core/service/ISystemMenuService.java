/**
 * 2016/9/26 21:31:47 Jack Liu created.
 */

package com.jc.cz_index.core.service;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.model.SystemMenu;
import com.jc.cz_index.model.SystemUser;

/**
 * 系统菜单 Service 接口 Created by Jack Liu on 2016/09/26.
 */
public interface ISystemMenuService extends IBaseService<SystemMenu> {
    /**
     * 
     * 描述：添加菜单
     * 
     * @param systemMenu
     * @author yangyongchuan 2016年10月17日 下午3:11:54
     * @version 1.0
     */
    public void addMenu(SystemMenu systemMenu) throws BaseException;



    /**
     * 
     * 描述：修改菜单
     * 
     * @param systemMenu
     * @author yangyongchuan 2016年10月17日 下午3:12:15
     * @version 1.0
     */
    public void updateMenu(SystemMenu systemMenu) throws BaseException;



    /**
     * 
     * 描述：检查用户是否具有该菜单，根据编号
     * 
     * @param systemUser
     * @param sn
     * @return
     * @author yangyongchuan 2017年3月6日 下午4:24:38
     * @version 1.0
     */
    public SystemMenu checkMenuBySn(SystemUser systemUser, String sn);

}
