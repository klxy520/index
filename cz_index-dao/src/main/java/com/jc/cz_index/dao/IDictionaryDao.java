package com.jc.cz_index.dao;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.Dictionary;

/**
 * dictionary Ibatis Dao 接口 Created by
 */
@Repository
public interface IDictionaryDao extends IDataProvider<Dictionary, Long> {

}
