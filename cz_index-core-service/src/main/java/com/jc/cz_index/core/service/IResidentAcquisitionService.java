package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.ResidentAcquisition;
import com.jc.cz_index.model.SystemUser;

/**
 * 
 * 描述：居民信息采集Service 接口
 * 
 * @author sunxuefeng 2017年10月20日 上午10:11:16
 * @version 1.0
 */
public interface IResidentAcquisitionService extends IBaseService<ResidentAcquisition> {
    /**
     * 
     * 描述:查询居民信息采集列表页面的数据
     * 
     * @param params
     * @param start
     * @param size
     * @return
     * @author sunxuefeng 2017年10月20日 上午11:24:03
     * @version 1.0
     */
    PagedList<ResidentAcquisition> queryResidentAcquisitionList(QueryParams params, Integer start, Integer size);



    /**
     * 描述：根据id删除居民采集信息
     * 
     * @param id
     * @return
     * @author sunxuefeng 2017年10月23日 下午2:49:34
     * @version 1.0
     */
    ResponseData deleteResidentAcquisitionById(Long id, SystemUser systemUser) throws Exception;



    /**
     * 
     * 描述：向居民采集信息表中批量插入数据
     * 
     * @param list
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年10月24日 下午1:33:19
     * @version 1.0
     */
    ResponseData bathAddResidentAcquisitions(List<ResidentAcquisition> list) throws Exception;



    /***
     * 
     * 描述：添加单个居民采集信息
     * 
     * @param residentAcquisition
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年10月25日 下午2:34:47
     * @version 1.0
     */
    ResponseData addResidentAcquisition(ResidentAcquisition residentAcquisition, SystemUser systemUser) throws Exception;



    /**
     * 
     * 描述：修改单个居民采集信息
     * 
     * @param residentAcquisition
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年10月25日 下午3:49:20
     * @version 1.0
     */
    ResponseData updateResidentAcquisition(ResidentAcquisition residentAcquisition, SystemUser systemUser) throws Exception;



    /**
     * 
     * 描述：通过身份证号查询单个居民信息是否存在
     * 
     * @param identityNumber
     * @return
     * @author sunxuefeng 2017年10月26日 上午10:16:56
     * @version 1.0
     */
    Boolean queryResidentsInfoByIdentityNumberExistence(String identityNumber);
}
