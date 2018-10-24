package com.jc.cz_index.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.jc.cz_index.common.utils.JaxbDateAdapter;

/**
 * 
 * 描述：基础类
 * 
 * @author yangyongchuan 2016年5月16日 下午3:03:45
 * @version 0.1
 * @since 0.1
 */
public class BaseBean implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    // 创建者id
    private Long              creator;
    // 创建时间
    private Date              createDate;
    // 修改者id
    private Long              updator;
    // 修改时间
    private Date              updateDate;

    // 临时属性
    // 创建者
    private SystemUser        creatorUser;
    // 修改者
    private SystemUser        updatorUser;

    // xml 临时属性
    // 创建人-姓名
    private String            createUser;
    // 最后修改人-名字
    private String            lastModifyUser;



    //@XmlTransient
    public Long getCreator() {
        return creator;
    }



    public void setCreator(Long creator) {
        this.creator = creator;
    }



    @XmlElement(name = "createTime")
    @XmlJavaTypeAdapter(value = JaxbDateAdapter.class)
    public Date getCreateDate() {
        return createDate;
    }



    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }



    //@XmlTransient
    public Long getUpdator() {
        return updator;
    }



    public void setUpdator(Long updator) {
        this.updator = updator;
    }



    @XmlJavaTypeAdapter(value = JaxbDateAdapter.class)
    @XmlElement(name = "lastModifyTime")
    public Date getUpdateDate() {
        return updateDate;
    }



    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }



    @XmlTransient
    public SystemUser getCreatorUser() {
        return creatorUser;
    }



    public void setCreatorUser(SystemUser creatorUser) {
        this.creatorUser = creatorUser;
    }



    @XmlTransient
    public SystemUser getUpdatorUser() {
        return updatorUser;
    }



    public void setUpdatorUser(SystemUser updatorUser) {
        this.updatorUser = updatorUser;
    }



    public String getCreateUser() {
        if (null != creatorUser) {
            return creatorUser.getName();
        }
        return createUser;
    }



    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }



    public String getLastModifyUser() {
        if (null != updatorUser) {
            return updatorUser.getName();
        }
        return lastModifyUser;
    }



    public void setLastModifyUser(String lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
    }

}
