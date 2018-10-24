package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.dto.syncLog.ContactSyncLogDto;
import com.jc.cz_index.model.ContactSyncLog;

/**
 * contactSyncLog Ibatis Dao 接口 Created by 
 */
@Repository
public interface IContactSyncLogDao extends IDataProvider<ContactSyncLog, Long> {

	/**
	 * 
	 * 描述：插入一组对象
	 * @param list
	 * @return
	 * @author wangdeyou 2018年1月4日 下午3:48:33 
	 * @version 1.0
	 */
	public int inserOrUpdatetList(List<ContactSyncLog> list);



    /***
     * 
     * 描述：查询所有联系人的同步日志
     * 
     * @param params
     * @return
     * @author sunxuefeng 2018年1月4日 下午8:21:49
     * @version 1.0
     */
    public List<ContactSyncLogDto> selectContactSyncLog(QueryParams params);



    /***
     * 
     * 描述：统计所有联系人的记录条数
     * 
     * @param params
     * @return
     * @author sunxuefeng 2018年1月4日 下午8:21:49
     * @version 1.0
     */
    public int countContactSyncLog(QueryParams params);

}


