package com.jc.cz_index.fem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IPersonSyncLogDao;
import com.jc.cz_index.fem.service.IPersonSyncLogService;
import com.jc.cz_index.model.PersonSyncLog;
import com.jc.cz_index.model.RequestEntity;
import com.jc.cz_index.model.ResponseEntity;

/**
 * personSyncLog Service 实现 Created by 
 */
@Service
public class PersonSyncLogService extends BaseService<PersonSyncLog> implements IPersonSyncLogService {
	@Autowired
	private IPersonSyncLogDao personSyncLogDao;

	@Override
	public IDataProvider<PersonSyncLog, Long> getModelDao() {
		return this.personSyncLogDao;
	}
	
}

