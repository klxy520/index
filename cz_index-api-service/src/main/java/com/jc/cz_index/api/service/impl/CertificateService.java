package com.jc.cz_index.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jc.cz_index.api.service.ICertificateService;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.SpringConfigTool;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WSUtils;
import com.jc.cz_index.dao.ICertificateDao;
import com.jc.cz_index.dao.ICertificateSyncLogDao;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IFrontEndMachineDao;
import com.jc.cz_index.dao.IPersonDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Certificate;
import com.jc.cz_index.model.CertificateSyncLog;
import com.jc.cz_index.model.FrontEndMachine;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.RequestEntity;
import com.jc.cz_index.model.ResponseEntity;

/**
 * certificate Service 实现 Created by
 */
@Service
public class CertificateService extends BaseService<Certificate> implements ICertificateService {
    private static final Logger    LOG = Logger.getLogger(CertificateService.class);
    @Autowired
    private ICertificateDao        certificateDao;
    @Autowired
    private ICertificateSyncLogDao certificateSyncLogDao;
    @Autowired
    private IFrontEndMachineDao    frontEndMachineDao;
    @Autowired
    private IPersonDao             personDao;



    @Override
    public IDataProvider<Certificate, Long> getModelDao() {
        return this.certificateDao;
    }



    /**
     * 统一入口
     */
    public ResponseEntity excute(RequestEntity requestEntity) {
        CertificateService certificateService = SpringConfigTool.getBean("certificateService", CertificateService.class);
        ResponseEntity re = new ResponseEntity();
        // 访问目标
        String action = requestEntity.getAction();
        try {
            switch (action) {
            case "getCertificates":
                // 根据MPIID获取所有证件
                re = certificateService.getCertificates(requestEntity.getPerson());
                break;
            case "registerCertificates":
                // 注册证件信息
                re = certificateService.registerCertificates(requestEntity.getPerson());
                break;
            case "updateCertificates":
                // 更新证件信息
                re = certificateService.updateCertificates(requestEntity.getPerson());
                break;
            case "removeCertificates":
                // 删除证件信息
                re = certificateService.removeCertificates(requestEntity.getPerson());
                break;
            default:
                LOG.error(action + " 访问地址不存在!");
                re = ResponseEntity.getNotFoundErrorResponse();
                break;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.getErrorResponse(e.getMessage());
        }
        return re;
    }



    /**
     * 根据MPIID获取所有证件
     */
    @Override
    public ResponseEntity getCertificates(Person person) {
        String mpiId = person.getMpiId();
        if (StringUtils.isEmpty(mpiId)) {
            LOG.error("主索引id为空");
            return ResponseEntity.getParamsErrorResponse("主索引id不能为空!");
        }
        // 查询未删除的主索引信息
        Person temp = personDao.getObjectByMpiId(mpiId);
        if (temp == null) {
            LOG.error("没有主索引信息");
            return ResponseEntity.getParamsErrorResponse("主索引信息不存在!");
        }
        QueryParams params = new QueryParams();
        params.addParameter("mpiId", mpiId);
        params.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE); // 未删除
        List<Certificate> certificates = certificateDao.queryDetailList(params);
        if (certificates != null && certificates.size() > 0) {
            Person result = new Person();
            result.setMpiId(mpiId);
            result.setCertificates(certificates);
            return ResponseEntity.getSuccessResponse(result, certificates.size());
        }
        LOG.error("数据不存在");
        return ResponseEntity.getErrorResponse("数据不存在", null);
    }



    /**
     * 注册证件信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity registerCertificates(Person person) throws Exception {
        List<Certificate> certificates = person.getCertificates();
        int n = 0;
        if (certificates != null && certificates.size() > 0) {
            for (Certificate cf : certificates) {
                if (StringUtils.isEmpty(cf.getMpiId())) {
                    LOG.error("主索引id为空");
                    return ResponseEntity.getParamsErrorResponse("主索引id不能为空");
                }
                if (StringUtils.isEmpty(cf.getCertificateTypeCode())) {
                    LOG.error("证件类别代码为空");
                    return ResponseEntity.getParamsErrorResponse("证件类别代码不能为空");
                }
                if (StringUtils.isEmpty(cf.getCertificateNo())) {
                    LOG.error("证件号码为空");
                    return ResponseEntity.getParamsErrorResponse("证件号码不能为空");
                }
                Person temp_person = personDao.getEnableObjectByMpiId(cf.getMpiId());
                if(temp_person == null){
                    LOG.error("主索引信息不存在");
                    return ResponseEntity.getErrorResponse("主索引信息不存在");
                }
                QueryParams params = new QueryParams();
                params.addParameter("mpiId", cf.getMpiId());
                params.addParameter("certificateTypeCode", cf.getCertificateTypeCode());
                params.addParameter("certificateNo", cf.getCertificateNo());
                params.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE); // 未删除
                List<Certificate> temp = certificateDao.queryBaseList(params);
                if (temp != null && temp.size() > 0) { // 存在相同数据
                    List<Certificate> results = new ArrayList<Certificate>();
                    results.add(cf);
                    person.setCertificates(results);
                    // 手动回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    LOG.error("存在重复证件信息");
                    return ResponseEntity.getErrorResponse("存在重复证件信息", person);
                    // continue;
                } else {
                    cf.setPersonId(temp_person.getId()); // 设置person_id
                    cf.setCreator(ContentUtils.ADMIN_USER_ID);
                    cf.setCreateDate(DateUtils.getCurrentDate());
                    cf.setDelFalg(ContentUtils.INFO_NOT_DELETE); // 未删除
                    n += certificateDao.insertObject(cf);
                    setSyncLogAsync(cf.getId(), ContentUtils.INFO_SYNC_STATUS_NO);
                }
            }
            return ResponseEntity.getSuccessResponse(null, n);
        }
        LOG.error("certificates不能为空");
        return ResponseEntity.getParamsErrorResponse("certificates不能为空");
    }



    /**
     * 更新证件信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity updateCertificates(Person person) throws Exception {
        Element doc_updateData = WSUtils.strConvertToXML(person.getUpdateData()).getRootElement();
        Element doc_currentData = WSUtils.strConvertToXML(person.getCurrentData()).getRootElement();
        // 数据检查
        ResponseEntity re = checkParamsForUpdate(doc_updateData, doc_currentData);
        if (!ContentUtils.EMPI_STATUS_CODE_SUCCESS.equals(re.getCode())) {
            return re;
        }
        QueryParams params = new QueryParams();
        params.addParameter("mpiId", doc_currentData.elementText("mpiId"));
        params.addParameter("certificateTypeCode", doc_currentData.elementText("certificateTypeCode"));
        params.addParameter("certificateNo", doc_currentData.elementText("certificateNo"));
        params.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE); // 未删除
        List<Certificate> certificate = certificateDao.queryBaseList(params);
        if (certificate != null && certificate.size() > 0) {
            Certificate cf = certificate.get(0);
            cf.setUpdator(ContentUtils.ADMIN_USER_ID);
            cf.setUpdateDate(DateUtils.getCurrentDate());
            cf.setMpiId(doc_updateData.elementText("mpiId"));
            cf.setCertificateTypeCode(doc_updateData.elementText("certificateTypeCode"));
            cf.setCertificateNo(doc_updateData.elementText("certificateNo"));
            int n = certificateDao.updateObject(cf);
            // 设置未同步
            setSyncLogAsync(cf.getId(), ContentUtils.INFO_SYNC_STATUS_NO);
            return ResponseEntity.getSuccessResponse(null, n);
        }
        return ResponseEntity.getErrorResponse("原始数据不存在/已删除");
    }



    public ResponseEntity checkParamsForUpdate(Element doc_updateData, Element doc_currentData) {
        // 校验更新数据
        String mpiId_update = doc_updateData.elementText("mpiId");
        String certificateTypeCode_update = doc_updateData.elementText("certificateTypeCode");
        String certificateNo_update = doc_updateData.elementText("certificateNo");
        if (StringUtils.isEmpty(mpiId_update)) {
            return ResponseEntity.getParamsErrorResponse("updateData:主索引id不能为空");
        }
        if (StringUtils.isEmpty(certificateTypeCode_update)) {
            return ResponseEntity.getParamsErrorResponse("updateData:证件类别代码不能为空");
        }
        if (StringUtils.isEmpty(certificateNo_update)) {
            return ResponseEntity.getParamsErrorResponse("updateData:证件号码不能为空");
        }
        // 校验当前数据
        String mpiId_current = doc_currentData.elementText("mpiId");
        String certificateTypeCode_current = doc_currentData.elementText("certificateTypeCode");
        String certificateNo_current = doc_currentData.elementText("certificateNo");
        if (StringUtils.isEmpty(mpiId_current)) {
            return ResponseEntity.getParamsErrorResponse("currentData:主索引id不能为空");
        }
        if (StringUtils.isEmpty(certificateTypeCode_current)) {
            return ResponseEntity.getParamsErrorResponse("currentData:证件类别代码不能为空");
        }
        if (StringUtils.isEmpty(certificateNo_current)) {
            return ResponseEntity.getParamsErrorResponse("currentData:证件号码不能为空");
        }
        if (!mpiId_update.equals(mpiId_current)) {
            return ResponseEntity.getParamsErrorResponse("updateData与currentData中mpiId不同");
        }

        Person person = personDao.getEnableObjectByMpiId(mpiId_current);
        if (person == null) {
            LOG.error("主索引信息不存在");
            return ResponseEntity.getErrorResponse("主索引信息不存在");
        }
        return ResponseEntity.getSuccessResponse(person, null);
    }



    /**
     * 删除证件信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity removeCertificates(Person person) throws Exception {
        List<Certificate> certificates = person.getCertificates();
        int n = 0;
        if (certificates != null && certificates.size() > 0) {
            for (Certificate cf : certificates) {
                if (StringUtils.isEmpty(cf.getMpiId())) {
                    LOG.error("主索引id为空");
                    return ResponseEntity.getParamsErrorResponse("主索引id不能为空");
                }
                if (StringUtils.isEmpty(cf.getCertificateTypeCode())) {
                    LOG.error("证件类别代码为空");
                    return ResponseEntity.getParamsErrorResponse("证件类别代码不能为空");
                }
                if (StringUtils.isEmpty(cf.getCertificateNo())) {
                    LOG.error("证件号码为空");
                    return ResponseEntity.getParamsErrorResponse("证件号码不能为空");
                }
                if(personDao.getEnableObjectByMpiId(cf.getMpiId()) == null){
                    LOG.error("主索引信息不存在");
                    return ResponseEntity.getErrorResponse("主索引信息不存在");
                }
                QueryParams params = new QueryParams();
                params.addParameter("mpiId", cf.getMpiId());
                params.addParameter("certificateTypeCode", cf.getCertificateTypeCode());
                params.addParameter("certificateNo", cf.getCertificateNo());
                params.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE); // 未删除
                List<Certificate> temp = certificateDao.queryBaseList(params);
                if (temp != null && temp.size() > 0) { // 存在未删除的数据
                    Certificate certificate = temp.get(0);
                    certificate.setDelFalg(ContentUtils.INFO_DELETE); // 删除
                    certificate.setDeleteor(ContentUtils.ADMIN_USER_ID);
                    certificate.setDeleteDate(DateUtils.getCurrentDate());
                    certificate.setUpdator(ContentUtils.ADMIN_USER_ID);
                    certificate.setUpdateDate(DateUtils.getCurrentDate());
                    n += certificateDao.updateObject(certificate);
                    setSyncLogAsync(certificate.getId(), ContentUtils.INFO_SYNC_STATUS_NO);
                } else {
                    Person person_result = new Person();
                    List<Certificate> results = new ArrayList<Certificate>();
                    results.add(cf);
                    person_result.setCertificates(results);
                    // 手动回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    LOG.error("原始数据不存在/已删除");
                    return ResponseEntity.getErrorResponse("原始数据不存在/已删除", person_result);
                    //continue;
                }
            }
            return ResponseEntity.getSuccessResponse(null, n);
        }
        LOG.error("certificates不能为空");
        return ResponseEntity.getParamsErrorResponse("certificates不能为空");

    }



    /**
     * 
     * 描述：异部设置同步记录为未同步
     * 
     * @param certificateId
     * @param syncStatus
     * @author fengshengliang 2017年12月29日 上午9:53:58
     * @version 1.0
     */
    public void setSyncLogAsync(final Long certificateId, final String syncStatus) {
        List<FrontEndMachine> fems = frontEndMachineDao.queryBaseList(null);
        List<CertificateSyncLog> cfss = new ArrayList<CertificateSyncLog>();
        for (FrontEndMachine fem : fems) {
            CertificateSyncLog cfs = new CertificateSyncLog();
            cfs.setCertificateId(certificateId);
            cfs.setFrontId(fem.getId());
            cfs.setSyncStatus(syncStatus);
            cfs.setCreator(ContentUtils.ADMIN_USER_ID);
            cfs.setCreateDate(DateUtils.getCurrentDate());
            cfs.setUpdator(ContentUtils.ADMIN_USER_ID);
            cfs.setUpdateDate(DateUtils.getCurrentDate());
            cfss.add(cfs);
        }
        certificateSyncLogDao.saveOrUpdateList(cfss);
    }
}
