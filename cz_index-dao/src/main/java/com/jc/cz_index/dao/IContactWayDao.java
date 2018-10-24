package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.ContactWay;

/**
 * contactWay Ibatis Dao 接口 Created by
 */
@Repository
public interface IContactWayDao extends IDataProvider<ContactWay, Long> {
    /**
     * 
     * 描述：查询所有联系方式
     * 
     * @param MpiId
     * @return
     * @author fengshengliang 2017年12月26日 下午7:51:58
     * @version 1.0
     */
    List<ContactWay> getObjectByMpiId(String mpiId);
    
    /**
     * 
     * 描述：获取所有为同步数据
     * @param id
     * @return
     * @author wangdeyou 2018年1月5日 上午10:56:15 
     * @version 1.0
     */
    List<ContactWay> getUnSyncObjectList(Long id);
    
    /**
     * 
     * 描述：同步联系方式数据
     * @param contactWay
     * @return
     * @author wangdeyou 2018年1月5日 上午11:26:37 
     * @version 1.0
     */
    int saveOrUpdate(ContactWay contactWay);
}
