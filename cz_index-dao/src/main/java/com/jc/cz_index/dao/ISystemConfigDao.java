/**
 * 2016/9/29 15:08:26 Jack Liu created.
 */

package com.jc.cz_index.dao;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.SystemConfig;

/**
 * 系统参数 Ibatis Dao 接口 Created by Jack Liu on 2016/09/29.
 */
@Repository
public interface ISystemConfigDao extends IDataProvider<SystemConfig, Long> {

}
