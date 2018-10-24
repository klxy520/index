package com.jc.cz_index.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.jc.cz_index.dto.syncLog.CardSyncLogDto;
import com.jc.cz_index.model.CardSyncLog;
/**
 * cardSyncLog Ibatis Dao 接口 Created by
 */
@Repository
public interface ICardSyncLogDao extends IDataProvider<CardSyncLog, Long> {
    /**
     * 插入一组对象
     *
     * @param list
     *            对象list
     * @return 影响的行数
     */
    public int insertOrUpdatetList(List<CardSyncLog> list);
	public List<CardSyncLogDto> queryCardSyncLogList(QueryParams params);
	public int querySyncLogCount(QueryParams params);

}
