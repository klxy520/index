package com.jc.cz_index.dto.resident;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 描述：基本信息管理dto
 * 
 * @author sunxuefeng 2017年9月27日 下午2:33:47
 * @version 1.0
 */
public class ResidentsInfoDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 主键 [主键]
    private Long              id;
    // 申办单位
    private String            bidUtil;
    // 银行卡号
    private String            bankCardNumber;
    // 卡的类别
    private String            cardType;
    // 发卡机构名称
    private String            issuersCardName;
    // 发卡机构代码
    private String            issuersCardCode;
    // 发卡序列号
    private String            issuingSerialNumber;
    // 发卡机构证书
    private String            issuersCardCertificate;
    // 发卡时间
    private String            issuingTime;
    // 姓名
    private String            name;
    // 性别(0,女,1 男)
    private String            sex;
    // 民族代码
    private String            national;
    // 出生日期
    private String            birthday;
    // 居民身份证号码
    private String            identityNumber;
    // 卡有效期
    private String            cardValidityPeriod;
    // 本人电话
    private String            selfPhone;
    // 医疗支付方式
    private String            medicalPayment;
    // 户籍地址
    private String            houseAddress;
    // 现住地址
    private String            nowAddress;
    // 联系人姓名
    private String            contactName;
    // 联系人关系
    private String            contactRelation;
    // 联系人电话
    private String            contactPhone;
    // 文化程度代码
    private String            educationLevelCode;
    // 婚姻状况代码
    private String            maritalStatusCode;
    // 职业代码
    private String            professionalCode;
    // 社保卡号
    private String            socialSecurityNum;
    //人员性质 0：常住人口，1：流动人口
    private Long         isFloating=0L;
    // 发卡银行
    private String            issuingBank;
    // 卡状态
    private String            cardStatus;
    // 芯片号
    private String            chipNum;
    // 成都银行健康卡同步状态:(0:未同步 1:同步成功 2:同步失败)
    private String            cardSyncStatus1;
    // 工商银行健康卡同步状态:(0:未同步 1:同步成功 2:同步失败)
    private String            cardSyncStatus2;
    //
    private String            cardSyncStatus3;
    // 创建时间
    private Date              createDate;
    // 修改时间
    private Date              updateDate;
    // 行政机构
    private String            office;
    // 区域机构
    private String            area;

    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public String getCardType() {
        return cardType;
    }



    public void setCardType(String cardType) {
        this.cardType = cardType;
    }



    public String getIssuersCardCode() {
        return issuersCardCode;
    }



    public void setIssuersCardCode(String issuersCardCode) {
        this.issuersCardCode = issuersCardCode;
    }



    public String getIssuingSerialNumber() {
        return issuingSerialNumber;
    }



    public void setIssuingSerialNumber(String issuingSerialNumber) {
        this.issuingSerialNumber = issuingSerialNumber;
    }



    public String getIssuersCardCertificate() {
        return issuersCardCertificate;
    }



    public void setIssuersCardCertificate(String issuersCardCertificate) {
        this.issuersCardCertificate = issuersCardCertificate;
    }



    public String getNational() {
        return national;
    }



    public void setNational(String national) {
        this.national = national;
    }



    public String getBirthday() {
        return birthday;
    }



    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }



    public String getCardValidityPeriod() {
        return cardValidityPeriod;
    }



    public void setCardValidityPeriod(String cardValidityPeriod) {
        this.cardValidityPeriod = cardValidityPeriod;
    }



    public String getSelfPhone() {
        return selfPhone;
    }



    public void setSelfPhone(String selfPhone) {
        this.selfPhone = selfPhone;
    }



    public String getMedicalPayment() {
        return medicalPayment;
    }



    public void setMedicalPayment(String medicalPayment) {
        this.medicalPayment = medicalPayment;
    }



    public String getHouseAddress() {
        return houseAddress;
    }



    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }



    public String getNowAddress() {
        return nowAddress;
    }



    public void setNowAddress(String nowAddress) {
        this.nowAddress = nowAddress;
    }



    public String getContactName() {
        return contactName;
    }



    public void setContactName(String contactName) {
        this.contactName = contactName;
    }



    public String getContactRelation() {
        return contactRelation;
    }



    public void setContactRelation(String contactRelation) {
        this.contactRelation = contactRelation;
    }



    public String getContactPhone() {
        return contactPhone;
    }



    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }



    public String getEducationLevelCode() {
        return educationLevelCode;
    }



    public void setEducationLevelCode(String educationLevelCode) {
        this.educationLevelCode = educationLevelCode;
    }



    public String getMaritalStatusCode() {
        return maritalStatusCode;
    }



    public void setMaritalStatusCode(String maritalStatusCode) {
        this.maritalStatusCode = maritalStatusCode;
    }



    public String getProfessionalCode() {
        return professionalCode;
    }



    public void setProfessionalCode(String professionalCode) {
        this.professionalCode = professionalCode;
    }



    public String getIssuingBank() {
        return issuingBank;
    }



    public void setIssuingBank(String issuingBank) {
        this.issuingBank = issuingBank;
    }



    public String getChipNum() {
        return chipNum;
    }



    public void setChipNum(String chipNum) {
        this.chipNum = chipNum;
    }



    public String getCardSyncStatus1() {
        return cardSyncStatus1;
    }



    public void setCardSyncStatus1(String cardSyncStatus1) {
        this.cardSyncStatus1 = cardSyncStatus1;
    }



    public String getCardSyncStatus2() {
        return cardSyncStatus2;
    }



    public void setCardSyncStatus2(String cardSyncStatus2) {
        this.cardSyncStatus2 = cardSyncStatus2;
    }



    public String getCardSyncStatus3() {
        return cardSyncStatus3;
    }



    public void setCardSyncStatus3(String cardSyncStatus3) {
        this.cardSyncStatus3 = cardSyncStatus3;
    }



    public String getOffice() {
        return office;
    }



    public void setOffice(String office) {
        this.office = office;
    }



    public String getArea() {
        return area;
    }



    public void setArea(String area) {
        this.area = area;
    }



    public String getBidUtil() {
        return bidUtil;
    }



    public void setBidUtil(String bidUtil) {
        this.bidUtil = bidUtil;
    }



    public String getBankCardNumber() {
        return bankCardNumber;
    }



    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }



    public String getIssuersCardName() {
        return issuersCardName;
    }



    public void setIssuersCardName(String issuersCardName) {
        this.issuersCardName = issuersCardName;
    }



    public String getIssuingTime() {
        return issuingTime;
    }



    public void setIssuingTime(String issuingTime) {
        this.issuingTime = issuingTime;
    }



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public String getSex() {
        return sex;
    }



    public void setSex(String sex) {
        this.sex = sex;
    }



    public String getIdentityNumber() {
        return identityNumber;
    }



    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }



    public String getSocialSecurityNum() {
        return socialSecurityNum;
    }



    public void setSocialSecurityNum(String socialSecurityNum) {
        this.socialSecurityNum = socialSecurityNum;
    }



    public String getCardStatus() {
        return cardStatus;
    }



    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }



    public Date getCreateDate() {
        return createDate;
    }



    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }



    public Date getUpdateDate() {
        return updateDate;
    }



    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }



    public Long getIsFloating() {
        return isFloating;
    }



    public void setIsFloating(Long isFloating) {
        this.isFloating = isFloating;
    }



  

}
