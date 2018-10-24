package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.model.Certificate;

/**
 * certificate Service 接口 Created by
 */
public interface ICertificateService extends IBaseService<Certificate> {

    public ResponseData addCertificate(Certificate certificate);



    public ResponseData updateCertificate(Certificate certificate);



    public ResponseData deleteCertificateById(Long id);



    /**
     * 
     * 描述：定时同步任务
     * 
     * @param id
     * @return
     * @author fengshengliang 2018年1月4日 下午8:56:26
     * @version 1.0
     */
    List<Certificate> excuteTask(Long id);
}
