/**
 * 2016/9/26 21:27:43 Jack Liu created.
 */

package com.jc.cz_index.model;

/**
 * 2016/09/26 Created by Jack Liu.
 */
public class Role extends BaseBean {

    // 序列化版本
    private static final long serialVersionUID = -8567601641904377856L;

    // 主键id [主键]
    private Long              id;
    // 角色名
    private String            name;
    // 权限标识
    private String            mark;
    // 角色描述
    private String            description;
    // 状态，0：启用，1：禁用
    private Integer           status;
    // 显示顺序
    private Integer           showIndex;



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
     * 获取角色名
     * 
     * @return 角色名
     */
    public String getName() {
        return name;
    }



    /**
     * 设置角色名
     * 
     * @param name
     *            角色名
     */
    public void setName(String name) {
        this.name = name;
    }



    /**
     * 获取权限标识
     * 
     * @return 权限标识
     */
    public String getMark() {
        return mark;
    }



    /**
     * 设置权限标识
     * 
     * @param mark
     *            权限标识
     */
    public void setMark(String mark) {
        this.mark = mark;
    }



    /**
     * 获取角色描述
     * 
     * @return 角色描述
     */
    public String getDescription() {
        return description;
    }



    /**
     * 设置角色描述
     * 
     * @param description
     *            角色描述
     */
    public void setDescription(String description) {
        this.description = description;
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
     * 获取显示顺序
     * 
     * @return 显示顺序
     */
    public Integer getShowIndex() {
        return showIndex;
    }



    /**
     * 设置显示顺序
     * 
     * @param showIndex
     *            显示顺序
     */
    public void setShowIndex(Integer showIndex) {
        this.showIndex = showIndex;
    }

}
