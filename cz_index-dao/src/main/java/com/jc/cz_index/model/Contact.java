package com.jc.cz_index.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "contact")
public class Contact extends BaseBean {
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
	
    //主索引id
    private String mpiId;
    //基本身份信息id
    private java.lang.Long personId;
    //联系人证件类别代码
    private String certificateTypeCode;
    //联系人证件号码
    private String certificateNo;
    //联系人姓名
    private String contactName;
    //联系人电话
    private String contactNo;
    //删除标记,(0,未删除1,已删除)
    private Integer delFalg;
    //删除人
    private java.lang.Long deleteor;
    //删除时间
    private java.util.Date deleteDate;
    //创建机构
    private String createUnit;
    //最后修改机构
    private String lastModifyUnit;
    public void setMpiId(String mpiId)
    {
        this.mpiId=mpiId;
    }
    public String getMpiId()
    {
        return this.mpiId;
    }
    public void setPersonId(java.lang.Long personId)
    {
        this.personId=personId;
    }
    public java.lang.Long getPersonId()
    {
        return this.personId;
    }
    public void setCertificateTypeCode(String certificateTypeCode)
    {
        this.certificateTypeCode=certificateTypeCode;
    }
    public String getCertificateTypeCode()
    {
        return this.certificateTypeCode;
    }
    public void setCertificateNo(String certificateNo)
    {
        this.certificateNo=certificateNo;
    }
    public String getCertificateNo()
    {
        return this.certificateNo;
    }
    public void setContactName(String contactName)
    {
        this.contactName=contactName;
    }
    public String getContactName()
    {
        return this.contactName;
    }
    public void setContactNo(String contactNo)
    {
        this.contactNo=contactNo;
    }
    public String getContactNo()
    {
        return this.contactNo;
    }
    public void setDelFalg(Integer delFalg)
    {
        this.delFalg=delFalg;
    }
    public Integer getDelFalg()
    {
        return this.delFalg;
    }
    public void setDeleteor(java.lang.Long deleteor)
    {
        this.deleteor=deleteor;
    }
    public java.lang.Long getDeleteor()
    {
        return this.deleteor;
    }
    public void setDeleteDate(java.util.Date deleteDate)
    {
        this.deleteDate=deleteDate;
    }
    public java.util.Date getDeleteDate()
    {
        return this.deleteDate;
    }
    public void setCreateUnit(String createUnit)
    {
        this.createUnit=createUnit;
    }
    public String getCreateUnit()
    {
        return this.createUnit;
    }
    public void setLastModifyUnit(String lastModifyUnit)
    {
        this.lastModifyUnit=lastModifyUnit;
    }
    public String getLastModifyUnit()
    {
        return this.lastModifyUnit;
    }


}

