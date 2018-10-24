/**
 * 2016/9/26 21:27:47 Jack Liu created.
 */

package com.jc.cz_index.model;

/**
 * 2016/09/26 Created by Jack Liu.
 */
public class SystemUserRole extends BaseBean {

    // 序列化版本
    private static final long serialVersionUID = -57172821861306584L;

    // 主键id [主键]
    private Long              id;
    // 用户认证ID
    private Long              authId;
    // 角色ID
    private Long              roleId;

    private SystemUserAuth    systemUserAuth;
    private Role              role;



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



    /**
     * 获取角色ID
     * 
     * @return 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }



    /**
     * 设置角色ID
     * 
     * @param roleId
     *            角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }



    public SystemUserAuth getSystemUserAuth() {
        return systemUserAuth;
    }



    public void setSystemUserAuth(SystemUserAuth systemUserAuth) {
        this.systemUserAuth = systemUserAuth;
    }



    public Role getRole() {
        return role;
    }



    public void setRole(Role role) {
        this.role = role;
    }

}
