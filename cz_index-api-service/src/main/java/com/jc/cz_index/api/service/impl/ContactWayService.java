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
import com.jc.cz_index.api.service.IContactWayService;
import com.jc.cz_index.common.exception.ParamsException;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.SpringConfigTool;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WSUtils;
import com.jc.cz_index.dao.IContactWayDao;
import com.jc.cz_index.dao.IContactWaySyncLogDao;
import com.jc.cz_index.model.ContactWay;
import com.jc.cz_index.model.ContactWaySyncLog;
import com.jc.cz_index.model.FrontEndMachine;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.RequestEntity;
import com.jc.cz_index.model.ResponseEntity;

/**
 * contactWay Service 实现 Created by 
 */
@Service
public class ContactWayService extends BaseService<ContactWay> implements IContactWayService {
	private static final Logger LOG = Logger.getLogger(PersonService.class);
	@Autowired
	private IContactWayDao 			contactWayDao;
	
	@Autowired
	private IFrontEndMachineDao 	frontEndMachineDao;
	
	@Autowired
	private IContactWaySyncLogDao	contactWaySyncLogDao;
	
	@Autowired
	private IPersonDao				personDao;

	@Override
	public IDataProvider<ContactWay, Long> getModelDao() {
		return this.contactWayDao;
	}
	
	public ResponseEntity excute(RequestEntity re) {
		ContactWayService thisContactWayService = SpringConfigTool.getBean("contactWayService", ContactWayService.class);
		ResponseEntity responseEntity = new ResponseEntity();
		String action = re.getAction();
		Person person = re.getPerson();
		try {
			switch (action) {
			case "registerContactWays":
				responseEntity = thisContactWayService.registerContactWays(person);
				break;
			case "updateContactWays":
				responseEntity = thisContactWayService.updateContactWays(person);
				break;
			case "removeContactWays":
				responseEntity = thisContactWayService.removeContactWays(person);
				break;
			case "getContactWays":
				responseEntity = thisContactWayService.getContactWays(person);
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
	public ResponseEntity registerContactWays(Person person) throws Exception{
		QueryParams params = new QueryParams();
		if (null == person) {
			throw new ParamsException("person不能为空");
		}
		if (CollectionsUtils.isNull(person.getContactWays())) {
			throw new ParamsException("contactWays不能为空");
		}
		List<ContactWaySyncLog> logList = new ArrayList<ContactWaySyncLog>();
		int size = person.getContactWays().size();
		int returnCount = 0;
		if (person.getContactWays() != null && size > 0) {
			for (int i=0;i<size;i++){
				QueryParams params2 = new QueryParams();
				ContactWay contactWay = person.getContactWays().get(i);
				/**
				 * 验证数据的正确性
				 */
				if (StringUtils.isEmpty(contactWay.getMpiId())) {
					throw new Exception("个人唯一号不能为空");
				}
				if (StringUtils.isEmpty(contactWay.getContactTypeCode())) {
					throw new Exception("联系方式代码不能为空");
				}
				if (StringUtils.isEmpty(contactWay.getContactNo())) {
					throw new Exception("联系电话不能为空");
				}
				params2.put("mpiId", contactWay.getMpiId());
				//获取person
				List<Person> persons = personDao.queryDetailList(params2);
				if (persons.size() == 0) {
					return ResponseEntity.getErrorResponse("个人唯一号不存在,添加失败,mpiId:"+contactWay.getMpiId());
				}
				/**
				 * 查询是否存在该联系方式信息
				 */
				params.put("mpiId", contactWay.getMpiId());
				params.put("contactTypeCode", contactWay.getContactTypeCode());
				params.put("contactNo", contactWay.getContactNo());
				params.put("delFalg", ContentUtils.INFO_NOT_DELETE);
				List<ContactWay> list = contactWayDao.queryDetailList(params);
				if (list.size() == 0) {
					contactWay.setPersonId(persons.get(0).getId());
					contactWay.setCreator(ContentUtils.ADMIN_USER_ID);
					contactWay.setCreateDate(DateUtils.getCurrentDate());
					contactWay.setDelFalg(ContentUtils.INFO_NOT_DELETE);
					contactWayDao.insertObject(contactWay);
					//同步记录
					ContactWaySyncLog contactWaySyncLog = new ContactWaySyncLog();
					contactWaySyncLog.setContactWayId(contactWay.getId());
					contactWaySyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
					contactWaySyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
					contactWaySyncLog.setCreateDate(DateUtils.getCurrentDate());
					logList.add(contactWaySyncLog);
					returnCount += 1;
				}else{
					List<ContactWay> results = new ArrayList<ContactWay>();
                    results.add(contactWay);
                    person.setContactWays(results);
                    // 手动回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    LOG.error("存在重复联系方式信息");
                    return ResponseEntity.getErrorResponse("存在重复联系方式信息", person);
				}
			}
			addSyncLog(logList);
			return ResponseEntity.getSuccessResponse(null, returnCount);
		}
		return ResponseEntity.getErrorResponse("联系方式信息为空,添加失败");
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseEntity updateContactWays(Person person) throws Exception{
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
		String update_contactTypeCode = update_Data.elementText("contactTypeCode");
		String update_contactNo = update_Data.elementText("contactNo");
		if (StringUtils.isEmpty(update_mpiId)) {
			throw new Exception("更新对象:mpiId不能为空");
		}
		if (StringUtils.isEmpty(update_contactTypeCode)) {
			throw new Exception("更新对象:contactTypeCode不能为空");
		}
		if (StringUtils.isEmpty(update_contactNo)) {
			throw new Exception("更新对象:contactNo不能为空");
		}
		//当前对象
		String current_mpiId = current_Data.elementText("mpiId");
		String current_contactTypeCode = current_Data.elementText("contactTypeCode");
		String current_contactNo = current_Data.elementText("contactNo");
		if (StringUtils.isEmpty(current_mpiId)) {
			throw new Exception("当前对象:mpiId不能为空");
		}
		if (StringUtils.isEmpty(current_contactTypeCode)) {
			throw new Exception("当前对象:certificateTypeCode不能为空");
		}
		if (StringUtils.isEmpty(current_contactNo)) {
			throw new Exception("当前对象:contactNo不能为空");
		}
		if (!update_mpiId.equals(current_mpiId)) {
			throw new Exception("mpiId不一致");
		}
		List<ContactWay> list = contactWayDao.getObjectByMpiId(current_mpiId);
		if (list.size() == 0) {
			throw new Exception("mpiId不存在");
		}
		List<ContactWaySyncLog> logList = new ArrayList<ContactWaySyncLog>();
		params.put("mpiId", current_mpiId);
		params.put("contactTypeCode", current_contactTypeCode);
		params.put("contactNo", current_contactNo);
		params.put("delFalg", ContentUtils.INFO_NOT_DELETE);
		List<ContactWay> contactWayList = contactWayDao.queryBaseList(params);
		if (contactWayList.size() == 0) {
			throw new Exception("没有找到要修改的对象");
		}
		ContactWay contactWay = contactWayList.get(0);
		contactWay.setContactTypeCode(update_contactTypeCode);
		contactWay.setContactNo(update_contactNo);
		contactWay.setUpdator(ContentUtils.ADMIN_USER_ID);
		contactWay.setUpdateDate(DateUtils.getCurrentDate());
		int count = contactWayDao.updateObject(contactWay);
		
		//同步记录
		ContactWaySyncLog contactWaySyncLog = new ContactWaySyncLog();
		contactWaySyncLog.setContactWayId(contactWay.getId());
		contactWaySyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
		contactWaySyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
		contactWaySyncLog.setCreateDate(DateUtils.getCurrentDate());
		logList.add(contactWaySyncLog);
		addSyncLog(logList);
		return ResponseEntity.getSuccessResponse(null, count);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseEntity removeContactWays(Person person) throws Exception{
		QueryParams params = new QueryParams();
		if (null == person) {
			throw new ParamsException("person不能为空");
		}
		if (CollectionsUtils.isNull(person.getContactWays())) {
			throw new ParamsException("contactWays不能为空");
		}
		List<ContactWaySyncLog> logList = new ArrayList<ContactWaySyncLog>();
		int size = person.getContactWays().size();
		int returnCount = 0;
		if (person.getContactWays() != null && size > 0) {
			for (int i=0;i<size;i++){
				ContactWay contactWay = person.getContactWays().get(i);
				/**
				 * 验证数据的正确性
				 */
				if (StringUtils.isEmpty(contactWay.getMpiId())) {
					throw new Exception("个人唯一号不能为空");
				}
				if (StringUtils.isEmpty(contactWay.getContactTypeCode())) {
					throw new Exception("联系方式代码不能为空");
				}
				if (StringUtils.isEmpty(contactWay.getContactNo())) {
					throw new Exception("联系电话不能为空");
				}
				/**
				 * 查询是否存在该联系方式信息
				 */
				params.put("mpiId", contactWay.getMpiId());
				params.put("contactTypeCode", contactWay.getContactTypeCode());
				params.put("contactNo", contactWay.getContactNo());
				params.put("delFalg", ContentUtils.INFO_NOT_DELETE);
				List<ContactWay> list = contactWayDao.queryBaseList(params);
				if (list.size() != 0) {
					list.get(0).setDelFalg(ContentUtils.INFO_DELETE);
					list.get(0).setDeleteor(ContentUtils.ADMIN_USER_ID);
					list.get(0).setDeleteDate(DateUtils.getCurrentDate());
					contactWayDao.updateObject(list.get(0));
					//同步记录
					ContactWaySyncLog contactWaySyncLog = new ContactWaySyncLog();
					contactWaySyncLog.setContactWayId(contactWay.getId());
					contactWaySyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
					contactWaySyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
					contactWaySyncLog.setCreateDate(DateUtils.getCurrentDate());
					logList.add(contactWaySyncLog);
					returnCount += 1;
				}else{
					Person person_contactWay = new Person();
	                List<ContactWay> results = new ArrayList<ContactWay>();
	                results.add(contactWay);
	                person_contactWay.setContactWays(results);
	                // 手动回滚
	                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	                LOG.error("原始数据不存在/已删除");
	                return ResponseEntity.getErrorResponse("原始数据不存在/已删除", person_contactWay);
				}
			}
			addSyncLog(logList);
			return ResponseEntity.getSuccessResponse(null, returnCount);
		}
		return ResponseEntity.getErrorResponse("联系方式信息为空,删除失败");
	}
	
	@Override
	public ResponseEntity getContactWays(Person person) {
		QueryParams params = new QueryParams();
		if (person != null) {
			if (StringUtils.isEmpty(person.getMpiId())) {
				return ResponseEntity.getErrorResponse("个人唯一号不能为空");
			}
			params.put("mpiId", person.getMpiId());
			params.put("delFalg", ContentUtils.INFO_NOT_DELETE);
			List<ContactWay> list = contactWayDao.queryDetailList(params);
			person.setContactWays(list);
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
	private void addSyncLog(List<ContactWaySyncLog> contactWaySyncLogs) {
        List<FrontEndMachine> frontEndMachineList = frontEndMachineDao.queryBaseList(null);
        if (CollectionsUtils.isNull(frontEndMachineList)) {
            return;
        }
        List<ContactWaySyncLog> List = new ArrayList<ContactWaySyncLog>();
        // 设置前置id
        for (ContactWaySyncLog contactWaySyncLog : contactWaySyncLogs) {
            for (FrontEndMachine fem : frontEndMachineList) {
            	ContactWaySyncLog contactWaySyncLog2 = new ContactWaySyncLog();
                BeanUtils.copyProperties(contactWaySyncLog, contactWaySyncLog2);
                contactWaySyncLog2.setFrontId(fem.getId());
                List.add(contactWaySyncLog2);
            }
        }
        contactWaySyncLogDao.inserOrUpdatetList(List);
    }
}

