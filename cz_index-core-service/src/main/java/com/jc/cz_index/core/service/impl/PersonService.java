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
import com.jc.cz_index.common.utils.EmpiUtils;
import com.jc.cz_index.core.service.IPersonService;
import com.jc.cz_index.dao.IAddressDao;
import com.jc.cz_index.dao.ICardDao;
import com.jc.cz_index.dao.ICertificateDao;
import com.jc.cz_index.dao.IContactDao;
import com.jc.cz_index.dao.IContactWayDao;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IFrontEndMachineDao;
import com.jc.cz_index.dao.IPersonDao;
import com.jc.cz_index.dao.IPersonSyncLogDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Address;
import com.jc.cz_index.model.Card;
import com.jc.cz_index.model.Certificate;
import com.jc.cz_index.model.Contact;
import com.jc.cz_index.model.ContactWay;
import com.jc.cz_index.model.FrontEndMachine;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.PersonSyncLog;
import com.jc.cz_index.model.SystemUser;

/**
 * 
 * 描述：基本身份信息Service实现
 * 
 * @author sunxuefeng 2017年12月26日 下午6:09:38
 * @version 1.0
 */
@Service
public class PersonService extends BaseService<Person>implements IPersonService {
    @Autowired
    private IPersonDao          personDao;
    @Autowired
    private IPersonSyncLogDao   personSyncLogDao;
    @Autowired
    private IAddressDao         addressDao;
    @Autowired
    private ICardDao            cardDao;
    @Autowired
    private IContactDao         contactDao;
    @Autowired
    private IContactWayDao      contactWayDao;
    @Autowired
    private ICertificateDao     certificateDao;
    @Autowired
    private IFrontEndMachineDao frontEndMachineDao;



    @Override
    public IDataProvider<Person, Long> getModelDao() {
        return this.personDao;
    }



    /***
     * 
     * 描述：根据基本身份信息ID删除某个基本身份信息
     * 
     * @param id
     * @param systemUser
     * @return
     * @author sunxuefeng 2017年12月25日 下午3:29:27
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData deletePersonById(Long id, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            Person person = personDao.getBaseObject(id);
            if (person == null) {
                throw new Exception("基本身份信息不能为空");
            }
            Long personId = person.getId();
            translatePersonIsDelete(personId);
            Long userId = systemUser.getId();
            Date currentDate = DateUtils.getCurrentDate();
            person.setDelFalg(ContentUtils.INFO_DELETE);
            person.setDeleteor(userId);
            person.setDeleteDate(currentDate);
            personDao.updateObject(person);
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            responseData.setMessage("基本身份信息删除成功");
            setSyncLogAsync(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return responseData;
    }



    /***
     * 
     * 描述：根据基本身份信息ID注销或者恢复某个基本身份信息
     * 
     * @param id
     * @param systemUser
     * @param falg
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年12月25日 下午4:19:14
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData cancelAndRecoveryPersonById(Long id, SystemUser systemUser, String falg) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            Person person = personDao.getBaseObject(id);
            if (person == null) {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                return responseData;
            }
            Long userId = systemUser.getId();
            Date currentDate = DateUtils.getCurrentDate();
            person.setStatus(falg);
            person.setUpdator(userId);
            person.setUpdateDate(currentDate);
            personDao.updateObject(person);
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            setSyncLogAsync(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    /**
     * 
     * 描述：添加单个基本身份信息
     * 
     * @param person
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年12月27日 下午4:04:05
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData addPerson(Person person, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();
        try {
            if (person == null) {
                throw new Exception("基本身份信息不能为空");
            }
            String mpiId = EmpiUtils.getEmpiId(person.getIdCard(), "01", person.getPersonName()); // 生成主索引id
            person.setMpiId(mpiId);
            person.setStatus(ContentUtils.PERSON_STATUS_ENABLE);
            Long userId = systemUser.getId();
            Date currentDate = DateUtils.getCurrentDate();
            person.setCreateDate(currentDate);
            person.setCreator(userId);
            person.setDelFalg(ContentUtils.INFO_NOT_DELETE);
            personDao.insertObject(person);
            Long id = person.getId();
            responseData.setMessage("基本身份信息添加成功");
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            setSyncLogAsync(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    /**
     * 描述：修改单个基本身份信息
     * 
     * @param person
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年12月28日 上午10:29:35
     * @version 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData updatePerson(Person person, SystemUser systemUser) throws Exception {
        ResponseData responseData = new ResponseData();

        try {
            if (person == null) {
                throw new Exception("基本身份信息不能为空");
            }
            Long id = person.getId();
            Person personDb = personDao.getBaseObject(id);
            if (personDb == null) {
                throw new Exception("基本身份信息不存在,修改失败");
            }
            Long userId = systemUser.getId();
            Date currentDate = DateUtils.getCurrentDate();
            BeanUtils.copyPropertiesIgnoreNull(person, personDb);
            personDb.setDeceasedTime(personDb.getDeceasedInd().equals(0) ? null : personDb.getDeceasedTime());
            personDb.setUpdateDate(currentDate);
            personDb.setUpdator(userId);
            personDao.updateObject(personDb);
            responseData.setMessage("基本身份信息修改成功");
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
            setSyncLogAsync(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return responseData;
    }



    public List<Person> excuteTask(Long id) {
        return personDao.getUnSyncObjectList(id);
    }



    /***
     * 
     * 描述：根据身份证号查询居基本身份信息是是否存在
     * 
     * @param idCard
     * @return
     * @author sunxuefeng 2017年10月10日 上午10:14:31
     * @version 1.0
     */
    public Boolean queryPersonByidCardExistence(String idCard) {
        QueryParams queryParams = new QueryParams();
        queryParams.put("idCard", idCard);
        queryParams.put("delFalg", ContentUtils.INFO_NOT_DELETE);
        List<Person> list = personDao.queryBaseList(queryParams);
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }



    /**
     * 
     * 描述：设置同步日志
     * 
     * @param personId
     * @author sunxuefeng 2018年1月5日 上午10:36:56
     * @version 1.0
     */
    private void setSyncLogAsync(Long personId) {
        List<FrontEndMachine> fems = frontEndMachineDao.queryBaseList(null);
        List<PersonSyncLog> psls = new ArrayList<PersonSyncLog>();
        Date currentDate = DateUtils.getCurrentDate();
        if (fems != null && fems.size() > 0) {
            for (FrontEndMachine fem : fems) {
                PersonSyncLog psl = new PersonSyncLog();
                psl.setPersonId(personId);
                psl.setFrontId(fem.getId());
                psl.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
                psl.setCreator(ContentUtils.ADMIN_USER_ID);
                psl.setCreateDate(currentDate);
                psl.setUpdator(ContentUtils.ADMIN_USER_ID);
                psl.setUpdateDate(currentDate);
                psls.add(psl);
            }
            personSyncLogDao.saveOrUpdateList(psls);
        }

    }



    /**
     * 判断基本身份信息是否可以删除
     */
    private void translatePersonIsDelete(Long personId) throws Exception {
        QueryParams queryParams = new QueryParams();
        queryParams.put("personId", personId);
        queryParams.put("delFalg", ContentUtils.INFO_NOT_DELETE);
        List<Address> addresselist = addressDao.queryBaseList(queryParams);
        if (addresselist != null && addresselist.size() > 0) {
            throw new Exception("先删除地址信息,才能删除基本身份信息");
        }
        List<Contact> contacts = contactDao.queryBaseList(queryParams);
        if (contacts != null && contacts.size() > 0) {
            throw new Exception("先删除联系人信息,才能删除基本身份信息");
        }
        List<ContactWay> contactWays = contactWayDao.queryBaseList(queryParams);
        if (contactWays != null && contactWays.size() > 0) {
            throw new Exception("先删除联系方式信息,才能删除基本身份信息");
        }
        List<Certificate> certificates = certificateDao.queryBaseList(queryParams);
        if (certificates != null && certificates.size() > 0) {
            throw new Exception("先删除证件信息,才能删除基本身份信息");
        }
        List<Card> cards = cardDao.queryBaseList(queryParams);
        if (cards != null && cards.size() > 0) {
            throw new Exception("先删除卡务信息,才能删除基本身份信息");
        }
    }
}