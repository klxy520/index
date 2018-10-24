package com.jc.cz_index.fem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.fem.service.IAddressService;
import com.jc.cz_index.dao.IAddressDao;
import com.jc.cz_index.model.Address;

/**
 * address Service 实现 Created by 
 */
@Service
public class AddressService extends BaseService<Address> implements IAddressService {
	@Autowired
	private IAddressDao addressDao;

	@Override
	public IDataProvider<Address, Long> getModelDao() {
		return this.addressDao;
	}

	@Override
	public int saveOrUpdate(Address address) {
		return addressDao.saveOrUpdate(address);
	}
}

