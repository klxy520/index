package com.jc.cz_index.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.syncLog.ContactSyncLogDto;
import com.jc.cz_index.core.service.IContactSyncLogService;
import com.jc.cz_index.dao.IContactSyncLogDao;
import com.jc.cz_index.model.ContactSyncLog;

/**
 * contactSyncLog Service 实现 Created by
 */
@Service
public class ContactSyncLogService extends BaseService<ContactSyncLog> implements IContactSyncLogService {
    @Autowired
    private IContactSyncLogDao contactSyncLogDao;



    @Override
    public IDataProvider<ContactSyncLog, Long> getModelDao() {
        return this.contactSyncLogDao;
    }



    @Override
    public int inserOrUpdatetList(List<ContactSyncLog> list) {
        return contactSyncLogDao.inserOrUpdatetList(list);
    }
    /**
     * 
     * 描述：查询所有基本联系人信息同步日志
     * @return
     * @author sunxuefeng 2018年1月4日 下午2:48:36 
     * @version 1.0
     */
    @Override
    public PagedList<ContactSyncLogDto> queryContactSyncLog(QueryParams params, int start, int size) {
        if (null == params) {
            params = new QueryParams();
        }
        if (start < 0)
            throw new IllegalArgumentException("pageIndex参数不能小于0.");
        if (size < 0)
            throw new IllegalArgumentException("pageSize参数不能小于0.");
        int total =contactSyncLogDao.countContactSyncLog(params);
        params.setStart(start);
        params.setSize(size);
        List<ContactSyncLogDto> list =contactSyncLogDao.selectContactSyncLog(params);
        PagedList<ContactSyncLogDto> result = new PagedList<>();
        result.getList().addAll(list);
        result.setPageIndex(start / size + 1);
        result.setPageSize(size);
        result.setTotalSize(total);
        result.setPageCount(total / size + 1);
        return result;
    }
}
