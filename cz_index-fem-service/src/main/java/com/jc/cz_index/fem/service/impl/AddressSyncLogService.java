package com.jc.cz_index.fem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.fem.service.IAddressSyncLogService;
import com.jc.cz_index.dao.IAddressSyncLogDao;
import com.jc.cz_index.model.AddressSyncLog;

/**
 * addressSyncLog Service 实现 Created by 
 */
@Service
public class AddressSyncLogService extends BaseService<AddressSyncLog> implements IAddressSyncLogService {
	@Autowired
	private IAddressSyncLogDao addressSyncLogDao;

	@Override
	public IDataProvider<AddressSyncLog, Long> getModelDao() {
		return this.addressSyncLogDao;
	}
}

