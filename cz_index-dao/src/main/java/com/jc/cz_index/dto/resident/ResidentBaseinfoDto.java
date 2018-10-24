package com.jc.cz_index.dto.resident;

import java.io.Serializable;

/**
 * 
 * 描述：居民健康卡基本信息dto
 * 
 * @author sunxuefeng 2017年8月30日 上午9:32:48
 * @version 1.0
 */
public class ResidentBaseinfoDto implements Serializable {
    private static final long serialVersionUID = 1L;
    // 主键 [主键]
    private Long              id;
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
    // 行政机构
    private String            office;
    // 区域机构
    private String            area;
    // 创建人
    private String            creator;
    // 创建时间
    private java.util.Date    createDate;
    // 修改时间
    private java.util.Date    updateDate;



    public java.util.Date getUpdateDate() {
        return updateDate;
    }



    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
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



    public String getCreator() {
        return creator;
    }



    public void setCreator(String creator) {
        this.creator = creator;
    }



    public java.util.Date getCreateDate() {
        return createDate;
    }



    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

}
