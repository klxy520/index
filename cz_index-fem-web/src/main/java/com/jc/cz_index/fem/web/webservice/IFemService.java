package com.jc.cz_index.fem.web.webservice;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Path;

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
 * @ClassName:  FemService   
 * @Description:TODO(同步service)   
 * @author: ZYX 
 * @date:   2017年12月29日 上午9:57:46     
 * @Copyright: 2017 ... Inc. All rights reserved. 
 * Note: 同步方法统一入口 
 */
@WebService
public interface IFemService {
	/**
	 * @Title: sendBaseMessage   
	 * @Description: TODO(接收基本身份信息并保存)   
	 * @param: @param persons      
	 * @return: void      
	 * @throws
	 */
	@Path("/syncPerson")
	public List<PersonSyncLog> syncPerson(List<Person> persons);
	
	@Path("/syncAddress")
	public List<AddressSyncLog> syncAddress(List<Address> adds);
	
	@Path("/syncCard")
	public List<CardSyncLog> syncCard(List<Card> cards);
	
	@Path("/syncContactWay")
	public List<ContactWaySyncLog> syncContactWay(List<ContactWay> ways);
	
	@Path("/syncContact")
	public List<ContactSyncLog> syncContact(List<Contact> contacts);
	
	@Path("/syncCertificate")
	public List<CertificateSyncLog> syncCertificate(List<Certificate> cers);
}
