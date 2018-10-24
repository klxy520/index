package com.jc.cz_index.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IFrontEndMachineDao;
import com.jc.cz_index.dao.IPersonDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.api.service.IContactService;
import com.jc.cz_index.common.exception.ParamsException;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.SpringConfigTool;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WSUtils;
import com.jc.cz_index.dao.IContactDao;
import com.jc.cz_index.dao.IContactSyncLogDao;
import com.jc.cz_index.model.Contact;
import com.jc.cz_index.model.ContactSyncLog;
import com.jc.cz_index.model.FrontEndMachine;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.RequestEntity;
import com.jc.cz_index.model.ResponseEntity;

/**
 * contact Service 实现 Created by 
 */
@Service
public class ContactService extends BaseService<Contact> implements IContactService {
	private static final Logger LOG = Logger.getLogger(PersonService.class);
	@Autowired
	private IContactDao 			contactDao;
	
	@Autowired
	private IFrontEndMachineDao 	frontEndMachineDao;
	
	@Autowired
	private IContactSyncLogDao		contactSyncLogDao;
	
	@Autowired
	private IPersonDao				personDao;

	@Override
	public IDataProvider<Contact, Long> getModelDao() {
		return this.contactDao;
	}
	
	public ResponseEntity excute(RequestEntity re) {
		ContactService thisConcatService = SpringConfigTool.getBean("contactService", ContactService.class);
		ResponseEntity responseEntity = new ResponseEntity();
		String action = re.getAction();
		Person person = re.getPerson();
		try {
			switch (action) {
			case "registerContacts":
				responseEntity = thisConcatService.registerContacts(person);
				break;
			case "updateContacts":
				responseEntity = thisConcatService.updateContacts(person);
				break;
			case "removeContacts":
				responseEntity = thisConcatService.removeContacts(person);
				break;
			case "getContacts":
				responseEntity = getContacts(person);
				break;
			default:
				LOG.error("访问地址不存在");
				responseEntity = ResponseEntity.getNotFoundErrorResponse();
				break;
			}
		}   catch (ParamsException pe){
            LOG.error("请求action:" + action + " 异常：" + pe.getMessage());
            responseEntity = ResponseEntity.getParamsErrorResponse(pe.getMessage());
        }   catch (Exception e) {
			LOG.error("请求action:" + action + " 异常：" + e.getMessage());
			responseEntity = ResponseEntity.getParamsErrorResponse(e.getMessage());
		}
        return responseEntity;
    }

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseEntity registerContacts(Person person) throws Exception {
		QueryParams params = new QueryParams();
		if (null == person) {
			throw new ParamsException("person不能为空");
		}
		if (CollectionsUtils.isNull(person.getContacts())) {
			throw new ParamsException("contacts不能为空");
		}
		int size = person.getContacts().size();
		int returnCount = 0;
		List<ContactSyncLog> logList = new ArrayList<ContactSyncLog>();
		if (person.getContacts() != null && size > 0) {
			for (int i=0;i<size;i++){
				QueryParams params2 = new QueryParams();
				Contact contact = person.getContacts().get(i);
				//验证是否符合标准
				if (StringUtils.isEmpty(contact.getMpiId())) {
					throw new Exception("个人唯一号不能为空");
				}
				if (StringUtils.isEmpty(contact.getCertificateTypeCode())) {
					throw new Exception("联系人证件类别代码不能为空");
				}
				if (StringUtils.isEmpty(contact.getCertificateNo())) {
					throw new Exception("联系人证件号码不能为空");
				}
				if (StringUtils.isEmpty(contact.getContactName())) {
					throw new Exception("联系人姓名不能为空");
				}
				if (StringUtils.isEmpty(contact.getContactNo())) {
					throw new Exception("联系人号码不能为空");
				}
				params2.put("mpiId", contact.getMpiId());
				//获取person
				List<Person> persons = personDao.queryDetailList(params2);
				if (persons.size() == 0) {
					return ResponseEntity.getErrorResponse("个人唯一号不存在,添加失败");
				}
				/**
				 * 查询是否存在该联系人
				 */
				params.put("mpiId", contact.getMpiId());
				params.put("certificateTypeCode", contact.getCertificateTypeCode());
				params.put("certificateNo", contact.getCertificateNo());
				params.put("delFalg", ContentUtils.INFO_NOT_DELETE);
				List<Contact> list = contactDao.queryBaseList(params);
				if (list.size() == 0) {
					contact.setPersonId(persons.get(0).getId());
					contact.setCreator(ContentUtils.ADMIN_USER_ID);
					contact.setCreateDate(DateUtils.getCurrentDate());
					contact.setDelFalg(ContentUtils.INFO_NOT_DELETE);
					contactDao.insertObject(contact);
					
					//同步记录
					ContactSyncLog contactSyncLog = new ContactSyncLog();
					contactSyncLog.setContactId(contact.getId());
					contactSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
					contactSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
					contactSyncLog.setCreateDate(DateUtils.getCurrentDate());
					logList.add(contactSyncLog);
					returnCount += 1;
				}else{
					List<Contact> results = new ArrayList<Contact>();
                    results.add(contact);
                    person.setContacts(results);
                    // 手动回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    LOG.error("存在重复联系人信息");
                    return ResponseEntity.getErrorResponse("存在重复联系人信息", person);
				}
			}
			addSyncLog(logList);
			return ResponseEntity.getSuccessResponse(null, returnCount);
		}
		return ResponseEntity.getErrorResponse("联系人为空,添加失败");
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseEntity updateContacts(Person person) throws Exception {
		if (null == person) {
			throw new ParamsException("person不能为空");
		}
		if (null == person.getUpdateData()) {
			throw new ParamsException("updateData不能为空");
		}
		if (null == person.getCurrentData()) {
			throw new ParamsException("currentData不能为空");
		}
		QueryParams params = new QueryParams();
		//String转XML
		Element update_Data = WSUtils.strConvertToXML(person.getUpdateData()).getRootElement();
		Element current_Data = WSUtils.strConvertToXML(person.getCurrentData()).getRootElement();
		//更新对象
		String update_mpiId = update_Data.elementText("mpiId");
		String update_certificateTypeCode = update_Data.elementText("certificateTypeCode");
		String update_certificateNo = update_Data.elementText("certificateNo");
		String update_contactName = update_Data.elementText("contactName");
		String update_contactNo = update_Data.elementText("contactNo");
		if (StringUtils.isEmpty(update_mpiId)) {
			throw new Exception("更新对象:mpiId不能为空");
		}
		if (StringUtils.isEmpty(update_certificateTypeCode)) {
			throw new Exception("更新对象:certificateTypeCode不能为空");
		}
		if (StringUtils.isEmpty(update_certificateNo)) {
			throw new Exception("更新对象:certificateNo不能为空");
		}
		if (StringUtils.isEmpty(update_contactName)) {
			throw new Exception("更新对象:contactName不能为空");
		}
		if (StringUtils.isEmpty(update_contactNo)) {
			throw new Exception("更新对象:contactNo不能为空");
		}
		//当前对象
		String current_mpiId = current_Data.elementText("mpiId");
		String current_certificateTypeCode = current_Data.elementText("certificateTypeCode");
		String current_certificateNo = current_Data.elementText("certificateNo");
		String current_contactName = current_Data.elementText("contactName");
		String current_contactNo = current_Data.elementText("contactNo");
		if (StringUtils.isEmpty(current_mpiId)) {
			throw new Exception("当前对象:mpiId不能为空");
		}
		if (StringUtils.isEmpty(current_certificateTypeCode)) {
			throw new Exception("当前对象:certificateTypeCode不能为空");
		}
		if (StringUtils.isEmpty(current_certificateNo)) {
			throw new Exception("当前对象:certificateNo不能为空");
		}
		if (StringUtils.isEmpty(current_contactName)) {
			throw new Exception("当前对象:contactName不能为空");
		}
		if (StringUtils.isEmpty(current_contactNo)) {
			throw new Exception("当前对象:contactNo不能为空");
		}
		if (!update_mpiId.equals(current_mpiId)) {
			throw new Exception("mpiId不一致");
		}
		List<Contact> list = contactDao.getObjectByMpiId(current_mpiId);
		if (list.size() == 0) {
			throw new Exception("mpiId不存在");
		}
		List<ContactSyncLog> logList = new ArrayList<ContactSyncLog>();
		params.put("mpiId", current_mpiId);
		params.put("certificateTypeCode", current_certificateTypeCode);
		params.put("certificateNo", current_certificateNo);
		params.put("contactName", current_contactName);
		params.put("contactNo", current_contactNo);
		params.put("delFalg", ContentUtils.INFO_NOT_DELETE);
		List<Contact> contactList = contactDao.queryBaseList(params);
		if (contactList.size() == 0) {
			throw new Exception("没有找到要修改的对象");
		}
		Contact contact = contactList.get(0);
		contact.setCertificateTypeCode(update_certificateTypeCode);
		contact.setCertificateNo(update_certificateNo);
		contact.setContactName(update_contactName);
		contact.setContactNo(update_contactNo);
		contact.setUpdator(ContentUtils.ADMIN_USER_ID);
		contact.setUpdateDate(DateUtils.getCurrentDate());
		int count = contactDao.updateObject(contact);
		//同步记录
		ContactSyncLog contactSyncLog = new ContactSyncLog();
		contactSyncLog.setContactId(contact.getId());
		contactSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
		contactSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
		contactSyncLog.setCreateDate(DateUtils.getCurrentDate());
		logList.add(contactSyncLog);
		addSyncLog(logList);
		return ResponseEntity.getSuccessResponse(null, count);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseEntity removeContacts(Person person) throws Exception {
		QueryParams params = new QueryParams();
		if (null == person) {
			throw new ParamsException("person不能为空");
		}
		if (CollectionsUtils.isNull(person.getContacts())) {
			throw new ParamsException("contacts不能为空");
		}
		List<ContactSyncLog> logList = new ArrayList<ContactSyncLog>();
		int size = person.getContacts().size();
		int returnCount = 0;
		if (person.getContacts() != null && size > 0) {
			for (int i=0;i<size;i++){
				QueryParams params2 = new QueryParams();
				Contact contact = person.getContacts().get(i);
				//验证是否符合标准
				if (StringUtils.isEmpty(contact.getMpiId())) {
					throw new Exception("个人唯一号不能为空");
				}
				if (StringUtils.isEmpty(contact.getCertificateTypeCode())) {
					throw new Exception("联系人证件类别代码不能为空");
				}
				if (StringUtils.isEmpty(contact.getCertificateNo())) {
					throw new Exception("联系人证件号码不能为空");
				}
				if (StringUtils.isEmpty(contact.getContactName())) {
					throw new Exception("联系人姓名不能为空");
				}
				if (StringUtils.isEmpty(contact.getContactNo())) {
					throw new Exception("联系人号码不能为空");
				}
				params2.put("mpiId", contact.getMpiId());
				//获取person
				List<Person> persons = personDao.queryDetailList(params2);
				if (persons.size() == 0) {
					return ResponseEntity.getErrorResponse("个人唯一号不存在,添加失败");
				}
				/**
				 * 查询是否存在该联系人
				 */
				params.put("mpiId", contact.getMpiId());
				params.put("certificateTypeCode", contact.getCertificateTypeCode());
				params.put("certificateNo", contact.getCertificateNo());
				params.put("contactName", contact.getContactName());
				params.put("contactNo", contact.getContactNo());
				params.put("delFalg", ContentUtils.INFO_NOT_DELETE);
				List<Contact> list = contactDao.queryBaseList(params);
				if (list.size() != 0) {
					list.get(0).setDelFalg(ContentUtils.INFO_DELETE);
					list.get(0).setDeleteDate(DateUtils.getCurrentDate());
					list.get(0).setDeleteor(ContentUtils.ADMIN_USER_ID);
					contactDao.updateObject(list.get(0));
					
					//同步记录
					ContactSyncLog contactSyncLog = new ContactSyncLog();
					contactSyncLog.setContactId(contact.getId());
					contactSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
					contactSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
					contactSyncLog.setCreateDate(DateUtils.getCurrentDate());
					logList.add(contactSyncLog);
					returnCount += 1;
				}else{
					Person person_contact = new Person();
	                List<Contact> results = new ArrayList<Contact>();
	                results.add(contact);
	                person_contact.setContacts(results);
	                // 手动回滚
	                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	                LOG.error("原始数据不存在/已删除");
	                return ResponseEntity.getErrorResponse("原始数据不存在/已删除", person_contact);
				}
			}
			addSyncLog(logList);
			return ResponseEntity.getSuccessResponse(null, returnCount);
		}
		return ResponseEntity.getErrorResponse("联系人为空,删除失败");
	}
	
	
	@Override
	public ResponseEntity getContacts(Person person) {
		QueryParams params = new QueryParams();
		if (person != null) {
			if (StringUtils.isEmpty(person.getMpiId())) {
				return ResponseEntity.getErrorResponse("个人唯一号不能为空");
			}
			params.put("mpiId", person.getMpiId());
			params.put("delFalg", ContentUtils.INFO_NOT_DELETE);
			List<Contact> list = contactDao.queryDetailList(params);
			person.setContacts(list);
			if (list.size() != 0) {
				return ResponseEntity.getSuccessResponse(person, list.size());
			}else{
				return ResponseEntity.getSuccessResponse(null, list.size());
			}
		}
		return ResponseEntity.getErrorResponse("参数错误");
	}
	
	/**
	 * 
	 * 描述：添加同步记录
	 * @param addressSyncLogs
	 * @author wangdeyou 2018年1月4日 下午3:58:08 
	 * @version 1.0
	 */
	private void addSyncLog(List<ContactSyncLog> contactSyncLogs) {
        List<FrontEndMachine> frontEndMachineList = frontEndMachineDao.queryBaseList(null);
        if (CollectionsUtils.isNull(frontEndMachineList)) {
            return;
        }
        List<ContactSyncLog> List = new ArrayList<ContactSyncLog>();
        // 设置前置id
        for (ContactSyncLog contactSyncLog : contactSyncLogs) {
            for (FrontEndMachine fem : frontEndMachineList) {
                ContactSyncLog contactSyncLog2 = new ContactSyncLog();
                BeanUtils.copyProperties(contactSyncLog, contactSyncLog2);
                contactSyncLog2.setFrontId(fem.getId());
                List.add(contactSyncLog2);
            }
        }
        contactSyncLogDao.inserOrUpdatetList(List);
    }
}

