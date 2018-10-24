/**
 * 2016/9/26 21:27:46 Jack Liu created.
 */

package com.jc.cz_index.model;

/**
 * 2016/09/26 Created by Jack Liu.
 */
public class SystemUser extends BaseBean {

    // 序列化版本
    private static final long serialVersionUID = -6874984207970547712L;

    // 主键id [主键]
    private Long              id;
    // 用户认证ID
    private Long              authId;
    // 真实姓名
    private String            name;
    // 创建者
    private Long              creator;
    // 联系电话
    private String            phone;
    // 电子邮箱
    private String            email;
    // 类型（1：平台管理员；2：商家管理员）
    private Integer           userType;
    // 行政机构ID
    private Long              organizationId=0L;
    // 区域机构id
    private Long              areaId=0L;
    // 员工编号
    private String            sn;
    /**
     * 用户认证
     */
    private SystemUserAuth    systemUserAuth;
    
    //机构区域
    private AdministrativeDivision      administrativeDivision;

    //行政机构
    private AdministrativeManagement    administrativeManagement;

    public AdministrativeDivision getAdministrativeDivision() {
        return administrativeDivision;
    }



    public void setAdministrativeDivision(AdministrativeDivision administrativeDivision) {
        this.administrativeDivision = administrativeDivision;
    }



    public AdministrativeManagement getAdministrativeManagement() {
        return administrativeManagement;
    }



    public void setAdministrativeManagement(AdministrativeManagement administrativeManagement) {
        this.administrativeManagement = administrativeManagement;
    }



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
     * 获取用户认证ID
     * 
     * @return 用户认证ID
     */
    public Long getAuthId() {
        return authId;
    }



    /**
     * 设置用户认证ID
     * 
     * @param authId
     *            用户认证ID
     */
    public void setAuthId(Long authId) {
        this.authId = authId;
    }



    public Long getAreaId() {
        return areaId;
    }



    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }



    /**
     * 获取真实姓名
     * 
     * @return 真实姓名
     */
    public String getName() {
        return name;
    }



    /**
     * 设置真实姓名
     * 
     * @param name
     *            真实姓名
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取联系电话
     * 
     * @return 联系电话
     */
    public String getPhone() {
        return phone;
    }



    /**
     * 设置联系电话
     * 
     * @param phone
     *            联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }



    /**
     * 获取电子邮箱
     * 
     * @return 电子邮箱
     */
    public String getEmail() {
        return email;
    }



    /**
     * 设置电子邮箱
     * 
     * @param email
     *            电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
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



    public SystemUserAuth getSystemUserAuth() {
        return systemUserAuth;
    }



    public void setSystemUserAuth(SystemUserAuth systemUserAuth) {
        this.systemUserAuth = systemUserAuth;
    }



    public Long getOrganizationId() {
        return organizationId;
    }



    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }



    public String getSn() {
        return sn;
    }



    public void setSn(String sn) {
        this.sn = sn;
    }

}
