package com.jc.cz_index.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.core.service.IAddressSyncLogService;
import com.jc.cz_index.dao.IAddressSyncLogDao;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.syncLog.AddressSyncLogDto;
import com.jc.cz_index.model.AddressSyncLog;

@Service
public class AddressSyncLogService extends BaseService<AddressSyncLog> implements IAddressSyncLogService{

	@Autowired
	private IAddressSyncLogDao		addressSyncLogDao;

	@Override
	public int doUpdateSyncInfo(List<AddressSyncLog> asl,Long fem_id) {
		for(int i=0;i<asl.size();i++){
			asl.get(i).setFrontId(fem_id);
			asl.get(i).setSyncStatus(ContentUtils.INFO_SYNC_STATUS_YES);
			asl.get(i).setUpdator(ContentUtils.ADMIN_USER_ID);
			asl.get(i).setUpdateDate(DateUtils.getCurrentDate());
		}
		return addressSyncLogDao.inserOrUpdatetList(asl);
	}

	@Override
	public IDataProvider<AddressSyncLog, Long> getModelDao() {
		// TODO Auto-generated method stub
		return this.addressSyncLogDao;
	}
	 /**
     * 
     * 描述：查询所有地址信息同步日志
     * 
     * @return
     * @author sunxuefeng 2018年1月4日 下午2:48:36
     * @version 1.0
     */
    @Override
    public PagedList<AddressSyncLogDto> queryAddressSyncLog(QueryParams params, int start, int size) {
        if (null == params) {
            params = new QueryParams();
        }
        if (start < 0)
            throw new IllegalArgumentException("pageIndex参数不能小于0.");
        if (size < 0)
            throw new IllegalArgumentException("pageSize参数不能小于0.");
        int total = addressSyncLogDao.countAddressSyncLog(params);
        params.setStart(start);
        params.setSize(size);
        List<AddressSyncLogDto> list =addressSyncLogDao.selectAddressSyncLog(params);
        PagedList<AddressSyncLogDto> result = new PagedList<>();
        result.getList().addAll(list);
        result.setPageIndex(start / size + 1);
        result.setPageSize(size);
        result.setTotalSize(total);
        result.setPageCount(total / size + 1);
        return result;
    }
	

}
