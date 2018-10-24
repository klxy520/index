package com.jc.cz_index.dto.syncLog;

import java.io.Serializable;
/**
 * 
 * 描述：地址信息日志dto
 * @author sunxuefeng 2018年1月4日 下午9:07:00 
 * @version 1.0
 */
public class AddressSyncLogDto implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer personId;    // 基本身份信息id
    private String personName;  // 基本身份信息姓名
    private String addressTypeCode; ////地址类别
    private Integer        addressSyncLogId;       // 地址同步日志id
    private Integer       addressId;              // 地址id
    private Integer        frontEndMachinId;
    private String         frontEndMachinecode;    // 前置机编码
    private Integer        frontEndMachinestate;   // 前置机状态，0：启用，1：禁用,3: 异常
    private String         frontEndMachineaddress; // 前置机地址
    private String         syncStatus;             // 0:未同步;1:已同步
    private java.util.Date createDate;             // 最后更新时间
    private String         remark;                 // 备注
    private java.util.Date updateDate;             // 创建时间
    public String getAddressTypeCode() {
        return addressTypeCode;
    }
    public void setAddressTypeCode(String addressTypeCode) {
        this.addressTypeCode = addressTypeCode;
    }
    public Integer getAddressSyncLogId() {
        return addressSyncLogId;
    }
    public void setAddressSyncLogId(Integer addressSyncLogId) {
        this.addressSyncLogId = addressSyncLogId;
    }
    
    public Integer getAddressId() {
        return addressId;
    }
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    public Integer getFrontEndMachinId() {
        return frontEndMachinId;
    }
    public void setFrontEndMachinId(Integer frontEndMachinId) {
        this.frontEndMachinId = frontEndMachinId;
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public java.util.Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }
    public Integer getPersonId() {
        return personId;
    }
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    
}
