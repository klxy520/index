package com.jc.cz_index.fem.service;

import com.jc.cz_index.model.Contact;

/**
 * contact Service 接口 Created by 
 */
public interface IContactService extends IBaseService<Contact> {
    /**
     * 
     * 描述：添加或修改对象
     * 
     * @param contact
     * @return
     * @author yangyongchuan 2018年1月4日 下午6:02:59
     * @version 1.0
     */
    int insertOrUpdate(Contact contact);
}

