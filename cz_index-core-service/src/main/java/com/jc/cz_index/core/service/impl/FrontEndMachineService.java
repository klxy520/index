package com.jc.cz_index.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.service.IFrontEndMachineService;
import com.jc.cz_index.dao.IFrontEndMachineDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.FrontEndMachine;
import com.jc.cz_index.model.SystemUser;

/**
 * frontEndMachine Service 实现 Created by 
 */
@Service
public class FrontEndMachineService extends BaseService<FrontEndMachine> implements IFrontEndMachineService {
	@Autowired
	private IFrontEndMachineDao frontEndMachineDao;

	@Override
	public IDataProvider<FrontEndMachine, Long> getModelDao() {
		return this.frontEndMachineDao;
	}

	@Override
	public void addFronEndMachine(FrontEndMachine frontEndMachine) throws BaseException {
		if (frontEndMachine == null) {
			throw new BaseException("添加失败,数据错误!");
		}else{
			SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
			frontEndMachine.setState(ContentUtils.FRON_END_MACHINE_ON);
			frontEndMachine.setCreator(loginUser.getId());
			frontEndMachine.setCreateDate(new Date());
			frontEndMachineDao.insertObject(frontEndMachine);
		}
	}

	@Override
	public void updateFronEndMachine(FrontEndMachine frontEndMachine) throws BaseException {
		if (frontEndMachine == null) {
			throw new BaseException("修改失败,数据错误!");
		}else{
			FrontEndMachine frontEndMachine2 = frontEndMachineDao.getBaseObject(frontEndMachine.getId());
			SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
			frontEndMachine2.setUpdator(loginUser.getId());
			frontEndMachine2.setUpdateDate(new Date());
			frontEndMachine2.setState(frontEndMachine.getState());
			frontEndMachine2.setFrontEndMachinecode(frontEndMachine.getFrontEndMachinecode());
			frontEndMachine2.setFrontEndMachineaddress(frontEndMachine.getFrontEndMachineaddress());
			frontEndMachine2.setRemarks(frontEndMachine.getRemarks());
			frontEndMachineDao.updateObject(frontEndMachine2);
		}
	}

	@Override
	public void enable(String id) throws BaseException {
		QueryParams params = new QueryParams();
        if (id == null) {
            throw new BaseException("参数错误");
        }
        params.put("id", id);
		List<FrontEndMachine> list = frontEndMachineDao.queryBaseList(params);
		if (list.size() == 0) {
			throw new BaseException("该前置机不存在");
		}
		FrontEndMachine frontEndMachine = list.get(0);
		frontEndMachine.setState(ContentUtils.FRON_END_MACHINE_ON);
		frontEndMachineDao.updateObject(frontEndMachine);
	}

	@Override
	public void disable(String id) throws BaseException {
		QueryParams params = new QueryParams();
        if (id == null) {
            throw new BaseException("参数错误");
        }
        params.put("id", id);
		List<FrontEndMachine> list = frontEndMachineDao.queryBaseList(params);
		if (list.size() == 0) {
			throw new BaseException("该前置机不存在");
		}
		FrontEndMachine frontEndMachine = list.get(0);
		frontEndMachine.setState(ContentUtils.FRON_END_MACHINE_OFF);
		frontEndMachineDao.updateObject(frontEndMachine);
		
	}

	@Override
	public void del(String id) throws BaseException {
		QueryParams params = new QueryParams();
		if(id == null){
			throw new BaseException("参数错误");
		}
		params.put("id", id);
		List<FrontEndMachine> list = frontEndMachineDao.queryBaseList(params);
		if (list.size() == 0) {
			throw new BaseException("该前置机不存在");
		}
		frontEndMachineDao.deleteObject(Long.parseLong(id));
	}
	@Override
	public List<FrontEndMachine> queryEffectiveList() {
		return frontEndMachineDao.getEffectiveList();
	}

	
}

