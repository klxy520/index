package com.jc.cz_index.dto.card;

import java.io.Serializable;

public class CardDto implements Serializable {

	/**
	 * 卡列表dto
	 */
	private static final long serialVersionUID = 1L;
	private Long personId;
	private String personName;
	private String idCard;
	private String mpiId;
	private Long cardId;
	private String cardNo;
	private String cardCode;
	private String lastModifyUnit;
	private String createUnit;
	private String cardTypeCode;
	private String status;
	private java.util.Date updateDate;
	private java.util.Date createDate;
	private java.util.Date validTime;
	
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getMpiId() {
		return mpiId;
	}
	public void setMpiId(String mpiId) {
		this.mpiId = mpiId;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getCardTypeCode() {
		return cardTypeCode;
	}
	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public java.util.Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public java.util.Date getValidTime() {
		return validTime;
	}
	public void setValidTime(java.util.Date validTime) {
		this.validTime = validTime;
	}
	public String getLastModifyUnit() {
		return lastModifyUnit;
	}
	public void setLastModifyUnit(String lastModifyUnit) {
		this.lastModifyUnit = lastModifyUnit;
	}
	public String getCreateUnit() {
		return createUnit;
	}
	public void setCreateUnit(String createUnit) {
		this.createUnit = createUnit;
	}

}
