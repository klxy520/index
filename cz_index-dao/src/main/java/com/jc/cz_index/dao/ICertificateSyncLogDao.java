package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.dto.syncLog.CertificateSyncLogDto;
import com.jc.cz_index.model.CertificateSyncLog;

/**
 * certificateSyncLog Ibatis Dao 接口 Created by
 */
@Repository
public interface ICertificateSyncLogDao extends IDataProvider<CertificateSyncLog, Long> {
    /**
     * 
     * 描述：批量插入/更新
     * 
     * @param certificateSyncLog
     * @return
     * @author fengshengliang 2018年1月4日 下午3:38:25
     * @version 1.0
     */
    public int saveOrUpdateList(List<CertificateSyncLog> certificateSyncLog);

	public List<CertificateSyncLogDto> queryCertificateSyncLogList(QueryParams params);

	public int querySyncLogCount(QueryParams params);
}
