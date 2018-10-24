package com.jc.cz_index.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.core.service.ICertificateSyncLogService;
import com.jc.cz_index.dao.ICertificateSyncLogDao;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IFrontEndMachineDao;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.syncLog.CertificateSyncLogDto;
import com.jc.cz_index.model.CardSyncLog;
import com.jc.cz_index.model.CertificateSyncLog;
import com.jc.cz_index.model.FrontEndMachine;

/**
 * certificateSyncLog Service 实现 Created by
 */
@Service
public class CertificateSyncLogService extends BaseService<CertificateSyncLog> implements ICertificateSyncLogService {
    @Autowired
    private ICertificateSyncLogDao certificateSyncLogDao;
    @Autowired
    private IFrontEndMachineDao    frontEndMachineDao;


	/**
	 * 
	 * 描述：所有人卡同步日志列表查询
	 * 
	 * @param params
	 * @param start
	 * @param size
	 * @return
	 * @author yangjunhui 2018年1月3日 下午5:10:38
	 * @version 1.0
	 */
	@Override
	public PagedList<CertificateSyncLogDto> queryCertificateSyncLogList(QueryParams params, int start, int size) {
		 if (null == params) {
	            params = new QueryParams();
	        }
	        if (start < 0)
	            throw new IllegalArgumentException("pageIndex参数不能小于0.");
	        if (size < 0)
	            throw new IllegalArgumentException("pageSize参数不能小于0.");
	        int total = certificateSyncLogDao.querySyncLogCount(params);
	        params.setStart(start);
	        params.setSize(size);
	        List<CertificateSyncLogDto> list = certificateSyncLogDao.queryCertificateSyncLogList(params);
	        PagedList<CertificateSyncLogDto> result = new PagedList<CertificateSyncLogDto>();
	        result.getList().addAll(list);
	        result.setPageIndex(start / size + 1);
	        result.setPageSize(size);
	        result.setTotalSize(total);
	        result.setPageCount(total / size + 1);
	        return result;
	}

    @Override
    public IDataProvider<CertificateSyncLog, Long> getModelDao() {
        return this.certificateSyncLogDao;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCertificateSyncLog(Long certificateId, String details, Long userid) {
        List<FrontEndMachine> femList = frontEndMachineDao.queryBaseList(null);
        List<CertificateSyncLog> list =new ArrayList<CertificateSyncLog>();
        for (FrontEndMachine frontEndMachine : femList) {
        	 CertificateSyncLog certificatelog = new CertificateSyncLog();
             certificatelog.setCertificateId(certificateId);
             certificatelog.setSyncStatus("0");
             certificatelog.setCreator(userid);
             certificatelog.setCreateDate(new Date());
             certificatelog.setUpdateDate(new Date());
             certificatelog.setUpdator(userid);;
            certificatelog.setFrontId(frontEndMachine.getId());
            list.add(certificatelog);
        }
        certificateSyncLogDao.saveOrUpdateList(list);

    }



    /**
     * 更新状态为同步状态
     */
    @Override
    public int doUpdateSyncInfo(List<CertificateSyncLog> csls, Long fem_id) {
        Date currentDate = DateUtils.getCurrentDate();
        for (CertificateSyncLog certificateSyncLog : csls) {
            certificateSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_YES);
            certificateSyncLog.setFrontId(fem_id);
            certificateSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
            certificateSyncLog.setCreateDate(currentDate);
            certificateSyncLog.setUpdator(ContentUtils.ADMIN_USER_ID);
            certificateSyncLog.setUpdateDate(DateUtils.getCurrentDate());
        }
        return certificateSyncLogDao.saveOrUpdateList(csls);
    }



}