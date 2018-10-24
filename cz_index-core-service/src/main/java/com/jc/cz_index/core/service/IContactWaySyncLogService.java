package com.jc.cz_index.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.syncLog.ContactWaySyncLogDto;
import com.jc.cz_index.model.ContactWaySyncLog;

/**
 * contactWaySyncLog Service 接口 Created by 
 */
@Service
public interface IContactWaySyncLogService extends IBaseService<ContactWaySyncLog> {

	
	void addContactWaySyncLog(Long contactWayId, String details, Long userid);
	
	/**
	 * 
	 * 描述：批量更新
	 * @param cwsl
	 * @param fem_id
	 * @return
	 * @author wangdeyou 2018年1月5日 上午11:12:57 
	 * @version 1.0
	 */
	int doUpdateSyncInfo(List<ContactWaySyncLog> cwsl,Long fem_id);

	public PagedList<ContactWaySyncLogDto> queryContactWaySyncLogList(QueryParams params, int start, int size);

}

