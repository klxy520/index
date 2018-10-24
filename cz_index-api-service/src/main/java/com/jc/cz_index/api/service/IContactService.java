package com.jc.cz_index.api.service;

import com.jc.cz_index.model.Contact;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.ResponseEntity;

/**
 * contact Service 接口 Created by 
 */
public interface IContactService extends IBaseService<Contact> {

	/**
	 * 
	 * 描述：注册联系人信息
	 * @param person
	 * @return
	 * @author wangdeyou 2017年12月29日 上午11:45:55 
	 * @version 1.0
	 */
	ResponseEntity registerContacts(Person person) throws Exception;
	
	/**
	 * 
	 * 描述：更新联系人信息
	 * @param person
	 * @return
	 * @author wangdeyou 2017年12月29日 上午11:46:12 
	 * @version 1.0
	 */
	ResponseEntity updateContacts(Person person) throws Exception;
	
	/**
	 * 
	 * 描述：删除联系人信息
	 * @param person
	 * @return
	 * @author wangdeyou 2017年12月29日 上午11:46:26 
	 * @version 1.0
	 */
	ResponseEntity removeContacts(Person person) throws Exception;
	
	/**
	 * 
	 * 描述：根据MPIID获取所有联系人
	 * @param person
	 * @return
	 * @author wangdeyou 2017年12月29日 下午4:14:05 
	 * @version 1.0
	 */
	ResponseEntity getContacts(Person person);
}

