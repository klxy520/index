package com.jc.cz_index.fem.service;

import com.jc.cz_index.model.Address;

/**
 * address Service 接口 Created by 
 */
public interface IAddressService extends IBaseService<Address> {
	
	/**
	 * 
	 * 描述：同步地址数据
	 * @param address
	 * @return
	 * @author wangdeyou 2018年1月4日 下午9:09:44 
	 * @version 1.0
	 */
	int saveOrUpdate(Address address);
}

