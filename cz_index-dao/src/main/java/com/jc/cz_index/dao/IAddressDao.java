package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.Address;

/**
 * address Ibatis Dao 接口 Created by
 */
@Repository
public interface IAddressDao extends IDataProvider<Address, Long> {
    /**
     * 
     * 描述：查询所有地址
     * 
     * @param MpiId
     * @return
     * @author fengshengliang 2017年12月26日 下午7:51:58
     * @version 1.0
     */
    List<Address> getObjectByMpiId(String mpiId);
    
    /**
     * 
     * 描述：获取未同步数据
     * @param id
     * @return
     * @author wangdeyou 2018年1月4日 下午6:20:34 
     * @version 1.0
     */
    List<Address> getUnSyncObjectList(Long id);
    
    /**
	 * 
	 * 描述：同步地址数据
	 * @param address
	 * @return
	 * @author wangdeyou 2018年1月4日 下午9:09:44 
	 * @version 1.0
	 */
    int saveOrUpdate(Address address);
}
