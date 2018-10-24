package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.dto.resident.ResidentBaseinfoDto;
import com.jc.cz_index.model.ResidentBaseinfo;

/***
 * 
 * 描述：健康卡的基本信息Dao 接口
 * 
 * @author sunxuefeng 2017年8月28日 上午9:39:00
 * @version 1.0
 */
@Repository
public interface IResidentBaseinfoDao extends IDataProvider<ResidentBaseinfo, Long> {
    /**
     * 
     * 描述：根据当前系统用户ID和当前用户所属机构的id加载居民健康卡的基本信息列表
     * 
     * @return
     * @author sunxuefeng 2017年8月30日 上午9:53:02
     * @version 1.0
     */
    public List<ResidentBaseinfoDto> selectResidentBaseinfolist(QueryParams queryParams);



    /**
     * 
     * 描述：根据当前系统用户ID和当前用户所属机构的id统计居民健康卡的基本信息的记录条数
     * 
     * @return
     * @author sunxuefeng 2017年8月30日 上午9:53:02
     * @version 1.0
     */
    public int countResidentBaseinfolist(QueryParams queryParams);



    /**
     * 
     * 描述：通过id查询居民健康卡的基本信息的详情
     * 
     * @param id
     * @return
     * @author sunxuefeng 2017年8月31日 下午4:58:48
     * @version 1.0
     */
    public ResidentBaseinfoDto selectResidentBaseinfoDetailsById(Long id);



    /**
     * 
     * 描述：通过身份证号或者居民健康卡号查询居民信息
     * 
     * @return
     * @author sunxuefeng 2017年8月30日 上午9:53:02
     * @version 1.0
     */
    public List<ResidentBaseinfoDto> selectOtherResidentBaseinfolist(QueryParams queryParams);



    /**
     * 
     * 描述：通过身份证号或者居民健康卡号统计居民信息
     * 
     * @return
     * @author sunxuefeng 2017年8月30日 上午9:53:02
     * @version 1.0
     */
    public int countOtherResidentBaseinfolist(QueryParams queryParams);
}
