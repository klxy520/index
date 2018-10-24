/**
 * 2016/9/26 21:27:44 Jack Liu created.
 */

package com.jc.cz_index.model;

/**
 * 2016/09/26 Created by Jack Liu.
 */
public class RoleField extends BaseBean {

    // 序列化版本
    private static final long serialVersionUID = -7388747114403521536L;

    // 主键id [主键]
    private Long              id;
    // 角色(权限)id
    private Long              roleId;
    // 菜单id
    private Long              fieldId;

    private TableField       field;
    private Role              role;



/*    `id` bigint(20) NOT NULL COMMENT '主键id',
    `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
    `field_id` bigint(20) DEFAULT NULL COMMENT '字段ID',
*/
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



    public Long getFieldId() {
        return fieldId;
    }



    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }



    public TableField getField() {
        return field;
    }



    public void setField(TableField field) {
        this.field = field;
    }



    public Role getRole() {
        return role;
    }



    public void setRole(Role role) {
        this.role = role;
    }



 




}
