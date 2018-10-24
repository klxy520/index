package com.jc.cz_index.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.core.service.IDemoService;
import com.jc.cz_index.dao.IDemoDao;
import com.jc.cz_index.model.Demo;

/**
 * demo Service 实现 Created by 
 */
@Service
public class DemoService extends BaseService<Demo> implements IDemoService
{
	@Autowired
	private IDemoDao demoDao;

	@Override
	public IDataProvider<Demo, Long> getModelDao() {
		// TODO Auto-generated method stub
		return this.demoDao;
	}
}

