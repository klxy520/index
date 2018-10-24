package com.jc.cz_index.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "contactWay")
public class ContactWay extends BaseBean {
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
    //联系人方式
    private String contactTypeCode;
    //联系号码
    private String contactNo;
    private Long personId;
    //删除标记,(0,未删除1,已删除)
    //@XmlTransient
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
    public void setContactTypeCode(String contactTypeCode)
    {
        this.contactTypeCode=contactTypeCode;
    }
    public String getContactTypeCode()
    {
        return this.contactTypeCode;
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
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}


}

