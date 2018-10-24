package com.jc.cz_index.core.service.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.core.service.IResidentBaseinfoService;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IInfoManagementLogDao;
import com.jc.cz_index.dao.IResidentBaseinfoDao;
import com.jc.cz_index.dao.IResidentExtendinfoDao;
import com.jc.cz_index.dao.IUpdateResidentLogDao;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.resident.ResidentBaseinfoDto;
import com.jc.cz_index.dto.resident.ResidentDataDto;
import com.jc.cz_index.dto.resident.ResidentExtendinfoExeclDto;
import com.jc.cz_index.model.InfoManagementLog;
import com.jc.cz_index.model.ResidentBaseinfo;
import com.jc.cz_index.model.ResidentExtendinfo;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.model.UpdateResidentLog;

/**
 * 
 * 描述：居民基本信息 Service 实现
 * 
 * @author sunxuefeng 2017年8月28日 上午9:50:12
 * @version 1.0
 */
@Service
public class ResidentBaseinfoService extends BaseService<ResidentBaseinfo> implements IResidentBaseinfoService {
    @Autowired
    private IResidentBaseinfoDao   residentBaseinfoDao;
    @Autowired
    private IInfoManagementLogDao  infoManagementLogDao;
    @Autowired
    private IUpdateResidentLogDao  updateResidentLogDao;
    @Autowired
    private IResidentExtendinfoDao residentExtendinfoDao;



    @Override
    public IDataProvider<ResidentBaseinfo, Long> getModelDao() {
        return this.residentBaseinfoDao;
    }



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
    public PagedList<ResidentBaseinfoDto> queryResidentBaseinfoList(QueryParams params, SystemUser user, Integer start, Integer size) {
        if (null == params) {
            params = new QueryParams();
        }
        if (start < 0)
            throw new IllegalArgumentException("pageIndex参数不能小于0.");
        if (size < 0)
            throw new IllegalArgumentException("pageSize参数不能小于0.");
        params.put("area", user.getAreaId());
        params.put("office", user.getOrganizationId());
        params.put("delFalg", 0);
        int total = residentBaseinfoDao.countResidentBaseinfolist(params);
        params.setStart(start);
        params.setSize(size);
        List<ResidentBaseinfoDto> list = residentBaseinfoDao.selectResidentBaseinfolist(params);
        PagedList<ResidentBaseinfoDto> result = new PagedList<ResidentBaseinfoDto>();
        result.getList().addAll(list);
        result.setPageIndex(start / size + 1);
        result.setPageSize(size);
        result.setTotalSize(total);
        result.setPageCount(total / size + 1);
        return result;
    }



    /**
     * 
     * 描述：添加居民健康卡基本信息
     * 
     * @param residentBaseinfo
     * @param systemUser
     * @return
     * @author sunxuefeng 2017年8月30日 上午11:22:31
     * @version 1.0
     * @throws BaseException
     */
    @Transactional(rollbackFor = BaseException.class)
    public ResponseData addResidentBaseinfo(ResidentBaseinfo residentBaseinfo, SystemUser systemUser) throws BaseException {
        ResponseData responseData = null;
        try {
            responseData = validationResidentBaseinfo(residentBaseinfo);// 验证居民健康卡基本信息
            if (responseData.getStatus() == ResponseData.ERROR_STATUS) {
                return responseData;
            }
            Long userId = systemUser.getId();
            Date date = DateUtils.getCurrentDate();
            residentBaseinfo.setCreator(userId);
            residentBaseinfo.setCreateDate(date);
            residentBaseinfo.setDelFalg(0);// 表示未删除
            int i = residentBaseinfoDao.insertObject(residentBaseinfo);// 新增基本信息
            if (i != 1) {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("居民健康卡信息添加失败");
                return responseData;
            }
            String name = systemUser.getName();
            Long residentBaseinfoId = residentBaseinfo.getId();
            String healthNumber = residentBaseinfo.getHealthNumber();
            ResidentExtendinfo residentExtendinfo = new ResidentExtendinfo();
            residentExtendinfo.setBaseId(residentBaseinfoId);
            residentExtendinfo.setHealthNumber(healthNumber);
            residentExtendinfoDao.insertObject(residentExtendinfo);
            InfoManagementLog infoManagementLog = new InfoManagementLog();// 新增操作日志
            infoManagementLog.setCreator(userId);
            infoManagementLog.setCreatorName(name);
            infoManagementLog.setCreateDate(date);
            infoManagementLog.setType(1);
            infoManagementLog.setFormName("居民基本信息");
            infoManagementLog.setRecordId(residentBaseinfoId);
            infoManagementLog.setDetails("<" + name + ">新增了一条居民健康卡基本数据,<居民姓名>为:[" + residentBaseinfo.getRealName() + "]," + "<身份证号>为:["
                    + residentBaseinfo.getIdNumber() + "]");
            infoManagementLogDao.insertObject(infoManagementLog);

            UpdateResidentLog updateResidentLog = new UpdateResidentLog();// 新增用户操作记录

            updateResidentLog.setCreateDate(date);
            updateResidentLog.setUserId(userId);
            updateResidentLog.setResidentId(residentBaseinfoId);
            updateResidentLogDao.insertObject(updateResidentLog);
            responseData.setMessage("居民健康卡基本信息添加成功");
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        } catch (Exception e) {
            throw new BaseException(e);
        }
        return responseData;
    }



    /**
     * 
     * 描述：修改居民健康卡基本信息
     * 
     * @param residentBaseinfo
     * @param systemUser
     * @return
     * @author sunxuefeng 2017年8月30日 上午11:22:31
     * @version 1.0
     * @throws BaseException
     */
    @Transactional(rollbackFor = BaseException.class)
    public ResponseData updateResidentBaseinfo(ResidentBaseinfo residentBaseinfo, SystemUser systemUser) throws BaseException {
        ResponseData responseData = null;
        try {
            responseData = validationResidentBaseinfo(residentBaseinfo);// 验证居民健康卡基本信息
            if (responseData.getStatus() == ResponseData.ERROR_STATUS) {
                return responseData;
            }
            Long id = residentBaseinfo.getId();
            if (id == null || id == 0) {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("居民健康卡基本信息修改失败");
                return responseData;
            }
            ResidentBaseinfo residentBaseinfoDB = residentBaseinfoDao.getBaseObject(id);
            if (residentBaseinfoDB != null) {
                Long userId = systemUser.getId();
                String userName = systemUser.getName();
                InfoManagementLog managementLog = generateInfoManagementLog(residentBaseinfo, residentBaseinfoDB, userName);
                Date date = DateUtils.getCurrentDate();
                if (managementLog != null) {
                    managementLog.setRecordId(id);
                    managementLog.setCreator(userId);
                    managementLog.setCreatorName(userName);
                    managementLog.setCreateDate(date);
                    infoManagementLogDao.insertObject(managementLog);
                }
                BeanUtils.copyPropertiesIgnoreNull(residentBaseinfo, residentBaseinfoDB);
                residentBaseinfoDB.setUpdator(userId);
                residentBaseinfoDB.setUpdateDate(date);
                residentBaseinfoDao.updateObject(residentBaseinfoDB);// 修改基本信息
                responseData.setStatus(ResponseData.SUCCESS_STATUS);
                responseData.setMessage("居民健康卡基本信息修改成功");
            } else {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("居民健康卡基本信息修改失败");
            }
        } catch (Exception e) {
            throw new BaseException(e);
        }

        return responseData;
    }



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
    @Transactional(rollbackFor = BaseException.class)
    public ResponseData deleteResidentBaseinfoById(Long id, SystemUser systemUser) throws BaseException {
        ResponseData responseData = new ResponseData();
        try {
            ResidentBaseinfo residentBaseinfo = residentBaseinfoDao.getBaseObject(id);
            if (residentBaseinfo == null) {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("居民健康卡基本信息不存在");
                return responseData;
            }
            Long userid = systemUser.getId();
            Date date = DateUtils.getCurrentDate();
            residentBaseinfo.setDelFalg(1);
            residentBaseinfo.setDeleteDate(date);
            residentBaseinfo.setDeleteor(userid);
            residentBaseinfoDao.updateObject(residentBaseinfo);// 逻辑删除基本信息
            String name = systemUser.getName();
            Long residentBaseinfoId = residentBaseinfo.getId();
            InfoManagementLog infoManagementLog = new InfoManagementLog(); // 新增操作日志
            infoManagementLog.setCreator(userid);
            infoManagementLog.setCreatorName(name);
            infoManagementLog.setCreateDate(date);
            infoManagementLog.setType(-1);
            infoManagementLog.setFormName("居民基本信息");
            infoManagementLog.setRecordId(residentBaseinfoId);
            infoManagementLog.setDetails("<" + name + ">" + "删除了一条居民健康卡基本数据和它的扩展信息,<居民姓名>为[" + residentBaseinfo.getRealName() + "],"
                    + "<身份证号>为:[" + residentBaseinfo.getIdNumber() + "]");
            infoManagementLogDao.insertObject(infoManagementLog);
            QueryParams params = new QueryParams();
            params.put("residentId", id);
            updateResidentLogDao.deleteObjectByWhere(params);// 删除用户操作记录
            params.put("baseId", id);
            params.put("delFalg", 0);
            List<ResidentExtendinfo> list = residentExtendinfoDao.queryBaseList(params);// 删除扩展信息
            if (list != null && list.size() > 0) {
                ResidentExtendinfo extendinfo = list.get(0);
                extendinfo.setDeleteor(userid);
                extendinfo.setDeleteDate(date);
                extendinfo.setDelFalg(1);
                residentExtendinfoDao.updateObject(extendinfo);
            }
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            responseData.setMessage("居民健康卡基本信息删除成功");
        } catch (Exception e) {
            throw new BaseException(e);
        }
        return responseData;
    }



    /**
     * 
     * 描述：通过id查询居民健康卡的基本信息的详情
     * 
     * @param id
     * @return
     * @author sunxuefeng 2017年8月31日 下午4:58:48
     * @version 1.0
     */
    public ResidentBaseinfoDto queryResidentBaseinfoDetailsById(Long id) {
        if (id != null) {
            return residentBaseinfoDao.selectResidentBaseinfoDetailsById(id);
        }
        return null;
    }



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
    public PagedList<ResidentBaseinfoDto> queryOtherResidentBaseinfoList(QueryParams params, Integer start, Integer size) {
        if (null == params) {
            params = new QueryParams();
        }
        if (start < 0)
            throw new IllegalArgumentException("pageIndex参数不能小于0.");
        if (size < 0)
            throw new IllegalArgumentException("pageSize参数不能小于0.");
        int total = residentBaseinfoDao.countOtherResidentBaseinfolist(params);
        params.setStart(start);
        params.setSize(size);
        List<ResidentBaseinfoDto> list = residentBaseinfoDao.selectOtherResidentBaseinfolist(params);
        PagedList<ResidentBaseinfoDto> result = new PagedList<ResidentBaseinfoDto>();
        result.getList().addAll(list);
        result.setPageIndex(start / size + 1);
        result.setPageSize(size);
        result.setTotalSize(total);
        result.setPageCount(total / size + 1);
        return result;
    }



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
     * @throws BaseException
     */
    @Transactional(rollbackFor = BaseException.class)
    public ResponseData addManagementScope(SystemUser systemUser, Long residentId) throws BaseException {
        ResponseData responseData = new ResponseData();
        try {
            ResidentBaseinfo residentBaseinfo = residentBaseinfoDao.getBaseObject(residentId);
            if (residentBaseinfo == null) {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("居民健康卡基本信息不存在");
                return responseData;
            }
            QueryParams params = new QueryParams();
            Long userId = systemUser.getId();
            params.put("userId", userId);
            params.put("residentId", residentId);
            params.put("delFalg", 0);
            List<UpdateResidentLog> list = updateResidentLogDao.queryBaseList(params);
            if (list != null && list.size() > 0) {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("重复添加");
                return responseData;
            }
            UpdateResidentLog updateResidentLog = new UpdateResidentLog();
            updateResidentLog.setCreateDate(DateUtils.getCurrentDate());
            updateResidentLog.setUserId(userId);
            updateResidentLog.setResidentId(residentId);
            updateResidentLogDao.insertObject(updateResidentLog);
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            responseData.setMessage("添加成功");
        } catch (Exception e) {
            throw new BaseException(e);
        }
        return responseData;
    }



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
    @Transactional(rollbackFor = BaseException.class)
    public ResponseData batchAddResidentBaseinfo(List<ResidentBaseinfo> list, SystemUser user) throws BaseException {
        ResponseData responseData = new ResponseData();
        try {
            List<ResidentBaseinfo> baseinfos = addResidentBaseinfoData(list, user);
            Date date = DateUtils.getCurrentDate();
            Long userId = user.getId();
            String name = user.getName();
            for (ResidentBaseinfo resident : baseinfos) {
                int i = residentBaseinfoDao.insertObject(resident);// 新增基本信息
                if (i != 1) {
                    responseData.setStatus(ResponseData.ERROR_STATUS);
                    responseData.setMessage("居民基本信息导入失败");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return responseData;
                }
                Long residentBaseinfoId = resident.getId();
                ResidentExtendinfo residentExtendinfo = new ResidentExtendinfo();
                residentExtendinfo.setBaseId(residentBaseinfoId);
                residentExtendinfo.setHealthNumber(resident.getHealthNumber());
                residentExtendinfoDao.insertObject(residentExtendinfo);
                UpdateResidentLog updateResidentLog = new UpdateResidentLog();// 新增用户操作记录
                updateResidentLog.setCreateDate(date);
                updateResidentLog.setUserId(userId);
                updateResidentLog.setResidentId(residentBaseinfoId);
                updateResidentLogDao.insertObject(updateResidentLog);
            }
            InfoManagementLog infoManagementLog = new InfoManagementLog();// 新增操作日志
            infoManagementLog.setCreator(userId);
            infoManagementLog.setCreatorName(name);
            infoManagementLog.setCreateDate(date);
            infoManagementLog.setType(1);
            infoManagementLog.setFormName("居民基本信息");
            infoManagementLog.setDetails(name + "批量导入了" + baseinfos.size() + "条居民健康卡信息");
            infoManagementLogDao.insertObject(infoManagementLog);
            responseData.setMessage("居民基本信息导入成功");
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        } catch (Exception e) {
            throw new BaseException(e);
        }
        return responseData;
    }



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
    @Transactional(rollbackFor = BaseException.class)
    public ResponseData batchAddResidentData(List<ResidentDataDto> list, SystemUser user) throws BaseException {
        ResponseData responseData = new ResponseData();
        Long userId = user.getId();
        Date date = DateUtils.getCurrentDate();
        Long organizationId = user.getOrganizationId();
        Long areaId = user.getAreaId();
        String name = user.getName();
        try {
            if (list != null && list.size() > 0) {
                for (ResidentDataDto residentDataDto : list) {
                    ResidentBaseinfo baseinfo = new ResidentBaseinfo();
                    BeanUtils.copyPropertiesIgnoreNull(residentDataDto, baseinfo);
                    baseinfo.setCreateDate(date);
                    baseinfo.setCreator(userId);
                    baseinfo.setOfficeId(organizationId);
                    baseinfo.setAreaId(areaId);
                    baseinfo.setDelFalg(0);
                    residentBaseinfoDao.insertObject(baseinfo);
                    String disabilityType = residentDataDto.getDisabilityType();
                    String helpHouse = residentDataDto.getHelpHouse();
                    String illnessType = residentDataDto.getIllnessType();
                    String insuranceType = residentDataDto.getInsuranceType();
                    String lowType = residentDataDto.getLowType();
                    String retiredCadres = residentDataDto.getRetiredCadres();
                    String unionFeature = residentDataDto.getUnionFeature();
                    if (!(StringUtils.isBlank(disabilityType) || StringUtils.isBlank(illnessType) || StringUtils.isBlank(unionFeature)
                            || StringUtils.isBlank(retiredCadres) || StringUtils.isBlank(helpHouse) || StringUtils.isBlank(insuranceType)
                            || StringUtils.isBlank(lowType))) {
                        ResidentExtendinfo extendinfo = new ResidentExtendinfo();
                        BeanUtils.copyPropertiesIgnoreNull(residentDataDto, extendinfo);
                        extendinfo.setBaseId(baseinfo.getId());
                        extendinfo.setCreateDate(date);
                        extendinfo.setCreator(userId);
                        extendinfo.setDelFalg(0);
                        residentExtendinfoDao.insertObject(extendinfo);
                    }
                    UpdateResidentLog updateResidentLog = new UpdateResidentLog();// 新增用户操作记录
                    updateResidentLog.setCreateDate(date);
                    updateResidentLog.setUserId(userId);
                    updateResidentLog.setResidentId(baseinfo.getId());
                    updateResidentLogDao.insertObject(updateResidentLog);
                }
                InfoManagementLog infoManagementLog = new InfoManagementLog();// 新增操作日志
                infoManagementLog.setCreator(userId);
                infoManagementLog.setCreatorName(name);
                infoManagementLog.setCreateDate(date);
                infoManagementLog.setType(1);
                infoManagementLog.setFormName("居民基本信息和扩展信息");
                infoManagementLog.setDetails("<" + name + ">批量导入了" + list.size() + "条居民健康卡信息");
                infoManagementLogDao.insertObject(infoManagementLog);
                responseData.setMessage("居民信息导入成功");
                responseData.setStatus(ResponseData.SUCCESS_STATUS);
            }
        } catch (Exception e) {
            throw new BaseException(e);
        }
        return responseData;
    }



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
    @Transactional(rollbackFor = BaseException.class)
    public ResponseData batchAddResidentExtendinfo(List<ResidentExtendinfoExeclDto> list, SystemUser user) throws BaseException {
        ResponseData responseData = new ResponseData();
        Long userId = user.getId();
        String name = user.getName();
        Date date = DateUtils.getCurrentDate();
        try {
            if (list != null && list.size() > 0) {
                for (ResidentExtendinfoExeclDto dto : list) {
                    QueryParams params = new QueryParams();
                    String healthNumber = dto.getHealthNumber();
                    params.put("healthNumber", healthNumber);
                    params.put("delFalg", 0);
                    ResidentBaseinfo baseinfo = this.queryOneBase(params);
                    if (baseinfo == null) {
                        responseData.setMessage("导入失败,健康卡号为" + healthNumber + "的居民信息不存在");
                        responseData.setStatus(ResponseData.ERROR_STATUS);
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return responseData;
                    }
                    params.put("healthNumber", healthNumber);
                    params.put("delFalg", 0);
                    List<ResidentExtendinfo> extendinfos = residentExtendinfoDao.queryBaseList(params);
                    if (extendinfos == null || extendinfos.size() == 0) {
                        responseData.setMessage("导入失败,健康卡号为" + healthNumber + "的居民信息不存在");
                        responseData.setStatus(ResponseData.ERROR_STATUS);
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return responseData;
                    }
                    ResidentExtendinfo extendinfo = extendinfos.get(0);
                    BeanUtils.copyPropertiesIgnoreNull(dto, extendinfo);
                    extendinfo.setCreateDate(date);
                    extendinfo.setCreator(userId);
                    extendinfo.setDelFalg(0);
                    residentExtendinfoDao.updateObject(extendinfo);
                }
                InfoManagementLog infoManagementLog = new InfoManagementLog();// 新增操作日志
                infoManagementLog.setCreator(userId);
                infoManagementLog.setCreatorName(name);
                infoManagementLog.setCreateDate(date);
                infoManagementLog.setType(1);
                infoManagementLog.setFormName("扩展信息");
                infoManagementLog.setDetails("<" + name + ">批量导入了" + list.size() + "条居民健康卡扩展信息");
                infoManagementLogDao.insertObject(infoManagementLog);
                responseData.setMessage("居民扩展信息导入成功");
                responseData.setStatus(ResponseData.SUCCESS_STATUS);
                return responseData;
            }
        } catch (Exception e) {
            throw new BaseException(e);
        }
        return null;
    }



    /**
     * 
     * 描述：通过居民健康卡号查询居民基本信息是否存在
     * 
     * @param healthNumber
     * @return
     * @author sunxuefeng 2017年9月11日 下午4:12:09
     * @version 1.0
     */
    public ResponseData queyResidentBasByexist(String healthNumber) {
        ResponseData responseData = new ResponseData();
        QueryParams params = new QueryParams();
        params.put("healthNumber", healthNumber);
        params.put("delFalg", 0);
        ResidentBaseinfo baseinfo = this.queryOneBase(params);
        if (baseinfo == null) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
        } else {
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        }
        return responseData;
    }



    /***
     * 
     * 描述：验证居民健康卡基本信息
     * 
     * @param residentBaseinfo
     * @return
     * @author sunxuefeng 2017年8月30日 上午11:40:35
     * @version 1.0
     */
    private ResponseData validationResidentBaseinfo(ResidentBaseinfo residentBaseinfo) {
        ResponseData responseData = new ResponseData();
        if (residentBaseinfo == null) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("民健康卡基本信息不为空");
        }
        String realName = residentBaseinfo.getRealName();
        if (realName == null || realName.equals("")) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("居民姓名不能为空");
        }
        String idNumber = residentBaseinfo.getIdNumber();
        if (idNumber == null || idNumber.equals("")) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("居民身份证号不能为空");
        }
        Date periodValidityDate = residentBaseinfo.getPeriodValidityDate();
        if (periodValidityDate == null) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("证件有效期不能为空");
        }
        String healthNumber = residentBaseinfo.getHealthNumber();
        if (healthNumber == null || healthNumber.equals("")) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("居民健康卡号不能为空");
        }

        String socialNumber = residentBaseinfo.getSocialNumber();
        if (socialNumber == null || socialNumber.equals("")) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("社保卡号不能为空");
        }
        return responseData;
    }



    /**
     * 
     * 描述：修改居民健康卡基本数据生成日志
     * 
     * @param residentBaseinfo
     * @param residentBaseinfoDB
     * @param userName
     * @return
     * @author sunxuefeng 2017年8月30日 下午6:49:59
     * @version 1.0
     */
    private InfoManagementLog generateInfoManagementLog(ResidentBaseinfo residentBaseinfo, ResidentBaseinfo residentBaseinfoDB,
            String userName) {
        ResidentBaseinfo baseinfo = new ResidentBaseinfo();
        copyPropertiesIgnoreNull(residentBaseinfoDB, baseinfo);
        copyPropertiesIgnoreNull(residentBaseinfo, residentBaseinfoDB);
        // String details =userName + "编辑居民姓名为:[" +
        // residentBaseinfoDB.getRealName() + "]," + "身份证号为:[" +
        // residentBaseinfoDB.getIdNumber()
        // + "]的居民健康卡扩展信息。";
        String fieldNames = "realName;真实姓名,居民健康卡,healthNumber;社保卡,socialNumber; ";
        // String fields = BeanUtils.getDifferenceByFields(baseinfo,
        // residentBaseinfoDB, fieldNames);
        /*
         * boolean falg = false; String realName =
         * residentBaseinfo.getRealName(); String idNumber =
         * residentBaseinfo.getIdNumber();
         * 
         * String details = "<" + userName + ">" + "修改了一条居民健康卡基本信息,<居民姓名>为:[" +
         * realName + "]," + "<身份证号>为:[" + idNumber + "]:"; String realNameDB =
         * residentBaseinfoDB.getRealName(); if (!realName.equals(realNameDB)) {
         * falg = true; details = details + "<居民姓名>由原来的:[" + realNameDB +
         * "],修改为:[" + realName + "],"; } String idNumberDB =
         * residentBaseinfoDB.getIdNumber(); if (!idNumber.equals(idNumberDB)) {
         * falg = true; details = details + "<身份证号>由原来的:[" + idNumberDB +
         * "],修改为:[" + idNumber + "],"; } String healthNumber =
         * residentBaseinfo.getHealthNumber(); String healthNumberDB =
         * residentBaseinfoDB.getHealthNumber(); if
         * (!healthNumber.equals(healthNumberDB)) { falg = true; details =
         * details + "<居民健康卡号>由原来的:[" + healthNumberDB + "],修改为:[" +
         * healthNumber + "],"; } String socialNumber =
         * residentBaseinfo.getSocialNumber(); String socialNumberDB =
         * residentBaseinfoDB.getSocialNumber(); if
         * (!socialNumber.equals(socialNumberDB)) { falg = true; details =
         * details + "<社保卡号>由原来的:[" + socialNumberDB + "],修改为:[" + socialNumber
         * + "],"; } String periodValidityDateDB =
         * DateUtils.parseDateFormat(residentBaseinfoDB.getPeriodValidityDate())
         * ; String periodValidityDate =
         * DateUtils.parseDateFormat(residentBaseinfo.getPeriodValidityDate());
         * if (!periodValidityDate.equals(periodValidityDateDB)) { falg = true;
         * details = details + "<证件有效期>由原来的:[" + periodValidityDateDB +
         * "],修改为:[" + periodValidityDate + "],"; } String sex =
         * residentBaseinfo.getSex(); String sexDB =
         * residentBaseinfoDB.getSex(); if (!sex.equals(sexDB)) { falg = true;
         * details = details + "<性别>由原来的:[" + sexDB + "],修改为:[" + sex + "],"; }
         * Integer age = residentBaseinfo.getAge(); Integer ageDB =
         * residentBaseinfoDB.getAge(); if (age != ageDB) { falg = true; details
         * = details + "<年龄>由原来的:[" + ageDB + "],修改为:[" + age + "],"; } String
         * nation = residentBaseinfo.getNation(); String nationDB =
         * residentBaseinfoDB.getNation(); if (!nation.equals(nationDB)) { falg
         * = true; details = details + "<民族>由原来的:[" + nationDB + "],修改为:[" +
         * nation + "],"; } String houseAddress =
         * residentBaseinfo.getHouseAddress(); String houseAddressDB =
         * residentBaseinfoDB.getHouseAddress(); if
         * (!(StringUtils.compare(houseAddress, houseAddressDB))) { falg = true;
         * details = details + "<户籍地址>由原来的:[" + houseAddressDB + "],修改为:[" +
         * houseAddress + "],"; } String newAddress =
         * residentBaseinfo.getNowAddress(); String newAddressDB =
         * residentBaseinfoDB.getNowAddress(); if
         * (!(StringUtils.compare(newAddress, newAddressDB))) { falg = true;
         * details = details + "<现住地址>由原来的:[" + newAddressDB + "],修改为:[" +
         * newAddress + "],"; } String education =
         * residentBaseinfo.getEducation(); String educationDB =
         * residentBaseinfoDB.getEducation(); if
         * (!(StringUtils.compare(education, educationDB))) { falg = true;
         * details = details + "<学历>由原来的:[" + educationDB + "],修改为:[" +
         * education + "],"; } String wrokUnit = residentBaseinfo.getWrokUnit();
         * String wrokUnitDB = residentBaseinfoDB.getWrokUnit(); if
         * (!(StringUtils.compare(wrokUnit, wrokUnitDB))) { falg = true; details
         * = details + "<工作单位>由原来的:[" + wrokUnitDB + "],修改为:[" + wrokUnit +
         * "],"; } String phone = residentBaseinfo.getPhone(); String phoneDB =
         * residentBaseinfoDB.getPhone(); if (!(StringUtils.compare(phone,
         * phoneDB))) { falg = true; details = details + "<联系电话>由原来的:[" +
         * phoneDB + "],修改为:[" + phone + "],"; } String postCode =
         * residentBaseinfo.getPostCode(); String postCodeDB =
         * residentBaseinfoDB.getPostCode(); if (!(StringUtils.compare(postCode,
         * postCodeDB))) { falg = true; details = details + "<邮编>由原来的:[" +
         * postCodeDB + "],修改为:[" + postCode + "],"; } Long areaId =
         * residentBaseinfo.getAreaId(); Long areaIdDB =
         * residentBaseinfoDB.getAreaId(); if (areaId != areaIdDB) { falg =
         * true; AdministrativeDivision division =
         * administrativeDivisionDao.getBaseObject(areaId);
         * AdministrativeDivision divisionDB =
         * administrativeDivisionDao.getBaseObject(areaIdDB); if (divisionDB !=
         * null) { falg = true; details = details + "<区域机构>由原来的:[" +
         * divisionDB.getName() + "],修改为:[" + division.getName() + "],"; } else
         * { falg = true; details = details + "<区域机构>添加为:[" + division.getName()
         * + "],"; } } Long officeId = residentBaseinfo.getOfficeId(); Long
         * officeIdDB = residentBaseinfoDB.getOfficeId(); if (officeId !=
         * officeIdDB) { AdministrativeManagement managementDB =
         * administrativeManagementDao.getBaseObject(officeIdDB);
         * AdministrativeManagement management =
         * administrativeManagementDao.getBaseObject(officeId); if (managementDB
         * != null) { falg = true; details = details + "<行政机构>由原来的:[" +
         * managementDB.getAdministrativeName() + "],修改为:[" +
         * management.getAdministrativeName() + "],"; } else { falg = true;
         * details = details + "<行政机构>添加为:[" +
         * management.getAdministrativeName() + "],"; } }
         */
        InfoManagementLog infoManagementLog = new InfoManagementLog();
        infoManagementLog.setType(0);
        infoManagementLog.setFormName("居民基本信息");
        infoManagementLog.setDetails(fieldNames);
        return infoManagementLog;
    }



    /**
     * 
     * 描述复制对象，忽略空字符串，null
     * 
     * @param src
     * @param target
     * @author yangjunhui 2017年9月21日 下午5:23:44
     * @version 1.0
     */
    public void copyPropertiesIgnoreNull(Object src, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }



    /**
     * 
     * 描述：获取为null和空字符串的熟悉名称
     * 
     * @param source
     * @return
     * @author yangjunhui 2017年9月21日 下午5:28:37
     * @version 1.0
     */
    public String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null || srcValue.toString().trim().length() <= 0)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }



    /**
     * 
     * 描述:对居民基本数据进行封装
     * 
     * @param list
     * @param systemUser
     * @author sunxuefeng 2017年9月7日 下午2:22:12
     * @version 1.0
     */
    private List<ResidentBaseinfo> addResidentBaseinfoData(List<ResidentBaseinfo> list, SystemUser systemUser) {
        List<ResidentBaseinfo> Baseinfos = new ArrayList<ResidentBaseinfo>();
        Long userId = systemUser.getId();
        Long organizationId = systemUser.getOrganizationId();
        Long areaId = systemUser.getAreaId();
        Date date = DateUtils.getCurrentDate();
        if (list != null && list.size() > 0) {
            for (ResidentBaseinfo residentBaseinfo : list) {
                residentBaseinfo.setCreateDate(date);
                residentBaseinfo.setCreator(userId);
                residentBaseinfo.setOfficeId(organizationId);
                residentBaseinfo.setAreaId(areaId);
                residentBaseinfo.setDelFalg(0);
                Baseinfos.add(residentBaseinfo);
            }
        }
        return Baseinfos;
    }
}
