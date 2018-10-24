package com.jc.cz_index.core.web.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.core.service.IAddressService;
import com.jc.cz_index.core.service.IAddressSyncLogService;
import com.jc.cz_index.core.service.ICardService;
import com.jc.cz_index.core.service.ICardSyncLogService;
import com.jc.cz_index.core.service.ICertificateService;
import com.jc.cz_index.core.service.ICertificateSyncLogService;
import com.jc.cz_index.core.service.IContactService;
import com.jc.cz_index.core.service.IContactSyncLogService;
import com.jc.cz_index.core.service.IContactWayService;
import com.jc.cz_index.core.service.IContactWaySyncLogService;
import com.jc.cz_index.core.service.IPersonService;
import com.jc.cz_index.core.service.IPersonSyncLogService;
import com.jc.cz_index.dao.IContactWaySyncLogDao;
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

/**
 * @ClassName: AppServiceClient
 * @Description:TODO(用于数据推送至前置机)
 * @author: ZYX
 * @date: 2017年12月28日 下午4:23:26
 * @Copyright: 2017 ... Inc. All rights reserved. Note: this content is limited
 *             to individual research use, no leakage and other business
 *             purposes
 */
@Component
public class FemServiceClient {
    private static final String        qName       = "http://impl.webservice.web.fem.cz_index.jc.com/";
    private static final String        serviceName = "FemServiceService";
    private static final String        servicePort = "FemServicePort";
    private static URL                 wsdlUrl;
    private static Service             s;
    private static IFemService         hs;

    @Autowired
    private IPersonSyncLogService      personSyncLogService;
    @Autowired
    private IPersonService             personService;

    @Autowired
    private IContactService            contactService;
    @Autowired
    private IContactSyncLogService     contactSyncLogService;

    @Autowired
    private ICardService               cardService;
    @Autowired
    private ICardSyncLogService        cardSyncLogService;

    @Autowired
    private ICertificateService        certificateService;
    @Autowired
    private ICertificateSyncLogService certificateSyncLogService;
    
    @Autowired
	private  IAddressSyncLogService addressSyncLogService;
	
	@Autowired
	private	 IAddressService addressService;
	
	@Autowired
	private IContactWayService contactWayService;
	@Autowired
	private IContactWaySyncLogService contactWaySyncLogService;



	/**
	 * 
	 * 描述：同步基本身份信息
	 * @param frontEndMachine
	 * @throws MalformedURLException
	 * @author fengshengliang 2018年1月5日 上午10:24:20 
	 * @version 1.0
	 */
    public void sendPersons(FrontEndMachine frontEndMachine) throws MalformedURLException {
        Long fem_id = frontEndMachine.getId();
        // 获取未同步数据
        List<Person> persons = personService.excuteTask(fem_id);
        doSendPersons(frontEndMachine, persons);
    }
    
    public int doSendPersons(FrontEndMachine frontEndMachine, List<Person> persons) throws MalformedURLException{
        Long fem_id = frontEndMachine.getId();
        String fem_url = frontEndMachine.getFrontEndMachineaddress();
        if(CollectionsUtils.isNull(persons)){
            return 0;
        }
        // 调用前置机同步数据
        List<PersonSyncLog> psl = createConect(fem_url).syncPerson(persons);
        // 根据前置机返回结果,修改同步状态
        if (psl != null && psl.size() > 0) {
            return personSyncLogService.doUpdateSyncInfo(psl, fem_id);
        }
        return 0;
    }


    /**
     * 
     * 描述：同步卡信息
     * 
     * @param fem
     * @throws MalformedURLException
     * @author yangyongchuan 2018年1月4日 下午10:09:19
     * @version 1.0
     */
    public void sendCard(FrontEndMachine fem) throws MalformedURLException {
        Long femId = fem.getId();
        List<Card> cards = cardService.excuteTask(femId);
        if (CollectionsUtils.isNull(cards)) {
            return;
        }
        doSendCard(fem, cards);
    }

    public int doSendCard(FrontEndMachine frontEndMachine,List<Card> cards) throws MalformedURLException{
        Long femId = frontEndMachine.getId();
        String femUrl = frontEndMachine.getFrontEndMachineaddress();
        // 同步数据
        List<CardSyncLog> cardSyncLogList = createConect(femUrl).syncCard(cards);
        if (CollectionsUtils.isNull(cardSyncLogList)) {
            return 0;
        }
        // 封装回传数据
        Date nowDate = DateUtils.getCurrentDate();
        for (CardSyncLog cardSyncLog : cardSyncLogList) {
            cardSyncLog.setFrontId(femId);
            cardSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_YES);
            cardSyncLog.setUpdator(ContentUtils.ADMIN_USER_ID);
            cardSyncLog.setUpdateDate(nowDate);
        }
        // 修改同步状态
        return cardSyncLogService.inserOrUpdatetList(cardSyncLogList);
    }


    /**
     * @Title: sendContact @Description: TODO(同步联系人信息) @param: @param url
     * @param: @param
     *             contacts @param: @return @param: @throws
     *             MalformedURLException @return: List<ContactSyncLog> @throws
     */
    public void sendContact(FrontEndMachine fem) throws MalformedURLException {
        Long femId = fem.getId();
        List<Contact> contacts = contactService.excuteTask(femId);
        if (CollectionsUtils.isNull(contacts)) {
            return;
        }
        doSendContact(fem, contacts);
    }
    
    public int doSendContact(FrontEndMachine frontEndMachine,List<Contact> contacts) throws MalformedURLException{
        Long femId = frontEndMachine.getId();
        String femUrl = frontEndMachine.getFrontEndMachineaddress();
        // 同步数据
        List<ContactSyncLog> contactSyncLogList = createConect(femUrl).syncContact(contacts);
        if (CollectionsUtils.isNull(contactSyncLogList)) {
            return 0;
        }
        // 封装回传数据
        Date nowDate = DateUtils.getCurrentDate();
        for (ContactSyncLog contactSyncLog : contactSyncLogList) {
            contactSyncLog.setFrontId(femId);
            contactSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_YES);
            contactSyncLog.setUpdator(ContentUtils.ADMIN_USER_ID);
            contactSyncLog.setUpdateDate(nowDate);
        }
        // 修改同步状态
        return contactSyncLogService.inserOrUpdatetList(contactSyncLogList);
    }


    /**
     * @Title: sendWay @Description: TODO(同步联系方式) @param: @param
     *         url @param: @param ways @param: @return @param: @throws
     *         MalformedURLException @return: List<ContactWaySyncLog> @throws
     */
    public void sendWay(FrontEndMachine frontEndMachine) throws MalformedURLException {
    	List<ContactWay> contactWays = contactWayService.excuteTask(frontEndMachine.getId());
    	doSendContactWay(frontEndMachine, contactWays);
    }

    public int doSendContactWay(FrontEndMachine frontEndMachine,List<ContactWay> contactWays) throws MalformedURLException{
        List<ContactWaySyncLog> cwsl = createConect(frontEndMachine.getFrontEndMachineaddress()).syncContactWay(contactWays);
        if (cwsl!=null && cwsl.size()>0) {
            return contactWaySyncLogService.doUpdateSyncInfo(cwsl,frontEndMachine.getId());
        }
        return 0;
    }


    /**
     * @Title: sendAddress @Description: TODO(同步地址信息) @param: @param
     *         url @param: @param adds @param: @return @param: @throws
     *         MalformedURLException @return: List<AddressSyncLog> @throws
     */
    public void sendAddress(FrontEndMachine frontEndMachine) throws MalformedURLException {
		List<Address> addresses = addressService.excuteTask(frontEndMachine.getId());
		doSendAddress(frontEndMachine, addresses);
	}
    
    public int doSendAddress(FrontEndMachine frontEndMachine,List<Address> addresses) throws MalformedURLException{
        List<AddressSyncLog> asl = createConect(frontEndMachine.getFrontEndMachineaddress()).syncAddress(addresses);
        if (asl!=null && asl.size()>0) {
            return addressSyncLogService.doUpdateSyncInfo(asl,frontEndMachine.getId());
        }
        return 0;
    }


    /**
     * 
     * 描述：同步证件信息
     * 
     * @param frontEndMachine
     * @throws MalformedURLException
     * @author fengshengliang 2018年1月4日 下午8:54:13
     * @version 1.0
     */
    public void sendCertificate(FrontEndMachine frontEndMachine) throws MalformedURLException {
        Long femId = frontEndMachine.getId();
        // 获取未同步的数据
        List<Certificate> certificates = certificateService.excuteTask(femId);
        doSendCertificate(frontEndMachine, certificates);
    }
    
    public int doSendCertificate(FrontEndMachine frontEndMachine, List<Certificate> certificates) throws MalformedURLException{
        Long fem_id = frontEndMachine.getId();
        String fem_url = frontEndMachine.getFrontEndMachineaddress();
        if(CollectionsUtils.isNull(certificates)){
            return 0;
        }
        // 调用前置机同步数据
        List<CertificateSyncLog> csl = createConect(fem_url).syncCertificate(certificates);
        // 根据前置机返回结果,修改同步状态
        if (csl != null && csl.size() > 0) {
            return certificateSyncLogService.doUpdateSyncInfo(csl, fem_id);
        }
        return 0;
    }



    /**
     * @Title: createConect @Description: TODO(创建webservice服务链接) @param: @param
     *         url @param: @return @param: @throws
     *         MalformedURLException @return: IFemService @throws
     */
    public static IFemService createConect(String url) throws MalformedURLException {
        wsdlUrl = new URL(url);
        s = Service.create(wsdlUrl, new QName(qName, serviceName));
        return (IFemService) s.getPort(new QName(qName, servicePort), IFemService.class);
    }
}
