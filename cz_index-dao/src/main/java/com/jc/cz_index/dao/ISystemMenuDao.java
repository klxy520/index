/**
 * 2016/9/26 21:31:47 Jack Liu created.
 */

package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.SystemMenu;

/**
 * 系统菜单 Ibatis Dao 接口 Created by Jack Liu on 2016/09/26.
 */
@Repository
public interface ISystemMenuDao extends IDataProvider<SystemMenu, Long> {

    /**
     * 
     * 描述：获取用户可操作菜单
     * 
     * @param authId
     * @return
     * @author yangyongchuan 2016年10月10日 下午5:12:21
     * @version 1.0
     */
    public List<SystemMenu> queryMenuByAuthId(Long authId);



    /**
     * 
     * 描述：根据父id获取子菜单
     * 
     * @param parentId
     * @return
     * @author yangyongchuan 2016年10月16日 下午10:14:53
     * @version 1.0
     */
    public List<SystemMenu> getMenuByParentId(Long parentId);



    /**
     * 
     * 描述：检查用户是否具有该菜单，根据编号
     * 
     * @param params
     * @return
     * @author yangyongchuan 2017年3月6日 下午4:34:01
     * @version 1.0
     */
    public SystemMenu checkMenuBySn(QueryParams params);
}
