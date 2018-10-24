/**
 * 2016/9/26 21:27:44 Jack Liu created.
 */

package com.jc.cz_index.model;

/**
 * 2016/09/26 Created by Jack Liu.
 */
public class RoleMenu extends BaseBean {

    // 序列化版本
    private static final long serialVersionUID = -7388747114403521536L;

    // 主键id [主键]
    private Long              id;
    // 角色(权限)id
    private Long              roleId;
    // 菜单id
    private Long              systemMenuId;

    private Role              role;
    private SystemMenu        systemMenu;



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
     * 获取角色(权限)id
     * 
     * @return 角色(权限)id
     */
    public Long getRoleId() {
        return roleId;
    }



    /**
     * 设置角色(权限)id
     * 
     * @param roleId
     *            角色(权限)id
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }



    /**
     * 获取菜单id
     * 
     * @return 菜单id
     */
    public Long getSystemMenuId() {
        return systemMenuId;
    }



    /**
     * 设置菜单id
     * 
     * @param systemMenuId
     *            菜单id
     */
    public void setSystemMenuId(Long systemMenuId) {
        this.systemMenuId = systemMenuId;
    }



    public Role getRole() {
        return role;
    }



    public void setRole(Role role) {
        this.role = role;
    }



    public SystemMenu getSystemMenu() {
        return systemMenu;
    }



    public void setSystemMenu(SystemMenu systemMenu) {
        this.systemMenu = systemMenu;
    }

}
