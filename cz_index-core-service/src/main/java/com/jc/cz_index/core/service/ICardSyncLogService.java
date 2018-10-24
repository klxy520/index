package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.syncLog.CardSyncLogDto;
import com.jc.cz_index.model.CardSyncLog;

/**
 * cardSyncLog Service 接口 Created by
 */
public interface ICardSyncLogService extends IBaseService<CardSyncLog> {
    /**
     * 添加异步日志 描述：
     * 
     * @param cardId
     * @param details
     * @param userid
     * @author yangjunhui 2018年1月4日 下午3:48:53
     * @version 1.0
     */
    public void addCardSyncLog(Long cardId, String details, Long userid);



    /**
     * 
     * 描述：查询异步日志列表
     * 
     * @param params
     * @param start
     * @param size
     * @return
     * @author yangjunhui 2018年1月4日 下午3:49:11
     * @version 1.0
     */
    public PagedList<CardSyncLogDto> queryCardSyncLogList(QueryParams params, int start, int size);



    /**
     * 
     * 描述：插入一组对象
     * 
     * @param list
     * @return
     * @author yangyongchuan 2018年1月4日 下午6:29:32
     * @version 1.0
     */
    public int inserOrUpdatetList(List<CardSyncLog> list);
}
