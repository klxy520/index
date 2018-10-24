package com.jc.cz_index.dto.mainIndex;

import java.io.Serializable;

import com.jc.cz_index.model.FrontEndMachine;
import com.jc.cz_index.model.Person;

/**
 * 
 * 描述：基本身份信息同步日志 Dto
 * 
 * @author sunxuefeng 2018年1月4日 下午2:31:41
 * @version 1.0
 */
public class PersonSyncLogDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long personId;    // 基本身份信息id
    private Long personLogId; // 基本身份信息同步日志id
    private String personName;  // 基本身份信息姓名
    private Long frontEndMachinId;

    private String frontEndMachinecode;// 前置机编码
    // 身份证
    private String         idCard;

    private Integer        frontEndMachinestate;   // 前置机状态，0：启用，1：禁用,3: 异常
    private String         frontEndMachineaddress; // 前置机地址
    private String         syncStatus;             // 0:未同步;1:已同步
    private java.util.Date createDate;             // 最后更新时间
    private String remark;//备注
    private java.util.Date updateDate; // 创建时间

    private Person person;
    private FrontEndMachine frontEndMachine;


    public Person getPerson() {
        return person;
    }
    
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public FrontEndMachine getFrontEndMachine() {
        return frontEndMachine;
    }

    public void setFrontEndMachine(FrontEndMachine frontEndMachine) {
        this.frontEndMachine = frontEndMachine;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getFrontEndMachinecode() {
        return frontEndMachinecode;
    }

    public void setFrontEndMachinecode(String frontEndMachinecode) {
        this.frontEndMachinecode = frontEndMachinecode;
    }

    public Integer getFrontEndMachinestate() {
        return frontEndMachinestate;
    }

    public void setFrontEndMachinestate(Integer frontEndMachinestate) {
        this.frontEndMachinestate = frontEndMachinestate;
    }

    public String getFrontEndMachineaddress() {
        return frontEndMachineaddress;
    }

    public void setFrontEndMachineaddress(String frontEndMachineaddress) {
        this.frontEndMachineaddress = frontEndMachineaddress;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    public java.util.Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getPersonLogId() {
        return personLogId;
    }

    public void setPersonLogId(Long personLogId) {
        this.personLogId = personLogId;
    }

    public Long getFrontEndMachinId() {
        return frontEndMachinId;
    }

    public void setFrontEndMachinId(Long frontEndMachinId) {
        this.frontEndMachinId = frontEndMachinId;
    }

}
