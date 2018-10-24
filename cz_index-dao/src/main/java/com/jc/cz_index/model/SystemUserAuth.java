/**
 * 2016/9/26 22:40:33 Jack Liu created.
 */

package com.jc.cz_index.model;

import java.util.Date;
import java.util.List;

/**
 * 2016/09/26 Created by Jack Liu.
 */
public class SystemUserAuth extends BaseBean {

    // 序列化版本
    private static final long serialVersionUID = -2339332831250192896L;

    // 主键id [主键]
    private Long              id;
    // 登录账号
    private String            loginName;
    // 密码
    private String            password;
    // 状态，0：启用，1：禁用
    private Integer           status;
    // 创建者
    private Long              creator;
    // 创建时间
    private Date              createDate;
    // 最近登录时间
    private Date              loginDate;
    // 最近修改密码时间
    private Date              updatePassDate;

    // 角色列表
    private List<Role>        roles;



    /**
     * 获取主键id [主键]
     * 
     * @return 主键id
     */
    public Long getId() {
        return id;
    }



    /**
     * 设置主键id [主键]
     * 
     * @param id
     *            主键id
     */
    public void setId(Long id) {
        this.id = id;
    }



    /**
     * 获取登录账号
     * 
     * @return 登录账号
     */
    public String getLoginName() {
        return loginName;
    }



    /**
     * 设置登录账号
     * 
     * @param loginName
     *            登录账号
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }



    /**
     * 获取密码
     * 
     * @return 密码
     */
    public String getPassword() {
        return password;
    }



    /**
     * 设置密码
     * 
     * @param password
     *            密码
     */
    public void setPassword(String password) {
        this.password = password;
    }



    /**
     * 获取状态，0：启用，1：禁用
     * 
     * @return 状态，0：启用，1：禁用
     */
    public Integer getStatus() {
        return status;
    }



    /**
     * 设置状态，0：启用，1：禁用
     * 
     * @param status
     *            状态，0：启用，1：禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }



    /**
     * 获取创建者
     * 
     * @return 创建者
     */
    public Long getCreator() {
        return creator;
    }



    /**
     * 设置创建者
     * 
     * @param creator
     *            创建者
     */
    public void setCreator(Long creator) {
        this.creator = creator;
    }



    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }



    /**
     * 设置创建时间
     * 
     * @param createDate
     *            创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }



    /**
     * 获取最近登录时间
     * 
     * @return 最近登录时间
     */
    public Date getLoginDate() {
        return loginDate;
    }



    /**
     * 设置最近登录时间
     * 
     * @param loginDate
     *            最近登录时间
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }



    /**
     * 获取最近修改密码时间
     * 
     * @return 最近修改密码时间
     */
    public Date getUpdatePassDate() {
        return updatePassDate;
    }



    /**
     * 设置最近修改密码时间
     * 
     * @param updatePassDate
     *            最近修改密码时间
     */
    public void setUpdatePassDate(Date updatePassDate) {
        this.updatePassDate = updatePassDate;
    }



    public List<Role> getRoles() {
        return roles;
    }



    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
