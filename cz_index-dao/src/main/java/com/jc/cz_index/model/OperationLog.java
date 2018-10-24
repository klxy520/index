/**
 * 2016/11/1 14:49:14 Jack Liu created.
 */

package com.jc.cz_index.model;

import java.util.Date;

/**
 * 2016/11/01 Created by Jack Liu.
 */
public class OperationLog extends BaseBean {

    // 序列化版本
    private static final long serialVersionUID = -8634646836963782656L;

    // 日志id [主键]
    private Long              id;
    // 用户ID
    private Long              userId;
    // 类型（1：平台管理员；2：商家管理员）
    private Integer           userType;
    // 用户姓名
    private String            userName;
    // 登录账号
    private String            loginName;
    // 表单名称
    private String            formName;
    // 表单记录id
    private String            recordId;
    // 操作类型: 增 , 删 , 改;
    private String            type;
    // 详细操作信息
    private String            detail;
    // 操作时间
    private Date              operationDate;
    // 备注
    private String            remark;



    /**
     * 获取日志id [主键]
     * 
     * @return 日志id
     */
    public Long getId() {
        return id;
    }



    /**
     * 设置日志id [主键]
     * 
     * @param id
     *            日志id
     */
    public void setId(Long id) {
        this.id = id;
    }



    /**
     * 获取用户ID
     * 
     * @return 用户ID
     */
    public Long getUserId() {
        return userId;
    }



    /**
     * 设置用户ID
     * 
     * @param userId
     *            用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }



    /**
     * 获取类型（1：平台管理员；2：商家管理员）
     * 
     * @return 类型（1：平台管理员；2：商家管理员）
     */
    public Integer getUserType() {
        return userType;
    }



    /**
     * 设置类型（1：平台管理员；2：商家管理员）
     * 
     * @param userType
     *            类型（1：平台管理员；2：商家管理员）
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }



    /**
     * 获取用户姓名
     * 
     * @return 用户姓名
     */
    public String getUserName() {
        return userName;
    }



    /**
     * 设置用户姓名
     * 
     * @param userName
     *            用户姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * 获取表单名称
     * 
     * @return 表单名称
     */
    public String getFormName() {
        return formName;
    }



    /**
     * 设置表单名称
     * 
     * @param formName
     *            表单名称
     */
    public void setFormName(String formName) {
        this.formName = formName;
    }



    /**
     * 获取表单记录id
     * 
     * @return 表单记录id
     */
    public String getRecordId() {
        return recordId;
    }



    /**
     * 设置表单记录id
     * 
     * @param recordId
     *            表单记录id
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }



    /**
     * 获取操作类型: 增 , 删 , 改;
     * 
     * @return 操作类型: 增 , 删 , 改;
     */
    public String getType() {
        return type;
    }



    /**
     * 设置操作类型: 增 , 删 , 改;
     * 
     * @param type
     *            操作类型: 增 , 删 , 改;
     */
    public void setType(String type) {
        this.type = type;
    }



    /**
     * 获取详细操作信息
     * 
     * @return 详细操作信息
     */
    public String getDetail() {
        return detail;
    }



    /**
     * 设置详细操作信息
     * 
     * @param detail
     *            详细操作信息
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }



    /**
     * 获取操作时间
     * 
     * @return 操作时间
     */
    public Date getOperationDate() {
        return operationDate;
    }



    /**
     * 设置操作时间
     * 
     * @param operationDate
     *            操作时间
     */
    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }



    /**
     * 获取备注
     * 
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }



    /**
     * 设置备注
     * 
     * @param remark
     *            备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
