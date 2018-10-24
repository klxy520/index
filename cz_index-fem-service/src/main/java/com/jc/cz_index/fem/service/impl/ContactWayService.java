package com.jc.cz_index.fem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.fem.service.IContactWayService;
import com.jc.cz_index.dao.IContactWayDao;
import com.jc.cz_index.model.ContactWay;

/**
 * contactWay Service 实现 Created by 
 */
@Service
public class ContactWayService extends BaseService<ContactWay> implements IContactWayService {
	@Autowired
	private IContactWayDao contactWayDao;

	@Override
	public IDataProvider<ContactWay, Long> getModelDao() {
		return this.contactWayDao;
	}

	@Override
	public int saveOrUpdate(ContactWay contactWay) {
		return contactWayDao.saveOrUpdate(contactWay);
	}
}

