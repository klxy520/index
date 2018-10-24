package com.jc.cz_index.dto.resident;

import java.io.Serializable;

/***
 * 
 * 描述：扩展信息dto
 * 
 * @author sunxuefeng 2017年9月11日 下午1:38:36
 * @version 1.0
 */
public class ResidentExtendinfoExeclDto implements Serializable {

    private static final long serialVersionUID = 1L;
    // 居民健康卡号
    private String            healthNumber;
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



    public String getHealthNumber() {
        return healthNumber;
    }



    public void setHealthNumber(String healthNumber) {
        this.healthNumber = healthNumber;
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
