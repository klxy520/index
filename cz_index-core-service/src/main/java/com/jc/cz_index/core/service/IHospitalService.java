package com.jc.cz_index.core.service;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.model.Hospital;
import com.jc.cz_index.model.SystemUser;

/***
 * 
 * 描述：医院机构Service 接口
 * 
 * @author sunxuefeng 2018年1月3日 下午1:33:09
 * @version 1.0
 */
public interface IHospitalService extends IBaseService<Hospital> {
    /**
     * 
     * 描述：根据ID删除机构
     * 
     * @param id
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2018年1月3日 下午4:24:20
     * @version 1.0
     */
    ResponseData deleteHospitalById(Long id, SystemUser systemUser) throws Exception;



    /**
     * 
     * 描述：禁用/启用一个医院
     * 
     * @param id
     * @param systemUser
     * @param falg
     * @return
     * @throws Exception
     * @author sunxuefeng 2018年1月3日 下午5:06:42
     * @version 1.0
     */
    ResponseData disableAndEnableHospitalById(Long id, SystemUser systemUser, Integer falg) throws Exception;



    /**
     * 
     * 描述：添加单个医院信息
     * 
     * @param hospital
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2018年1月4日 上午9:56:46
     * @version 1.0
     */
    ResponseData addHospital(Hospital hospital, SystemUser systemUser) throws Exception;



    /**
     * 
     * 描述：修改单个医院
     * 
     * @param hospital
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2018年1月4日 上午9:57:58
     * @version 1.0
     */
    ResponseData updateHospital(Hospital hospital, SystemUser systemUser) throws Exception;
    /***
     * 
     * 描述：根据医院名称查询查询医院否存在
     * 
     * @param name
     * @return
     * @author sunxuefeng 2017年10月10日 上午10:14:31
     * @version 1.0
     */
    public Boolean queryHospitalByNameExistence(String name);
    /***
     * 
     * 描述：根据医院key查询查询医院否存在
     * 
     * @param name
     * @return
     * @author sunxuefeng 2017年10月10日 上午10:14:31
     * @version 1.0
     */
    public Boolean queryHospitalByKeyExistence(String key);
}
