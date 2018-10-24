package com.jc.cz_index.model;

/***
 * 
 * 描述：居民基本数据实体类
 * 
 * @author sunxuefeng 2017年9月4日 下午5:45:55
 * @version 1.0
 */
public class ResidentBaseinfo extends BaseBean {
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

    // 居民姓名
    private String         realName;
    // 居民健康卡号
    private String         healthNumber;
    // 社保卡号
    private String         socialNumber;
    // 身份证号
    private String         idNumber;
    // 证件有效期
    private java.util.Date periodValidityDate;
    // 性别
    private String         sex;
    // 年龄
    private Integer        age;
    // 民族
    private String         nation;
    // 户籍地址
    private String         houseAddress;
    // 新住地址
    private String         nowAddress;
    // 邮编
    private String         postCode;
    // 联系电话
    private String         phone;
    // 工作单位
    private String         wrokUnit;
    // 学历
    private String         education;
    // 行政机构
    private java.lang.Long officeId;
    // 区域机构
    private java.lang.Long areaId;
    // 删除标记,(0,未删除,1,已删除)
    private Integer        delFalg;
    // 删除人
    private java.lang.Long deleteor;
    // 删除时间
    private java.util.Date deleteDate;



    public void setRealName(String realName) {
        this.realName = realName;
    }



    public String getRealName() {
        return this.realName;
    }



    public void setHealthNumber(String healthNumber) {
        this.healthNumber = healthNumber;
    }



    public String getHealthNumber() {
        return this.healthNumber;
    }



    public void setSocialNumber(String socialNumber) {
        this.socialNumber = socialNumber;
    }



    public String getSocialNumber() {
        return this.socialNumber;
    }



    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }



    public String getIdNumber() {
        return this.idNumber;
    }



    public void setPeriodValidityDate(java.util.Date periodValidityDate) {
        this.periodValidityDate = periodValidityDate;
    }



    public java.util.Date getPeriodValidityDate() {
        return this.periodValidityDate;
    }



    public void setSex(String sex) {
        this.sex = sex;
    }



    public String getSex() {
        return this.sex;
    }



    public void setAge(Integer age) {
        this.age = age;
    }



    public Integer getAge() {
        return this.age;
    }



    public void setNation(String nation) {
        this.nation = nation;
    }



    public String getNation() {
        return this.nation;
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



    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getPhone() {
        return this.phone;
    }



    public void setWrokUnit(String wrokUnit) {
        this.wrokUnit = wrokUnit;
    }



    public String getWrokUnit() {
        return this.wrokUnit;
    }



    public void setEducation(String education) {
        this.education = education;
    }



    public String getEducation() {
        return this.education;
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
