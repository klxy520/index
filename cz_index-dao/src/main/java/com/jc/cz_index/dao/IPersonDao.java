package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.Person;

/**
 * Person Ibatis Dao 接口 Created by 
 */
@Repository
public interface IPersonDao extends IDataProvider<Person, Long> {
    /**
     * 
     * 描述：通过mpiId获取person信息
     * @param mpiId
     * @return
     * @author fengshengliang 2017年12月28日 下午5:15:02 
     * @version 1.0
     */
    Person getObjectByMpiId(String mpiId);
    
    /**
     * 
     * 描述：通过mpiId获取未删除的person信息
     * @param mpiId
     * @return
     * @author fengshengliang 2017年12月28日 下午5:15:02 
     * @version 1.0
     */
    Person  getEnableObjectByMpiId(String mpiId);
    
    /**
     * 
     * 描述：获取未同步的数据
     * @param address
     * @return
     * @author fengshengliang 2018年1月4日 上午9:56:18 
     * @version 1.0
     */
    List<Person> getUnSyncObjectList(Long id);
    
    /**
     * 
     * 描述：更新数据
     * @param person
     * @return
     * @author fengshengliang 2018年1月4日 下午6:12:33 
     * @version 1.0
     */
    int saveOrUpdate(Person person);
    
    /**
     * 
     * 描述：通过证件信息查询主索引简要信息
     * @param params
     * @return
     * @author fengshengliang 2018年1月16日 上午9:55:17 
     * @version 1.0
     */
    List<Person> getObjectByCertificate(QueryParams params);
    
    /**
     * 
     * 描述：通过地址信息查询主索引简要信息
     * @param params
     * @return
     * @author fengshengliang 2018年1月16日 上午9:56:11 
     * @version 1.0
     */
    List<Person> getObjectByAddress(QueryParams params);
    
    /**
     * 
     * 描述：通过联系方式信息查询主索引简要信息
     * @param params
     * @return
     * @author fengshengliang 2018年1月16日 上午10:20:48 
     * @version 1.0
     */
    List<Person> getObjectByContactWay(QueryParams params);
    
    /**
     * 
     * 描述：通过卡信息查询主索引简要信息
     * @param params
     * @return
     * @author fengshengliang 2018年1月16日 上午11:08:45 
     * @version 1.0
     */
    List<Person> getObjectByCard(QueryParams params);
    
    /**
     * 
     * 描述：通过联系人信息查询主索引简要信息
     * @param params
     * @return
     * @author fengshengliang 2018年1月16日 上午11:24:26 
     * @version 1.0
     */
    List<Person> getObjectByContact(QueryParams params);

}


