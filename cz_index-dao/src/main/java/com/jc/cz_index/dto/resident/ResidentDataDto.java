package com.jc.cz_index.dto.resident;

import java.io.Serializable;

/***
 * 
 * 描述：居民信息dto(包含基本信息和扩展信息)
 * 
 * @author sunxuefeng 2017年9月8日 上午9:59:41
 * @version 1.0
 */
public class ResidentDataDto implements Serializable {
    private static final long serialVersionUID = 1L;
    // 居民姓名
    private String            realName;
    // 居民健康卡号
    private String            healthNumber;
    // 社保卡号
    private String            socialNumber;
    // 身份证号
    private String            idNumber;
    // 证件有效期
    private java.util.Date    periodValidityDate;
    // 性别
    private String            sex;
    // 年龄
    private Integer           age;
    // 民族
    private String            nation;
    // 户籍地址
    private String            houseAddress;
    // 新住地址
    private String            nowAddress;
    // 邮编
    private String            postCode;
    // 联系电话
    private String            phone;
    // 工作单位
    private String            wrokUnit;
    // 学历
    private String            education;
    // 医保类型
    private String            insuranceType;
    // 病种类型
    private String            illnessType;
    // 残疾类型
    private String            disabilityType;
    // 工会特征
    private String            unionFeature;
    // 离休干部
    private String            retiredCadres;
    // 扶贫户
    private String            helpHouse;
    // 低保类型
    private String            lowType;



    public String getRealName() {
        return realName;
    }



    public void setRealName(String realName) {
        this.realName = realName;
    }



    public String getHealthNumber() {
        return healthNumber;
    }



    public void setHealthNumber(String healthNumber) {
        this.healthNumber = healthNumber;
    }



    public String getSocialNumber() {
        return socialNumber;
    }



    public void setSocialNumber(String socialNumber) {
        this.socialNumber = socialNumber;
    }



    public String getIdNumber() {
        return idNumber;
    }



    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }



    public java.util.Date getPeriodValidityDate() {
        return periodValidityDate;
    }



    public void setPeriodValidityDate(java.util.Date periodValidityDate) {
        this.periodValidityDate = periodValidityDate;
    }



    public String getSex() {
        return sex;
    }



    public void setSex(String sex) {
        this.sex = sex;
    }



    public Integer getAge() {
        return age;
    }



    public void setAge(Integer age) {
        this.age = age;
    }



    public String getNation() {
        return nation;
    }



    public void setNation(String nation) {
        this.nation = nation;
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



    public String getPostCode() {
        return postCode;
    }



    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }



    public String getPhone() {
        return phone;
    }



    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getWrokUnit() {
        return wrokUnit;
    }



    public void setWrokUnit(String wrokUnit) {
        this.wrokUnit = wrokUnit;
    }



    public String getEducation() {
        return education;
    }



    public void setEducation(String education) {
        this.education = education;
    }



    public String getInsuranceType() {
        return insuranceType;
    }



    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }



    public String getIllnessType() {
        return illnessType;
    }



    public void setIllnessType(String illnessType) {
        this.illnessType = illnessType;
    }



    public String getDisabilityType() {
        return disabilityType;
    }



    public void setDisabilityType(String disabilityType) {
        this.disabilityType = disabilityType;
    }



    public String getUnionFeature() {
        return unionFeature;
    }



    public void setUnionFeature(String unionFeature) {
        this.unionFeature = unionFeature;
    }



    public String getRetiredCadres() {
        return retiredCadres;
    }



    public void setRetiredCadres(String retiredCadres) {
        this.retiredCadres = retiredCadres;
    }



    public String getHelpHouse() {
        return helpHouse;
    }



    public void setHelpHouse(String helpHouse) {
        this.helpHouse = helpHouse;
    }



    public String getLowType() {
        return lowType;
    }



    public void setLowType(String lowType) {
        this.lowType = lowType;
    }
}
