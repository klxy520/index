package com.jc.cz_index.dao;

import org.springframework.stereotype.Repository;
import com.jc.cz_index.model.Hospital;

/***
 * 
 * 描述：医院管理 Dao 接口
 * 
 * @author sunxuefeng 2018年1月3日 下午1:24:48
 * @version 1.0
 */
@Repository
public interface IHospitalDao extends IDataProvider<Hospital, Long> {

}
