package com.jc.cz_index.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IResidentAcquisitionDao;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.ResidentAcquisition;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.IResidentAcquisitionService;

/***
 * 
 * 描述：居民信息 采集Service 实现
 * 
 * @author sunxuefeng 2017年10月20日 上午10:09:50
 * @version 1.0
 */
@Service
public class ResidentAcquisitionService extends BaseService<ResidentAcquisition> implements IResidentAcquisitionService {
    @Autowired
    private IResidentAcquisitionDao residentacquisitionDao;



    @Override
    public IDataProvider<ResidentAcquisition, Long> getModelDao() {
        // TODO Auto-generated method stub
        return this.residentacquisitionDao;
    }



    /**
     * 
     * 描述:查询居民信息采集列表页面的数据
     * 
     * @param params
     * @param start
     * @param size
     * @return
     * @author sunxuefeng 2017年10月20日 上午11:24:03
     * @version 1.0
     */
    @Override
    public PagedList<ResidentAcquisition> queryResidentAcquisitionList(QueryParams params, Integer start, Integer size) {
        if (null == params) {
            params = new QueryParams();
        }
        if (start < 0) {
            throw new IllegalArgumentException("pageIndex参数不能小于0.");
        }
        if (size < 0) {
            throw new IllegalArgumentException("pageSize参数不能小于0.");
        }
        params.put("delFalg", 0);
        int total = residentacquisitionDao.queryCount(params);
        params.setStart(start);
        params.setSize(size);
        List<ResidentAcquisition> list = residentacquisitionDao.queryBaseList(params);
        PagedList<ResidentAcquisition> result = new PagedList<ResidentAcquisition>();
        result.getList().addAll(list);
        result.setPageIndex(start / size + 1);
        result.setPageSize(size);
        result.setTotalSize(total);
        result.setPageCount(total / size + 1);
        return result;
    }



    /**
     * 描述：根据id删除居民采集信息
     * 
     * @param id
     * @return
     * @author sunxuefeng 2017年10月23日 下午2:49:34
     * @version 1.0
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData deleteResidentAcquisitionById(Long id, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            ResidentAcquisition residentAcquisition = residentacquisitionDao.getBaseObject(id);
            if (residentAcquisition == null) {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("居民采集信息删除失败");
                return responseData;
            }
            residentAcquisition.setDelFalg(1);
            residentAcquisition.setDeleteor(systemUser.getId());
            residentAcquisition.setDeleteDate(DateUtils.getCurrentDate());
            residentacquisitionDao.updateObject(residentAcquisition);
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            responseData.setMessage("居民采集信息删除成功");
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    /**
     * 
     * 描述：向居民采集信息表中批量插入数据
     * 
     * @param list
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年10月24日 下午1:33:19
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData bathAddResidentAcquisitions(List<ResidentAcquisition> list) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            //ResidentAcquisition[] acquisitions = new ResidentAcquisition[list.size()];
            //list.toArray(acquisitions);
            //residentacquisitionDao.insertList(acquisitions);
            residentacquisitionDao.insertList(list);
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            responseData.setMessage("居民采集信息导入成功");
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    /***
     * 
     * 描述：添加单个居民采集信息
     * 
     * @param residentAcquisition
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年10月25日 下午2:34:47
     * @version 1.0
     */
    @Override
    public ResponseData addResidentAcquisition(ResidentAcquisition residentAcquisition, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            if (residentAcquisition == null) {
                throw new Exception("居民采集信息不能为空");
            }
            residentAcquisition.setCreateDate(DateUtils.getCurrentDate());
            residentAcquisition.setCreator(systemUser.getId());
            residentAcquisition.setDelFalg(0);
            residentacquisitionDao.insertObject(residentAcquisition);
            responseData.setMessage("居民采集信息添加成功");
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    /**
     * 
     * 描述：修改单个居民采集信息
     * 
     * @param residentAcquisition
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年10月25日 下午3:49:20
     * @version 1.0
     */
    @Override
    public ResponseData updateResidentAcquisition(ResidentAcquisition residentAcquisition, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            if (residentAcquisition == null) {
                throw new Exception("居民采集信息不能为空");
            }
            ResidentAcquisition residentAcquisitionDb = residentacquisitionDao.getBaseObject(residentAcquisition.getId());
            if (residentAcquisitionDb == null) {
                throw new Exception("居民采集信息不存在,修改失败");
            }
            BeanUtils.copyPropertiesIgnoreNull(residentAcquisition, residentAcquisitionDb);
            residentAcquisitionDb.setUpdateDate(DateUtils.getCurrentDate());
            residentAcquisitionDb.setUpdator(systemUser.getId());
            residentacquisitionDao.updateObject(residentAcquisitionDb);
            responseData.setMessage("居民采集信息修改成功");
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    /**
     * 描述：通过身份证号查询单个居民信息是否存在
     * 
     * @param identityNumber
     * @return
     * @author sunxuefeng 2017年10月26日 上午10:16:56
     * @version 1.0
     */
    @Override
    public Boolean queryResidentsInfoByIdentityNumberExistence(String identityNumber) {
        Boolean falg = false;
        QueryParams queryParams = new QueryParams();
        queryParams.put("identityNumber", identityNumber);
        queryParams.put("delFalg", 0);
        List<ResidentAcquisition> list = residentacquisitionDao.queryBaseList(queryParams);
        if (list != null && list.size() > 0) {
            falg = true;
        }
        return falg;
    }
}
