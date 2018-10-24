package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.model.ContactWay;

/**
 * contactWay Service 接口 Created by 
 */
public interface IContactWayService extends IBaseService<ContactWay> {

	public ResponseData addContactWay(ContactWay contactWay);

	public ResponseData updateContactWay(ContactWay contactWay);

	public ResponseData deleteContactWayById(Long id);
	
	/**
	 * 
	 * 描述：获取同步数据
	 * @param id
	 * @return
	 * @author wangdeyou 2018年1月5日 上午10:49:38 
	 * @version 1.0
	 */
	List<ContactWay> excuteTask(Long id);

}

