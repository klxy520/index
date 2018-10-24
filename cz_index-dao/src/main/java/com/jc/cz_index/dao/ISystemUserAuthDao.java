/**
 * 2016/9/26 21:27:47 Jack Liu created.
 */

package com.jc.cz_index.dao;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.SystemUserAuth;

/**
 * 系统用户认证信息 Ibatis Dao 接口 Created by Jack Liu on 2016/09/26.
 */
@Repository
public interface ISystemUserAuthDao extends IDataProvider<SystemUserAuth, Long> {

}
