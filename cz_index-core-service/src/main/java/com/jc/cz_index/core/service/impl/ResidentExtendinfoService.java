package com.jc.cz_index.core.service.impl;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
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
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IInfoManagementLogDao;
import com.jc.cz_index.core.service.IResidentExtendinfoService;
import com.jc.cz_index.dao.IResidentExtendinfoDao;
import com.jc.cz_index.dao.IResidentsInfoDao;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.InfoManagementLog;
import com.jc.cz_index.model.ResidentExtendinfo;
import com.jc.cz_index.model.ResidentsInfo;
import com.jc.cz_index.model.SystemUser;

/**
 * residentExtendinfo Service 实现 Created by
 */
@Service
public class ResidentExtendinfoService extends BaseService<ResidentExtendinfo> implements IResidentExtendinfoService {
    @Autowired
    private IResidentExtendinfoDao residentExtendinfoDao;
    @Autowired
    private IInfoManagementLogDao  infoManagementLogDao;
    @Autowired
    private IResidentsInfoDao      residentsInfoDao;
    private static final Logger    LOG = Logger.getLogger(ResidentExtendinfoService.class);



    @Override
    public IDataProvider<ResidentExtendinfo, Long> getModelDao() {
        return this.residentExtendinfoDao;
    }



    /**
     * 
     * 描述：根据id查询扩展信息
     * 
     * @param id
     * @return
     * @author yangjunhui 2017年9月18日 下午1:39:03
     * @version 1.0
     */
    @Override
    public ResidentExtendinfo queryResidentExtendinfoDetailsById(Long id) {
        if (id != null) {
            return residentExtendinfoDao.queryResidentExtendinfoDetailsById(id);
        }
        return null;

    }



    /**
     * 
     * 描述：修改扩展信息
     * 
     * @param extendInfo
     * @return
     * @author yangjunhui 2017年9月18日 下午1:39:31
     * @version 1.0
     */
    @Override
    public ResponseData updateResidenExtendinfo(ResidentExtendinfo extendInfo) {
        ResponseData responseData = new ResponseData();
        Long id = extendInfo.getBaseId();
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        ResidentExtendinfo extendDB = residentExtendinfoDao.queryResidentExtendinfoDetailsById(id);// 数据库信息
        if (extendDB != null) {    
            ResidentExtendinfo extendBefore = new ResidentExtendinfo();
            org.springframework.beans.BeanUtils.copyProperties(extendDB, extendBefore);// 将数据库的内容拷贝到extendBefore，记录原始信息          
            extendDB.setUpdator(loginUser.getId());
            extendDB.setUpdateDate(new Date()); 
            extendDB.setIsDisableFederation(extendInfo.getIsDisableFederation());
            extendDB.setIsCivilAffairs(extendInfo.getIsCivilAffairs());
            String logStatus = createInfoManagementLog(extendBefore, extendDB);
            if (logStatus.equals("日志创建成功")) {
                responseData.setStatus(0);
                responseData.setMessage("居民健康卡扩展信息修改成功");
                residentExtendinfoDao.updateExtendByBaseId(extendDB);// 修改基本信息
            } else {
                responseData.setStatus(1);
                responseData.setMessage(logStatus);
            }
        } else {
            LOG.error("获取该账户原有信息失败");
            responseData.setStatus(1);
            responseData.setMessage("居民健康卡扩展信息修改失败");
        }
        return responseData;
    }



    /**
     * 
     * 描述：添加扩展信息
     * 
     * @param extendInfo
     * @return
     * @throws BaseException
     * @author yangjunhui 2017年9月18日 下午1:33:07
     * @version 1.0
     */
    public ResponseData addResidenExtendinfo(ResidentExtendinfo extendInfo) {
        ResponseData responseData = new ResponseData();
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        try {
            extendInfo.setCreator(loginUser.getId());
            extendInfo.setCreateDate(new Date());
            String logStatus = createInfoManagementLog(null, extendInfo);
            if (!logStatus.equals("日志创建成功")) {
                responseData.setStatus(0);
                responseData.setMessage(logStatus);
                return responseData;// 修改基本信息
            }
            int i = residentExtendinfoDao.insertObject(extendInfo);// 新增基本信息
            if (i != 1) {
                responseData.setStatus(1);
                responseData.setMessage("居民健康卡信息添加失败");
                return responseData;
            }
            responseData.setMessage("居民健康卡扩展数据添加成功");
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 
     * 描述： 向信息操作表添加日志
     * 
     * @param originalObj
     * @param newObj
     * @return
     * @author yangjunhui 2017年9月18日 下午1:56:49
     * @version 1.0
     */
    public String createInfoManagementLog(ResidentExtendinfo original, ResidentExtendinfo newObj) {
        InfoManagementLog infoManagementLog = new InfoManagementLog();
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        infoManagementLog.setFormName("居民扩展信息");
        infoManagementLog.setRecordId(newObj.getBaseId());
        infoManagementLog.setCreateDate(new Date());
        infoManagementLog.setCreator(loginUser.getId());
        infoManagementLog.setCreatorName(loginUser.getName());
        String details = loginUser.getName() + "编辑居民姓名为:[" + newObj.getRealName() + "]," + "身份证号为:[" + newObj.getIdNumber()
                + "]的居民健康卡扩展信息。";
        if (original == null) {
            infoManagementLog.setType(1);// 设置操作类型为添加
            try {
                String updateStr = BeanUtils.getObjectNotNullField(newObj, 7, 15);
                if (updateStr.length() <= 0) {
                    return "修改内容与原有信息相同";
                }
                details += "新增信息为：" + updateStr;
                LOG.info("居民扩展信息添加内容【" + details + "】");
            } catch (Exception e) {
                e.printStackTrace();
            }
            infoManagementLog.setDetails(details);
        } else {
            infoManagementLog.setType(0);// 设置修改
            try {
                String updateStr = BeanUtils.getDifferenceByFields(original, newObj,
                        "医保类型,insuranceType;病种类型,illnessType;残疾类型,disabilityType;工会特征,unionFeature;离休干部,retiredCadres;扶贫户,helpHouse;低保户,lowType;残联性质 ,isDisableFederation;民政性质,isCivilAffairs;");
                if (null == updateStr || updateStr.length() <= 0) {
                    return "修改内容与原有信息相同";
                }
                details += updateStr;
                LOG.info("居民扩展信息修改内容【" + details + "】");
            } catch (IllegalArgumentException e) {
                LOG.error("获取实体不相同属性失败：" + e);
                e.printStackTrace();
            }
        }
        infoManagementLog.setDetails(details);
        infoManagementLogDao.insertObject(infoManagementLog);
        return "日志创建成功";
    }



    /**
     * 
     * 描述： 查询列表信息
     * 
     * @param params
     * @param start
     * @param size
     * @return
     * @author yangjunhui 2017年9月20日 上午11:49:05
     * @version 1.0
     */
    public PagedList<ResidentExtendinfo> queryPagedListForTable(QueryParams params, int start, int size) {
        if (null == params) {
            params = new QueryParams();
        }
        if (start < 0)
            throw new IllegalArgumentException("pageIndex参数不能小于0.");
        if (size < 0)
            throw new IllegalArgumentException("pageSize参数不能小于0.");
        int total = queryAllCount(params);
        params.setStart(start);
        params.setSize(size);
        List<ResidentExtendinfo> list = residentExtendinfoDao.queryPagedListForTable(params);
        PagedList<ResidentExtendinfo> result = new PagedList<>();
        result.getList().addAll(list);
        result.setPageIndex(start / size + 1);
        result.setPageSize(size);
        result.setTotalSize(total);
        result.setPageCount(total / size + 1);
        return result;
    }



    /**
     * 
     * 描述： 满足条件的数据条数
     * 
     * @param params
     * @return
     * @author yangjunhui 2017年9月20日 上午11:50:44
     * @version 1.0
     */
    private int queryAllCount(QueryParams params) {
        return residentExtendinfoDao.getQueryCount(params);
    }



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
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchAddResidentExtendinfo(List<ResidentExtendinfo> list, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        Long userId = systemUser.getId();
        String name = systemUser.getName();
        Date date = DateUtils.getCurrentDate();
        try {
            if (list != null && list.size() > 0) {
                for (ResidentExtendinfo dto : list) {
                    QueryParams params = new QueryParams();
                    String healthNumber = dto.getHealthNumber();
                    params.put("identityNumber", healthNumber);
                    params.put("delFalg", 0);
                    List<ResidentsInfo> residentsInfos = residentsInfoDao.queryBaseList(params);
                    if (residentsInfos == null || residentsInfos.size() == 0) {
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
                    for (ResidentExtendinfo residentExtendinfo : extendinfos) {
                        residentExtendinfo.setIsCivilAffairs(dto.getIsCivilAffairs());
                        residentExtendinfo.setIsDisableFederation(dto.getIsDisableFederation());
                        residentExtendinfo.setUpdateDate(date);
                        residentExtendinfo.setUpdator(userId);
                        residentExtendinfo.setDelFalg(0);
                        residentExtendinfoDao.updateObject(residentExtendinfo);
                    }
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
            throw new Exception(e);
        }
        return null;
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
}
