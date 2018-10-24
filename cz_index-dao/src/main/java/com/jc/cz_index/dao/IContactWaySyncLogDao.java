package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.dto.syncLog.ContactWaySyncLogDto;
import com.jc.cz_index.model.ContactWaySyncLog;

/**
 * contactWaySyncLog Ibatis Dao 接口 Created by 
 */
@Repository
public interface IContactWaySyncLogDao extends IDataProvider<ContactWaySyncLog, Long> {

	/**
	 * 
	 * 描述：插入一组对象
	 * @param list
	 * @return
	 * @author wangdeyou 2018年1月4日 下午3:48:33 
	 * @version 1.0
	 */
	public int inserOrUpdatetList(List<ContactWaySyncLog> list);

	public List<ContactWaySyncLogDto> queryContactWaySyncLogList(QueryParams params);

	public int querySyncLogCount(QueryParams params);
	
}


