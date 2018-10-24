package com.jc.cz_index.api.service;

import com.jc.cz_index.model.Certificate;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.ResponseEntity;

/**
 * certificate Service 接口 Created by 
 */
public interface ICertificateService extends IBaseService<Certificate> {
    /**
     * 
     * 描述：根据MPIID获取所有证件
     * @param person
     * @return
     * @author fengshengliang 2017年12月28日 下午10:11:49 
     * @version 1.0
     */
    ResponseEntity getCertificates(Person person);
    
    /**
     * 
     * 描述：注册证件信息
     * @param person
     * @return
     * @author fengshengliang 2017年12月28日 下午10:22:34 
     * @version 1.0
     * @throws Exception 
     */
    ResponseEntity registerCertificates(Person person) throws Exception;
    
    /**
     * 
     * 描述：更新证件信息
     * @param person
     * @return
     * @author fengshengliang 2017年12月29日 上午10:03:04 
     * @version 1.0
     * @throws Exception 
     */
    ResponseEntity updateCertificates(Person person) throws Exception;
    
    /**
     * 
     * 描述：删除证件信息
     * @param person
     * @return
     * @author fengshengliang 2017年12月29日 上午10:01:48 
     * @version 1.0
     * @throws Exception 
     */
    ResponseEntity removeCertificates(Person person) throws Exception;
    
}

