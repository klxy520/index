package com.jc.cz_index.core.service;
import java.util.List;

import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.syncLog.ContactSyncLogDto;
import com.jc.cz_index.model.ContactSyncLog;

/**
 * contactSyncLog Service 接口 Created by
 */
public interface IContactSyncLogService extends IBaseService<ContactSyncLog> {
        
    /**
     * 
     * 描述：查询所有基本联系人信息同步日志
     * @return
     * @author sunxuefeng 2018年1月4日 下午2:48:36 
     * @version 1.0
     */
    public PagedList<ContactSyncLogDto> queryContactSyncLog(QueryParams params, int start, int size);
    /**
     * 
     * 描述：插入一组对象
     * @param list
     * @return
     * @author yangyongchuan 2018年1月4日 下午6:29:32 
     * @version 1.0
     */
    public int inserOrUpdatetList(List<ContactSyncLog> list);
}
