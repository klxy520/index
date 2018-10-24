package com.jc.cz_index.model;

/**
 * 
 * 描述：基本身份信息
 * 
 * @author sunxuefeng 2017年12月22日 下午4:13:52
 * @version 1.0
 */
public class BasicIdentityInfo extends BaseBean {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 主键 [主键]
    private Long id;



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

    // 主索引id
    private String         indexId;
    // 姓名
    private String         name;
    // 性别 1:男 2：女
    private String         sex;
    // 民族
    private String         national;
    // 证件类型：01=居民身份证,02=居民户口簿,03=护照,04=军官证,05=驾驶证,06=港澳居民来往内地通行证,07=台湾居民来往内地通行证,08=出生医学证明,99=其他法定有效证件
    private String         idCardType;
    // 身份证号码
    private String         idCard;
    // 户籍地址
    private String         permanentAddress;
    // 血型
    private String         bloodType;
    // 学历 1-初中 ，2-高中（高中，职高，中专，技校）,3-大专（大学专科），4-大本（大学本科），5-硕士（硕士研究生），6-博士（博士研究生）
    private String         educationLevel;
    // 婚姻状况 1:未婚 2：已婚 3：离异
    private String         maritalStatus;
    // 出身日期 格式（19900523）
    private java.util.Date birthTime;
    // 身份状态 0-正常 1-注销
    private String         identityStatus;
    // 母语
    private String         languageCommunication;
    // 国籍
    private String         nationality;
    // 联系电话
    private String         contactNumber;
    // 出身地
    private String         birthPlace;
    // 头像
    private String         photo;
    // 死亡标记
    private Integer        deceasedInd;
    // 死亡时间
    private java.util.Date deceasedTime;
    // 多胎标记
    private Integer        multipleBirthInd;
    // 多胎顺序
    private String         multipleBirthOrder;
    // 雇员信息
    private String         asEmployee;
    // 当前居住地
    private String         asCitizen;
    // 学生信息
    private String         asStudent;
    // 成员信息
    private String         asMember;
    // 其他标识信息
    private String         asOtheriIds;
    // 监护人信息
    private String         guardian;
    // 联系方式
    private String         contact;
    // 删除标记,(0,未删除1,已删除)
    private Integer        delFalg;
    // 删除人
    private java.lang.Long deleteor;
    // 删除时间
    private java.util.Date deleteDate;



    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }



    public String getIndexId() {
        return this.indexId;
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



    public void setNational(String national) {
        this.national = national;
    }



    public String getNational() {
        return this.national;
    }



    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }



    public String getIdCardType() {
        return this.idCardType;
    }



    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }



    public String getIdCard() {
        return this.idCard;
    }



    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }



    public String getPermanentAddress() {
        return this.permanentAddress;
    }



    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }



    public String getBloodType() {
        return this.bloodType;
    }



    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }



    public String getEducationLevel() {
        return this.educationLevel;
    }



    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }



    public String getMaritalStatus() {
        return this.maritalStatus;
    }



    public void setBirthTime(java.util.Date birthTime) {
        this.birthTime = birthTime;
    }



    public java.util.Date getBirthTime() {
        return this.birthTime;
    }



    public void setIdentityStatus(String identityStatus) {
        this.identityStatus = identityStatus;
    }



    public String getIdentityStatus() {
        return this.identityStatus;
    }



    public void setLanguageCommunication(String languageCommunication) {
        this.languageCommunication = languageCommunication;
    }



    public String getLanguageCommunication() {
        return this.languageCommunication;
    }



    public void setNationality(String nationality) {
        this.nationality = nationality;
    }



    public String getNationality() {
        return this.nationality;
    }



    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }



    public String getContactNumber() {
        return this.contactNumber;
    }



    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }



    public String getBirthPlace() {
        return this.birthPlace;
    }



    public void setPhoto(String photo) {
        this.photo = photo;
    }



    public String getPhoto() {
        return this.photo;
    }



    public void setDeceasedInd(Integer deceasedInd) {
        this.deceasedInd = deceasedInd;
    }



    public Integer getDeceasedInd() {
        return this.deceasedInd;
    }



    public void setDeceasedTime(java.util.Date deceasedTime) {
        this.deceasedTime = deceasedTime;
    }



    public java.util.Date getDeceasedTime() {
        return this.deceasedTime;
    }



    public void setMultipleBirthInd(Integer multipleBirthInd) {
        this.multipleBirthInd = multipleBirthInd;
    }



    public Integer getMultipleBirthInd() {
        return this.multipleBirthInd;
    }



    public void setMultipleBirthOrder(String multipleBirthOrder) {
        this.multipleBirthOrder = multipleBirthOrder;
    }



    public String getMultipleBirthOrder() {
        return this.multipleBirthOrder;
    }



    public void setAsEmployee(String asEmployee) {
        this.asEmployee = asEmployee;
    }



    public String getAsEmployee() {
        return this.asEmployee;
    }



    public void setAsCitizen(String asCitizen) {
        this.asCitizen = asCitizen;
    }



    public String getAsCitizen() {
        return this.asCitizen;
    }



    public void setAsStudent(String asStudent) {
        this.asStudent = asStudent;
    }



    public String getAsStudent() {
        return this.asStudent;
    }



    public void setAsMember(String asMember) {
        this.asMember = asMember;
    }



    public String getAsMember() {
        return this.asMember;
    }



    public void setAsOtheriIds(String asOtheriIds) {
        this.asOtheriIds = asOtheriIds;
    }



    public String getAsOtheriIds() {
        return this.asOtheriIds;
    }



    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }



    public String getGuardian() {
        return this.guardian;
    }



    public void setContact(String contact) {
        this.contact = contact;
    }



    public String getContact() {
        return this.contact;
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
