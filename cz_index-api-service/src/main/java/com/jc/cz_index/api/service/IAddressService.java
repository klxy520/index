package com.jc.cz_index.api.service;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.model.Address;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.ResponseEntity;

/**
 * address Service 接口 Created by 
 */
public interface IAddressService extends IBaseService<Address> {

	/**
	 * 
	 * 描述：添加地址信息
	 * @param address
	 * @throws BaseException
	 * @author wangdeyou 2017年12月28日 下午1:15:50 
	 * @version 1.0
	 */
	ResponseEntity registerAddresses(Person person) throws Exception;
	
	/**
	 * 
	 * 描述：更新地址信息
	 * @param address
	 * @throws BaseException
	 * @author wangdeyou 2017年12月28日 下午1:16:51 
	 * @version 1.0
	 */
	ResponseEntity updateAddresses(Person person) throws Exception;
	
	/**
	 * 
	 * 描述：删除地址信息
	 * @param id
	 * @throws BaseException
	 * @author wangdeyou 2017年12月28日 下午1:17:01 
	 * @version 1.0
	 */
	ResponseEntity removeAddresses(Person person) throws Exception;
	
	/**
	 * 
	 * 描述：获取已注册的地址信息
	 * @param person
	 * @return
	 * @author wangdeyou 2017年12月28日 下午9:09:28 
	 * @version 1.0
	 */
	ResponseEntity getAddresses(Person person);
}

