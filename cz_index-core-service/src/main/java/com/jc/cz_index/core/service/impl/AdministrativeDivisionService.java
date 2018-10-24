package com.jc.cz_index.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.core.service.IAdministrativeDivisionService;
import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.dao.IAdministrativeDivisionDao;
import com.jc.cz_index.model.AdministrativeDivision;
import com.jc.cz_index.model.SystemUser;

/**
 * administrativeDivision Service 实现 Created by 
 */
@Service
public class AdministrativeDivisionService extends BaseService<AdministrativeDivision> implements IAdministrativeDivisionService
{
	@Autowired
	private IAdministrativeDivisionDao administrativeDivisionDao;

	@Override
	public IDataProvider<AdministrativeDivision, Long> getModelDao() {
		// TODO Auto-generated method stub
		return this.administrativeDivisionDao;
	}

    @Override
    public void addAdministrativedivision(AdministrativeDivision administrativeDivision) throws BaseException {
        List<AdministrativeDivision> list = new ArrayList<>();
        QueryParams params = new QueryParams();
        params.put("name", administrativeDivision.getName());
        params.put("isDelete", 0);
        list = administrativeDivisionDao.queryBaseList(params);
        if(list != null && list.size() != 0){
            throw new BaseException("添加失败,该区域已存在!");
        }else{
            SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
            administrativeDivision.setCreator(loginUser.getId());
            administrativeDivision.setCreateDate(new Date());
            administrativeDivision.setIsDelete(0);//0表示未删除,1表示已删除
            administrativeDivisionDao.insertObject(administrativeDivision);
        }
    }

    @Override
    public void updateAdministrativedivision(AdministrativeDivision administrativeDivision) throws BaseException {
        AdministrativeDivision administrativeDivision2 = administrativeDivisionDao.getBaseObject(administrativeDivision.getId());
        if (administrativeDivision2 == null) {
            throw new BaseException("修改区域机构不存在");
        }
        String str = "区域名称,name;地址,address;电话,phone;邮编,zipCode;负责人,personCharge;备注,remark;";
        String returnStr = BeanUtils.getDifferenceByFields(administrativeDivision2,administrativeDivision,str);
        if(null == returnStr || returnStr.length() <= 0){
            throw new BaseException("修改失败,与原有信息相同!");
        }else{
            administrativeDivision.setUpdateDate(new Date());
            administrativeDivision.setCreator(administrativeDivision2.getCreator());
            administrativeDivision.setCreateDate(administrativeDivision2.getCreateDate());
            administrativeDivisionDao.updateObject(administrativeDivision);
        }
        
    }

    @Override
    public void del(String id) throws BaseException {
        AdministrativeDivision administrativeDivision = new AdministrativeDivision();
        administrativeDivision = administrativeDivisionDao.getBaseObject(Long.parseLong(id));
        if (administrativeDivision == null) {
            throw new BaseException("删除失败,该区域机构不存在");
        }else{
            administrativeDivision.setIsDelete(1);
            administrativeDivisionDao.updateObject(administrativeDivision);
        }
    }

    @Override
    public List<AdministrativeDivision> getAdministrativeDivisionList(Long id) {
        QueryParams params = new QueryParams();
        params.put("parentid", id);
        params.put("isDelete", 0);
        return queryBaseList(params);
    }

    @Override
    public List<Object> getAdminDiv(Long id) {
        List<Object> list = new ArrayList<Object>();
        AdministrativeDivision administrativeDivision1 = administrativeDivisionDao.getBaseObject(id);
        list.add(administrativeDivision1);
        Long parenId1 = administrativeDivision1.getParentid();
        if (parenId1 != null && parenId1>1) {
            AdministrativeDivision administrativeDivision2 = administrativeDivisionDao.getBaseObject(parenId1);
            list.add(administrativeDivision2);
        }
        return list;
    }
}

