package com.jc.cz_index.api.service.impl;



import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.dom4j.Element;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IFrontEndMachineDao;
import com.jc.cz_index.dao.IPersonDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.api.service.IAddressService;
import com.jc.cz_index.common.exception.ParamsException;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.SpringConfigTool;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WSUtils;
import com.jc.cz_index.dao.IAddressDao;
import com.jc.cz_index.dao.IAddressSyncLogDao;
import com.jc.cz_index.model.Address;
import com.jc.cz_index.model.AddressSyncLog;
import com.jc.cz_index.model.FrontEndMachine;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.RequestEntity;
import com.jc.cz_index.model.ResponseEntity;

/**
 * address Service 实现 Created by 
 */
@Service
public class AddressService extends BaseService<Address> implements IAddressService {
	private static final Logger LOG = Logger.getLogger(PersonService.class);
	@Autowired
	private IAddressDao 			addressDao;
	
	@Autowired
	private IFrontEndMachineDao 	frontEndMachineDao;
	
	@Autowired
	private IAddressSyncLogDao		addressSyncLogDao;
	
	@Autowired
	private IPersonDao				personDao;

	@Override
	public IDataProvider<Address, Long> getModelDao() {
		return this.addressDao;
	}
	
	public ResponseEntity excute(RequestEntity re) {
		AddressService thisAddressService = SpringConfigTool.getBean("addressService", AddressService.class);
		ResponseEntity responseEntity = new ResponseEntity();
		String action = re.getAction();
		Person person = re.getPerson();
		try {
			switch (action) {
			case "registerAddresses":
				responseEntity = thisAddressService.registerAddresses(person);
				break;
			case "updateAddresses":
				responseEntity = thisAddressService.updateAddresses(person);
				break;
			case "removeAddresses":
				responseEntity = thisAddressService.removeAddresses(person);
				break;
			case "getAddresses":
				responseEntity = thisAddressService.getAddresses(person);
				break;
			default:
				LOG.error("访问地址不存在");
				responseEntity = ResponseEntity.getNotFoundErrorResponse();
				break;
			}
		}  catch (ParamsException pe){
            LOG.error("请求action:" + action + " 异常：" + pe.getMessage());
            responseEntity = ResponseEntity.getParamsErrorResponse(pe.getMessage());
        }  catch (Exception e) {
			LOG.error("请求action:" + action + " 异常：" + e.getMessage());
			responseEntity = ResponseEntity.getErrorResponse(e.getMessage());
		}
        return responseEntity;
    }
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseEntity registerAddresses(Person person) throws Exception {
		QueryParams params = new QueryParams();
		if (null == person) {
			throw new ParamsException("person不能为空");
		}
		if (CollectionsUtils.isNull(person.getAddresses())) {
			throw new ParamsException("addresses不能为空");
		}
		List<AddressSyncLog> logList = new ArrayList<AddressSyncLog>();
		int size = person.getAddresses().size();
		int returnCount = 0;
		if (person.getAddresses() != null && size > 0) {
			for (int i=0;i<size;i++){
				QueryParams params2 = new QueryParams();
				Address address = person.getAddresses().get(i);
				//验证地址是否符合标准
				if (StringUtils.isEmpty(address.getMpiId())) {
					throw new Exception("个人唯一号不能为空");
				}
				if (StringUtils.isEmpty(address.getAddressTypeCode())) {
					throw new Exception("地址类别不能为空");
				}
				if (StringUtils.isEmpty(address.getAddress())) {
					throw new Exception("详细地址不能为空");
				}
				params2.put("mpiId", address.getMpiId());
				//获取person
				List<Person> persons = personDao.queryDetailList(params2);
				if (persons.size() == 0) {
					return ResponseEntity.getErrorResponse("个人唯一号不存在,添加失败");
				}
				/**
				 * 查询是否存在该地址
				 */
				params.put("mpiId", address.getMpiId());
				params.put("addressTypeCode", address.getAddressTypeCode());
				params.put("address", address.getAddress());
				params.put("delFalg", ContentUtils.INFO_NOT_DELETE);
				List<Address> list = addressDao.queryBaseList(params);
				if (list.size() == 0) {
					address.setPersonId(persons.get(0).getId());
					address.setCreator(ContentUtils.ADMIN_USER_ID);
					address.setCreateDate(DateUtils.getCurrentDate());
					address.setDelFalg(ContentUtils.INFO_NOT_DELETE);
					addressDao.insertObject(address);
					//同步记录
					AddressSyncLog addressSyncLog = new AddressSyncLog();
					addressSyncLog.setAddressId(address.getId());
					addressSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
					addressSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
					addressSyncLog.setCreateDate(DateUtils.getCurrentDate());
					logList.add(addressSyncLog);
					returnCount += 1;
				}else{
					List<Address> results = new ArrayList<Address>();
                    results.add(address);
                    person.setAddresses(results);
                    // 手动回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    LOG.error("存在重复地址信息");
                    return ResponseEntity.getErrorResponse("存在重复地址信息", person);
				}
			}
			//添加或修改地址同步记录
			addSyncLog(logList);
			return ResponseEntity.getSuccessResponse(null, returnCount);
		}
		return ResponseEntity.getErrorResponse("地址为空,添加失败");
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseEntity updateAddresses(Person person) throws Exception{
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
		String update_addressTypeCode = update_Data.elementText("addressTypeCode");
		String update_address = update_Data.elementText("address");
		if (StringUtils.isEmpty(update_mpiId)) {
			throw new Exception("更新对象:mpiId不能为空");
		}
		if (StringUtils.isEmpty(update_addressTypeCode)) {
			throw new Exception("更新对象:addressTypeCode不能为空");
		}
		if (StringUtils.isEmpty(update_address)) {
			throw new Exception("更新对象:address不能为空");
		}
		//当前对象
		String current_mpiId = current_Data.elementText("mpiId");
		String current_addressTypeCode = current_Data.elementText("addressTypeCode");
		String current_address = current_Data.elementText("address");
		if (StringUtils.isEmpty(current_mpiId)) {
			throw new Exception("当前对象:mpiId不能为空");
		}
		if (StringUtils.isEmpty(current_addressTypeCode)) {
			throw new Exception("当前对象:addressTypeCode不能为空");
		}
		if (StringUtils.isEmpty(current_address)) {
			throw new Exception("当前对象:address不能为空");
		}
		if (!update_mpiId.equals(current_mpiId)) {
			throw new Exception("mpiId不一致");
		}
		List<Address> list = addressDao.getObjectByMpiId(current_mpiId);
		if (list.size() == 0) {
			throw new Exception("mpiId不存在");
		}
		List<AddressSyncLog> logList = new ArrayList<AddressSyncLog>();
		params.put("mpiId", current_mpiId);
		params.put("addressTypeCode", current_addressTypeCode);
		params.put("address", current_address);
		params.put("delFalg", ContentUtils.INFO_NOT_DELETE);
		List<Address> addressList = addressDao.queryBaseList(params);
		if (addressList.size() == 0) {
			throw new Exception("没有找到要修改的对象");
		}
		Address address = addressList.get(0);
		address.setAddressTypeCode(update_addressTypeCode);
		address.setAddress(update_address);
		address.setUpdator(ContentUtils.ADMIN_USER_ID);
		address.setUpdateDate(DateUtils.getCurrentDate());
		int count = addressDao.updateObject(address);
		
		//同比记录
		AddressSyncLog addressSyncLog = new AddressSyncLog();
		addressSyncLog.setAddressId(address.getId());
		addressSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
		addressSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
		addressSyncLog.setCreateDate(DateUtils.getCurrentDate());
		logList.add(addressSyncLog);
		addSyncLog(logList);
		return ResponseEntity.getSuccessResponse(null, count);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseEntity removeAddresses(Person person) throws Exception{
		if (null == person) {
			throw new ParamsException("person不能为空");
		}
		if (CollectionsUtils.isNull(person.getAddresses())) {
			throw new ParamsException("addresses不能为空");
		}
		QueryParams params = new QueryParams();
		List<AddressSyncLog> logList = new ArrayList<AddressSyncLog>();
		int size = person.getAddresses().size();
		int returnCount = 0;
		if (person.getAddresses() != null && size > 0) {
			for (int i=0;i<size;i++){
				QueryParams params2 = new QueryParams();
				Address address = person.getAddresses().get(i);
				//验证地址是否符合标准
				if (StringUtils.isEmpty(address.getMpiId())) {
					throw new Exception("个人唯一号不能为空");
				}
				if (StringUtils.isEmpty(address.getAddressTypeCode())) {
					throw new Exception("地址类别不能为空");
				}
				if (StringUtils.isEmpty(address.getAddress())) {
					throw new Exception("详细地址不能为空");
				}
				params2.put("mpiId", address.getMpiId());
				//获取person
				List<Person> persons = personDao.queryDetailList(params2);
				if (persons.size() == 0) {
					return ResponseEntity.getErrorResponse("个人唯一号不存在,添加失败");
				}
				/**
				 * 查询是否存在该地址
				 */
				params.put("mpiId", address.getMpiId());
				params.put("addressTypeCode", address.getAddressTypeCode());
				params.put("address", address.getAddress());
				params.put("delFalg", ContentUtils.INFO_NOT_DELETE);
				List<Address> list = addressDao.queryBaseList(params);
				if (list.size() != 0) {
					list.get(0).setDelFalg(ContentUtils.INFO_DELETE);
					list.get(0).setDeleteor(ContentUtils.ADMIN_USER_ID);
					list.get(0).setDeleteDate(DateUtils.getCurrentDate());
					addressDao.updateObject(list.get(0));
					
					AddressSyncLog addressSyncLog = new AddressSyncLog();
					addressSyncLog.setAddressId(address.getId());
					addressSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
					addressSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
					addressSyncLog.setCreateDate(DateUtils.getCurrentDate());
					logList.add(addressSyncLog);
					returnCount += 1;
				}else{
					Person person_address = new Person();
	                List<Address> results = new ArrayList<Address>();
	                results.add(address);
	                person_address.setAddresses(results);
	                // 手动回滚
	                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	                LOG.error("原始数据不存在/已删除");
	                return ResponseEntity.getErrorResponse("原始数据不存在/已删除", person_address);
				}
			}
			addSyncLog(logList);
			return ResponseEntity.getSuccessResponse(null, returnCount);
		}
		return ResponseEntity.getErrorResponse("地址为空,删除失败");
	}

	@Override
	public ResponseEntity getAddresses(Person person) {
		QueryParams params = new QueryParams();
		if (person != null) {
			if (StringUtils.isEmpty(person.getMpiId())) {
				return ResponseEntity.getErrorResponse("个人唯一号不能为空");
			}
			params.put("mpiId", person.getMpiId());
			params.put("delFalg", ContentUtils.INFO_NOT_DELETE);
			List<Address> list = addressDao.queryDetailList(params);
			person.setAddresses(list);
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
	private void addSyncLog(List<AddressSyncLog> addressSyncLogs) {
        List<FrontEndMachine> frontEndMachineList = frontEndMachineDao.queryBaseList(null);
        if (CollectionsUtils.isNull(frontEndMachineList)) {
            return;
        }
        List<AddressSyncLog> addressSyncLogs2 = new ArrayList<AddressSyncLog>();
        // 设置前置id
        for (AddressSyncLog addressSyncLog : addressSyncLogs) {
            for (FrontEndMachine fem : frontEndMachineList) {
                AddressSyncLog addressSyncLog2 = new AddressSyncLog();
                BeanUtils.copyProperties(addressSyncLog, addressSyncLog2);
                addressSyncLog2.setFrontId(fem.getId());
                addressSyncLogs2.add(addressSyncLog2);
            }
        }
        addressSyncLogDao.inserOrUpdatetList(addressSyncLogs2);
    }
	
	
}

