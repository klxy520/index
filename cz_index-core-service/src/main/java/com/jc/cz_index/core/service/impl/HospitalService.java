package com.jc.cz_index.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.core.service.IHospitalService;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IHospitalDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Hospital;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.SystemUser;

/***
 * 
 * 描述：医院机构 Service 实现
 * 
 * @author sunxuefeng 2018年1月3日 下午1:33:40
 * @version 1.0
 */
@Service
public class HospitalService extends BaseService<Hospital>implements IHospitalService {
    @Autowired
    private IHospitalDao hospitalDao;



    @Override
    public IDataProvider<Hospital, Long> getModelDao() {
        return this.hospitalDao;
    }



    /**
     * 
     * 描述：根据ID删除机构
     * 
     * @param id
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2018年1月3日 下午4:24:20
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData deleteHospitalById(Long id, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            Hospital hospital = hospitalDao.getBaseObject(id);
            if (hospital == null) {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("医院信息删除失败");
                return responseData;
            }
            Long userId = systemUser.getId();
            Date currentDate = DateUtils.getCurrentDate();
            hospital.setDelFalg(ContentUtils.INFO_DELETE);
            hospital.setUpdator(userId);
            hospital.setUpdateDate(currentDate);
            hospitalDao.updateObject(hospital);
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            responseData.setMessage("医院信息删除成功");
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    /**
     * 
     * 描述：禁用/启用一个医院
     * 
     * @param id
     * @param systemUser
     * @param falg
     * @return
     * @throws Exception
     * @author sunxuefeng 2018年1月3日 下午5:06:42
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData disableAndEnableHospitalById(Long id, SystemUser systemUser, Integer falg) throws Exception {

        ResponseData responseData = new ResponseData();
        try {
            Hospital hospital = hospitalDao.getBaseObject(id);
            if (hospital == null) {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                return responseData;
            }
            Long userId = systemUser.getId();
            Date currentDate = DateUtils.getCurrentDate();
            hospital.setStatus(falg);
            hospital.setUpdator(userId);
            hospital.setUpdateDate(currentDate);
            hospitalDao.updateObject(hospital);
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    /***
     * 
     * 描述：添加单个医院信息
     * 
     * @param addHospital
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2018年1月4日 上午9:56:46
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData addHospital(Hospital hospital, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            if (hospital == null) {
                throw new Exception("医院信息不能为空");
            }
            Long userId = systemUser.getId();
            Date currentDate = DateUtils.getCurrentDate();
            hospital.setStatus(ContentUtils.DB_FIELD_STATUS_ENABLE);
            hospital.setCreateDate(currentDate);
            hospital.setCreator(userId);
            hospital.setDelFalg(ContentUtils.INFO_NOT_DELETE);
            hospitalDao.insertObject(hospital);
            responseData.setMessage("医院信息添加成功");
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    /**
     * 
     * 描述：修改单个医院信息
     * 
     * @param hospital
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2018年1月4日 上午9:57:58
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData updateHospital(Hospital hospital, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            if (hospital == null) {
                throw new Exception("医院信息不能为空");
            }
            Long id = hospital.getId();
            Hospital hospitalDb = hospitalDao.getBaseObject(id);
            if (hospitalDb == null) {
                throw new Exception("医院信息不存在,修改失败");
            }
            Long userId = systemUser.getId();
            Date currentDate = DateUtils.getCurrentDate();
            BeanUtils.copyPropertiesIgnoreNull(hospital, hospitalDb);
            hospitalDb.setUpdateDate(currentDate);
            hospitalDb.setUpdator(userId);
            hospitalDao.updateObject(hospitalDb);
            responseData.setMessage("医院信息修改成功");
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }
    /***
     * 
     * 描述：根据医院名称查询查询医院否存在
     * 
     * @param name
     * @return
     * @author sunxuefeng 2017年10月10日 上午10:14:31
     * @version 1.0
     */
    public Boolean queryHospitalByNameExistence(String name){
        QueryParams queryParams = new QueryParams();
        queryParams.put("name", name);
        queryParams.put("delFalg", 0);
        List<Hospital> list = hospitalDao.queryBaseList(queryParams);
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }
    /***
     * 
     * 描述：根据医院key查询查询医院否存在
     * 
     * @param name
     * @return
     * @author sunxuefeng 2017年10月10日 上午10:14:31
     * @version 1.0
     */
    public Boolean queryHospitalByKeyExistence(String key){
        QueryParams queryParams = new QueryParams();
        queryParams.put("hKey", key);
        queryParams.put("delFalg", 0);
        List<Hospital> list = hospitalDao.queryBaseList(queryParams);
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
        
    }
}
