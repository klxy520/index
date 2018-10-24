package com.jc.cz_index.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.core.service.IUpdateResidentLogService;
import com.jc.cz_index.dao.IUpdateResidentLogDao;
import com.jc.cz_index.model.UpdateResidentLog;

/**
 * updateResidentLog Service 实现 Created by 
 */
@Service
public class UpdateResidentLogService extends BaseService<UpdateResidentLog> implements IUpdateResidentLogService
{
	@Autowired
	private IUpdateResidentLogDao updateResidentLogDao;

	@Override
	public IDataProvider<UpdateResidentLog, Long> getModelDao() {
		// TODO Auto-generated method stub
		return this.updateResidentLogDao;
	}
}

