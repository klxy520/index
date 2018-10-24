package com.jc.cz_index.fem.service;

import com.jc.cz_index.model.Certificate;

/**
 * certificate Service 接口 Created by 
 */
public interface ICertificateService extends IBaseService<Certificate> {
    /**
     * 
     * 描述：同步数据
     * 
     * @param person
     * @return
     * @author fengshengliang 2018年1月4日 下午6:05:18
     * @version 1.0
     */
    int saveOrUpdate(Certificate certificate);
}

