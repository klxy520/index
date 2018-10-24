package com.jc.cz_index.api.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.api.service.IFrontEndMachineService;
import com.jc.cz_index.dao.IFrontEndMachineDao;
import com.jc.cz_index.model.FrontEndMachine;

/**
 * frontEndMachine Service 实现 Created by 
 */
@Service
public class FrontEndMachineService extends BaseService<FrontEndMachine> implements IFrontEndMachineService {
	@Autowired
	private IFrontEndMachineDao frontEndMachineDao;

	@Override
	public IDataProvider<FrontEndMachine, Long> getModelDao() {
		return this.frontEndMachineDao;
	}
}

