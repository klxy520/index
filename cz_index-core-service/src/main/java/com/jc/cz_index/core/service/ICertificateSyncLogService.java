package com.jc.cz_index.core.service;

import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.syncLog.CertificateSyncLogDto;
import java.util.List;
import com.jc.cz_index.model.CertificateSyncLog;

/**
 * certificateSyncLog Service 接口 Created by
 */
public interface ICertificateSyncLogService extends IBaseService<CertificateSyncLog> {

	public void addCertificateSyncLog(Long certificateId, String details, Long id);

	/**
	 * 批量更新状态 描述：
	 * 
	 * @param csl
	 * @param fem_id
	 * @return
	 * @author fengshengliang 2018年1月4日 下午5:01:14
	 * @version 1.0
	 */
	int doUpdateSyncInfo(List<CertificateSyncLog> csl, Long fem_id);

	public PagedList<CertificateSyncLogDto> queryCertificateSyncLogList(QueryParams params, int start, int size);

}
