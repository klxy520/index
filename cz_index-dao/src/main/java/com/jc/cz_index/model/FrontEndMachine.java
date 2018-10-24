package com.jc.cz_index.model;

public class FrontEndMachine extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 主键 [主键]
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// 前置机编码
	private String frontEndMachinecode;
	// 前置机地址
	private String frontEndMachineaddress;
	// 状态，0：启用，1：禁用,3: 异常
	private Integer state;
	// 备注
	private String remarks;

	// 创建者
	private SystemUser creatorUser;
	// 修改者
	private SystemUser updatorUser;

	public SystemUser getCreatorUser() {
		return creatorUser;
	}

	public void setCreatorUser(SystemUser creatorUser) {
		this.creatorUser = creatorUser;
	}

	public SystemUser getUpdatorUser() {
		return updatorUser;
	}

	public void setUpdatorUser(SystemUser updatorUser) {
		this.updatorUser = updatorUser;
	}

	public void setFrontEndMachinecode(String frontEndMachinecode) {
		this.frontEndMachinecode = frontEndMachinecode;
	}

	public String getFrontEndMachinecode() {
		return this.frontEndMachinecode;
	}

	public void setFrontEndMachineaddress(String frontEndMachineaddress) {
		this.frontEndMachineaddress = frontEndMachineaddress;
	}

	public String getFrontEndMachineaddress() {
		return this.frontEndMachineaddress;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getState() {
		return this.state;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return this.remarks;
	}

}
