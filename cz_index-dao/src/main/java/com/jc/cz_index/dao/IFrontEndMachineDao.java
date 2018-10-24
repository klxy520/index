package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.jc.cz_index.model.FrontEndMachine;

/**
 * frontEndMachine Ibatis Dao 接口 Created by 
 */
@Repository
public interface IFrontEndMachineDao extends IDataProvider<FrontEndMachine, Long> {
	/**
	 * @Title: getEffectiveList   
	 * @Description: TODO(获取可用的前置机)   
	 * @param: @return      
	 * @return: List<FrontEndMachine>      
	 * @throws
	 */
	List<FrontEndMachine> getEffectiveList();
}


