package com.jc.cz_index.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.core.service.IAddressService;
import com.jc.cz_index.dao.IAddressDao;
import com.jc.cz_index.dao.IAddressSyncLogDao;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IFrontEndMachineDao;
import com.jc.cz_index.dao.IPersonDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Address;
import com.jc.cz_index.model.AddressSyncLog;
import com.jc.cz_index.model.FrontEndMachine;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.SystemUser;

/***
 * 
 * 描述：地址管理Service 实现
 * 
 * @author sunxuefeng 2017年12月28日 下午3:20:00
 * @version 1.0
 */
@Service
public class AddressService extends BaseService<Address>implements IAddressService {
    @Autowired
    private IAddressDao         addressDao;
    @Autowired
    private IPersonDao          personDao;
    @Autowired
    private IAddressSyncLogDao  addressSyncLogDao;
    @Autowired
    private IFrontEndMachineDao frontEndMachineDao;



    @Override
    public IDataProvider<Address, Long> getModelDao() {
        return this.addressDao;
    }



    /**
     * 描述：根据基本ID删除地址信息信息
     * 
     * @param id
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年12月28日 下午5:58:33
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData deleteAddressById(Long id, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            Address address = addressDao.getBaseObject(id);
            if (address == null) {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("地址信息删除失败");
                return responseData;
            }
            Long userId = systemUser.getId();
            Date currentDate = DateUtils.getCurrentDate();
            address.setDelFalg(ContentUtils.INFO_DELETE);
            address.setDeleteor(userId);
            address.setDeleteDate(currentDate);
            addressDao.updateObject(address);
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            responseData.setMessage("地址信息删除成功");
            setSyncLogAsync(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    /**
     * 
     * 描述：添加单个地址信息
     * 
     * @param address
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年12月29日 上午10:30:16
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData addAddress(Address address, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            if (address == null) {
                throw new Exception("地址信息不能为空");
            }
            QueryParams queryParams = new QueryParams();
            queryParams.put("mpiId", address.getMpiId());
            List<Person> list = personDao.queryBaseList(queryParams);
            if (list == null || list.size() != 1) {
                throw new Exception("该地址的基本身份信息不存在");
            }
            Person person = list.get(0);
            address.setPersonId(person.getId());
            Date currentDate = DateUtils.getCurrentDate();
            address.setCreateDate(currentDate);
            Long userId = systemUser.getId();
            address.setCreator(userId);
            address.setDelFalg(ContentUtils.INFO_NOT_DELETE);
            addressDao.insertObject(address);
            Long id = address.getId();
            responseData.setMessage("地址信息添加成功");
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            setSyncLogAsync(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    /**
     * 
     * 描述：修改单个地址信息
     * 
     * @param address
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年12月29日 上午10:28:58
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData updateAddress(Address address, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            if (address == null) {
                throw new Exception("地址信息不能为空");
            }
            Address addressDb = addressDao.getBaseObject(address.getId());
            if (addressDb == null) {
                throw new Exception("地址信息不存在,修改失败");
            }
            BeanUtils.copyPropertiesIgnoreNull(address, addressDb);
            Date currentDate = DateUtils.getCurrentDate();
            Long id = addressDb.getId();
            Long userId = systemUser.getId();
            addressDb.setUpdateDate(currentDate);
            addressDb.setUpdator(userId);
            addressDao.updateObject(addressDb);
            responseData.setMessage("地址信息修改成功");
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            setSyncLogAsync(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    public List<Address> excuteTask(Long id) {
        return (addressDao.getUnSyncObjectList(id));
    }



    /**
     * 
     * 描述：设置同步日志
     * 
     * @param personId
     * @author sunxuefeng 2018年1月5日 上午10:36:56
     * @version 1.0
     */
    private void setSyncLogAsync(Long addressId) {
        List<FrontEndMachine> fems = frontEndMachineDao.queryBaseList(null);
        List<AddressSyncLog> psls = new ArrayList<AddressSyncLog>();
        Date currentDate = DateUtils.getCurrentDate();
        if (fems != null && fems.size() > 0) {
            for (FrontEndMachine fem : fems) {
                AddressSyncLog psl = new AddressSyncLog();
                psl.setAddressId(addressId);
                psl.setFrontId(fem.getId());
                psl.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
                psl.setCreator(ContentUtils.ADMIN_USER_ID);
                psl.setCreateDate(currentDate);
                psl.setUpdator(ContentUtils.ADMIN_USER_ID);
                psl.setUpdateDate(currentDate);
                psls.add(psl);
            }
            addressSyncLogDao.inserOrUpdatetList(psls);
        }
    }
}
