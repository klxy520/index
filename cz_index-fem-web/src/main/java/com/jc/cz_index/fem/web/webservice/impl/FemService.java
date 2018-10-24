package com.jc.cz_index.fem.web.webservice.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.fem.service.IAddressService;
import com.jc.cz_index.fem.service.ICardService;
import com.jc.cz_index.fem.service.ICertificateService;
import com.jc.cz_index.fem.service.IContactService;
import com.jc.cz_index.fem.service.IContactWayService;
import com.jc.cz_index.fem.service.IPersonService;
import com.jc.cz_index.fem.web.webservice.IFemService;
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
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.PersonSyncLog;

/**
 * @ClassName: FemService
 * @Description:TODO(同步服务接口实现类)
 * @author: ZYX
 * @date: 2017年12月29日 上午10:42:02
 * @Copyright: 2017 ... Inc. All rights reserved. Note: this content is limited
 *             to individual research use, no leakage and other business
 *             purposes
 */
@WebService(endpointInterface = "com.jc.cz_index.fem.web.webservice.IFemService")
@SOAPBinding(style = Style.RPC)
public class FemService implements IFemService {
    private static final Logger logger = Logger.getLogger(FemService.class);
    @Autowired
    private IPersonService      personService;
    @Autowired
    private IContactService     contactService;
    @Autowired
    private ICardService        cardService;
    @Autowired
    private ICertificateService certificateService;
    @Autowired
    private IAddressService 	addressService;
    @Autowired
    private IContactWayService	contactWayService;



    /**
     * 同步居民信息
     */
    @Override
    public List<PersonSyncLog> syncPerson(List<Person> persons) {
        System.out.println("--同步person数据--");
        List<PersonSyncLog> psls = new ArrayList<PersonSyncLog>();
        if (persons != null && persons.size() > 0) {
            for (Person person : persons) {
                try {
                    int n = personService.saveOrUpdate(person);
                    if (n > 0) {
                        PersonSyncLog psl = new PersonSyncLog();
                        psl.setPersonId(person.getId());
                        psls.add(psl);
                    }
                } catch (Exception e) {
                    logger.error("同步基本身份信息异常,基本身份信息主键ID:" + person.getId());
                    logger.error("异常信息:" + e.getMessage());
                }
            }
        }
        return psls;
    }



    @Override
	public List<AddressSyncLog> syncAddress(List<Address> adds) {
		List<AddressSyncLog> asls = new ArrayList<AddressSyncLog>();
		if (adds != null && adds.size()>0) {
			for (Address address : adds) {
				try {
					int n = addressService.saveOrUpdate(address);
					if (n > 0) {
						AddressSyncLog asl = new AddressSyncLog();
						asl.setAddressId(address.getId());
						asls.add(asl);
					}
				} catch (Exception e) {
					logger.error("地址信息记录异常,地址表主键ID:" + address.getId());
	                logger.error("异常信息:" + e.getMessage());
				}
			}
		}
		return asls;
	}



    @Override
    public List<CardSyncLog> syncCard(List<Card> cards) {
        if (CollectionsUtils.isNull(cards)) {
            return null;
        }
        List<CardSyncLog> cardSyncLogs = new ArrayList<CardSyncLog>();
        for (Card card : cards) {
            try {
                int result = cardService.insertOrUpdate(card);
                if (result > 0) {
                    CardSyncLog cardSyncLog = new CardSyncLog();
                    cardSyncLog.setCardId(card.getId());
                    cardSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_YES);
                    cardSyncLogs.add(cardSyncLog);
                }
            } catch (Exception e) {
                logger.error("同步联系人记录异常,联系人表主键ID:" + card.getId());
                logger.error("异常信息:" + e.getMessage());
            }
        }
        return cardSyncLogs;
    }



    @Override
    public List<ContactWaySyncLog> syncContactWay(List<ContactWay> ways) {
        List<ContactWaySyncLog> contactWaySyncLogs = new ArrayList<ContactWaySyncLog>();
        if (ways!=null && ways.size()>0) {
        	for (ContactWay contactWay : ways) {
        		try {
        			int n = contactWayService.saveOrUpdate(contactWay);
    				if (n>0) {
    					ContactWaySyncLog cwsl = new ContactWaySyncLog();
    					cwsl.setContactWayId(contactWay.getId());
    					cwsl.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_YES);
    					contactWaySyncLogs.add(cwsl);
    				}
    			} catch (Exception e) {
    				logger.error("同步联系信息记录异常,联系信息表主键ID:" + contactWay.getId());
                    logger.error("异常信息:" + e.getMessage());
    			}
			}
		}
        return contactWaySyncLogs;
    }



    @Override
    public List<ContactSyncLog> syncContact(List<Contact> contacts) {
        if (CollectionsUtils.isNull(contacts)) {
            return null;
        }
        List<ContactSyncLog> contactSyncLogs = new ArrayList<ContactSyncLog>();
        for (Contact contact : contacts) {
            try {
                int result = contactService.insertOrUpdate(contact);
                if (result > 0) {
                    ContactSyncLog contactSyncLog = new ContactSyncLog();
                    contactSyncLog.setContactId(contact.getId());
                    contactSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_YES);
                    contactSyncLogs.add(contactSyncLog);
                }
            } catch (Exception e) {
                logger.error("同步联系人记录异常,联系人表主键ID:" + contact.getId());
                logger.error("异常信息:" + e.getMessage());
            }
        }
        return contactSyncLogs;
    }



    @Override
    public List<CertificateSyncLog> syncCertificate(List<Certificate> cers) {
        System.out.println("--同步Certificate数据--");
        List<CertificateSyncLog> csls = new ArrayList<CertificateSyncLog>();
        if (cers != null && cers.size() > 0) {
            for (Certificate certificate : cers) {
                try {
                    int n = certificateService.saveOrUpdate(certificate);
                    if (n > 0) {
                        CertificateSyncLog csl = new CertificateSyncLog();
                        csl.setCertificateId(certificate.getId());
                        csls.add(csl);
                    }
                } catch (Exception e) {
                    logger.error("同步证件信息异常,证件信息主键ID:" + certificate.getId());
                    logger.error("异常信息:" + e.getMessage());
                }
            }
        }
        return csls;
    }
}
