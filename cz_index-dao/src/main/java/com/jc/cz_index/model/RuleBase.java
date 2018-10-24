package com.jc.cz_index.model;

public class RuleBase extends BaseBean {
    /**
     * 
     */
    private static final long  serialVersionUID = 1L;

    /**
     * 删除
     */
    final static public String status_del       = "1";
    /**
     * 未删除
     */
    final static public String status_not_del   = "2";

    // 主键 [主键]
    private Long               id;



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

    // 简称
    private String         value;
    // 全称
    private String         valueAbbreviation;
    // 状态
    private String         status;
    // 新增时间
    private java.util.Date addTime;
    // 更新时间
    private java.util.Date upTime;



    public void setValue(String value) {
        this.value = value;
    }



    public String getValue() {
        return this.value;
    }



    public java.util.Date getAddTime() {
        return addTime;
    }



    public void setAddTime(java.util.Date addTime) {
        this.addTime = addTime;
    }



    public java.util.Date getUpTime() {
        return upTime;
    }



    public void setUpTime(java.util.Date upTime) {
        this.upTime = upTime;
    }



    public void setValueAbbreviation(String valueAbbreviation) {
        this.valueAbbreviation = valueAbbreviation;
    }



    public String getValueAbbreviation() {
        return this.valueAbbreviation;
    }



    public void setStatus(String status) {
        this.status = status;
    }



    public String getStatus() {
        return this.status;
    }

    // TODO
}
