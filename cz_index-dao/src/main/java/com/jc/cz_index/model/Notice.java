package com.jc.cz_index.model;

import java.util.Date;

public class Notice extends BaseBean{
    /**
     * 
     * 描述：公告
     * 
     * @author yangjunhui 2017年8月28日 下午1:03:45
     * @version 0.1
     * @since 0.1
     */
    private static final long serialVersionUID = -6676914469048766521L;
    // 主键id [主键]
    private Long            id;
    // 公告标题
    private String          title;
    // 内容
    private String          content;
    // 公告开始时间
    private Date            startTime;
    // 公告结束时间
    private Date            endTime;
    // 公告状态  0：禁用，1：启用，
    private Long            status;
    //创建人名称
    private String          creatorName;
    //创建人名称
    private SystemUser      updatorUser;
    //创建时间
    private Date            createDate;
    //公告更新时间
    private Date            updateDate;
    
    private SystemUser      creatorUser;
    
 
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
   
    public Long getStatus() {
        return status;
    }
    public void setStatus(Long status) {
        this.status = status;
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public String getCreatorName() {
        return creatorName;
    }
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
    public SystemUser getUpdatorUser() {
        return updatorUser;
    }
    public void setUpdatorUser(SystemUser updatorUser) {
        this.updatorUser = updatorUser;
    }
    public SystemUser getCreatorUser() {
        return creatorUser;
    }
    public void setCreatorUser(SystemUser creatorUser) {
        this.creatorUser = creatorUser;
    }
   
  
}
