package com.jc.cz_index.model;

public class ResidentExtendinfo extends BaseBean 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  final String describe = "序列号,字段描述,扩展表ID,居民基本表BaseId,居民姓名,身份证号码,扩展表身份证号,医保类型,病种类型,残疾类型,工会特征,离休干部,扶贫户,低保户,民政性质,残联性质,,,,,,";
	
	// 主键 [主键]
	private Long              id;
	//居民基本信息表id
    private java.lang.Long baseId;
	//居民姓名
    private String realName;
    //s身份证（基本表身份证）
	 private String idNumber;
    //扩展表身份证号
    private String healthNumber;
    //医保类型
    private String insuranceType="无";
    //病种类型
    private String illnessType="无";
    //残疾类型
    private String disabilityType="无";
    //工会特征
    private String unionFeature="无";
    //离休干部
    private String retiredCadres="无";
    //扶贫户
    private String helpHouse="无";
    //低保类型
    private String lowType="无"; 
    //民政性质
   private String isCivilAffairs="无";
   //残联性质
   private String  isDisableFederation="无";
    
    
    public String getIsCivilAffairs() {
    return isCivilAffairs;
}
public void setIsCivilAffairs(String isCivilAffairs) {
    this.isCivilAffairs = isCivilAffairs;
}
public String getIsDisableFederation() {
    return isDisableFederation;
}
public void setIsDisableFederation(String isDisableFederation) {
    this.isDisableFederation = isDisableFederation;
}
    //删除标记,(0,未删除,1,已删除)
    private Integer delFalg=0;
  
    //删除人
    private java.lang.Long deleteor;
    //删除时间
    private java.util.Date deleteDate;
    public void setBaseId(java.lang.Long baseId)
    {
        this.baseId=baseId;
    }
    public java.lang.Long getBaseId()
    {
        return this.baseId;
    }
    public void setHealthNumber(String healthNumber)
    {
        this.healthNumber=healthNumber;
    }
    public String getHealthNumber()
    {
        return this.healthNumber;
    }
    public void setInsuranceType(String insuranceType)
    {
        this.insuranceType=insuranceType;
    }
    public String getInsuranceType()
    {
        return this.insuranceType;
    }
    public void setIllnessType(String illnessType)
    {
        this.illnessType=illnessType;
    }
    public String getIllnessType()
    {
        return this.illnessType;
    }
    public void setDisabilityType(String disabilityType)
    {
        this.disabilityType=disabilityType;
    }
    public String getDisabilityType()
    {
        return this.disabilityType;
    }
    public void setUnionFeature(String unionFeature)
    {
        this.unionFeature=unionFeature;
    }
    public String getUnionFeature()
    {
        return this.unionFeature;
    }
    public void setRetiredCadres(String retiredCadres)
    {
        this.retiredCadres=retiredCadres;
    }
    public String getRetiredCadres()
    {
        return this.retiredCadres;
    }
    public void setHelpHouse(String helpHouse)
    {
        this.helpHouse=helpHouse;
    }
    public String getHelpHouse()
    {
        return this.helpHouse;
    }
    public void setLowType(String lowType)
    {
        this.lowType=lowType;
    }
    public String getLowType()
    {
        return this.lowType;
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
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
     public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getIdNumber() {
        return idNumber;
    }
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    public  String getDescribe() {
        return describe;
    }

}

