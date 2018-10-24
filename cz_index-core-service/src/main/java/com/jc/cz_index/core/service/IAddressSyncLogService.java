package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.syncLog.AddressSyncLogDto;
import com.jc.cz_index.model.AddressSyncLog;

public interface IAddressSyncLogService extends IBaseService<AddressSyncLog> {

    /**
     * 
     * 描述：批量更新
     * 
     * @param asl
     * @return
     * @author wangdeyou 2018年1月4日 下午7:47:47
     * @version 1.0
     */
    int doUpdateSyncInfo(List<AddressSyncLog> asl, Long fem_id);



    /**
     * 
     * 描述：查询所有地址信息同步日志
     * 
     * @return
     * @author sunxuefeng 2018年1月4日 下午2:48:36
     * @version 1.0
     */
     PagedList<AddressSyncLogDto> queryAddressSyncLog(QueryParams params, int start, int size);
}
