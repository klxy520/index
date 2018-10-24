package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.ResidentBaseinfo;
import com.jc.cz_index.model.ResidentExtendinfo;


/**
 * residentExtendinfo Ibatis Dao 接口 Created by 
 */
@Repository
public interface IResidentExtendinfoDao extends IDataProvider<ResidentExtendinfo, Long>
{

   

    public int getQueryCount(QueryParams params);

    public List<ResidentExtendinfo> queryPagedListForTable(QueryParams params);

    public void updateExtendByBaseId(ResidentExtendinfo extendInfo);

    public ResidentExtendinfo queryResidentExtendinfoDetailsById(Long id);

   


}


