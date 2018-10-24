package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.dto.mainIndex.PersonSyncLogDto;
import com.jc.cz_index.model.PersonSyncLog;

/**
 * personSyncLog Ibatis Dao 接口 Created by
 */
@Repository
public interface IPersonSyncLogDao extends IDataProvider<PersonSyncLog, Long> {

    /**
     * 
     * 描述：批量插入/更新
     * 
     * @param personSyncLogs
     * @return
     * @author fengshengliang 2018年1月4日 下午3:38:25
     * @version 1.0
     */
    public int saveOrUpdateList(List<PersonSyncLog> personSyncLogs);



    /**
     * 
     * 描述：查询所有基本身份信息同步记录
     * 
     * @return
     * @author sunxuefeng 2018年1月4日 下午2:48:36
     * @version 1.0
     */
    public List<PersonSyncLogDto> selectPersonSyncLog(QueryParams params);



    /**
     * 
     * 描述：统计所有基本身份信息同步记录条数
     * 
     * @return
     * @author sunxuefeng 2018年1月4日 下午2:48:36
     * @version 1.0
     */
    public int countPersonSyncLog(QueryParams params);
}
