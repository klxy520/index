package com.jc.cz_index.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IInfoManagementLogDao;
import com.jc.cz_index.dao.IResidentExtendinfoDao;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.IResidentsInfoService;
import com.jc.cz_index.dao.IResidentsInfoDao;
import com.jc.cz_index.dao.IUpdateResidentLogDao;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.resident.ResidentsInfoDto;
import com.jc.cz_index.model.InfoManagementLog;
import com.jc.cz_index.model.ResidentExtendinfo;
import com.jc.cz_index.model.ResidentsInfo;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.model.UpdateResidentLog;

/**
 * 
 * 描述：基本信息管理Service 实现
 * 
 * @author sunxuefeng 2017年9月27日 下午2:03:31
 * @version 1.0
 */
@Service
public class ResidentsInfoService extends BaseService<ResidentsInfo> implements IResidentsInfoService {
    @Autowired
    private IResidentsInfoDao      residentsInfoDao;
    @Autowired
    private IUpdateResidentLogDao  updateResidentLogDao;
    @Autowired
    private IInfoManagementLogDao  infoManagementLogDao;
    @Autowired
    private IResidentExtendinfoDao residentExtendinfoDao;
    @Autowired
    private IOperationLogService   operationLogService;
    private static final Logger    LOG = Logger.getLogger(ResidentsInfoService.class);

    private static final String field = "主键,id;姓名,name;居民身份证号码,identityNumber;申办单位,bidUtil;卡的类别,cardType;发卡机构名称,issuersCardName;发卡机构代码,issuersCardCode;发卡序列号,issuingSerialNumber;发卡机构证书,issuersCardCertificate;" + "发卡时间,issuingTime;性别,sex;民族代码,nationalCode;出生日期,birthday;卡有效期,cardValidityPeriod;银行卡号,bankCardNumber;本人电话,selfPhone;医疗支付方式,medicalPayment;户籍地址,houseAddress;现住地址,nowAddress;联系人姓名,contactName;联系人关系,contactRelation;联系人电话,contactPhone;文化程度代码,educationLevelCode;" + "婚姻状况代码,maritalStatusCode;职业代码,professionalCode;社保卡号,socialSecurityNum;发卡银行,issuingBank;卡状态,cardStatus;芯片号,chipNum;行政机构,officeId;区域机构,areaId;成行同步状态,cardSyncStatus1;工行同步状态,cardSyncStatus2;";


    @Override
    public IDataProvider<ResidentsInfo, Long> getModelDao() {
        // TODO Auto-generated method stub
        return this.residentsInfoDao; 
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
    public PagedList<ResidentsInfoDto> queryResidentBaseinfoList(QueryParams params, SystemUser user, Integer start, Integer size) {
        if (null == params) {
            params = new QueryParams();
        }
        if (start < 0)
            throw new IllegalArgumentException("pageIndex参数不能小于0.");
        if (size < 0)
            throw new IllegalArgumentException("pageSize参数不能小于0.");
        Long areaId = user.getAreaId();
        Long organizationId = user.getOrganizationId();
        if (StringUtils.compareIsNull(areaId, organizationId)) {
            params.put("office", organizationId);
            params.put("area", areaId);
        } else {
            if (areaId == null || areaId == 0) {
                params.put("area", -1);
            } else {
                params.put("area", areaId);
            }
            if (organizationId == null || organizationId == 0) {
                params.put("office", -1);
            } else {
                params.put("office", organizationId);
            }
        }
        int total = residentsInfoDao.countResidentBaseinfolist(params);
        params.setStart(start);
        params.setSize(size);
        params.put("delFalg", 0);
        List<ResidentsInfoDto> list = residentsInfoDao.selectResidentBaseinfolist(params);
        PagedList<ResidentsInfoDto> result = new PagedList<ResidentsInfoDto>();
        result.getList().addAll(list);
        result.setPageIndex(start / size + 1);
        result.setPageSize(size);
        result.setTotalSize(total);
        result.setPageCount(total / size + 1);
        return result;
    }



    /**
     * 
     * 描述：批量导入居民基本信息
     * 
     * @param list
     * @param user
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年9月28日 上午11:46:32
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchAddResidentsinfo(List<ResidentsInfo> list, SystemUser user) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            List<ResidentsInfo> Data = addResidentsInfoData(list, user);
            Date date = DateUtils.getCurrentDate();
            Long userId = user.getId();
            String name = user.getName();
               Long areaId = user.getAreaId();
               if (areaId==null||areaId==0) {
                   areaId=1l;
               }
               Long organizationId = user.getOrganizationId();
            for (ResidentsInfo residentsInfo : Data) {
                residentsInfo.setAreaId(areaId);
                residentsInfo.setOfficeId(organizationId);
                int i = residentsInfoDao.insertObject(residentsInfo);// 新增基本信息
                if (i != 1) {
                    responseData.setStatus(ResponseData.ERROR_STATUS);
                    responseData.setMessage("居民基本信息导入失败");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return responseData;
                }
                Long recordId = residentsInfo.getId();
                UpdateResidentLog updateResidentLog = new UpdateResidentLog();// 新增用户操作记录
                updateResidentLog.setCreateDate(date);
                updateResidentLog.setUserId(userId);
                updateResidentLog.setResidentId(recordId);
                updateResidentLogDao.insertObject(updateResidentLog);
                ResidentExtendinfo residentExtendinfo = new ResidentExtendinfo();
                residentExtendinfo.setBaseId(recordId);
                residentExtendinfo.setCreateDate(date);
                residentExtendinfo.setHealthNumber(residentsInfo.getIdentityNumber());
                residentExtendinfoDao.insertObject(residentExtendinfo);
            }
            InfoManagementLog infoManagementLog = new InfoManagementLog();// 新增操作日志
            infoManagementLog.setCreator(userId);
            infoManagementLog.setCreatorName(name);
            infoManagementLog.setCreateDate(date);
            infoManagementLog.setType(1);
            infoManagementLog.setFormName("居民基本信息");
            infoManagementLog.setDetails("<" + name + ">批量导入了" + Data.size() + "条居民健康卡信息");
            infoManagementLogDao.insertObject(infoManagementLog);
            responseData.setMessage("居民基本信息导入成功");
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        } catch (Exception e) {
            throw new Exception(e);
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
    @Transactional(rollbackFor = Exception.class)
    public ResponseData deleteResidentsinfoById(Long id, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            ResidentsInfo residentsInfo = residentsInfoDao.getBaseObject(id);
            if (residentsInfo == null) {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("居民健康卡基本信息不存在");
                return responseData;
            }
            Long userid = systemUser.getId();
            Date date = DateUtils.getCurrentDate();
            residentsInfo.setDelFalg(1);
            residentsInfo.setDeleteDate(date);
            residentsInfo.setDeleteor(userid);
            residentsInfoDao.updateObject(residentsInfo);// 逻辑删除基本信息
            String name = systemUser.getName();
            Long residentsInfoId = residentsInfo.getId();
            InfoManagementLog infoManagementLog = new InfoManagementLog(); // 新增操作日志
            infoManagementLog.setCreator(userid);
            infoManagementLog.setCreatorName(name);
            infoManagementLog.setCreateDate(date);
            infoManagementLog.setType(-1);
            infoManagementLog.setFormName("居民基本信息");
            infoManagementLog.setRecordId(residentsInfoId);
            infoManagementLog.setDetails("<" + name + ">" + "删除了一条居民健康卡基本数据和它的扩展信息,<居民姓名>为[" + residentsInfo.getName() + "]," + "<身份证号>为:["
                    + residentsInfo.getIdentityNumber() + "]");
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
            responseData.setMessage("基本信息和扩展信息删除成功");
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
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
    public PagedList<ResidentsInfo> queryOtherResidentsInfoList(QueryParams params, Integer start, Integer size) {
        if (null == params) {
            params = new QueryParams();
        }
        if (start < 0)
            throw new IllegalArgumentException("pageIndex参数不能小于0.");
        if (size < 0)
            throw new IllegalArgumentException("pageSize参数不能小于0.");
        params.put("otherFalg", 1);
        params.put("delFalg", 0);
        int total = residentsInfoDao.queryCount(params);
        params.setStart(start);
        params.setSize(size);
        List<ResidentsInfo> list = residentsInfoDao.queryBaseList(params);
        PagedList<ResidentsInfo> result = new PagedList<ResidentsInfo>();
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
    public ResponseData addManagementScope(SystemUser systemUser, Long residentId) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            ResidentsInfo residentsInfo = residentsInfoDao.getBaseObject(residentId);
            if (residentsInfo == null) {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("居民健康卡基本信息不存在");
                return responseData;
            }
            QueryParams params = new QueryParams();
            Long userId = systemUser.getId();
            Long areaId = systemUser.getAreaId();
            Long organizationId = systemUser.getOrganizationId();
            if (StringUtils.compareIsNull(areaId, organizationId)) {
                params.put("office", organizationId);
                params.put("area", areaId);
                params.put("Falg", 1);
            } else {
                if (areaId == null || areaId == 0) {
                    params.put("area", -1);
                } else {
                    params.put("area", areaId);
                }
                if (organizationId == null || organizationId == 0) {
                    params.put("office", -1);
                } else {
                    params.put("office", organizationId);
                }
            }
            params.put("residentId", residentId);
            List<UpdateResidentLog> list = updateResidentLogDao.queryBaseListByWheres(params);
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
            throw new Exception(e);
        }
        return responseData;
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
    private List<ResidentsInfo> addResidentsInfoData(List<ResidentsInfo> list, SystemUser systemUser) {
        List<ResidentsInfo> Baseinfos = new ArrayList<ResidentsInfo>();
        Long userId = systemUser.getId();
        Long organizationId = systemUser.getOrganizationId();
        Long areaId = systemUser.getAreaId();
        Date date = DateUtils.getCurrentDate();
        if (list != null && list.size() > 0) {
            for (ResidentsInfo residentsInfo : list) {
                residentsInfo.setCreateDate(date);
                residentsInfo.setCreator(userId);
                residentsInfo.setOfficeId(organizationId);
                residentsInfo.setAreaId(areaId);
                residentsInfo.setDelFalg(0);
                Baseinfos.add(residentsInfo);
            }
        }
        return Baseinfos;
    }



    /**
     * 
     * 描述：添加基本信息
     * 
     * @param residentInfo
     * @return
     * @author yangjunhui 2017年9月28日 下午1:31:40
     * @version 1.0
     */
    @Transactional(rollbackFor = BaseException.class)
    public ResponseData addResidentsInfo(ResidentsInfo residentInfo) {
        ResponseData responseData = new ResponseData();
        Date date = new Date();
        try {
            SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
            Long userId = loginUser.getId();
            residentInfo.setCreateDate(date);
            residentInfo.setDelFalg(0);
            residentInfo.setCreator(userId);
            residentsInfoDao.insertObject(residentInfo);
            ResidentExtendinfo residentExtendinfo = new ResidentExtendinfo();
            residentExtendinfo.setBaseId(residentInfo.getId());
            residentExtendinfo.setHealthNumber(residentInfo.getIdentityNumber());
            residentExtendinfo.setCreateDate(date);
            residentExtendinfo.setCreator(userId);
            residentExtendinfoDao.insertObject(residentExtendinfo);
            operationLogService.addOperationLog(
                    "居民健康卡基本信息表", residentInfo.getId() + "", "保存居民信息", " 增加了一条居民健康卡基本信息; id: [ " + residentInfo.getId()
                            + "] , 具体信息 :居民姓名为:[" + residentInfo.getName() + "]," + "身份证号为:[" + residentInfo.getIdentityNumber() + "]",
                    "无");// 操作日志添加记录

            InfoManagementLog infoLog = new InfoManagementLog();// 创建信息表操作日志
            infoLog.setCreateDate(date);
            infoLog.setCreator(userId);
            infoLog.setCreatorName(loginUser.getName());
            infoLog.setFormName("居民健康卡基本信息表");
            infoLog.setRecordId(residentInfo.getId());
            infoLog.setType(1);
            infoLog.setRemark("无");
            String deatils = BeanUtils.getBeanPropertiesByFields(residentInfo, field);
            deatils = loginUser.getName() + "添加一条居民信息，详情【" + deatils + "】";
            infoLog.setDetails(deatils);
            infoManagementLogDao.insertObject(infoLog);
            UpdateResidentLog updateResidentLog = new UpdateResidentLog();// 新增用户操作记录
            updateResidentLog.setCreateDate(new Date());
            updateResidentLog.setUserId(userId);
            updateResidentLog.setResidentId(residentInfo.getId());
            updateResidentLogDao.insertObject(updateResidentLog);
            responseData.setMessage("信息添加成功");
            responseData.setStatus(0);
        } catch (Exception e) {
            LOG.error("基本信息添加错误：" + e.getMessage());
            responseData.setMessage("信息添加失败");
            responseData.setStatus(1);
        }

        return responseData;
    }
    

    /**
     * 
     * 描述：app添加流动居民信息
     * 
     * @param residentInfo
     * @return
     * @author yangjunhui 2017年9月28日 下午1:31:40
     * @version 1.0
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public ResponseData addResidentsInfoByApp(ResidentsInfo residentInfo) {
        ResponseData responseData = new ResponseData();
        boolean isMatch=false;
        try {
            residentInfo.setCreateDate(new Date());
            residentInfo.setDelFalg(0);
            residentInfo.setIsFloating(1L);
            residentInfo.setCreator(1L);
            String idnum=residentInfo.getIdentityNumber();
            String [] regx={"^\\d{6}(18\\d{2}|19\\d{2}|20\\d{2})([0][1-9]|1[0-2])([0][1-9]|[1-2][0-9]|[3][0-1])([0-9]{3})([0-9]|x|X)$","^\\d{6}(\\d{2})([0][1-9]|1[0-2])([0][1-9]|[1-2][0-9]|[3][0-1])([0-9]{3})$"};
            for(int i=0;i<2;i++){
                Pattern p = Pattern.compile(regx[i]);
                Matcher m= p.matcher(idnum);
                if(m.matches()){
                    residentInfo.setBirthday( m.group(1)+ m.group(2)+ m.group(3));
                    Integer sex=Integer.valueOf(m.group(4))%2==0?2:1;
                    residentInfo.setSex(sex.toString());
                    residentInfo.setAreaId(0L);
                    residentInfo.setOfficeId(0L);
                    residentsInfoDao.insertObject(residentInfo);
                    isMatch=true;
                    break;
                } 
            }
            if(!isMatch){
                throw new BaseException("居民身份证格式不准确");  
            }else{
                ResidentExtendinfo residentExtendinfo = new ResidentExtendinfo();
                residentExtendinfo.setBaseId(residentInfo.getId());
                residentExtendinfo.setCreateDate(new Date());
                residentExtendinfo.setHealthNumber(residentInfo.getIdentityNumber());
                residentExtendinfo.setCreator(1L);
                residentExtendinfoDao.insertObject(residentExtendinfo);
                InfoManagementLog infoLog = new InfoManagementLog();// 创建信息表操作日志
                infoLog.setCreateDate(new Date());
                infoLog.setCreator(1L);
                infoLog.setCreatorName("超级管理员");
                infoLog.setFormName("居民健康卡基本信息表");
                infoLog.setRecordId(residentInfo.getId());
                infoLog.setType(1);
                infoLog.setRemark("app添加流动居民");
                String fields = "主键,id;姓名,name;居民身份证号码,identityNumber;";  
                String deatils = BeanUtils.getBeanPropertiesByFields(residentInfo, fields);
                deatils = "超级管理员通过app添加一条流动居民信息，详情【" + deatils + "】";
                infoLog.setDetails(deatils);
                infoManagementLogDao.insertObject(infoLog);  
                responseData = ResponseData.getSuccessResponse("流动人员添加成功");
                LOG.info("流动人员信息添加成功："+deatils);
            }          
        } catch (Exception e) {
            LOG.error("流动人员信息添加错误：" + e.getMessage());
            responseData = ResponseData.getErrorResponse(e.getMessage());
          
        }
        return responseData;
    }

    
    



    /**
     * 
     * 描述：修改
     * 
     * @param residentInfo
     * @return
     * @author yangjunhui 2017年9月28日 下午3:51:08
     * @version 1.0
     */
    @Transactional(rollbackFor = BaseException.class)
    public ResponseData updateResidentsInfo(ResidentsInfo residentInfo) {
        ResponseData responseData = new ResponseData();
        try {
            ResidentsInfo residentInfoDB = new ResidentsInfo();
            SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
            residentInfoDB = residentsInfoDao.getBaseObject(residentInfo.getId());
            ResidentsInfo residentInfoBefore = new ResidentsInfo();
            org.springframework.beans.BeanUtils.copyProperties(residentInfoDB, residentInfoBefore);
            BeanUtils.copyPropertiesIgnoreNull(residentInfo, residentInfoDB);
            residentInfoDB.setOfficeId(residentInfo.getOfficeId());
            residentInfoDB.setEducationLevelCode(residentInfo.getEducationLevelCode());
            residentInfoDB.setMaritalStatusCode(residentInfo.getMaritalStatusCode());
            residentInfoDB.setProfessionalCode(residentInfo.getProfessionalCode());
            residentInfoDB.setMedicalPayment(residentInfo.getMedicalPayment());
            residentInfoDB.setUpdateDate(new Date());
            residentInfoDB.setUpdator(loginUser.getId()); 
            residentInfoDB.setIsFloating(residentInfoBefore.getIsFloating());//设置人口性质
            String details = BeanUtils.getDifferenceByFields(residentInfoBefore, residentInfoDB, field);
            if (details.length() <= 0) {
                responseData.setMessage("与原有数据相同");
                responseData.setStatus(1);
                LOG.info("居民基本信息,修改内容与原有数据相同，不作修改");
                return responseData;
            }         
            residentsInfoDao.updateObject(residentInfoDB);
            InfoManagementLog infoLog = new InfoManagementLog();// 创建信息表操作日志
            infoLog.setCreateDate(new Date());
            infoLog.setCreator(loginUser.getId());
            infoLog.setCreatorName(loginUser.getName());
            infoLog.setFormName("居民健康卡基本信息表");
            infoLog.setRecordId(residentInfo.getId());
            infoLog.setType(0);
            infoLog.setRemark("无");
            details = loginUser.getName() + "修改居民【" + residentInfoBefore.getName() + "[" + residentInfoBefore.getIdentityNumber() + "]"
                    + "】的基本信息信息，详情【" + details + "】";
            infoLog.setDetails(details);
            infoManagementLogDao.insertObject(infoLog);
            UpdateResidentLog updateResidentLog = new UpdateResidentLog();// 新增用户操作记录
            updateResidentLog.setCreateDate(new Date());
            updateResidentLog.setUserId(loginUser.getId());
            updateResidentLog.setResidentId(residentInfo.getId());
            updateResidentLogDao.insertObject(updateResidentLog);
            responseData.setMessage("信息修改成功");
            responseData.setStatus(0);
        } catch (Exception e) {
            LOG.error("居民信息添加失败；失败原因：" + e.getMessage());
            responseData.setMessage("修改失败，原因："+e.getMessage());
            responseData.setStatus(1);
        }
        return responseData;
    }



    /***
     * 
     * 描述：根据身份证号查询居民信息是是否存在
     * 
     * @param identityNumber
     * @return
     * @author sunxuefeng 2017年10月10日 上午10:14:31
     * @version 1.0
     */
    public Boolean queryResidentsInfoByIdentityNumberExistence(String identityNumber) {
        QueryParams queryParams = new QueryParams();
        queryParams.put("identityNumber", identityNumber);
        queryParams.put("delFalg", 0);
        List<ResidentsInfo> list = residentsInfoDao.queryBaseList(queryParams);
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }
}
