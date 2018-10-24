package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.jc.cz_index.dto.resident.ResidentsInfoDto;
import com.jc.cz_index.model.ResidentsInfo;

/**
 * 
 * 描述：基本信息管理 Dao 接口
 * 
 * @author sunxuefeng 2017年9月27日 下午2:09:28
 * @version 1.0
 */
@Repository
public interface IResidentsInfoDao extends IDataProvider<ResidentsInfo, Long> {
    /**
     * 
     * 描述：根据当前系统用户ID和当前用户所属机构的id加载居民健康卡的基本信息列表
     * 
     * @return
     * @author sunxuefeng 2017年8月30日 上午9:53:02
     * @version 1.0
     */
    public List<ResidentsInfoDto> selectResidentBaseinfolist(QueryParams queryParams);



    /**
     * 
     * 描述：根据当前系统用户ID和当前用户所属机构的id统计居民健康卡的基本信息的记录条数
     * 
     * @return
     * @author sunxuefeng 2017年8月30日 上午9:53:02
     * @version 1.0
     */
    public int countResidentBaseinfolist(QueryParams queryParams);

}
