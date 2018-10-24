/**
 * 2016/9/26 21:27:43 Jack Liu created.
 */

package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.Role;

/**
 * 角色表兼顾权限的表示 Ibatis Dao 接口 Created by Jack Liu on 2016/09/26.
 */
@Repository
public interface IRoleDao extends IDataProvider<Role, Long> {

    /**
     * 
     * 描述：获取用户角色
     * 
     * @param authId
     * @return
     * @author yangyongchuan 2016年9月26日 下午11:17:05
     * @version 1.0
     */
    List<Role> findEnabledRoleByAuthId(Long authId);



    /**
     * 
     * 描述：获取菜单需要的权限
     * 
     * @param menuId
     * @return
     * @author yangyongchuan 2016年9月27日 上午9:34:44
     * @version 1.0
     */
    List<Role> findByMenuForAuthority(Long menuId);

}
