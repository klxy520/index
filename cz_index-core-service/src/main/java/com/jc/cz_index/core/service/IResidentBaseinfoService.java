package com.jc.cz_index.core.service;

import java.util.List;
import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.resident.ResidentBaseinfoDto;
import com.jc.cz_index.dto.resident.ResidentDataDto;
import com.jc.cz_index.dto.resident.ResidentExtendinfoExeclDto;
import com.jc.cz_index.model.ResidentBaseinfo;
import com.jc.cz_index.model.SystemUser;

/***
 * 
 * 描述：居民基本信息 Service 接口
 * 
 * @author sunxuefeng 2017年8月28日 上午9:43:00
 * @version 1.0
 */
public interface IResidentBaseinfoService extends IBaseService<ResidentBaseinfo> {
    /***
     * 
     * 描述：根据当前系统用户ID和当前用户所属机构的id加载居民健康卡的基本信息列表
     * 
     * @param params
     * @param user
     * @param start
     * @param size
     * @return
     * @author sunxuefeng 2017年8月30日 上午10:03:42
     * @version 1.0
     */
    public PagedList<ResidentBaseinfoDto> queryResidentBaseinfoList(QueryParams params, SystemUser user, Integer start, Integer size);



    /**
     * 
     * 描述：添加居民健康卡基本信息
     * 
     * @param residentBaseinfo
     * @param systemUser
     * @return
     * @author sunxuefeng 2017年8月30日 上午11:22:31
     * @version 1.0
     */
    public ResponseData addResidentBaseinfo(ResidentBaseinfo residentBaseinfo, SystemUser systemUser) throws BaseException;



    /**
     * 
     * 描述：修改居民健康卡基本信息
     * 
     * @param residentBaseinfo
     * @param systemUser
     * @return
     * @author sunxuefeng 2017年8月30日 上午11:22:31
     * @version 1.0
     */
    public ResponseData updateResidentBaseinfo(ResidentBaseinfo residentBaseinfo, SystemUser systemUser) throws BaseException;



    /**
     * 
     * 描述：根据ID删除居民健康卡基本信息
     * 
     * @param id
     * @param systemUser
     * @return
     * @throws BaseException
     * @author sunxuefeng 2017年8月31日 下午2:56:34
     * @version 1.0
     */
    public ResponseData deleteResidentBaseinfoById(Long id, SystemUser systemUser) throws BaseException;



    /**
     * 
     * 描述：通过id查询居民健康卡的基本信息的详情
     * 
     * @param id
     * @return
     * @author sunxuefeng 2017年8月31日 下午4:58:48
     * @version 1.0
     */
    public ResidentBaseinfoDto queryResidentBaseinfoDetailsById(Long id);



    /***
     * 
     * 描述：通过身份证号或者居民健康卡号查询居民信息
     * 
     * @param params
     * @param start
     * @param size
     * @return
     * @author sunxuefeng 2017年9月1日 下午1:48:17
     * @version 1.0
     */
    public PagedList<ResidentBaseinfoDto> queryOtherResidentBaseinfoList(QueryParams params, Integer start, Integer size);



    /***
     * 
     * 描述：把其他居民健康卡信息添加到我的管理权限范围之内
     * 
     * @param params
     * @param start
     * @param size
     * @return
     * @author sunxuefeng 2017年9月1日 下午1:48:17
     * @version 1.0
     */
    public ResponseData addManagementScope(SystemUser systemUser, Long residentId) throws BaseException;



    /**
     * 
     * 描述：批量插入居民基本信息
     * 
     * @param list
     * @param user
     * @return
     * @author sunxuefeng 2017年9月7日 下午2:35:37
     * @version 1.0
     * @throws BaseException
     */
    public ResponseData batchAddResidentBaseinfo(List<ResidentBaseinfo> list, SystemUser user) throws BaseException;
    /**
     * 
     * 描述：批量插入居民信息(包括基本信息和扩展信息)
     * 
     * @param list
     * @param user
     * @return
     * @author sunxuefeng 2017年9月7日 下午2:35:37
     * @version 1.0
     * @throws BaseException
     */
    public ResponseData batchAddResidentData(List<ResidentDataDto> list, SystemUser user) throws BaseException;
    /**
     * 
     * 描述：批量插入居民信息(扩展信息)
     * 
     * @param list
     * @param user
     * @return
     * @author sunxuefeng 2017年9月7日 下午2:35:37
     * @version 1.0
     * @throws BaseException
     */
    public ResponseData batchAddResidentExtendinfo(List<ResidentExtendinfoExeclDto> list, SystemUser user) throws BaseException;
    /**
     * 
     * 描述：通过居民健康卡号查询居民基本信息是否存在
     * @param healthNumber
     * @return
     * @author sunxuefeng 2017年9月11日 下午4:12:09 
     * @version 1.0
     */
    public ResponseData queyResidentBasByexist(String healthNumber);
}
