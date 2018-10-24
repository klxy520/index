package com.jc.cz_index.fem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.ICertificateSyncLogDao;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.fem.service.ICertificateSyncLogService;
import com.jc.cz_index.model.CertificateSyncLog;

/**
 * certificateSyncLog Service 实现 Created by
 */
@Service
public class CertificateSyncLogService extends BaseService<CertificateSyncLog> implements ICertificateSyncLogService {
	@Autowired
	private ICertificateSyncLogDao certificateSyncLogDao;

	@Override
	public IDataProvider<CertificateSyncLog, Long> getModelDao() {
		return this.certificateSyncLogDao;
	}


}