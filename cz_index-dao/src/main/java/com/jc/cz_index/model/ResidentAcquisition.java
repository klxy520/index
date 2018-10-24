package com.jc.cz_index.model;

/**
 * 
 * 描述：居民信息采集实体
 * 
 * @author sunxuefeng 2017年10月20日 上午10:06:25
 * @version 1.0
 */
public class ResidentAcquisition extends BaseBean {
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

    // 申办单位
    private String         bidUtil;
    // 姓名
    private String         name;
    // 居民身份证号码
    private String         identityNumber;
    // 发证机关
    private String         issuersCertificateOrgan;
    // 证件有效期
    private String         certificateValidityPeriod;
    // 民族
    private String         national;
    // 文化程度
    private String         educationLevel;
    // 户籍地址
    private String         houseAddress;
    // 现住地址
    private String         nowAddress;
    // 邮编
    private String         postCode;
    // 联系电话
    private String         contactPhone;
    // 新农合号
    private String         newRuralNumber;
    // 社保卡号
    private String         socialSecurityNum;
    // 工资卡发放银行
    private String         salaryCardBank;
    // 健康卡发放银行
    private String         healthCardBank;
    // 职业
    private String         professional;
    // 行业
    private String         industry;
    // 删除标记,(0,未删除1,已删除)
    private Integer        delFalg;
    // 删除人
    private java.lang.Long deleteor;
    // 删除时间
    private java.util.Date deleteDate;
    private SystemUser     creatorUser;
    private SystemUser     updatorUser;



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



    public void setBidUtil(String bidUtil) {
        this.bidUtil = bidUtil;
    }



    public String getBidUtil() {
        return this.bidUtil;
    }



    public void setName(String name) {
        this.name = name;
    }



    public String getName() {
        return this.name;
    }



    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }



    public String getIdentityNumber() {
        return this.identityNumber;
    }



    public void setIssuersCertificateOrgan(String issuersCertificateOrgan) {
        this.issuersCertificateOrgan = issuersCertificateOrgan;
    }



    public String getIssuersCertificateOrgan() {
        return this.issuersCertificateOrgan;
    }



    public void setCertificateValidityPeriod(String certificateValidityPeriod) {
        this.certificateValidityPeriod = certificateValidityPeriod;
    }



    public String getCertificateValidityPeriod() {
        return this.certificateValidityPeriod;
    }



    public void setNational(String national) {
        this.national = national;
    }



    public String getNational() {
        return this.national;
    }



    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }



    public String getEducationLevel() {
        return this.educationLevel;
    }



    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }



    public String getHouseAddress() {
        return this.houseAddress;
    }



    public void setNowAddress(String nowAddress) {
        this.nowAddress = nowAddress;
    }



    public String getNowAddress() {
        return this.nowAddress;
    }



    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }



    public String getPostCode() {
        return this.postCode;
    }



    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }



    public String getContactPhone() {
        return this.contactPhone;
    }



    public void setNewRuralNumber(String newRuralNumber) {
        this.newRuralNumber = newRuralNumber;
    }



    public String getNewRuralNumber() {
        return this.newRuralNumber;
    }



    public void setSocialSecurityNum(String socialSecurityNum) {
        this.socialSecurityNum = socialSecurityNum;
    }



    public String getSocialSecurityNum() {
        return this.socialSecurityNum;
    }



    public void setSalaryCardBank(String salaryCardBank) {
        this.salaryCardBank = salaryCardBank;
    }



    public String getSalaryCardBank() {
        return this.salaryCardBank;
    }



    public void setHealthCardBank(String healthCardBank) {
        this.healthCardBank = healthCardBank;
    }



    public String getHealthCardBank() {
        return this.healthCardBank;
    }



    public void setProfessional(String professional) {
        this.professional = professional;
    }



    public String getProfessional() {
        return this.professional;
    }



    public void setIndustry(String industry) {
        this.industry = industry;
    }



    public String getIndustry() {
        return this.industry;
    }



    public void setDelFalg(Integer delFalg) {
        this.delFalg = delFalg;
    }



    public Integer getDelFalg() {
        return this.delFalg;
    }



    public void setDeleteor(java.lang.Long deleteor) {
        this.deleteor = deleteor;
    }



    public java.lang.Long getDeleteor() {
        return this.deleteor;
    }



    public void setDeleteDate(java.util.Date deleteDate) {
        this.deleteDate = deleteDate;
    }



    public java.util.Date getDeleteDate() {
        return this.deleteDate;
    }
}
