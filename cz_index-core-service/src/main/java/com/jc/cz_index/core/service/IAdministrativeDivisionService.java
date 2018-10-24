package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.model.AdministrativeDivision;

/**
 * administrativeDivision Service 接口 Created by 
 */
public interface IAdministrativeDivisionService extends IBaseService<AdministrativeDivision>
{
    /**
     * 
     * 描述：添加行政区域
     * @param administrativeDivision
     * @throws BaseException
     * @author wangdeyou 2017年9月1日 上午10:05:46 
     * @version 1.0
     */
    void addAdministrativedivision(AdministrativeDivision administrativeDivision) throws BaseException;
    
    /**
     * 
     * 描述：修改行政区域
     * @param administrativeDivision
     * @throws BaseException
     * @author wangdeyou 2017年9月1日 上午10:05:59 
     * @version 1.0
     */
    void updateAdministrativedivision(AdministrativeDivision administrativeDivision) throws BaseException;
    
    /**
     * 
     * 描述：删除行政区域(软删除)
     * @param id
     * @throws BaseException
     * @author wangdeyou 2017年9月1日 上午10:15:32 
     * @version 1.0
     */
    void del(String id) throws BaseException;
    
    /**
     * 
     * 描述：通过父级id获取所有机构区域
     * @param id
     * @return
     * @author wangdeyou 2017年9月5日 下午1:36:49 
     * @version 1.0
     */
    List<AdministrativeDivision> getAdministrativeDivisionList(Long id);
    
    /**
     * 
     * 描述：通过ID查询机构区域
     * @param id
     * @return
     * @author wangdeyou 2017年9月6日 下午3:24:56 
     * @version 1.0
     */
    List<Object> getAdminDiv(Long id);
}

