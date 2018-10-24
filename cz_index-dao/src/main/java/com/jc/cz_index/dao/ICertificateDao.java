package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.Certificate;

/**
 * certificate Ibatis Dao 接口 Created by
 */
@Repository
public interface ICertificateDao extends IDataProvider<Certificate, Long> {
    /**
     * 
     * 描述：查询所有证件
     * 
     * @param MpiId
     * @return
     * @author fengshengliang 2017年12月26日 下午7:51:58
     * @version 1.0
     */
    List<Certificate> getObjectByMpiId(String mpiId);
    
    /**
     * 
     * 描述：获取未同步记录
     * @param id
     * @return
     * @author fengshengliang 2018年1月4日 下午8:59:36 
     * @version 1.0
     */
    List<Certificate> getUnSyncObjectList(Long id);
    
    /**
     * 
     * 描述：更新数据
     * @param certificate
     * @return
     * @author fengshengliang 2018年1月4日 下午6:12:33 
     * @version 1.0
     */
    int saveOrUpdate(Certificate certificate);
}
