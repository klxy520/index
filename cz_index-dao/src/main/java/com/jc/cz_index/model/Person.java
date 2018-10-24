package com.jc.cz_index.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.jc.cz_index.common.utils.JaxbDateAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "person")
public class Person extends BaseBean {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 主键 [主键]
//    @XmlTransient
    private Long              id;



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

    // 主索引id
    private String         mpiId;
    // 出生日期
    @XmlJavaTypeAdapter(value = JaxbDateAdapter.class)
    private java.util.Date birthday;
    // 血型代码
    private String         bloodTypeCode;
    // 文化程度代码
    private String         educationCode;
    // 户籍地址
    private String         address;
    // 保险类别
    private String         insuranceCode;
    // 婚姻状况代码
    private String         maritalStatusCode;
    // 姓名
    private String         personName;
    // 身份证
    private String         idCard;
    // 一卡通号码
    private String         cardNo;
    // 国籍代码
    private String         nationalityCode;
    // 民族代码
    private String         nationCode;
    // 户籍标志
    private String         registeredPermanent;
    // 死亡标记0:表示未死亡1:表示已死亡
    private Integer        deceasedInd;
    // 死亡时间
    private java.util.Date deceasedTime;
    // RH血型代码
    private String         rhBloodCode;
    // 性别代码
    private String         sexCode;
    // 开始工作日期
    @XmlJavaTypeAdapter(value = JaxbDateAdapter.class)
    private java.util.Date startWorkDate;
    // 工作类别代码
    private String         workCode;
    // 工作单位
    private String         workPlace;
    // 医保类类别
    private String         insuranceType;
    // 本人电话
    private String         contactNo;
    // 状态 0-正常 1-注销
    private String         status;
    // 删除标记,(0,未删除1,已删除)
    private Integer        delFalg;
    // 删除人
    private java.lang.Long deleteor;
    // 删除时间
    @XmlJavaTypeAdapter(value = JaxbDateAdapter.class)
    private java.util.Date deleteDate;
    
    // 更新数据
    private String         updateData;
    // 当前数据
    private String         currentData;



    public void setUpdateData(String updateData) {
        this.updateData = updateData;
    }



    public void setCurrentData(String currentData) {
        this.currentData = currentData;
    }



    public String getUpdateData() {
        return updateData;
    }



    public String getCurrentData() {
        return currentData;
    }

    // 证件
    @XmlElementWrapper(name = "certificates")
    @XmlElement(name = "certificate")
    private List<Certificate> certificates;

    // 地址
    @XmlElementWrapper(name = "addresses")
    @XmlElement(name = "addr")
    private List<Address>     addresses;

    // 联系人
    @XmlElementWrapper(name = "contacts")
    @XmlElement(name = "contact")
    private List<Contact>     contacts;

    // 联系方式
    @XmlElementWrapper(name = "contactWays")
    @XmlElement(name = "contactWay")
    private List<ContactWay>  contactWays;

    // 卡
    @XmlElementWrapper(name = "cards")
    @XmlElement(name = "card")
    private List<Card>        cards;


    public List<Card> getCards() {
        return cards;
    }



    public void setCards(List<Card> cards) {
        this.cards = cards;
    }



    public List<ContactWay> getContactWays() {
        return contactWays;
    }



    public void setContactWays(List<ContactWay> contactWays) {
        this.contactWays = contactWays;
    }



    public List<Contact> getContacts() {
        return contacts;
    }



    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }



    public List<Address> getAddresses() {
        return addresses;
    }



    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }



    public List<Certificate> getCertificates() {
        return certificates;
    }



    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }



    public void setMpiId(String mpiId) {
        this.mpiId = mpiId;
    }



    public String getMpiId() {
        return this.mpiId;
    }



    public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
    }



    public java.util.Date getBirthday() {
        return this.birthday;
    }



    public void setBloodTypeCode(String bloodTypeCode) {
        this.bloodTypeCode = bloodTypeCode;
    }



    public String getBloodTypeCode() {
        return this.bloodTypeCode;
    }



    public void setEducationCode(String educationCode) {
        this.educationCode = educationCode;
    }



    public String getEducationCode() {
        return this.educationCode;
    }



    public void setAddress(String address) {
        this.address = address;
    }



    public String getAddress() {
        return this.address;
    }



    public void setInsuranceCode(String insuranceCode) {
        this.insuranceCode = insuranceCode;
    }



    public String getInsuranceCode() {
        return this.insuranceCode;
    }



    public void setMaritalStatusCode(String maritalStatusCode) {
        this.maritalStatusCode = maritalStatusCode;
    }



    public String getMaritalStatusCode() {
        return this.maritalStatusCode;
    }



    public void setPersonName(String personName) {
        this.personName = personName;
    }



    public String getPersonName() {
        return this.personName;
    }



    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }



    public String getIdCard() {
        return this.idCard;
    }



    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }



    public String getCardNo() {
        return this.cardNo;
    }



    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }



    public String getNationalityCode() {
        return this.nationalityCode;
    }



    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }



    public String getNationCode() {
        return this.nationCode;
    }



    public void setRegisteredPermanent(String registeredPermanent) {
        this.registeredPermanent = registeredPermanent;
    }



    public String getRegisteredPermanent() {
        return this.registeredPermanent;
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



    public void setRhBloodCode(String rhBloodCode) {
        this.rhBloodCode = rhBloodCode;
    }



    public String getRhBloodCode() {
        return this.rhBloodCode;
    }



    public void setSexCode(String sexCode) {
        this.sexCode = sexCode;
    }



    public String getSexCode() {
        return this.sexCode;
    }



    public void setStartWorkDate(java.util.Date startWorkDate) {
        this.startWorkDate = startWorkDate;
    }



    public java.util.Date getStartWorkDate() {
        return this.startWorkDate;
    }



    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }



    public String getWorkCode() {
        return this.workCode;
    }



    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }



    public String getWorkPlace() {
        return this.workPlace;
    }



    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }



    public String getInsuranceType() {
        return this.insuranceType;
    }



    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }



    public String getContactNo() {
        return this.contactNo;
    }



    public void setStatus(String status) {
        this.status = status;
    }



    public String getStatus() {
        return this.status;
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



    public Person() {
        super();
    }
    
    @Override  
    public boolean equals(Object person) {  
        Person p = (Person) person;  
        return id.equals(p.id) && mpiId.equals(p.mpiId);  
    }  
      
    @Override  
    public int hashCode() {  
        String str = id + mpiId;  
        return str.hashCode();  
    }  

}
