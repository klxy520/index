package com.jc.cz_index.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.core.service.IPersonSyncLogService;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IPersonSyncLogDao;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.mainIndex.PersonSyncLogDto;
import com.jc.cz_index.model.PersonSyncLog;

/**
 * personSyncLog Service 实现 Created by
 */
@Service
public class PersonSyncLogService extends BaseService<PersonSyncLog> implements IPersonSyncLogService {
    @Autowired
    private IPersonSyncLogDao personSyncLogDao;


    
    @Override
    public IDataProvider<PersonSyncLog, Long> getModelDao() {
        return this.personSyncLogDao;
    }


    /**
     * 更新状态为同步状态
     */
    @Override
    public int doUpdateSyncInfo(List<PersonSyncLog> psls, Long fem_id) {
        Date currentDate = DateUtils.getCurrentDate();
        for(PersonSyncLog personSyncLog : psls){
            personSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_YES);
            personSyncLog.setFrontId(fem_id);
            personSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
            personSyncLog.setCreateDate(currentDate);
            personSyncLog.setUpdator(ContentUtils.ADMIN_USER_ID);
            personSyncLog.setUpdateDate(currentDate);
        }
        return personSyncLogDao.saveOrUpdateList(psls);
    }



    /**
     * 
     * 描述：查询基本身份信息同步日志列表
     * 
     * @param params
     * @param start
     * @param size
     * @return
     * @author sunxuefeng 2018年1月4日 下午2:51:24
     * @version 1.0
     */
    public PagedList<PersonSyncLogDto> queryPersonSyncLogList(QueryParams params, int start, int size) {
        if (null == params) {
            params = new QueryParams();
        }
        if (start < 0)
            throw new IllegalArgumentException("pageIndex参数不能小于0.");
        if (size < 0)
            throw new IllegalArgumentException("pageSize参数不能小于0.");
        int total = personSyncLogDao.countPersonSyncLog(params);
        params.setStart(start);
        params.setSize(size);
        List<PersonSyncLogDto> list = personSyncLogDao.selectPersonSyncLog(params);
        PagedList<PersonSyncLogDto> result = new PagedList<>();
        result.getList().addAll(list);
        result.setPageIndex(start / size + 1);
        result.setPageSize(size);
        result.setTotalSize(total);
        result.setPageCount(total / size + 1);
        return result;
    }
}
