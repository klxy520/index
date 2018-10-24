package com.jc.cz_index.model;

public class CertificateSyncLog extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 主键 [主键]
	private Long              id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
    //证件信息主键id
    private java.lang.Long certificateId;
    //前置机id
    private java.lang.Long frontId;
    //0:未同步;1:已同步
    private String syncStatus;
    //创建人
    private java.lang.Long creator;
    //最后更新者
    private java.lang.Long updator;
    //创建时间
    private java.util.Date createDate;
    //最后更新时间
    private java.util.Date updateDate;
   
    //备注
    private String remark;
    private Certificate certificate;
    
    private FrontEndMachine fem;
    public void setCertificateId(java.lang.Long certificateId)
    {
        this.certificateId=certificateId;
    }
    public java.lang.Long getCertificateId()
    {
        return this.certificateId;
    }
    public void setFrontId(java.lang.Long frontId)
    {
        this.frontId=frontId;
    }
    public java.lang.Long getFrontId()
    {
        return this.frontId;
    }
    public void setSyncStatus(String syncStatus)
    {
        this.syncStatus=syncStatus;
    }
    public String getSyncStatus()
    {
        return this.syncStatus;
    }
    public void setCreator(java.lang.Long creator)
    {
        this.creator=creator;
    }
    public java.lang.Long getCreator()
    {
        return this.creator;
    }
    public void setUpdator(java.lang.Long updator)
    {
        this.updator=updator;
    }
    public java.lang.Long getUpdator()
    {
        return this.updator;
    }
    public void setCreateDate(java.util.Date createDate)
    {
        this.createDate=createDate;
    }
    public java.util.Date getCreateDate()
    {
        return this.createDate;
    }
    public void setUpdateDate(java.util.Date updateDate)
    {
        this.updateDate=updateDate;
    }
    public java.util.Date getUpdateDate()
    {
        return this.updateDate;
    }
    public void setRemark(String remark)
    {
        this.remark=remark;
    }
    public String getRemark()
    {
        return this.remark;
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
	


}

