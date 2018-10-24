package com.jc.cz_index.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.service.IAdministrativeManagementService;
import com.jc.cz_index.dao.IAdministrativeManagementDao;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.AdministrativeManagement;
import com.jc.cz_index.model.SystemUser;

/**
 * administrativeManagement Service 实现 Created by 
 */
@Service
public class AdministrativeManagementService extends BaseService<AdministrativeManagement> implements IAdministrativeManagementService
{
	@Autowired
	private IAdministrativeManagementDao administrativeManagementDao;

	@Override
	public IDataProvider<AdministrativeManagement, Long> getModelDao() {
		return this.administrativeManagementDao;
	}

    @Override
    public void addAdministrativeManagement(AdministrativeManagement administrativeManagement) throws BaseException {
        QueryParams params = new QueryParams();
        params.put("administrativeName", administrativeManagement.getAdministrativeName());
        params.put("isDelete", 0);
        List<AdministrativeManagement> list = administrativeManagementDao.queryBaseList(params);
        if (list != null && list.size() != 0) {
            throw new BaseException("添加失败,该行政机构已存在");
        }else{
            //获取当前登录用户
            SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
            //获取用户ID
            administrativeManagement.setCreator(loginUser.getId());
            //获取当前时间
            administrativeManagement.setCreateDate(new Date());
            administrativeManagement.setIsDelete(0);//0表示未删除,1表示已删除
            administrativeManagementDao.insertObject(administrativeManagement);
        }
    }

    @Override
    public void updateAdministrativeManagement(AdministrativeManagement administrativeManagement) throws BaseException {
        AdministrativeManagement administrativeManagement2 = administrativeManagementDao.getBaseObject(administrativeManagement.getId());
        if (administrativeManagement2 == null) {
            throw new BaseException("修改行政机构不存在");
        }
        String str = "行政名称,administrativeName;地址,address;电话,phone;邮编,zipCode;负责人,personCharge;备注,remark;";
        String returnStr = BeanUtils.getDifferenceByFields(administrativeManagement2,administrativeManagement,str);
        if (null == returnStr || returnStr.length() <= 0) {
            throw new BaseException("修改失败,与原有信息相同!");
        }else{
          //获取当前时间
            administrativeManagement.setUpdateDate(new Date());
            administrativeManagement.setCreator(administrativeManagement2.getCreator());
            administrativeManagement.setCreateDate(administrativeManagement2.getCreateDate());
            administrativeManagementDao.updateObject(administrativeManagement);
        }
    }

    @Override
    public void del(String id) throws BaseException {
        AdministrativeManagement administrativeManagement = administrativeManagementDao.getBaseObject(Long.parseLong(id));
        if (administrativeManagement == null) {
            throw new BaseException("删除失败,该行政机构不存在");
        }else{
            administrativeManagement.setIsDelete(1);
            administrativeManagementDao.updateObject(administrativeManagement);
        }
    }
}

