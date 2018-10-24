package com.jc.cz_index.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "addr")
public class Address extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 主键 [主键]
	//@XmlTransient
	private Long              id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
    //基本身份信息id
	//@XmlTransient
    private java.lang.Long personId;
    //主索引id
    private String mpiId;
    //地址类别代码
    private String addressTypeCode;
    //详细地址
    private String address;
    //邮编
    private String postalCode;
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
    public void setPersonId(java.lang.Long personId)
    {
        this.personId=personId;
    }
    public java.lang.Long getPersonId()
    {
        return this.personId;
    }
    public void setMpiId(String mpiId)
    {
        this.mpiId=mpiId;
    }
    public String getMpiId()
    {
        return this.mpiId;
    }
    public void setAddressTypeCode(String addressTypeCode)
    {
        this.addressTypeCode=addressTypeCode;
    }
    public String getAddressTypeCode()
    {
        return this.addressTypeCode;
    }
    public void setAddress(String address)
    {
        this.address=address;
    }
    public String getAddress()
    {
        return this.address;
    }
    public void setPostalCode(String postalCode)
    {
        this.postalCode=postalCode;
    }
    public String getPostalCode()
    {
        return this.postalCode;
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

