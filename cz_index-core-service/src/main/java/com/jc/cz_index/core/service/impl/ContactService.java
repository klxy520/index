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
import com.jc.cz_index.core.service.IContactService;
import com.jc.cz_index.dao.IContactDao;
import com.jc.cz_index.dao.IContactSyncLogDao;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IFrontEndMachineDao;
import com.jc.cz_index.dao.IPersonDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Contact;
import com.jc.cz_index.model.ContactSyncLog;
import com.jc.cz_index.model.FrontEndMachine;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.SystemUser;

/***
 * 
 * 描述：联系人管理 Service
 * 
 * @author sunxuefeng 2018年1月2日 上午11:44:10
 * @version 1.0
 */
@Service
public class ContactService extends BaseService<Contact>implements IContactService {
    @Autowired
    private IContactDao         contactDao;
    @Autowired
    private IPersonDao          personDao;
    @Autowired
    private IContactSyncLogDao  contactSyncLogDao;
    @Autowired
    private IFrontEndMachineDao frontEndMachineDao;



    @Override
    public IDataProvider<Contact, Long> getModelDao() {
        return this.contactDao;
    }



    @Override
    public List<Contact> excuteTask(Long femId) {
        return contactDao.getUnSyncObjectList(femId);
    }



    /**
     * 
     * 描述：根据ID删除联系人信息信息
     * 
     * @param id
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2018年1月2日 上午11:45:14
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData deleteContactById(Long id, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            Contact contact = contactDao.getBaseObject(id);
            if (contact == null) {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("联系人删除失败");
                return responseData;
            }
            Long userId = systemUser.getId();
            Date currentDate = DateUtils.getCurrentDate();
            contact.setDelFalg(ContentUtils.INFO_DELETE);
            contact.setDeleteor(userId);
            contact.setDeleteDate(currentDate);
            contactDao.updateObject(contact);
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            responseData.setMessage("联系人删除成功");
            setSyncLogAsync(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    /**
     * 添加单个联系人 描述：
     * 
     * @param contact
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2018年1月2日 上午11:46:23
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData addContact(Contact contact, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            if (contact == null) {
                throw new Exception("联系人信息不能为空");
            }
            QueryParams queryParams = new QueryParams();
            queryParams.put("mpiId", contact.getMpiId());
            List<Person> list = personDao.queryBaseList(queryParams);
            if (list == null || list.size() != 1) {
                throw new Exception("联系人的基本身份信息不存在");
            }
            Person person = list.get(0);
            contact.setPersonId(person.getId());
            Date currentDate = DateUtils.getCurrentDate();
            Long userId = systemUser.getId();
            contact.setCreateDate(currentDate);
            contact.setCreator(userId);
            contact.setDelFalg(ContentUtils.INFO_NOT_DELETE);
            contactDao.insertObject(contact);
            Long id = contact.getId();
            responseData.setMessage("联系人添加成功");
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            setSyncLogAsync(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    /**
     * 
     * 描述：修改单个联系人
     * 
     * @param contact
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2018年1月2日 上午11:46:49
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData updateContact(Contact contact, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            if (contact == null) {
                throw new Exception("联系人不能为空");
            }
            Contact contactDb = contactDao.getBaseObject(contact.getId());
            if (contactDb == null) {
                throw new Exception("联系人不存在,修改失败");
            }
            BeanUtils.copyPropertiesIgnoreNull(contact, contactDb);
            Date currentDate = DateUtils.getCurrentDate();
            Long id = contact.getId();
            Long userId = systemUser.getId();
            contactDb.setUpdateDate(currentDate);
            contactDb.setUpdator(userId);
            contactDao.updateObject(contactDb);
            responseData.setMessage("联系人修改成功");
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            setSyncLogAsync(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    /**
     * 
     * 描述：设置同步日志
     * 
     * @param contactId
     * @author sunxuefeng 2018年1月5日 上午10:36:56
     * @version 1.0
     */
    private void setSyncLogAsync(Long contactId) {
        List<FrontEndMachine> fems = frontEndMachineDao.queryBaseList(null);
        List<ContactSyncLog> psls = new ArrayList<ContactSyncLog>();
        Date currentDate = DateUtils.getCurrentDate();
        if (fems != null && fems.size() > 0) {
            for (FrontEndMachine fem : fems) {
                ContactSyncLog psl = new ContactSyncLog();
                psl.setContactId(contactId);
                psl.setFrontId(fem.getId());
                psl.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
                psl.setCreator(ContentUtils.ADMIN_USER_ID);
                psl.setCreateDate(currentDate);
                psl.setUpdator(ContentUtils.ADMIN_USER_ID);
                psl.setUpdateDate(currentDate);
                psls.add(psl);
            }
            contactSyncLogDao.inserOrUpdatetList(psls);
        }
    }
}