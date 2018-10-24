package com.jc.cz_index.model;

/***
 * 
 * 描述：基本信息管理
 * 
 * @author sunxuefeng 2017年9月27日 下午2:08:27
 * @version 1.0
 */
public class ResidentsInfo extends BaseBean {
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
    // 银行卡号
    private String         bankCardNumber;
    // 卡的类别
    private String         cardType;
    // 发卡机构名称
    private String         issuersCardName;
    // 发卡机构代码
    private String         issuersCardCode;
    // 发卡序列号
    private String         issuingSerialNumber;
    // 发卡机构证书
    private String         issuersCardCertificate;
    // 发卡时间
    private String         issuingTime;
    // 姓名
    private String         name;
    // 性别(0,女,1 男)
    private String         sex;
    // 民族代码
    private String         nationalCode;
    // 出生日期
    private String         birthday;
    // 居民身份证号码
    private String         identityNumber;
    // 卡有效期
    private String         cardValidityPeriod;
    // 本人电话
    private String         selfPhone;
    // 医疗支付方式
    private String         medicalPayment;
    // 户籍地址
    private String         houseAddress;
    // 现住地址
    private String         nowAddress;
    // 联系人姓名
    private String         contactName;
    // 联系人关系
    private String         contactRelation;
    // 联系人电话
    private String         contactPhone;
    // 文化程度代码
    private String         educationLevelCode;
    // 婚姻状况代码
    private String         maritalStatusCode;
    // 职业代码
    private String         professionalCode;
    // 社保卡号
    private String         socialSecurityNum;
    // 发卡银行
    private String         issuingBank;
    //人员性质 0：常住人口，1：流动人口
    private Long         isFloating=0L;
    // 卡状态
    private String         cardStatus;
    // 芯片号
    private String         chipNum;
    // 行政机构
    private java.lang.Long officeId;
    // 区域机构
    private java.lang.Long areaId;
    // 成都银行健康卡同步状态:(0:未同步 1:同步成功 2:同步失败)
    private String         cardSyncStatus1;
    // 工商银行健康卡同步状态:(0:未同步 1:同步成功 2:同步失败)
    private String         cardSyncStatus2;
    //
    private String         cardSyncStatus3;
    // 删除标记,(0,未删除1,已删除)
    private Integer        delFalg;
    // 删除人
    private java.lang.Long deleteor;
    // 删除时间
    private java.util.Date deleteDate;
    
    private String areaName;
    private String officeName;
    private String creatorName;
    private String updatorName;
  



    public String getAreaName() {
        return areaName;
    }



    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }



    public String getOfficeName() {
        return officeName;
    }



    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }



    public String getCreatorName() {
        return creatorName;
    }



    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }



    public String getUpdatorName() {
        return updatorName;
    }



    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }



    public void setBidUtil(String bidUtil) {
        this.bidUtil = bidUtil;
    }



    public String getBidUtil() {
        return this.bidUtil;
    }



    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }



    public String getBankCardNumber() {
        return this.bankCardNumber;
    }



    public void setCardType(String cardType) {
        this.cardType = cardType;
    }



    public String getCardType() {
        return this.cardType;
    }



    public void setIssuersCardName(String issuersCardName) {
        this.issuersCardName = issuersCardName;
    }



    public String getIssuersCardName() {
        return this.issuersCardName;
    }



    public void setIssuersCardCode(String issuersCardCode) {
        this.issuersCardCode = issuersCardCode;
    }



    public String getIssuersCardCode() {
        return this.issuersCardCode;
    }



    public void setIssuingSerialNumber(String issuingSerialNumber) {
        this.issuingSerialNumber = issuingSerialNumber;
    }



    public String getIssuingSerialNumber() {
        return this.issuingSerialNumber;
    }



    public void setIssuersCardCertificate(String issuersCardCertificate) {
        this.issuersCardCertificate = issuersCardCertificate;
    }



    public String getIssuersCardCertificate() {
        return this.issuersCardCertificate;
    }



    public void setIssuingTime(String issuingTime) {
        this.issuingTime = issuingTime;
    }



    public String getIssuingTime() {
        return this.issuingTime;
    }



    public void setName(String name) {
        this.name = name;
    }



    public String getName() {
        return this.name;
    }



    public void setSex(String sex) {
        this.sex = sex;
    }



    public String getSex() {
        return this.sex;
    }



    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }



    public String getNationalCode() {
        return this.nationalCode;
    }



    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }



    public String getBirthday() {
        return this.birthday;
    }



    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }



    public String getIdentityNumber() {
        return this.identityNumber;
    }



    public void setCardValidityPeriod(String cardValidityPeriod) {
        this.cardValidityPeriod = cardValidityPeriod;
    }



    public String getCardValidityPeriod() {
        return this.cardValidityPeriod;
    }



    public void setSelfPhone(String selfPhone) {
        this.selfPhone = selfPhone;
    }



    public String getSelfPhone() {
        return this.selfPhone;
    }



    public void setMedicalPayment(String medicalPayment) {
        this.medicalPayment = medicalPayment;
    }



    public String getMedicalPayment() {
        return this.medicalPayment;
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



    public void setContactName(String contactName) {
        this.contactName = contactName;
    }



    public String getContactName() {
        return this.contactName;
    }



    public void setContactRelation(String contactRelation) {
        this.contactRelation = contactRelation;
    }



    public String getContactRelation() {
        return this.contactRelation;
    }



    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }



    public String getContactPhone() {
        return this.contactPhone;
    }



    public void setEducationLevelCode(String educationLevelCode) {
        this.educationLevelCode = educationLevelCode;
    }



    public String getEducationLevelCode() {
        return this.educationLevelCode;
    }



    public void setMaritalStatusCode(String maritalStatusCode) {
        this.maritalStatusCode = maritalStatusCode;
    }



    public String getMaritalStatusCode() {
        return this.maritalStatusCode;
    }



    public void setProfessionalCode(String professionalCode) {
        this.professionalCode = professionalCode;
    }



    public String getProfessionalCode() {
        return this.professionalCode;
    }



    public void setSocialSecurityNum(String socialSecurityNum) {
        this.socialSecurityNum = socialSecurityNum;
    }



    public String getSocialSecurityNum() {
        return this.socialSecurityNum;
    }



    public void setIssuingBank(String issuingBank) {
        this.issuingBank = issuingBank;
    }



    public String getIssuingBank() {
        return this.issuingBank;
    }



    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }



    public String getCardStatus() {
        return this.cardStatus;
    }



    public void setChipNum(String chipNum) {
        this.chipNum = chipNum;
    }



    public String getChipNum() {
        return this.chipNum;
    }



    public void setOfficeId(java.lang.Long officeId) {
        this.officeId = officeId;
    }



    public java.lang.Long getOfficeId() {
        return this.officeId;
    }



    public void setAreaId(java.lang.Long areaId) {
        this.areaId = areaId;
    }



    public java.lang.Long getAreaId() {
        return this.areaId;
    }



    public void setCardSyncStatus1(String cardSyncStatus1) {
        this.cardSyncStatus1 = cardSyncStatus1;
    }



    public String getCardSyncStatus1() {
        return this.cardSyncStatus1;
    }



    public void setCardSyncStatus2(String cardSyncStatus2) {
        this.cardSyncStatus2 = cardSyncStatus2;
    }



    public String getCardSyncStatus2() {
        return this.cardSyncStatus2;
    }



    public void setCardSyncStatus3(String cardSyncStatus3) {
        this.cardSyncStatus3 = cardSyncStatus3;
    }



    public String getCardSyncStatus3() {
        return this.cardSyncStatus3;
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



    public Long getIsFloating() {
        return isFloating;
    }



    public void setIsFloating(Long isFloating) {
        this.isFloating = isFloating;
    }




}
