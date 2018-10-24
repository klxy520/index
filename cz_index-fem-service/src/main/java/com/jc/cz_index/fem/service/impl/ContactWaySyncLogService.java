package com.jc.cz_index.fem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.fem.service.IContactWaySyncLogService;
import com.jc.cz_index.dao.IContactWaySyncLogDao;
import com.jc.cz_index.model.ContactWaySyncLog;

/**
 * contactWaySyncLog Service 实现 Created by 
 */
@Service
public class ContactWaySyncLogService extends BaseService<ContactWaySyncLog> implements IContactWaySyncLogService {
	@Autowired
	private IContactWaySyncLogDao contactWaySyncLogDao;

	@Override
	public IDataProvider<ContactWaySyncLog, Long> getModelDao() {
		return this.contactWaySyncLogDao;
	}
}

