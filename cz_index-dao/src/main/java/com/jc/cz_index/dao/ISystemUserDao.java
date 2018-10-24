/**
 * 2016/9/26 21:27:46 Jack Liu created.
 */

package com.jc.cz_index.dao;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.SystemUser;

/**
 * 系统用户信息 Ibatis Dao 接口 Created by Jack Liu on 2016/09/26.
 */
@Repository
public interface ISystemUserDao extends IDataProvider<SystemUser, Long> {

}
