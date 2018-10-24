package com.jc.cz_index.core.service;

import com.jc.cz_index.model.InfoManagementLog;


/**
 * 信息操作日志 Service 接口 Created by yangjunhui on 2016/11/01.
 */
public interface IinfoManagementLogService extends IBaseService<InfoManagementLog> {
    /**
     * 
     * 描述：添加日志系统信息管理日志service
     * 
     * @param formName
     *            修改数据表名称
     * @param recordId
     *            修改数据ID
     * @param type
     *            修改类型：1增加，-1删除，0修改
     * @param detail
     *            详情
     * @param remark
     *            备注
     * @author yangjunhui 2017年8月29日 下午4:27:45
     * @version 1.0
     */
    public void addInfoManagementLog(String formName, Long recordId, Integer type, String detail, String remark);



    /**
     * 
     * 描述： 用于查询指定日志详情
     * 
     * @param id
     *            日志ID
     * @return
     * @author yangjunhui 2017年8月29日 下午4:36:40
     * @version 1.0
     */
    public InfoManagementLog getLogById(Long id);

}
