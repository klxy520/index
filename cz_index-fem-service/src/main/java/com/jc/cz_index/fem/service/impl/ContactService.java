package com.jc.cz_index.fem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IContactDao;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.fem.service.IContactService;
import com.jc.cz_index.model.Contact;

/**
 * contact Service 实现 Created by
 */
@Service
public class ContactService extends BaseService<Contact> implements IContactService {
    @Autowired
    private IContactDao contactDao;



    @Override
    public IDataProvider<Contact, Long> getModelDao() {
        return this.contactDao;
    }



    @Override
    public int insertOrUpdate(Contact contact) {
        return contactDao.insertOrUpdate(contact);
    }

}
