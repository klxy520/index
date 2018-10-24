package com.jc.cz_index.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.jc.cz_index.common.utils.JaxbDateAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "certificate")
public class Certificate extends BaseBean {
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
    // 基本身份信息id
    //@XmlTransient
    private java.lang.Long personId;
    // 证件类别代码
    private String         certificateTypeCode;
    // 证件号码
    private String         certificateNo;
    // 删除标记,(0,未删除1,已删除)
    //@XmlTransient
    private Integer        delFalg;
    // 删除人
    //@XmlTransient
    private java.lang.Long deleteor;
    // 删除时间
    @XmlJavaTypeAdapter(value = JaxbDateAdapter.class)
    private java.util.Date deleteDate;

    // 创建地址(对方传的)
    private String         createUnit;
    // 最后修改地址(对方传的)
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



    public void setCertificateTypeCode(String certificateTypeCode) {
        this.certificateTypeCode = certificateTypeCode;
    }



    public String getCertificateTypeCode() {
        return this.certificateTypeCode;
    }



    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }



    public String getCertificateNo() {
        return this.certificateNo;
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



    public String getCreateUnit() {
        return createUnit;
    }



    public void setCreateUnit(String createUnit) {
        this.createUnit = createUnit;
    }



    public String getLastModifyUnit() {
        return lastModifyUnit;
    }



    public void setLastModifyUnit(String lastModifyUnit) {
        this.lastModifyUnit = lastModifyUnit;
    }

}
