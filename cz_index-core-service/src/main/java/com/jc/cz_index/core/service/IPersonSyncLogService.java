package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.mainIndex.PersonSyncLogDto;
import com.jc.cz_index.model.PersonSyncLog;

/**
 * personSyncLog Service 接口 Created by
 */
public interface IPersonSyncLogService extends IBaseService<PersonSyncLog> {

    /**
     * 批量更新状态 描述：
     * 
     * @param psl
     * @param fem_id
     * @return
     * @author fengshengliang 2018年1月4日 下午5:01:14
     * @version 1.0
     */
    int doUpdateSyncInfo(List<PersonSyncLog> psl, Long fem_id);



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

    public PagedList<PersonSyncLogDto> queryPersonSyncLogList(QueryParams params, int start, int size);

}
