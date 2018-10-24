package com.jc.cz_index.core.service;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.model.AdministrativeManagement;

/**
 * administrativeManagement Service 接口 Created by 
 */
public interface IAdministrativeManagementService extends IBaseService<AdministrativeManagement>
{
    /**
     * 
     * 描述：添加行政机构
     * @param administrativeManagement
     * @throws BaseException
     * @author wangdeyou 2017年9月1日 上午10:30:32 
     * @version 1.0
     */
    void addAdministrativeManagement(AdministrativeManagement administrativeManagement) throws BaseException;
    
    /**
     * 
     * 描述：修改行政机构
     * @param administrativeManagement
     * @throws BaseException
     * @author wangdeyou 2017年9月1日 上午10:30:47 
     * @version 1.0
     */
    void updateAdministrativeManagement(AdministrativeManagement administrativeManagement) throws BaseException;
    
    /**
     * 
     * 描述：删除行政机构(软删除)
     * @param id
     * @throws BaseException
     * @author wangdeyou 2017年9月1日 上午10:30:55 
     * @version 1.0
     */
    void del(String id) throws BaseException;
}

