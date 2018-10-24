package com.jc.cz_index.dto.syncLog;
import com.jc.cz_index.model.BaseBean;
import com.jc.cz_index.model.Certificate;
import com.jc.cz_index.model.FrontEndMachine;

public class CertificateSyncLogDto extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3666606447412671500L;

	
	
	
	
	private Long id;
	private Long CertificateId;
	private Long frontId;
	private String remark;
	private String syncStatus;
	private FrontEndMachine fem;
	private String femCode;
	private String femAddress;
	private Integer femState;
	private String personName;
	private String personId;

	
	private Certificate certificate;
	private String certificateNo;
	private String type;
	
	public String getType() {
		return this.certificate.getCertificateTypeCode();
	}
	
	public String getCertificateNo() {
		return this.certificate.getCertificateNo();
	}
	
	public String getFemCode() {
		return this.fem.getFrontEndMachinecode();
	}
	public String getCreatorName() {
		return super.getCreatorUser()==null?"":super.getCreatorUser().getName();
	}
	
	public String getUpdatorName() {
		return super.getUpdatorUser()==null?"":super.getUpdatorUser().getName();
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
	public Certificate getCertificate() {
		return certificate;
	}
	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}
	public FrontEndMachine getFem() {
		return fem;
	}
	public void setFem(FrontEndMachine fem) {
		this.fem = fem;
	}
	

	public Long getCertificateId() {
		return CertificateId;
	}

	public void setCertificateId(Long CertificateId) {
		this.CertificateId = CertificateId;
	}

	public void setCertificateNo(String CertificateNo) {
		this.certificateNo = certificateNo;
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
