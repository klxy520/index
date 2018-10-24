package com.jc.cz_index.fem.service;

import com.jc.cz_index.model.ContactWay;

/**
 * contactWay Service 接口 Created by 
 */
public interface IContactWayService extends IBaseService<ContactWay> {

	/**
	 * 
	 * 描述：同步联系方式数据
	 * @param contactWay
	 * @return
	 * @author wangdeyou 2018年1月5日 上午11:32:08 
	 * @version 1.0
	 */
	int saveOrUpdate(ContactWay contactWay);
}

