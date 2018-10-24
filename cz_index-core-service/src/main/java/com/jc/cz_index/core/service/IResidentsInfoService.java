package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.resident.ResidentsInfoDto;
import com.jc.cz_index.model.ResidentsInfo;
import com.jc.cz_index.model.SystemUser;

/***
 * 
 * 描述：基本信息管理 Service 接口
 * 
 * @author sunxuefeng 2017年9月27日 下午2:02:26
 * @version 1.0
 */
public interface IResidentsInfoService extends IBaseService<ResidentsInfo> {
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
    public PagedList<ResidentsInfoDto> queryResidentBaseinfoList(QueryParams params, SystemUser user, Integer start, Integer size);



    /**
     * 
     * 描述：批量插入居民基本
     * 
     * @param list
     * @param user
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年9月28日 上午11:46:32
     * @version 1.0
     */
    public ResponseData batchAddResidentsinfo(List<ResidentsInfo> list, SystemUser user) throws Exception;



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
    public ResponseData deleteResidentsinfoById(Long id, SystemUser systemUser) throws Exception;



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
    public PagedList<ResidentsInfo> queryOtherResidentsInfoList(QueryParams params, Integer start, Integer size);



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
    public ResponseData addManagementScope(SystemUser systemUser, Long residentId) throws Exception;



    /**
     * 
     * 描述：添加基本信息
     * 
     * @param residentInfo
     * @return
     * @author yangjunhui 2017年9月28日 下午1:31:40
     * @version 1.0
     */
    public ResponseData addResidentsInfo(ResidentsInfo residentInfo);



    /**
     * 
     * 描述：修改
     * 
     * @param residentInfo
     * @return
     * @author yangjunhui 2017年9月28日 下午3:51:08
     * @version 1.0
     */
    public ResponseData updateResidentsInfo(ResidentsInfo residentInfo);



    /***
     * 
     * 描述：根据身份证号查询居民信息是是否存在
     * 
     * @param identityNumber
     * @return
     * @author sunxuefeng 2017年10月10日 上午10:14:31
     * @version 1.0
     */
    public Boolean queryResidentsInfoByIdentityNumberExistence(String identityNumber);



    public ResponseData addResidentsInfoByApp(ResidentsInfo residentBaseinfo);

}
