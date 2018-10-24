package com.jc.cz_index.dto.syncLog;
import com.jc.cz_index.model.BaseBean;
import com.jc.cz_index.model.Card;
import com.jc.cz_index.model.FrontEndMachine;

public class CardSyncLogDto extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3666606447412671500L;
	private Long id;
	private Long cardId;
	private Long frontId;
	private String remark;
	private String syncStatus;
	private FrontEndMachine fem;
	private String femCode;
	private String femAddress;
	private Integer femState;
	private String personName;
	private String personId;
	
	private Card card;
	private String cardNo;
	private String type;
	
	
	public String getCardNo() {
		return this.card.getCardNo();
	}
	public String getType() {
		return this.card.getCardTypeCode();
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
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public FrontEndMachine getFem() {
		return fem;
	}
	public void setFem(FrontEndMachine fem) {
		this.fem = fem;
	}
	

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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
