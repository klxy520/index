package com.jc.cz_index.model;

import java.util.Date;

/**
 * 
 * 描述：
 * @author yangjunhui  2017年8月30日 上午11:04:49 
 * @version 1.0
 */
public class InfoManagementLog extends BaseBean {
    /**
     * 卡务信息管理日志
     */
    private static final long serialVersionUID = 684261318749154261L;
    // 日志ID
    private Long              id;
    // 创建人姓名
    private String            creatorName;
//    // 创建者ID
//    private Long              creator;
    // 日志产生时间
    private Date              createDate;
    // 日志类型0：修改；1增加；-1删除
    private int            type;
    // 操作表名称
    private String            formName;
    // 操作数据表中对应的数据ID
    private Long            recordId;
    // 详情
    private String            details;
    // 备注
    private String            remark;



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public String getCreatorName() {
        return creatorName;
    }



    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }





    public Date getCreateDate() {
        return createDate;
    }



    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }



    public int getType() {
        return type;
    }



    public void setType(int type) {
        this.type = type;
    }



    public String getFormName() {
        return formName;
    }



    public void setFormName(String formName) {
        this.formName = formName;
    }



    public String getDetails() {
        return details;
    }



    public void setDetails(String details) {
        this.details = details;
    }



    public String getRemark() {
        return remark;
    }



    public void setRemark(String remark) {
        this.remark = remark;
    }



    public Long getRecordId() {
        return recordId;
    }



    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }




}
