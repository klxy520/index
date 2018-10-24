package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.ResidentExtendinfo;
import com.jc.cz_index.model.SystemUser;

/**
 * residentExtendinfo Service 接口 Created by
 */
public interface IResidentExtendinfoService extends IBaseService<ResidentExtendinfo> {
    /**
     * 
     * 描述：根据id查询扩展信息
     * 
     * @param id
     * @return
     * @author yangjunhui 2017年9月18日 下午1:39:03
     * @version 1.0
     */
    public ResidentExtendinfo queryResidentExtendinfoDetailsById(Long id);



    /**
     * 
     * 描述：修改扩展信息
     * 
     * @param extendInfo
     * @return
     * @author yangjunhui 2017年9月18日 下午1:39:31
     * @version 1.0
     */
    public ResponseData updateResidenExtendinfo(ResidentExtendinfo extendInfo);



    /**
     * 
     * 描述：添加扩展信息
     * 
     * @param extendInfo
     * @return
     * @author yangjunhui 2017年9月18日 下午1:39:59
     * @version 1.0
     */
    public ResponseData addResidenExtendinfo(ResidentExtendinfo extendInfo);



    public PagedList<ResidentExtendinfo> queryPagedListForTable(QueryParams params, int start, int size);



    /***
     * 
     * 描述：批量插入扩展信息
     * 
     * @param list
     * @param systemUser
     * @return
     * @author sunxuefeng 2017年10月11日 下午1:32:19
     * @version 1.0
     */
    public ResponseData batchAddResidentExtendinfo(List<ResidentExtendinfo> list, SystemUser systemUser)throws Exception;
}
