package com.jc.cz_index.dao;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.Demo;

/**
 * demo Ibatis Dao 接口 Created by
 */
@Repository
public interface IDemoDao extends IDataProvider<Demo, Long> {

}
