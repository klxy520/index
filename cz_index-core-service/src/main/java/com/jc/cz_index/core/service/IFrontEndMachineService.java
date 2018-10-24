package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.model.FrontEndMachine;

/**
 * frontEndMachine Service 接口 Created by 
 */
public interface IFrontEndMachineService extends IBaseService<FrontEndMachine> {
	
	/**
	 * 
	 * 描述：添加前置机
	 * @param frontEndMachine
	 * @throws BaseException
	 * @author wangdeyou 2017年12月26日 下午3:25:28 
	 * @version 1.0
	 */
	void addFronEndMachine(FrontEndMachine frontEndMachine) throws BaseException;
	
	/**
	 * 
	 * 描述：修改前置机
	 * @param frontEndMachine
	 * @throws BaseException
	 * @author wangdeyou 2017年12月26日 下午6:34:19 
	 * @version 1.0
	 */
	void updateFronEndMachine(FrontEndMachine frontEndMachine) throws BaseException;
	
	/**
	 * 
	 * 描述：启用前置机
	 * @param id
	 * @throws BaseException
	 * @author wangdeyou 2017年12月26日 下午6:52:16 
	 * @version 1.0
	 */
	void enable(String id) throws BaseException;
	
	/**
	 * 
	 * 描述：禁用前置机
	 * @param id
	 * @throws BaseException
	 * @author wangdeyou 2017年12月26日 下午7:02:06 
	 * @version 1.0
	 */
	void disable(String id) throws BaseException;
	
	/**
	 * 
	 * 描述：删除前置机
	 * @param id
	 * @throws BaseException
	 * @author wangdeyou 2017年12月27日 上午10:41:58 
	 * @version 1.0
	 */
	void del(String id) throws BaseException;
	/**
	 * @Title: queryEffectiveList   
	 * @Description: TODO(查询有效的前置机)   
	 * @param: @return      
	 * @return: List<FrontEndMachine>      
	 * @throws
	 */
	public List<FrontEndMachine> queryEffectiveList();
}

