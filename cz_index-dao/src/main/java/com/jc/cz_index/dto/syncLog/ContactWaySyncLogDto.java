package com.jc.cz_index.dto.syncLog;
import com.jc.cz_index.model.BaseBean;
import com.jc.cz_index.model.Certificate;
import com.jc.cz_index.model.ContactWay;
import com.jc.cz_index.model.FrontEndMachine;

public class ContactWaySyncLogDto extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3666606447412671500L;
	private Long id;
	private Long contactWayId; 
	private Long frontId;
	private String remark;
	private String syncStatus;
	private FrontEndMachine fem;
	private String femCode;
	private String femAddress;
	private Integer femState;
	private String personName;
	private String personId;
	private ContactWay contactWay;
	private String contactNo;
	private String type;
	
	public String getType() {
		return this.contactWay.getContactTypeCode();
	}
	
	public String getFemCode() {
		return this.fem.getFrontEndMachinecode();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getFrontId() {
		return frontId;
	}
	public void setFrontId(Long frontId) {
		this.frontId = frontId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSyncStatus() {
		return syncStatus;
	}
	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

	public FrontEndMachine getFem() {
		return fem;
	}
	public void setFem(FrontEndMachine fem) {
		this.fem = fem;
	}


	public void setFemCode(String femCode) {
		this.femCode = femCode;
	}

	public String getFemAddress() {
		return this.fem.getFrontEndMachineaddress();
	}

	public void setFemAddress(String femAddress) {
		this.femAddress = femAddress;
	}

	public Integer getFemState() {
		return this.fem.getState();
	}

	public void setFemState(Integer femState) {
		this.femState = femState;
	}
	public ContactWay getContactWay() {
		return contactWay;
	}
	public void setContactWay(ContactWay contactWay) {
		this.contactWay = contactWay;
	}
	public String getContactNo() {
		return this.contactWay.getContactNo();
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public Long getContactWayId() {
		return contactWayId;
	}
	public void setContactWayId(Long contactWayId) {
		this.contactWayId = contactWayId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
}
