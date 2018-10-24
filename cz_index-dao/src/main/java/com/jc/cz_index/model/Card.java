package com.jc.cz_index.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.jc.cz_index.common.utils.JaxbDateAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "card")
public class Card extends BaseBean {
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

    // 主索引id
    private String         mpiId;
    // 基本身份信息id
    private java.lang.Long personId;
    // 卡类别代码
    private String         cardTypeCode;
    // 卡号
    private String         cardNo;
    // 卡内号
    private String         cardCode;
    // 发卡时间
    @XmlJavaTypeAdapter(value = JaxbDateAdapter.class)
    private java.util.Date createTime;
    // 发卡机构
    private String         createUnit;
    // 发卡人
//    private String         createUser;
    // 卡有效期
    @XmlJavaTypeAdapter(value = JaxbDateAdapter.class)
    private java.util.Date validTime;
    // 卡状态:0:正常1:挂失2:注销3:失效
    private Integer        status;
    // 删除标记,(0,未删除1,已删除)
    private Integer        delFalg;
    // 删除人
    private java.lang.Long deleteor;
    // 删除时间
    private java.util.Date deleteDate;

    // 最后修改机构
    private String         lastModifyUnit;



    public void setMpiId(String mpiId) {
        this.mpiId = mpiId;
    }



    public String getMpiId() {
        return this.mpiId;
    }



    public void setPersonId(java.lang.Long personId) {
        this.personId = personId;
    }



    public java.lang.Long getPersonId() {
        return this.personId;
    }



    public void setCardTypeCode(String cardTypeCode) {
        this.cardTypeCode = cardTypeCode;
    }



    public String getCardTypeCode() {
        return this.cardTypeCode;
    }



    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }



    public String getCardNo() {
        return this.cardNo;
    }



    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }



    public String getCardCode() {
        return this.cardCode;
    }



    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }



    public java.util.Date getCreateTime() {
        return this.createTime;
    }



    public void setCreateUnit(String createUnit) {
        this.createUnit = createUnit;
    }



    public String getCreateUnit() {
        return this.createUnit;
    }



//    public void setCreateUser(String createUser) {
//        this.createUser = createUser;
//    }
//
//
//
//    public String getCreateUser() {
//        return this.createUser;
//    }



    public void setValidTime(java.util.Date validTime) {
        this.validTime = validTime;
    }



    public java.util.Date getValidTime() {
        return this.validTime;
    }



    public void setStatus(Integer status) {
        this.status = status;
    }



    public Integer getStatus() {
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



    public String getLastModifyUnit() {
        return lastModifyUnit;
    }



    public void setLastModifyUnit(String lastModifyUnit) {
        this.lastModifyUnit = lastModifyUnit;
    }

}
