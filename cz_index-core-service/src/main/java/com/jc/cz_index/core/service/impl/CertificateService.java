package com.jc.cz_index.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.service.ICertificateService;
import com.jc.cz_index.core.service.ICertificateSyncLogService;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.dao.ICertificateDao;
import com.jc.cz_index.model.Certificate;
import com.jc.cz_index.model.SystemUser;

/**
 * certificate Service 实现 Created by
 */
@Service
public class CertificateService extends BaseService<Certificate> implements ICertificateService {
    @Autowired
    private ICertificateDao            certificateDao;
    @Autowired
    private IOperationLogService       operationLogService;
    @Autowired
    private ICertificateSyncLogService iCertificateSyncLogService;

    private final String               fields = "主键id,id;主索引id,mpiId;基本身份信息id,personId;修改机构,lastModifyUnit;创建机构,createUnit;证件类别代码,certificateTypeCode;证件号码,certificateNo;创建人,creator;创建时间,createDate;最后更新者,updator;最后更新时间,updateDate;";


	
    @Override
    public IDataProvider<Certificate, Long> getModelDao() {
        return this.certificateDao;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData deleteCertificateById(Long certificateId) {
        ResponseData responseData = new ResponseData();
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
		Certificate certificateDB=new Certificate();
		certificateDB=certificateDao.getBaseObject(certificateId);
		try {
			String details=BeanUtils.getBeanPropertiesByFields(certificateDB, fields);
			certificateDB.setDeleteor(loginUser.getId());
			certificateDB.setDeleteDate(new Date());
			certificateDB.setDelFalg(1);
			certificateDB.setId(certificateId);
			certificateDao.updateObject(certificateDB);
			iCertificateSyncLogService.addCertificateSyncLog(certificateId,"",loginUser.getId());
			operationLogService.addOperationLog("证件信息", certificateId+"", "删除证件信息","被删除证件原有详情："+details, "无");
			responseData=ResponseData.getSuccessResponse();
		} catch (Exception e) {
			responseData=ResponseData.getErrorResponse(e.getMessage());
		}
		return responseData;
		
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseData addCertificate(Certificate certificate) {
		ResponseData responseData=new ResponseData();
		SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
		try {
			certificate.setCreator(loginUser.getId());
			certificate.setCreateDate(new Date());
			certificate.setDelFalg(0);
			certificateDao.insertObject(certificate);
			String details=BeanUtils.getBeanPropertiesByFields(certificate, fields);
			iCertificateSyncLogService.addCertificateSyncLog(certificate.getId(),"",loginUser.getId());
			operationLogService.addOperationLog("证件信息",certificate.getId()+"", "添加证件信息","被添加证件详情："+details, "无");
			 responseData=ResponseData.getSuccessResponse();
		} catch (Exception e) {
			responseData=ResponseData.getErrorResponse(e.getMessage());
			  e.printStackTrace();
		}
		return responseData;
	}







    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData updateCertificate(Certificate certificate) {
        ResponseData responseData = new ResponseData();

        try {
            Certificate certificateDB = certificateDao.getBaseObject(certificate.getId());
            Certificate certificateNew = new Certificate();
            BeanUtils.copyPropertiesIgnoreNull(certificateDB, certificateNew);// 将数据库的内容拷贝到certificateNew，记录原始信息
            BeanUtils.copyPropertiesIgnoreNull(certificate, certificateNew);// 将数据库的内容拷贝到extendBefore，记录原始信息
            String details = BeanUtils.getDifferenceByFields(certificateDB, certificateNew,
                    "证件类别代码,certificateTypeCode;修改机构,lastModifyUnit;创建机构,createUnit;证件号码,certificateNo;");
          
                SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
                certificateNew.setUpdator(loginUser.getId());
                certificateNew.setUpdateDate(new Date());
                certificateDao.updateObject(certificateNew);
                iCertificateSyncLogService.addCertificateSyncLog(certificate.getId(), "", loginUser.getId());
                operationLogService.addOperationLog("证件信息", certificate.getId() + "", "修改证件信息", "被修改证件详情：" + details, "无");
                responseData=ResponseData.getSuccessResponse();
                responseData.setMessage("修改成功");
        } catch (Exception e) {
        	responseData=ResponseData.getErrorResponse(e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 获取需要同步的日志
     */
    @Override
    public List<Certificate> excuteTask(Long id) {
        return certificateDao.getUnSyncObjectList(id);
    }

}
