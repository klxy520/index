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
import com.jc.cz_index.core.service.IContactWayService;
import com.jc.cz_index.core.service.IContactWaySyncLogService;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.dao.IContactWayDao;
import com.jc.cz_index.model.ContactWay;
import com.jc.cz_index.model.SystemUser;

/**
 * contactWay Service 实现 Created by 
 */
@Service
public class ContactWayService extends BaseService<ContactWay> implements IContactWayService {
	@Autowired
	private IContactWayDao contactWayDao;
    @Autowired
    private IOperationLogService operationLogService;
    @Autowired
	private IContactWaySyncLogService iContactWaySyncLogService;

	
	private final String fields ="主键id,id;主索引id,mpiId;基本身份信息id,personId;修改机构,lastModifyUnit;创建机构,createUnit;联系人方式代码,contactTypeCode;联系人号码,contactNo;创建人,creator;创建时间,createDate;最后更新者,updator;最后更新时间,updateDate;";

	
	@Override
	public IDataProvider<ContactWay, Long> getModelDao() {
		return this.contactWayDao;
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseData deleteContactWayById(Long contactWayId) {
		ResponseData responseData=new ResponseData();
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
		ContactWay contactWayDB=new ContactWay();
		contactWayDB=contactWayDao.getBaseObject(contactWayId);
		try {
			String details=BeanUtils.getBeanPropertiesByFields(contactWayDB, fields);
			contactWayDB.setDeleteor(loginUser.getId());
			contactWayDB.setDeleteDate(new Date());
			contactWayDB.setDelFalg(1);
			contactWayDB.setId(contactWayId);
			contactWayDao.updateObject(contactWayDB);
			iContactWaySyncLogService.addContactWaySyncLog(contactWayId,details,loginUser.getId());
		
			operationLogService.addOperationLog("联系方式信息", contactWayId+"", "删除联系方式信息","被删除联系方式原有详情："+details, "无");
			responseData=ResponseData.getSuccessResponse();
		} catch (Exception e) {
			responseData=ResponseData.getErrorResponse(e.getMessage());
		}
		
		return responseData;
		
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseData addContactWay(ContactWay contactWay) {
		ResponseData responseData=new ResponseData();
		SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
		try {
			contactWay.setCreator(loginUser.getId());
			contactWay.setCreateDate(new Date());
			contactWay.setDelFalg(0);
			contactWayDao.insertObject(contactWay);
			String details=BeanUtils.getBeanPropertiesByFields(contactWay, fields);
			iContactWaySyncLogService.addContactWaySyncLog(contactWay.getId(),details,loginUser.getId());
			operationLogService.addOperationLog("联系方式信息",contactWay.getId()+"", "添加联系方式信息","被添加联系方式信息详情："+details, "无");
			responseData=ResponseData.getSuccessResponse();
		} catch (Exception e) {
			responseData=ResponseData.getErrorResponse(e.getMessage());
			  e.printStackTrace();
		}
		return responseData;
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseData updateContactWay(ContactWay contactWay) {
		ResponseData responseData=new ResponseData();	
		try {
			ContactWay contactWayDB=contactWayDao.getBaseObject(contactWay.getId());
			ContactWay contactWayNew=new ContactWay();
            BeanUtils.copyPropertiesIgnoreNull(contactWayDB, contactWayNew);// 将数据库的内容拷贝到contactWayNew，记录原始信息          
            BeanUtils.copyPropertiesIgnoreNull(contactWay, contactWayNew);// 将数据库的内容拷贝到extendBefore，记录原始信息          
			String details=BeanUtils.getDifferenceByFields(contactWayDB, contactWayNew, "联系人方式,contactTypeCode;修改机构,lastModifyUnit;创建机构,createUnit;联系人号码,contactNo;");
			SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
			contactWayNew.setUpdator(loginUser.getId());
			contactWayNew.setUpdateDate(new Date());
			contactWayDao.updateObject(contactWayNew);
			iContactWaySyncLogService.addContactWaySyncLog(contactWay.getId(),details,loginUser.getId());
			operationLogService.addOperationLog("联系方式信息",contactWay.getId()+"", "修改联系方式信息","被修改联系方式信息详情："+details, "无");
			responseData=ResponseData.getSuccessResponse();
			responseData.setMessage("修改成功");
		} catch (Exception e) {
			responseData=ResponseData.getErrorResponse(e.getMessage());
			e.printStackTrace();
		}
		return responseData;
	}
	@Override
	public List<ContactWay> excuteTask(Long id) {
		return contactWayDao.getUnSyncObjectList(id);
	}
}

