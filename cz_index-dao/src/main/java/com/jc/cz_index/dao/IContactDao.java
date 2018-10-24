package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.Contact;

/**
 * contact Ibatis Dao 接口 Created by
 */
@Repository
public interface IContactDao extends IDataProvider<Contact, Long> {
    /**
     * 
     * 描述：查询所有联系人
     * 
     * @param MpiId
     * @return
     * @author fengshengliang 2017年12月26日 下午7:51:58
     * @version 1.0
     */
    List<Contact> getObjectByMpiId(String mpiId);



    /**
     * 
     * 描述：获取未同步的数据
     * 
     * @param femId
     * @return
     * @author yangyongchuan 2018年1月4日 下午5:29:48
     * @version 1.0
     */
    List<Contact> getUnSyncObjectList(Long femId);



    /**
     * 
     * 描述：添加或修改对象
     * 
     * @param contact
     * @return
     * @author yangyongchuan 2018年1月4日 下午6:02:59
     * @version 1.0
     */
    int insertOrUpdate(Contact contact);
}
