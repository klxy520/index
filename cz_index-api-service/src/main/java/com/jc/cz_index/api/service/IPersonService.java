package com.jc.cz_index.api.service;

import java.util.List;

import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.ResponseEntity;

/**
 * Empi Service 接口 Created by 
 */
public interface IPersonService extends IBaseService<Person> {
    
    
    /**
     * 
     * 描述：个人主索引注册
     * @param request
     * @return
     * @author fengshengliang 2017年12月26日 下午6:02:22 
     * @version 1.0
     * @throws Exception 
     */
    ResponseEntity submitMPI(Person person) throws Exception;
    
    /**
     * 
     * 描述：个人主索引查询
     * @param person
     * @param create
     * @return
     * @author fengshengliang 2017年12月26日 下午7:24:50 
     * @version 1.0
     * @throws Exception 
     */
    ResponseEntity getMPIID(Person person,boolean create) throws Exception;
    
    /**
     * 
     * 描述：为虚拟化应用提供 主索引id 和 居民健康卡卡号(身份证)
     * @param person
     * @param create
     * @return
     * @throws Exception
     * @author fengshengliang 2018年1月11日 上午9:43:46 
     * @version 1.0
     */
    public ResponseEntity getMPIIDForKtVsirtual(Person person,boolean create) throws Exception;
    /**
     * 
     * 描述：个人简要信息查询
     * @param person
     * @return
     * @author fengshengliang 2017年12月26日 下午7:41:32 
     * @version 1.0
     */
    ResponseEntity getMPI(Person person);
    
    /**
     * 
     * 描述：个人详细信息查询
     * @param person
     * @return
     * @author fengshengliang 2017年12月26日 下午7:42:14 
     * @version 1.0
     */
    ResponseEntity getMPIDetail(Person person);
    
    /**
     * 
     * 描述：个人信息更新服务
     * @param person
     * @return
     * @author fengshengliang 2017年12月26日 下午7:42:52 
     * @version 1.0
     * @throws Exception 
     */
    ResponseEntity updateMPI(Person person) throws Exception;
    
    /**
     * 
     * 描述：个人信息注销服务
     * @param person
     * @return
     * @author fengshengliang 2017年12月26日 下午7:43:35 
     * @version 1.0
     * @throws Exception 
     */
    ResponseEntity writeOffMPI(Person person) throws Exception;
    
    /**
     * 
     * 描述：个人信息打死亡标记
     * @param person
     * @return
     * @author fengshengliang 2017年12月26日 下午7:44:03 
     * @version 1.0
     * @throws Exception 
     */
    ResponseEntity deathMPI(Person person) throws Exception;
    
    /**
     * 
     * 描述：个人信息取消死亡标记
     * @param person
     * @author fengshengliang 2017年12月26日 下午7:44:45 
     * @version 1.0
     * @throws Exception 
     */
    ResponseEntity undeathMPI(Person person) throws Exception;
    
    /**
     * 
     * 描述：个人主索引批量注册服务
     * @param persons
     * @param batch
     * @author fengshengliang 2017年12月26日 下午7:45:09 
     * @version 1.0
     */
    ResponseEntity batchSubmitMPI(List<Person> persons,boolean batch) throws Exception;
    /**
     * 
     * 描述： 实名认证
     * @param authPersons
     * @return
     * @author yangjunhui 2018年1月24日 下午1:59:00 
     * @version 1.0
     */
    
    ResponseEntity getNameAuth(Person authPersons);
}

