package com.jc.cz_index.model;

public class AdministrativeManagement extends BaseBean 
{
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
	
    //行政名称
    private String administrativeName;
    //地址
    private String address;
    //电话
    private String phone;
    //邮编
    private String zipCode;
    //负责人
    private String personCharge;
    //创建者
    private java.lang.Long creator;
    //创建时间
    private java.util.Date createDate;
    //修改时间
    private java.util.Date updateDate;
    //备注
    private String remark;
    //是否删除（0：未删除；1：已删除；）
    private Integer isDelete;
    public void setAdministrativeName(String administrativeName)
    {
        this.administrativeName=administrativeName;
    }
    public String getAdministrativeName()
    {
        return this.administrativeName;
    }
    public void setAddress(String address)
    {
        this.address=address;
    }
    public String getAddress()
    {
        return this.address;
    }
    public void setPhone(String phone)
    {
        this.phone=phone;
    }
    public String getPhone()
    {
        return this.phone;
    }
    public void setZipCode(String zipCode)
    {
        this.zipCode=zipCode;
    }
    public String getZipCode()
    {
        return this.zipCode;
    }
    public void setPersonCharge(String personCharge)
    {
        this.personCharge=personCharge;
    }
    public String getPersonCharge()
    {
        return this.personCharge;
    }
    public void setCreator(java.lang.Long creator)
    {
        this.creator=creator;
    }
    public java.lang.Long getCreator()
    {
        return this.creator;
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
    public void setIsDelete(Integer isDelete)
    {
        this.isDelete=isDelete;
    }
    public Integer getIsDelete()
    {
        return this.isDelete;
    }

}

