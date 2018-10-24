package com.jc.cz_index.fem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.ICertificateDao;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.fem.service.ICertificateService;
import com.jc.cz_index.model.Certificate;

/**
 * certificate Service 实现 Created by
 */
@Service
public class CertificateService extends BaseService<Certificate> implements ICertificateService {
    @Autowired
    private ICertificateDao certificateDao;



    @Override
    public IDataProvider<Certificate, Long> getModelDao() {
        return this.certificateDao;
    }



    @Override
    public int saveOrUpdate(Certificate certificate) {
        return certificateDao.saveOrUpdate(certificate);
    }
}
