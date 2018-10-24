package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.jc.cz_index.model.UpdateResidentLog;

/**
 * 
 * 描述：健康卡基本信息用户更新记录Dao
 * 
 * @author sunxuefeng 2017年8月30日 下午1:56:49
 * @version 1.0
 */
@Repository
public interface IUpdateResidentLogDao extends IDataProvider<UpdateResidentLog, Long> {
    /**
     * 
     * 描述：根据添加查询更新记录
     * @param wheres
     * @return
     * @author sunxuefeng 2017年10月13日 下午3:06:20 
     * @version 1.0
     */
    public List<UpdateResidentLog> queryBaseListByWheres(QueryParams wheres);

}
