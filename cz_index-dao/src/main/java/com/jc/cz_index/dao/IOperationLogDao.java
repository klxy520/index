/**
 * 2016/11/1 14:49:15 Jack Liu created.
 */

package com.jc.cz_index.dao;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.OperationLog;

/**
 * 操作日志 Ibatis Dao 接口 Created by Jack Liu on 2016/11/01.
 */
@Repository
public interface IOperationLogDao extends IDataProvider<OperationLog, Long> {

}
