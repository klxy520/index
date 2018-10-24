/**
 * 2016/9/26 21:27:48 Jack Liu created.
 */

package com.jc.cz_index.dao;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.SystemUserRole;

/**
 * 系统用户角色 Ibatis Dao 接口 Created by Jack Liu on 2016/09/26.
 */
@Repository
public interface ISystemUserRoleDao extends IDataProvider<SystemUserRole, Long> {

}
