package com.jc.cz_index.api.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.cz_index.api.service.IPersonService;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.EmpiUtils;
import com.jc.cz_index.common.utils.SpringConfigTool;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.dao.IAddressDao;
import com.jc.cz_index.dao.IAddressSyncLogDao;
import com.jc.cz_index.dao.ICardDao;
import com.jc.cz_index.dao.ICardSyncLogDao;
import com.jc.cz_index.dao.ICertificateDao;
import com.jc.cz_index.dao.ICertificateSyncLogDao;
import com.jc.cz_index.dao.IContactDao;
import com.jc.cz_index.dao.IContactSyncLogDao;
import com.jc.cz_index.dao.IContactWayDao;
import com.jc.cz_index.dao.IContactWaySyncLogDao;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IFrontEndMachineDao;
import com.jc.cz_index.dao.IPersonDao;
import com.jc.cz_index.dao.IPersonSyncLogDao;
import com.jc.cz_index.dao.IResidentExtendinfoDao;
import com.jc.cz_index.dao.IResidentsInfoDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Address;
import com.jc.cz_index.model.AddressSyncLog;
import com.jc.cz_index.model.Card;
import com.jc.cz_index.model.CardSyncLog;
import com.jc.cz_index.model.Certificate;
import com.jc.cz_index.model.CertificateSyncLog;
import com.jc.cz_index.model.Contact;
import com.jc.cz_index.model.ContactSyncLog;
import com.jc.cz_index.model.ContactWay;
import com.jc.cz_index.model.ContactWaySyncLog;
import com.jc.cz_index.model.FrontEndMachine;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.PersonSyncLog;
import com.jc.cz_index.model.RequestEntity;
import com.jc.cz_index.model.ResidentExtendinfo;
import com.jc.cz_index.model.ResidentsInfo;
import com.jc.cz_index.model.ResponseEntity;

/**
 * person Service 实现 Created by
 */
/**
 * 
 * 描述：
 * 
 * @author fengshengliang 2017年12月28日 上午10:19:12
 * @version 1.0
 */
@Service
public class PersonService extends BaseService<Person> implements IPersonService {
    private static final Logger LOG = Logger.getLogger(PersonService.class);
    @Autowired
    private IPersonDao          personDao;
    @Autowired
    private ICertificateDao     certificateDao;
    @Autowired
    private IAddressDao         addressDao;
    @Autowired
    private IContactDao         contactDao;
    @Autowired
    private IContactWayDao      contactWayDao;
    @Autowired
    private ICardDao            cardDao;
    @Autowired
    private IPersonSyncLogDao   personSyncLogDao;
    @Autowired
    private IFrontEndMachineDao frontEndMachineDao;
    @Autowired
    private ICardSyncLogDao     cardSyncLogDao;
    @Autowired
    private IAddressSyncLogDao  addressSyncLogDao;
    @Autowired
    private IContactSyncLogDao  contactSyncLogDao;
    @Autowired
    private IContactWaySyncLogDao contactWaySyncLogDao;
    @Autowired
    private ICertificateSyncLogDao certificateSyncLogDao;
    @Autowired
    private IResidentsInfoDao    residentsInfoDao;
    @Autowired
    private IResidentExtendinfoDao residentExtendinfoDao;


    @Override
    public IDataProvider<Person, Long> getModelDao() {
        return this.personDao;
    }



    /**
     * 统一入口
     * 
     * @throws Exception
     */
    @Override
    public ResponseEntity excute(RequestEntity requestEntity) {
        PersonService personService = SpringConfigTool.getBean("personService", PersonService.class);
        ResponseEntity re = new ResponseEntity();
        // 访问目标
        String action = requestEntity.getAction();
        Person person = requestEntity.getPerson();
        try {
            switch (action) {
            case "submitMPI":
                // 个人主索引注册
                if(person==null){
                    return ResponseEntity.getParamsErrorResponse("请求参数不能为空");
                }
                re = personService.submitMPI(person);
                break;
            case "getMPIID":
                // 个人主索引查询服务
                if(person==null){
                    return ResponseEntity.getParamsErrorResponse("请求参数不能为空");
                }
                re = personService.getMPIID(person, requestEntity.isCreate());
                break;
            case "getMPI":
                // 个人简要信息查询
                if(person==null){
                    return ResponseEntity.getParamsErrorResponse("请求参数不能为空");
                }
                re = personService.getMPI(person);
                break;
            case "getMPIDetail":
                // 个人详细信息查询
                if(person==null){
                    return ResponseEntity.getParamsErrorResponse("请求参数不能为空");
                }
                re = personService.getMPIDetail(person);
                break;
            case "updateMPI":
                // 更新个人信息
                if(person==null){
                    return ResponseEntity.getParamsErrorResponse("请求参数不能为空");
                }
                re = personService.updateMPI(person);
                break;
            case "writeOffMPI":
                // 注销个人信息
                if(person==null){
                    return ResponseEntity.getParamsErrorResponse("请求参数不能为空");
                }
                re = personService.writeOffMPI(person);
                break;
            case "deathMPI":
                // 打死亡标记
                if(person==null){
                    return ResponseEntity.getParamsErrorResponse("请求参数不能为空");
                }
                re = personService.deathMPI(person);
                break;
            case "undeathMPI":
                // 取消打死亡标记
                if(person==null){
                    return ResponseEntity.getParamsErrorResponse("请求参数不能为空");
                }
                re = personService.undeathMPI(person);
                break;
            case "batchSubmitMPI":
                // 个人主索引批量注册服务
                re = personService.batchSubmitMPI(requestEntity.getPersons(), requestEntity.isBatch());
                break;
            case "getMPIIDForKtVsirtual":
                if(person==null){
                    return ResponseEntity.getParamsErrorResponse("请求参数不能为空");
                }
                re = personService.getMPIIDForKtVsirtual(person, requestEntity.isCreate());
                break;
                
            case "getNameAuth":
                if(person==null){
                    return ResponseEntity.getParamsErrorResponse("请求参数不能为空");
                }
                re = personService.getNameAuth(requestEntity.getPerson());
                break;
            default:
                LOG.error(action + " 访问地址不存在!");
                re = ResponseEntity.getNotFoundErrorResponse();
                break;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.getErrorResponse(e.getMessage());
        }
        return re;
    }


/**
 * 
 * 描述：
 * @param persons
 * @return
 * @author yangjunhui 2018年1月24日 上午11:02:21 
 * @version 1.0
 */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity getNameAuth(Person person) {
    	 String authName = person.getPersonName();
         String idNumber = person.getIdCard();
         if (StringUtils.isEmpty(authName)) {
             return ResponseEntity.getParamsErrorResponse("姓名不能为空!");
         }
         if (StringUtils.isEmpty(idNumber)) {
             return ResponseEntity.getParamsErrorResponse("身份证号不能为空!");
         }
         QueryParams params = new QueryParams();
         params.addParameter("identityNumber", idNumber);
         params.addParameter("delFalg", 0);
         List<ResidentsInfo> residents =  residentsInfoDao.queryBaseList(params);
         ResidentsInfo residentInfo=new ResidentsInfo();
         residentInfo.setIdentityNumber(idNumber);
         residentInfo.setName(authName);
         if(null==residents||residents.size()==0){
        	 boolean isMatch=false;
        	 String [] regx={"^\\d{6}(18\\d{2}|19\\d{2}|20\\d{2})([0][1-9]|1[0-2])([0][1-9]|[1-2][0-9]|[3][0-1])([0-9]{3})([0-9]|x|X)$","^\\d{6}(\\d{2})([0][1-9]|1[0-2])([0][1-9]|[1-2][0-9]|[3][0-1])([0-9]{3})$"};
             for(int i=0;i<2;i++){
                 Pattern p = Pattern.compile(regx[i]);
                 Matcher m= p.matcher(idNumber);
                 if(m.matches()){
                	
                	 residentInfo.setCreateDate(new Date());
                	 residentInfo.setDelFalg(0);
                	 residentInfo.setIsFloating(1L);
                	 residentInfo.setCreator(1L);
                	 residentInfo.setBirthday( m.group(1)+ m.group(2)+ m.group(3));
                     Integer sex=Integer.valueOf(m.group(4))%2==0?2:1;
                     residentInfo.setSex(sex.toString());
                     residentInfo.setAreaId(0L);
                     residentInfo.setOfficeId(0L);
                     residentsInfoDao.insertObject(residentInfo);
                     isMatch=true;
                     break;
                 } 
             }
             if(isMatch){
            	  ResidentExtendinfo residentExtendinfo = new ResidentExtendinfo();
                  residentExtendinfo.setBaseId(residentInfo.getId());
                  residentExtendinfo.setCreateDate(new Date());
                  residentExtendinfo.setHealthNumber(residentInfo.getIdentityNumber());
                  residentExtendinfo.setCreator(1L);
                  residentExtendinfoDao.insertObject(residentExtendinfo);        
                  return ResponseEntity.getSuccessResponse("认证成功!");
             }else{
            	 return ResponseEntity.getErrorResponse("认证失败，身份证号格式错误");
             }          
         }
        String name= residents.get(0).getName();
        if(null!=name&&authName.equals(name)){
            return ResponseEntity.getSuccessResponse("认证成功!");
        }
        return ResponseEntity.getErrorResponse("认证失败，信息不匹配!");
	}



	/**
     * 个人主索引注册
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity submitMPI(Person person) throws Exception {
        if(person==null){
            return ResponseEntity.getParamsErrorResponse("请求参数不能为空");
        }
        if (person.getBirthday() == null) {
            return ResponseEntity.getParamsErrorResponse("出生日期不能为空!");
        }
        if (StringUtils.isEmpty(person.getPersonName())) {
            return ResponseEntity.getParamsErrorResponse("姓名不能为空!");
        }
        // if (StringUtils.isEmpty(person.getCardNo())) {
        // return ResponseEntity.getParamsErrorResponse("一卡通号不能为空!");
        // }
        if (StringUtils.isEmpty(person.getIdCard())) {
            return ResponseEntity.getParamsErrorResponse("身份证号不能为空!");
        }
        if (StringUtils.isEmpty(person.getSexCode())) {
            return ResponseEntity.getParamsErrorResponse("性别代码不能为空!");
        }
        // 生成主索引id
        String mpiId = EmpiUtils.getEmpiId(person.getIdCard(), "01", person.getPersonName());
        // 查询数据库中是否存在未删除的信息
        QueryParams params = new QueryParams();
        params.addParameter("mpiId", mpiId);
        params.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE);
        List<Person> persons = personDao.queryDetailList(params);
        if (persons != null && persons.size() > 0) {
            /**
             * 暂时更新主信息
             */
            person.setId(persons.get(0).getId());
            return updateMPIBaseInfo(person,"detail"); //返回详细信息
        }
        person.setMpiId(mpiId);
        // 未删除
        person.setDelFalg(ContentUtils.INFO_NOT_DELETE);
        // 正常
        person.setStatus(ContentUtils.PERSON_STATUS_ENABLE);
        // 未死亡
        person.setDeceasedInd(ContentUtils.PERSON_DECEASED_IND_ENABLE);
        person.setCreator(ContentUtils.ADMIN_USER_ID);
        person.setCreateDate(DateUtils.getCurrentDate());

        // 执行注册
        ResponseEntity re = doRegisterAnnotherInfo(person);
        if (re.getCode().equals(ContentUtils.EMPI_STATUS_CODE_SUCCESS)) {
            // 设置未同步
            setSyncLogAsync(person.getId(), ContentUtils.INFO_SYNC_STATUS_NO);
        }
        Person result = new Person();
        result.setMpiId(person.getMpiId());
        re.setPerson(result);
        return re;

    }



    /**
     * 
     * 描述：注册信息
     * 
     * @param person
     * @author fengshengliang 2017年12月27日 上午11:47:31
     * @version 1.0
     */
    public ResponseEntity doRegisterAnnotherInfo(Person person) throws Exception {
        int n = personDao.insertObject(person);
        Long personId = person.getId();
        String mpiId = person.getMpiId();
        // 证件信息
        List<Certificate> certificates = person.getCertificates();
        List<CertificateSyncLog> certificateSyncLogList = new ArrayList<CertificateSyncLog>();
        // 地址信息
        List<Address> addresses = person.getAddresses();
        List<AddressSyncLog> addressSyncLogList = new ArrayList<AddressSyncLog>();
        // 联系人信息
        List<Contact> contacts = person.getContacts();
        List<ContactSyncLog> contactSyncLogList = new ArrayList<ContactSyncLog>();
        // 联系方式信息
        List<ContactWay> contactWays = person.getContactWays();
        List<ContactWaySyncLog> contactWaySyncLogList = new ArrayList<ContactWaySyncLog>();
        // 卡信息
        List<Card> cards = person.getCards();
        List<CardSyncLog> cardSyncLogList = new ArrayList<CardSyncLog>();

        // 注册证件信息
        if (certificates != null && certificates.size() > 0) {
            for (Certificate cer : certificates) {
                boolean isExist = certificatesIsExist(cer, mpiId);
                if (isExist) {
                    throw new Exception("存在重复证件信息");
                }
                cer.setMpiId(mpiId);
                cer.setPersonId(personId);
                // 未删除
                cer.setDelFalg(ContentUtils.INFO_NOT_DELETE);
                cer.setCreator(ContentUtils.ADMIN_USER_ID);
                cer.setCreateDate(DateUtils.getCurrentDate());
                certificateDao.insertObject(cer);
                //同步记录
                CertificateSyncLog certificateSyncLog = new CertificateSyncLog();
                certificateSyncLog.setCertificateId(cer.getId());
                certificateSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
                certificateSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
                certificateSyncLog.setCreateDate(DateUtils.getCurrentDate());
                certificateSyncLog.setUpdator(ContentUtils.ADMIN_USER_ID);
                certificateSyncLog.setUpdateDate(DateUtils.getCurrentDate());
                certificateSyncLogList.add(certificateSyncLog);
            }
//            certificateDao.insertList(certificates);
            setCertificateSyncLogAsync(certificateSyncLogList);
        }
        // 注册地址信息
        if (addresses != null && addresses.size() > 0) {
            for (Address addr : addresses) {
                boolean isExist = addressesIsExist(addr, mpiId);
                if (isExist) {
                    throw new Exception("存在重复地址信息");
                }
                addr.setMpiId(mpiId);
                addr.setPersonId(personId);
                // 未删除
                addr.setDelFalg(ContentUtils.INFO_NOT_DELETE);
                addr.setCreator(ContentUtils.ADMIN_USER_ID);
                addr.setCreateDate(DateUtils.getCurrentDate());
                addressDao.insertObject(addr);
                
                //同步记录
                AddressSyncLog addressSyncLog = new AddressSyncLog();
                addressSyncLog.setAddressId(addr.getId());
                addressSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
                addressSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
                addressSyncLog.setCreateDate(DateUtils.getCurrentDate());
                addressSyncLogList.add(addressSyncLog);
            }
//            addressDao.insertList(addresses);
            // 设置地址同步记录
            setAddressSyncLog(addressSyncLogList);
        }
        // 注册联系人
        if (contacts != null && contacts.size() > 0) {
            for (Contact cont : contacts) {
                boolean isExist = contactIsExist(cont, mpiId);
                if (isExist) {
                    throw new Exception("存在重复联系人信息");
                }
                cont.setMpiId(mpiId);
                cont.setPersonId(personId);
                // 未删除
                cont.setDelFalg(ContentUtils.INFO_NOT_DELETE);
                cont.setCreator(ContentUtils.ADMIN_USER_ID);
                cont.setCreateDate(DateUtils.getCurrentDate());
                contactDao.insertObject(cont);
                
                //同步记录
                ContactSyncLog contactSyncLog = new ContactSyncLog();
                contactSyncLog.setContactId(cont.getId());
                contactSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
                contactSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
                contactSyncLog.setCreateDate(DateUtils.getCurrentDate());
                contactSyncLogList.add(contactSyncLog);
            }
//            contactDao.insertList(contacts);
            // 设置联系人同步
            setContactSyncLog(contactSyncLogList);
        }
        // 注册联系方式
        if (contactWays != null && contactWays.size() > 0) {
            for (ContactWay contw : contactWays) {
                boolean isExist = contactWayIsExist(contw, mpiId);
                if (isExist) {
                    throw new Exception("存在重复联系人信息");
                }
                contw.setMpiId(mpiId);
                contw.setPersonId(personId);
                contw.setDelFalg(ContentUtils.INFO_NOT_DELETE);
                contw.setCreator(ContentUtils.ADMIN_USER_ID);
                contw.setCreateDate(DateUtils.getCurrentDate());
                contactWayDao.insertObject(contw);
                
                //同步记录
                ContactWaySyncLog contactWaySyncLog = new ContactWaySyncLog();
                contactWaySyncLog.setContactWayId(contw.getId());
                contactWaySyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
                contactWaySyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
                contactWaySyncLog.setCreateDate(DateUtils.getCurrentDate());
                contactWaySyncLogList.add(contactWaySyncLog);
            }
//            contactWayDao.insertList(contactWays);
            // 设置联系方式同步日志
            setContactWaySyncLog(contactWaySyncLogList);
        }
        // 注册卡信息
        if (cards != null && cards.size() > 0) {
            for (Card card : cards) {
                boolean isExist = cardIsExist(card, mpiId);
                if (isExist) {
                    throw new Exception("存在重复的卡信息");
                }
                card.setMpiId(mpiId);
                card.setPersonId(personId);
                card.setStatus(ContentUtils.CARD_STATUS_NORMAL);
                card.setDelFalg(ContentUtils.INFO_NOT_DELETE);
                card.setCreator(ContentUtils.ADMIN_USER_ID);
                card.setCreateDate(DateUtils.getCurrentDate());
                cardDao.insertObject(card);
                
                CardSyncLog cardSyncLog = new CardSyncLog();
                cardSyncLog.setCardId(card.getId());
                cardSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
                cardSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
                cardSyncLog.setCreateDate(DateUtils.getCurrentDate());
                cardSyncLogList.add(cardSyncLog);
            }
//            cardDao.insertList(cards);
            // 设置卡同步日志
            setCardSyncLog(cardSyncLogList);
        }

        return ResponseEntity.getSuccessResponse(person, n);
    }




    /**
     * 个人主索引查询
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity getMPIID(Person person, boolean create) throws Exception {
        List<Person> persons = new ArrayList<Person>();
        if(person == null){
            return ResponseEntity.getParamsErrorResponse("参数不允许为空值");
        }
        QueryParams params = assembleParams(person);
        if(CollectionsUtils.isNull(params)){
            return queryForList(person,"getMPIID");
        }
        // 能够生成主索引信息
        if (!StringUtils.isEmpty((String) params.get("idCard")) && !StringUtils.isEmpty((String) params.get("personName"))) {
            // 生成主索引id
            String mpiId = EmpiUtils.getEmpiId((String) params.get("idCard"), "01", (String) params.get("personName"));
            // 是否存在未删除的主索引信息
            Person temp = personDao.getEnableObjectByMpiId(mpiId);
            if (temp != null) {
                Person result = new Person();
                result.setMpiId(temp.getMpiId());
                persons.add(result);
                return ResponseEntity.getSuccessResponsePersons(persons, 1);
            }
            // 自动注册 (至少需要包含:身份证+姓名)
            if (create) {
                person.setMpiId(mpiId);
                // 未删除
                person.setDelFalg(ContentUtils.INFO_NOT_DELETE);
                // 未死亡
                person.setDeceasedInd(ContentUtils.PERSON_DECEASED_IND_ENABLE);
                // 未注销
                person.setStatus(ContentUtils.PERSON_STATUS_ENABLE);
                person.setCreator(ContentUtils.ADMIN_USER_ID);
                person.setCreateDate(DateUtils.getCurrentDate());
                ResponseEntity re = doRegisterAnnotherInfo(person);

                if (re.getCode().equals(ContentUtils.EMPI_STATUS_CODE_SUCCESS)) {
                    // 设置未同步
                    setSyncLogAsync(person.getId(), ContentUtils.INFO_SYNC_STATUS_NO);
                }
                return re;
            }
            LOG.error("主索引信息不存在");
            return ResponseEntity.getSuccessResponsePersons(null,0);
        } else {
            // 查询有效信息
            params.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE);
            List<Person> temps = personDao.queryDetailList(params);
            if (temps != null && temps.size() > 0) {
                List<Person> results = new ArrayList<Person>();
                for (Person temp : temps) {
                    Person pr = new Person();
                    pr.setMpiId(temp.getMpiId());
                    results.add(pr);
                }
                return ResponseEntity.getSuccessResponsePersons(results, temps.size());
            } else {
                return ResponseEntity.getSuccessResponsePersons(null,0);
            }
        }
    }



    /**
     * 个人简要信息查询
     */
    @Override
    public ResponseEntity getMPI(Person person) {
        List<Person> persons = new ArrayList<Person>();
        if(person == null){
            return ResponseEntity.getParamsErrorResponse("参数不允许为空值");
        }
        QueryParams params = assembleParams(person);
        if(CollectionsUtils.isNull(params)){ // 基本属性为空
            // 检查关联属性是否为空
            return queryForList(person,"getMPI");
        }
        String mpiId_temp = (String) params.get("mpiId");
        if (!StringUtils.isEmpty(mpiId_temp)) {
            // 获取未删除的主索引信息
            QueryParams par = new QueryParams();
            par.addParameter("mpiId", mpiId_temp);
            par.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE);
            persons = queryBaseList(par);
            return ResponseEntity.getSuccessResponsePersons(CollectionsUtils.isNull(persons)?null:persons, persons.size());
        }
        if (!StringUtils.isEmpty((String) params.get("idCard")) && !StringUtils.isEmpty((String) params.get("personName"))) {
            // 生成主索引id
            String mpiId = EmpiUtils.getEmpiId((String) params.get("idCard"), "01", (String) params.get("personName"));
            // 获取未删除的主索引信息
            QueryParams par = new QueryParams();
            par.addParameter("mpiId", mpiId);
            par.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE);
            persons = queryBaseList(par);
            return ResponseEntity.getSuccessResponsePersons(CollectionsUtils.isNull(persons)?null:persons, persons.size());
        }
        // 否则,通过其他基本信息查询未删除的主索引信息
        params.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE);
        persons = personDao.queryBaseList(params);
        return ResponseEntity.getSuccessResponsePersons(CollectionsUtils.isNull(persons)?null:persons, persons.size());
    }

    /**
     * 
     * 描述：通过关联信息查询主索引简要信息
     * @param person
     * @return
     * @author fengshengliang 2018年1月16日 下午3:03:04 
     * @version 1.0
     */
    public ResponseEntity queryForList(Person person,String mpidOrMpiId){
        List<Person> persons = new ArrayList<Person>();
        // 地址
        if(CollectionsUtils.isNotNull(person.getAddresses())){
            for(Address address : person.getAddresses()){
                QueryParams params = new QueryParams();
                params.addParameter("addressTypeCode", address.getAddressTypeCode());
                params.addParameter("address",address.getAddress());
                params.addParameter("postalCode",address.getPostalCode());
                List<Person> temp = personDao.getObjectByAddress(params);
                if(CollectionsUtils.isNotNull(temp)){
                    persons.addAll(temp);
                }
            }
            
        }
        // 联系方式
        if(CollectionsUtils.isNotNull(person.getContactWays())){
            for(ContactWay contactWay : person.getContactWays()){
                QueryParams params = new QueryParams();
                params.addParameter("contactTypeCode",contactWay.getContactTypeCode());
                params.addParameter("contactNo",contactWay.getContactNo());
                List<Person> temp = personDao.getObjectByContactWay(params);
                if(CollectionsUtils.isNotNull(temp)){
                    persons.addAll(temp);
                }
            }
            
        }
        if(CollectionsUtils.isNotNull(person.getCertificates())){
            for(Certificate certificate : person.getCertificates()){
                QueryParams params = new QueryParams();
                params.addParameter("certificateTypeCode",certificate.getCertificateTypeCode());
                params.addParameter("certificateNo",certificate.getCertificateNo());
                List<Person> temp = personDao.getObjectByCertificate(params);
                if(CollectionsUtils.isNotNull(temp)){
                    persons.addAll(temp);
                }
            }
        }
        if(CollectionsUtils.isNotNull(person.getCards())){
            for(Card card : person.getCards()){
                QueryParams params = new QueryParams();
                params.addParameter("cardTypeCode",card.getCardTypeCode());
                params.addParameter("cardNo",card.getCardNo());
                params.addParameter("cardCode",card.getCardCode());
                // params.addParameter("createTime",card.getCreateTime());
                params.addParameter("createUnit",card.getCreateUnit());
                // params.addParameter("createUser",card.getCreateUser());
                params.addParameter("status",card.getStatus());
                params.addParameter("validTime",card.getValidTime());
                List<Person> temp = personDao.getObjectByCard(params);
                if(CollectionsUtils.isNotNull(temp)){
                    persons.addAll(temp);
                }
            }

        }
        if(CollectionsUtils.isNotNull(person.getContacts())){
            for(Contact contact : person.getContacts()){
                QueryParams params = new QueryParams();
                params.addParameter("certificateTypeCode",contact.getCertificateTypeCode());
                params.addParameter("certificateNo",contact.getCertificateNo());
                params.addParameter("contactName",contact.getContactName());
                List<Person> temp = personDao.getObjectByContact(params);
                if(CollectionsUtils.isNotNull(temp)){
                    persons.addAll(temp);
                }
            }
        }
        if(CollectionsUtils.isNotNull(persons)){
            List<Person> temps = new ArrayList<Person>(new HashSet<Person>(persons));
            if("getMPI".equals(mpidOrMpiId)){
                return ResponseEntity.getSuccessResponsePersons(temps, temps.size());
            }
            // 否则只需要返回主索引Id
            List<Person> result = new ArrayList<Person>();
            for (Person p : temps) {
                Person e = new Person();
                e.setMpiId(p.getMpiId());
                result.add(e);
            }
            temps.clear();
            return ResponseEntity.getSuccessResponsePersons(result, temps.size());
        }else{
            return ResponseEntity.getSuccessResponse(null, 0);
        }
    }

    /**
     * 
     * 描述：为虚拟化应用提供 主索引id 和 居民健康卡卡号(身份证)
     * 
     * @param person
     * @return
     * @author fengshengliang 2018年1月10日 上午10:00:08
     * @version 1.0
     */
    public ResponseEntity getMPIIDForKtVsirtual(Person person, boolean create) throws Exception {
        if(person==null){
            return ResponseEntity.getParamsErrorResponse("请求参数不能为空");
        }
        String idCard = person.getIdCard();
        String personName = person.getPersonName();
        String contactNo = person.getContactNo();
        if (StringUtils.isEmpty(idCard)) {
            return ResponseEntity.getParamsErrorResponse("身份证号码不能为空");
        }
        if (StringUtils.isEmpty(personName)) {
            return ResponseEntity.getParamsErrorResponse("姓名不能为空");
        }
        if(StringUtils.isEmpty(contactNo)){
            return ResponseEntity.getParamsErrorResponse("电话号码不能为空");
        }
        // 生成主索引id
        String mpiId = EmpiUtils.getEmpiId(idCard.trim(), "01", personName);
        // 查询是否有未删除的主索引信息
        Person temp = personDao.getEnableObjectByMpiId(mpiId);
        if (temp != null) {
            List<Person> persons = new ArrayList<Person>();
            Person result = new Person();
            result.setMpiId(temp.getMpiId());

            QueryParams cardParams = new QueryParams();
            cardParams.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE);
            cardParams.addParameter("mpiId", temp.getMpiId());
            cardParams.addParameter("cardTypeCode", "01"); // 居民健康卡
            // 查询未删除的实体居民健康卡
            List<Card> cards = cardDao.queryBaseList(cardParams);
            // 存在居民健康卡,则返回
            if (CollectionsUtils.isNotNull(cards)) {
                Card card_temp = new Card();
                card_temp.setCardNo(cards.get(0).getCardNo());
                card_temp.setCardCode(cards.get(0).getCardTypeCode());
                cards.clear();
                cards.add(card_temp);
                result.setCards(cards);
            } else {// 否则返回证件(身份证)
                // 查询是否存在临时卡
                QueryParams paramsCard = new QueryParams();
                paramsCard.addParameter("mpiId", temp.getMpiId());
                cardParams.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE);
                paramsCard.addParameter("cardTypeCode", "06");
                List<Card> cardTemp = cardDao.queryBaseList(paramsCard);
                // 不存在临时卡直接注册
                if(CollectionsUtils.isNull(cardTemp)){
                    Card card = new Card();
                    card.setMpiId(mpiId);
                    card.setCardTypeCode("06"); // 临时健康卡(卡号为身份证号码)
                    card.setPersonId(temp.getId());
                    card.setCardNo(person.getIdCard());
                    card.setCreator(ContentUtils.ADMIN_USER_ID);
                    card.setCreateDate(DateUtils.getCurrentDate());
                    card.setStatus(ContentUtils.CARD_STATUS_NORMAL);
                    card.setDelFalg(ContentUtils.INFO_NOT_DELETE);
                    cardDao.insertObject(card);
                    
                    // 卡的同步log 创建
                    List<CardSyncLog> cardSyncLogList = new ArrayList<CardSyncLog>();
                    CardSyncLog cardSyncLog = new CardSyncLog();
                    cardSyncLog.setCardId(card.getId());
                    cardSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
                    cardSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
                    cardSyncLog.setCreateDate(DateUtils.getCurrentDate());
                    cardSyncLogList.add(cardSyncLog);
                    // 设置卡未同步
                    setCardSyncLog(cardSyncLogList);
                    cardTemp.clear();
                    cardTemp.add(card);
                }
                // 存在则直接返回
                result.setCards(cardTemp);
            }
            persons.add(result);
            return ResponseEntity.getSuccessResponsePersons(persons, 1);
        }
        // 自动注册
        if (create) {
            // 判断身份证是否存在,预防身份证相同,姓名不同的错误数据
            QueryParams params = new QueryParams();
            params.addParameter("idCard", idCard);
            params.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE);
            Person person_error = queryOneBase(params);
            if (person_error != null) {
                return ResponseEntity.getErrorResponse("存在身份证与姓名不匹配情况!");
            }
            person.setMpiId(mpiId);
            // 未删除
            person.setDelFalg(ContentUtils.INFO_NOT_DELETE);
            // 未死亡
            person.setDeceasedInd(ContentUtils.PERSON_DECEASED_IND_ENABLE);
            // 未注销
            person.setStatus(ContentUtils.PERSON_STATUS_ENABLE);
            person.setCreator(ContentUtils.ADMIN_USER_ID);
            person.setCreateDate(DateUtils.getCurrentDate());
            // 添加临时卡
            Card cardTemp = new Card();
            cardTemp.setMpiId(mpiId);
            cardTemp.setCardNo(person.getIdCard());
            cardTemp.setCardTypeCode("06"); // 临时健康卡(卡号为身份证号码)
            cardTemp.setCreator(ContentUtils.ADMIN_USER_ID);
            cardTemp.setCreateDate(DateUtils.getCurrentDate());
            cardTemp.setStatus(ContentUtils.CARD_STATUS_NORMAL);
            cardTemp.setDelFalg(ContentUtils.INFO_NOT_DELETE);
            List<Card> cardsTemp = new ArrayList<Card>();
            cardsTemp.add(cardTemp);
            person.setCards(cardsTemp);
            doRegisterAnnotherInfo(person);

            // 设置基本信息未同步
            setSyncLogAsync(person.getId(), ContentUtils.INFO_SYNC_STATUS_NO);
            
            // 返回主索引Id和临时卡
            QueryParams cardParams = new QueryParams();
            cardParams.addParameter("mpiId", mpiId);
            cardParams.addParameter("cardTypeCode", "06");
            cardParams.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE);
            cardsTemp.clear();
            cardsTemp = cardDao.queryBaseList(cardParams);
            List<Person> result_persons = new ArrayList<Person>(); 
            Person result_person = new Person();
            result_person.setMpiId(mpiId);
            result_person.setCards(cardsTemp);
            result_persons.add(result_person);
            return ResponseEntity.getSuccessResponsePersons(result_persons, null);
        }
        LOG.error("主索引信息不存在");
        return ResponseEntity.getErrorResponse("没有相应查询结果");
    }



    /**
     * 个人详细信息查询
     */
    @Override
    public ResponseEntity getMPIDetail(Person person) {
        String mpiId = person.getMpiId();
        if (StringUtils.isEmpty(mpiId)) {
            return ResponseEntity.getParamsErrorResponse("主索引Id不允许为空");
        }
        QueryParams params = new QueryParams();
        params.addParameter("mpiId", mpiId);
        params.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE);
        // 查未删除的个人详细信息
        List<Person> persons = personDao.queryDetailList(params);
        return ResponseEntity.getSuccessResponsePersons(CollectionsUtils.isNull(persons)?null:persons, persons.size());
    }



    /**
     * 更新个人信息
     */
    @Override
    public ResponseEntity updateMPI(Person person) throws Exception {
        if (StringUtils.isEmpty(person.getMpiId())) {
            return ResponseEntity.getParamsErrorResponse("主索引id不能为空!");
        }
        if (person.getBirthday() == null) {
            return ResponseEntity.getParamsErrorResponse("出生日期不能为空!");
        }
        if (StringUtils.isEmpty(person.getPersonName())) {
            return ResponseEntity.getParamsErrorResponse("姓名不能为空!");
        }
        // if (StringUtils.isEmpty(person.getCardNo())) {
        // return ResponseEntity.getParamsErrorResponse("一卡通号不能为空!");
        // }
        if (StringUtils.isEmpty(person.getIdCard())) {
            return ResponseEntity.getParamsErrorResponse("身份证号不能为空!");
        }
        if (StringUtils.isEmpty(person.getSexCode())) {
            return ResponseEntity.getParamsErrorResponse("性别代码不能为空!");
        }
        
        String mpiId = person.getMpiId();
        // 查询是否存在未删除的个人信息
        Person temp = personDao.getEnableObjectByMpiId(mpiId);
        if (temp == null) {
            LOG.error("主索引信息不存在");
            return ResponseEntity.getErrorResponse("更新失败,主索引信息不存在!");
        }else{
            person.setId(temp.getId());
            return updateMPIBaseInfo(person,"base");// 返回基本信息
        }
    }
    
    /**
     * 
     * 描述：更新基本信息
     * @param person
     * @return
     * @author fengshengliang 2018年1月16日 下午5:17:58 
     * @version 1.0
     * @throws Exception 
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity updateMPIBaseInfo(Person person,String returnType) throws Exception{
        QueryParams updateParams = assembleParams(person);
        updateParams.addParameter("id", person.getId());
        updateParams.addParameter("updator", ContentUtils.ADMIN_USER_ID);
        updateParams.addParameter("updateDate", DateUtils.getCurrentDate());
        personDao.updateObjectByFields(updateParams);
        // 设置同步信息
        setSyncLogAsync(person.getId(), ContentUtils.INFO_SYNC_STATUS_NO);
        
        updateParams.clear();
        updateParams.addParameter("id", person.getId());
        List<Person> temp = new ArrayList<Person>();
        if("detail".equals(returnType)){
            temp = queryDetailList(updateParams);
        }else if("base".equals(returnType)){
            temp = queryBaseList(updateParams);
        }
        return ResponseEntity.getSuccessResponsePersons(CollectionsUtils.isNull(temp)?null:temp, temp.size());
    }



    /**
     * 注销个人信息
     * @throws Exception 
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity writeOffMPI(Person person) throws Exception {
        String mpiId = person.getMpiId();
        if (StringUtils.isEmpty(mpiId)) {
            LOG.error("主索引id为空");
            return ResponseEntity.getParamsErrorResponse("主索引id不能为空!");
        }
        //查询是否存在未删除,且未注销的信息
        QueryParams params = new QueryParams();
        params.addParameter("mpiId", mpiId);
        params.addParameter("status", ContentUtils.PERSON_STATUS_ENABLE);
        params.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE);
        Person temp = queryOneBase(params);
        if (temp == null) {
            LOG.error("主索引Id对应信息不存在");
            return ResponseEntity.getErrorResponse("主索引Id对应信息不存!");
        }
        // 注销
        temp.setStatus(ContentUtils.PERSON_STATUS_DISABLE);
        temp.setUpdator(ContentUtils.ADMIN_USER_ID);
        temp.setUpdateDate(DateUtils.getCurrentDate());
        int n = personDao.updateObject(temp);
        // 设置同步信息
        setSyncLogAsync(temp.getId(), ContentUtils.INFO_SYNC_STATUS_NO);
        temp.setId(null);
        return ResponseEntity.getSuccessResponse(null, 0);
    }



    /**
     * 打死亡标记
     * @throws Exception 
     */
    @Override
    public ResponseEntity deathMPI(Person person) throws Exception {
        String mpiId = person.getMpiId();
        if (StringUtils.isEmpty(mpiId)) {
            LOG.error("主索引id为空");
            return ResponseEntity.getParamsErrorResponse("主索引id不能为空!");
        }
        //查询是否存在未删除,且未死亡信息
        QueryParams params = new QueryParams();
        params.addParameter("mpiId", mpiId);
        //params.addParameter("deceasedInd", ContentUtils.PERSON_DECEASED_IND_ENABLE);
        params.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE);
        Person temp = queryOneBase(params);
        if (temp == null) {
            LOG.error("主索引Id对应信息不存在");
            return ResponseEntity.getErrorResponse("主索引Id对应信息不存在");
        }
        if(ContentUtils.PERSON_DECEASED_IND_DISABLE.equals(temp.getDeceasedInd())){
            LOG.error("重复打死亡标记");
            return ResponseEntity.getErrorResponse("请不要重复打死亡标记");
        }
        // 打死亡标记
        temp.setDeceasedInd(ContentUtils.PERSON_DECEASED_IND_DISABLE);
        temp.setDeceasedTime(DateUtils.getCurrentDate());
        temp.setUpdator(ContentUtils.ADMIN_USER_ID);
        temp.setUpdateDate(DateUtils.getCurrentDate());
        personDao.updateObject(temp);
        // 设置同步信息
        setSyncLogAsync(temp.getId(), ContentUtils.INFO_SYNC_STATUS_NO);
        temp.setId(null);
        return ResponseEntity.getSuccessResponse(null, 0);
    }



    /**
     * 取消打死标记
     * @throws Exception 
     */
    @Override
    public ResponseEntity undeathMPI(Person person) throws Exception {
        String mpiId = person.getMpiId();
        if (StringUtils.isEmpty(mpiId)) {
            LOG.error("主索引id为空");
            return ResponseEntity.getParamsErrorResponse("主索引id不能为空!");
        }
        // 查询是否存在未删除,且死亡的信息
        QueryParams params = new QueryParams();
        params.addParameter("mpiId", mpiId);
        //params.addParameter("deceasedInd", ContentUtils.PERSON_DECEASED_IND_DISABLE);
        params.addParameter("delFalg", ContentUtils.INFO_NOT_DELETE);
        Person temp = queryOneBase(params);
        if (temp == null) {
            LOG.error("主索引Id对应信息不存在");
            return ResponseEntity.getErrorResponse("主索引Id对应信息不存在!");
        }
        if(ContentUtils.PERSON_DECEASED_IND_ENABLE.equals(temp.getDeceasedInd())){
            LOG.error("重复取消死亡标记");
            return ResponseEntity.getErrorResponse("请不要重复取消死亡标记");
        }
        // 取消死亡标记
        temp.setDeceasedInd(ContentUtils.PERSON_DECEASED_IND_ENABLE);
        temp.setDeceasedTime(null);
        temp.setUpdator(ContentUtils.ADMIN_USER_ID);
        temp.setUpdateDate(DateUtils.getCurrentDate());
        int n = personDao.updateObject(temp);
        // 设置同步信息
        setSyncLogAsync(temp.getId(), ContentUtils.INFO_SYNC_STATUS_NO);
        temp.setId(null);
        return ResponseEntity.getSuccessResponse(null, n);
    }



    /**
     * 批量注册
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity batchSubmitMPI(List<Person> persons, boolean batch) throws Exception {
        if (persons != null && persons.size() > 0) {
            for (Person person : persons) {
                ResponseEntity responseEntity = null;
                try {
                    responseEntity = submitMPI(person);
                    // 操作失败
                    if (!ContentUtils.EMPI_STATUS_CODE_SUCCESS.equals(responseEntity.getCode())) {
                        // 整体事物提交
                        if (batch) {
                            // 手动回滚事务
                            // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            LOG.error(responseEntity.getMessage());
                            throw new Exception(responseEntity.getMessage());
                        } else {
                            return ResponseEntity.getErrorResponse(responseEntity.getMessage(), person);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.error(e.getMessage());
                    throw new Exception(e.getMessage());
                }
            }
            return ResponseEntity.getSuccessResponse(null, persons.size());
        } else {
            LOG.error("persons不能为空");
            return ResponseEntity.getParamsErrorResponse("persons不能为空");
        }
    }



    /**
     * 
     * 描述：异部设置同步记录为未同步
     * 
     * @param certificateId
     * @param syncStatus
     * @author fengshengliang 2017年12月29日 上午9:53:58
     * @version 1.0
     */
    public void setSyncLogAsync(final Long personId, final String syncStatus) throws Exception {
        List<FrontEndMachine> fems = frontEndMachineDao.queryBaseList(null);
        List<PersonSyncLog> psls = new ArrayList<PersonSyncLog>();
        for (FrontEndMachine fem : fems) {
            PersonSyncLog psl = new PersonSyncLog();
            psl.setPersonId(personId);
            psl.setFrontId(fem.getId());
            psl.setSyncStatus(syncStatus);
            psl.setCreator(ContentUtils.ADMIN_USER_ID);
            psl.setCreateDate(DateUtils.getCurrentDate());
            psl.setUpdator(ContentUtils.ADMIN_USER_ID);
            psl.setUpdateDate(DateUtils.getCurrentDate());
            psls.add(psl);
        }
        personSyncLogDao.saveOrUpdateList(psls);
    }

    private void setCertificateSyncLogAsync(List<CertificateSyncLog> certificateSyncLogList) {
        List<FrontEndMachine> frontEndMachineList = frontEndMachineDao.queryBaseList(null);
        if (CollectionsUtils.isNull(frontEndMachineList)) {
            return;
        }
        List<CertificateSyncLog> addcertificateSyncLogList = new ArrayList<CertificateSyncLog>();
        // 设置前置id
        for (CertificateSyncLog certificateSyncLog : certificateSyncLogList){
            for (FrontEndMachine fem : frontEndMachineList) {
                CertificateSyncLog certificateSyncLogClone = new CertificateSyncLog();
                BeanUtils.copyProperties(certificateSyncLog, certificateSyncLogClone);
                certificateSyncLogClone.setFrontId(fem.getId());
                addcertificateSyncLogList.add(certificateSyncLogClone);
            }
        }
        certificateSyncLogDao.saveOrUpdateList(addcertificateSyncLogList);
    }
    
    /**
     * 
     * 描述：增加卡的同步记录
     * @param cardSyncLogList
     * @author fengshengliang 2018年1月23日 下午3:01:26 
     * @version 1.0
     */
    private void setCardSyncLog(List<CardSyncLog> cardSyncLogList) {
        List<FrontEndMachine> frontEndMachineList = frontEndMachineDao.queryBaseList(null);
        if (CollectionsUtils.isNull(frontEndMachineList)) {
            return;
        }
        List<CardSyncLog> addCardSyncLogList = new ArrayList<CardSyncLog>();
        // 设置前置id
        for (CardSyncLog cardSyncLog : cardSyncLogList) {
            for (FrontEndMachine fem : frontEndMachineList) {
                CardSyncLog cardSyncLogClone = new CardSyncLog();
                BeanUtils.copyProperties(cardSyncLog, cardSyncLogClone);
                cardSyncLogClone.setFrontId(fem.getId());
                addCardSyncLogList.add(cardSyncLogClone);
            }
        }
        cardSyncLogDao.insertOrUpdatetList(addCardSyncLogList);
    }
    
    /**
     * 
     * 描述：增加地址同步记录
     * @param addressSyncLogs
     * @author fengshengliang 2018年1月23日 下午4:02:41 
     * @version 1.0
     */
    private void setAddressSyncLog(List<AddressSyncLog> addressSyncLogs) {
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
    
    /**
     * 
     * 描述：增加同步联系人日志
     * @param contactSyncLogs
     * @author fengshengliang 2018年1月23日 下午4:08:11 
     * @version 1.0
     */
    private void setContactSyncLog(List<ContactSyncLog> contactSyncLogs) {
        List<FrontEndMachine> frontEndMachineList = frontEndMachineDao.queryBaseList(null);
        if (CollectionsUtils.isNull(frontEndMachineList)) {
            return;
        }
        List<ContactSyncLog> List = new ArrayList<ContactSyncLog>();
        // 设置前置id
        for (ContactSyncLog contactSyncLog : contactSyncLogs) {
            for (FrontEndMachine fem : frontEndMachineList) {
                ContactSyncLog contactSyncLog2 = new ContactSyncLog();
                BeanUtils.copyProperties(contactSyncLog, contactSyncLog2);
                contactSyncLog2.setFrontId(fem.getId());
                List.add(contactSyncLog2);
            }
        }
        contactSyncLogDao.inserOrUpdatetList(List);
    }
    
    /**
     * 
     * 描述：增加同步联系方式日志
     * @param contactWaySyncLogs
     * @author fengshengliang 2018年1月23日 下午4:15:23 
     * @version 1.0
     */
    private void setContactWaySyncLog(List<ContactWaySyncLog> contactWaySyncLogs) {
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
    
    /**
     * 
     * 描述：检查证件是否存在
     * 
     * @param certificateId
     * @param syncStatus
     * @author fengshengliang 2017年12月29日 上午9:53:58
     * @version 1.0
     */
    public boolean certificatesIsExist(Certificate cer, String mpiId) {
        String certificateTypeCode = cer.getCertificateTypeCode();
        String certificateNo = cer.getCertificateNo();
        Integer delFalg = ContentUtils.INFO_NOT_DELETE;

        QueryParams params = new QueryParams();
        params.addParameter("mpiId", mpiId);
        params.addParameter("certificateTypeCode", certificateTypeCode);
        params.addParameter("certificateNo", certificateNo);
        params.addParameter("delFalg", delFalg);

        List<Certificate> temp = certificateDao.queryBaseList(params);
        if (temp != null && temp.size() > 0) {
            return true;
        }
        return false;
    }



    /**
     * 
     * 描述：检查地址是否存在
     * 
     * @param certificateId
     * @author fengshengliang 2017年12月29日 上午9:53:58
     * @version 1.0
     */
    public boolean addressesIsExist(Address addr, String mpiId) {
        String addressTypeCode = addr.getAddressTypeCode();
        String address = addr.getAddress();
        String postalCode = addr.getPostalCode();
        Integer delFalg = ContentUtils.INFO_NOT_DELETE;

        QueryParams params = new QueryParams();
        params.addParameter("mpiId", mpiId);
        params.addParameter("addressTypeCode", addressTypeCode);
        params.addParameter("address", address);
        params.addParameter("postalCode", postalCode);
        params.addParameter("delFalg", delFalg);

        List<Address> temp = addressDao.queryBaseList(params);
        if (temp != null && temp.size() > 0) {
            return true;
        }
        return false;
    }



    /**
     * 
     * 描述：检查联系方式是否存在
     * 
     * @param certificateId
     * @param syncStatus
     * @author fengshengliang 2017年12月29日 上午9:53:58
     * @version 1.0
     */
    public boolean contactWayIsExist(ContactWay contactWay, String mpiId) {
        String contactTypeCode = contactWay.getContactTypeCode();
        String contactNo = contactWay.getContactNo();
        Integer delFalg = ContentUtils.INFO_NOT_DELETE;

        QueryParams params = new QueryParams();
        params.addParameter("mpiId", mpiId);
        params.addParameter("contactTypeCode", contactTypeCode);
        params.addParameter("contactNo", contactNo);
        params.addParameter("delFalg", delFalg);

        List<ContactWay> temp = contactWayDao.queryBaseList(params);
        if (temp != null && temp.size() > 0) {
            return true;
        }
        return false;
    }



    public boolean contactIsExist(Contact cont, String mpiId) {
        String certificateTypeCode = cont.getCertificateTypeCode();
        String certificateNo = cont.getCertificateNo();
        String contactName = cont.getContactName();
        String contactNo = cont.getContactNo();
        Integer delFalg = ContentUtils.INFO_NOT_DELETE;

        QueryParams params = new QueryParams();
        params.addParameter("mpiId", mpiId);
        params.addParameter("certificateTypeCode", certificateTypeCode);
        params.addParameter("certificateNo", certificateNo);
        params.addParameter("contactName", contactName);
        params.addParameter("contactNo", contactNo);
        params.addParameter("delFalg", delFalg);

        List<Contact> temp = contactDao.queryBaseList(params);
        if (temp != null && temp.size() > 0) {
            return true;
        }
        return false;
    }



    public boolean cardIsExist(Card card, String mpiId) {
        String cardTypeCode = card.getCardTypeCode();
        String cardNo = card.getCardNo();
        String cardCode = card.getCardCode();

        Integer delFalg = ContentUtils.INFO_NOT_DELETE;

        QueryParams params = new QueryParams();
        params.addParameter("mpiId", mpiId);
        params.addParameter("cardTypeCode", cardTypeCode);
        params.addParameter("cardNo", cardNo);
        params.addParameter("cardCode", cardCode);
        params.addParameter("delFalg", delFalg);

        List<Card> temp = cardDao.queryBaseList(params);
        if (temp != null && temp.size() > 0) {
            return true;
        }
        return false;
    }



    public ResponseEntity checkNull(QueryParams params) {
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("mpiId", "主索引id不能为空!");
        resultMap.put("birthday", "出生日期不能为空!");
        resultMap.put("personName", "姓名不能为空!");
        resultMap.put("idCard", "身份证号不能为空!");
        resultMap.put("sexCode", "性别代码不能为空!");

        Iterator<Entry<String, Object>> iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            String key = entry.getKey();
            Object val = entry.getValue();
            if (BeanUtils.isNull(val)) {
                return ResponseEntity.getParamsErrorResponse(resultMap.get(key));
            }
        }
        return ResponseEntity.getSuccessResponse(null, null);

    }



    public QueryParams assembleParams(Person person) {
        if(person == null){
            return null;
        }
        QueryParams params = new QueryParams();
        try {
            Class<?> c = person.getClass();
            Field[] fields = c.getDeclaredFields();
//            for (Field f : fields) {
//                f.setAccessible(true);
//            }
            for (Field f : fields) {
                f.setAccessible(true);
                if(f.getType().isAssignableFrom(List.class)){ // 排除集合元素
                    continue;
                }
                String field = f.toString().substring(f.toString().lastIndexOf(".") + 1); // 取出属性名称
                if("updateData".equals(field)||"currentData".equals(field)||"serialVersionUID".equals(field)){
                    continue;
                }
                if (!BeanUtils.isNull(f.get(person))) {
                    params.put(field, f.get(person));
                }
            }
        } catch (Exception e) {
            LOG.error("参数解析失败!");
            return null;
        }
        return params;
    }
}
