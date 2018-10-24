package com.jc.cz_index.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IFrontEndMachineDao;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.syncLog.CardSyncLogDto;
import com.jc.cz_index.dto.syncLog.ContactWaySyncLogDto;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.core.service.IContactWaySyncLogService;
import com.jc.cz_index.dao.IContactWaySyncLogDao;
import com.jc.cz_index.model.ContactWaySyncLog;
import com.jc.cz_index.model.FrontEndMachine;

/**
 * contactWaySyncLog Service 实现 Created by
 */
@Service
public class ContactWaySyncLogService extends BaseService<ContactWaySyncLog> implements IContactWaySyncLogService {
	@Autowired
	private IContactWaySyncLogDao contactWaySyncLogDao;
	@Autowired
	private IFrontEndMachineDao frontEndMachineDao;

	@Override
	public IDataProvider<ContactWaySyncLog, Long> getModelDao() {
		return this.contactWaySyncLogDao;
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addContactWaySyncLog(Long contactWayId, String details, Long userid) {
		List<FrontEndMachine> femList = frontEndMachineDao.queryBaseList(null);
		
		List<ContactWaySyncLog> list=new ArrayList<ContactWaySyncLog>();
		for (FrontEndMachine frontEndMachine : femList) {
			ContactWaySyncLog contactWaylog = new ContactWaySyncLog();
			contactWaylog.setContactWayId(contactWayId);
			contactWaylog.setSyncStatus("0");
			contactWaylog.setCreator(userid);
			contactWaylog.setCreateDate(new Date());
			contactWaylog.setFrontId(frontEndMachine.getId());
			contactWaylog.setUpdateDate(new Date());
			contactWaylog.setUpdator(userid);
			list.add(contactWaylog);
		}
		contactWaySyncLogDao.inserOrUpdatetList(list);

	}
	@Override
	public int doUpdateSyncInfo(List<ContactWaySyncLog> cwsl, Long fem_id) {
		for(int i=0;i<cwsl.size();i++){
			cwsl.get(i).setFrontId(fem_id);
			cwsl.get(i).setSyncStatus(ContentUtils.INFO_SYNC_STATUS_YES);
			cwsl.get(i).setUpdator(ContentUtils.ADMIN_USER_ID);
			cwsl.get(i).setUpdateDate(DateUtils.getCurrentDate());
		}
		return contactWaySyncLogDao.inserOrUpdatetList(cwsl);
	}
	@Override
	public PagedList<ContactWaySyncLogDto> queryContactWaySyncLogList(QueryParams params, int start, int size) {
		 if (null == params) {
	            params = new QueryParams();
	        }
	        if (start < 0)
	            throw new IllegalArgumentException("pageIndex参数不能小于0.");
	        if (size < 0)
	            throw new IllegalArgumentException("pageSize参数不能小于0.");
	        int total = contactWaySyncLogDao.querySyncLogCount(params);
	        params.setStart(start);
	        params.setSize(size);
	        List<ContactWaySyncLogDto> list = contactWaySyncLogDao.queryContactWaySyncLogList(params);
	        PagedList<ContactWaySyncLogDto> result = new PagedList<ContactWaySyncLogDto>();
	        result.getList().addAll(list);
	        result.setPageIndex(start / size + 1);
	        result.setPageSize(size);
	        result.setTotalSize(total);
	        result.setPageCount(total / size + 1);
	        return result;

	}

}