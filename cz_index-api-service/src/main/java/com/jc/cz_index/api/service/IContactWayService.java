package com.jc.cz_index.api.service;

import com.jc.cz_index.model.ContactWay;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.ResponseEntity;

/**
 * contactWay Service 接口 Created by 
 */
public interface IContactWayService extends IBaseService<ContactWay> {

	/**
	 * 
	 * 描述：注册联系方式信息
	 * @param person
	 * @return
	 * @author wangdeyou 2017年12月28日 下午7:18:07 
	 * @version 1.0
	 */
	ResponseEntity registerContactWays(Person person) throws Exception;
	
	/**
	 * 
	 * 描述：更新联系方式信息
	 * @param person
	 * @return
	 * @author wangdeyou 2017年12月28日 下午7:19:16 
	 * @version 1.0
	 */
	ResponseEntity updateContactWays(Person person) throws Exception;
	
	/**
	 * 
	 * 描述：删除联系方式信息
	 * @param person
	 * @return
	 * @author wangdeyou 2017年12月28日 下午7:19:58 
	 * @version 1.0
	 */
	ResponseEntity removeContactWays(Person person) throws Exception;
	
	/**
	 * 
	 * 描述：根据MPIID获取所有联系方式
	 * @param person
	 * @return
	 * @author wangdeyou 2017年12月29日 下午4:26:33 
	 * @version 1.0
	 */
	ResponseEntity getContactWays(Person person);
}

