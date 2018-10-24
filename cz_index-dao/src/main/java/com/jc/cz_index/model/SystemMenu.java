/**
 * 2016/9/26 21:31:47 Jack Liu created.
 */

package com.jc.cz_index.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jc.cz_index.common.bean.BaseType;

/**
 * 2016/09/26 Created by Jack Liu.
 */
public class SystemMenu extends BaseBean {

    // 序列化版本
    private static final long serialVersionUID = -3194679139738287104L;

    // 菜单id [主键]
    private Long              id;
    // 父级id
    private Long              parentId;
    // 编号
    private String            sn;
    // 菜单名称
    private String            name;
    // 菜单描述
    private String            description;
    // 菜单url链接，支持spring antpathmatcher表达式
    private String            targetUrl;
    // 菜单图标路径
    private String            iconUrl;
    // 显示顺序
    private Integer           showIndex;
    // 类型，0：菜单，1：按钮
    private Integer           type;
    // HTTP方法(用于权限控制)，0：GET，1：POST，2：PUT，3：DELETE，...
    private Integer           method;
    // 状态，0：启用，1：禁用
    private Integer           status;
    // 创建者
    private Long              creator;
    // 创建者
    private Long              updator;
    // 创建时间
    private Date              createDate;
    // 最后更新时间
    private Date              updateDate;
    // 是否默认展开，0：否，1：是
    private Integer           defaultOpen;

    // 关联属性
    // 子菜单
    private List<SystemMenu>  subMenuList      = new ArrayList<SystemMenu>();



    /**
     * 获取菜单id [主键]
     * 
     * @return 菜单id
     */
    public Long getId() {
        return id;
    }



    /**
     * 设置菜单id [主键]
     * 
     * @param id
     *            菜单id
     */
    public void setId(Long id) {
        this.id = id;
    }



    /**
     * 获取父级id
     * 
     * @return 父级id
     */
    public Long getParentId() {
        return parentId;
    }



    /**
     * 设置父级id
     * 
     * @param parentId
     *            父级id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }



    /**
     * 获取编号
     * 
     * @return 编号
     */
    public String getSn() {
        return sn;
    }



    /**
     * 设置编号
     * 
     * @param sn
     *            编号
     */
    public void setSn(String sn) {
        this.sn = sn;
    }



    /**
     * 获取菜单名称
     * 
     * @return 菜单名称
     */
    public String getName() {
        return name;
    }



    /**
     * 设置菜单名称
     * 
     * @param name
     *            菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }



    /**
     * 获取菜单描述
     * 
     * @return 菜单描述
     */
    public String getDescription() {
        return description;
    }



    /**
     * 设置菜单描述
     * 
     * @param description
     *            菜单描述
     */
    public void setDescription(String description) {
        this.description = description;
    }



    /**
     * 获取菜单url链接，支持spring antpathmatcher表达式
     * 
     * @return 菜单url链接，支持spring antpathmatcher表达式
     */
    public String getTargetUrl() {
        return targetUrl;
    }



    /**
     * 设置菜单url链接，支持spring antpathmatcher表达式
     * 
     * @param targetUrl
     *            菜单url链接，支持spring antpathmatcher表达式
     */
    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }



    /**
     * 获取菜单图标路径
     * 
     * @return 菜单图标路径
     */
    public String getIconUrl() {
        return iconUrl;
    }



    /**
     * 设置菜单图标路径
     * 
     * @param iconUrl
     *            菜单图标路径
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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



    /**
     * 获取类型，0：菜单，1：按钮
     * 
     * @return 类型，0：菜单，1：按钮
     */
    public Integer getType() {
        return type;
    }



    /**
     * 设置类型，0：菜单，1：按钮
     * 
     * @param type
     *            类型，0：菜单，1：按钮
     */
    public void setType(Integer type) {
        this.type = type;
    }



    /**
     * 获取类型，0：菜单，1：按钮
     * 
     * @return 类型，0：菜单，1：按钮
     */
    public Integer getMethod() {
        return method;
    }



    /**
     * 设置类型，0：菜单，1：按钮
     * 
     * @param method
     *            类型，0：菜单，1：按钮
     */
    public void setMethod(Integer method) {
        this.method = method;
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
     * 获取创建者
     * 
     * @return 创建者
     */
    public Long getUpdator() {
        return updator;
    }



    /**
     * 设置创建者
     * 
     * @param updator
     *            创建者
     */
    public void setUpdator(Long updator) {
        this.updator = updator;
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
     * 获取最后更新时间
     * 
     * @return 最后更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }



    /**
     * 设置最后更新时间
     * 
     * @param updateDate
     *            最后更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }



    public Integer getDefaultOpen() {
        return defaultOpen;
    }



    public void setDefaultOpen(Integer defaultOpen) {
        this.defaultOpen = defaultOpen;
    }



    public List<SystemMenu> getSubMenuList() {
        return subMenuList;
    }



    public void setSubMenuList(List<SystemMenu> subMenuList) {
        this.subMenuList = subMenuList;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SystemMenu other = (SystemMenu) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public static enum HttpMethodType implements BaseType {
        GET("0", "GET"), POST("1", "POST"), PUT("2", "PUT"), DELETE("3", "DELETE");

        private String code;
        private String desc;



        HttpMethodType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }



        public String getCode() {
            return this.code;
        }



        public String getDesc() {
            return this.desc;
        }



        public static HttpMethodType getHttpMethodType(String code) {
            if ((null == code) || (code.trim().equals(""))) {
                return null;
            }
            for (HttpMethodType methodType : values()) {
                if (code.equals(methodType.getCode())) {
                    return methodType;
                }
            }
            return null;
        }
    }
}
