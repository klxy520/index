package com.jc.cz_index.model;

import java.util.ArrayList;
import java.util.List;

public class Dictionary extends BaseBean {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 主键 [主键]
    private Long              id;



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

    // 上级字典id号
    private java.lang.Long   parentid;
    // 字典名称
    private String           name;
    // 字典key
    private String           dictkey;
    // 字典值
    private String           value;
    // 描述
    private String           description;
    // 状态，0：禁用，1：启用
    private Integer          status;
    // 是否默认, 0:不是, 1:是
    private Integer          isdefault;
    // 显示顺序
    private Integer          showindex;

    // 关联属性
    // 子机构
    private List<Dictionary> subDicitonaryList = new ArrayList<Dictionary>();



    public List<Dictionary> getSubDicitonaryList() {
        return subDicitonaryList;
    }



    public void setSubDicitonaryList(List<Dictionary> subDicitonaryList) {
        this.subDicitonaryList = subDicitonaryList;
    }



    public void setParentid(java.lang.Long parentid) {
        this.parentid = parentid;
    }



    public java.lang.Long getParentid() {
        return this.parentid;
    }



    public void setName(String name) {
        this.name = name;
    }



    public String getName() {
        return this.name;
    }



    public void setDictkey(String dictkey) {
        this.dictkey = dictkey;
    }



    public String getDictkey() {
        return this.dictkey;
    }



    public void setValue(String value) {
        this.value = value;
    }



    public String getValue() {
        return this.value;
    }



    public void setDescription(String description) {
        this.description = description;
    }



    public String getDescription() {
        return this.description;
    }



    public void setStatus(Integer status) {
        this.status = status;
    }



    public Integer getStatus() {
        return this.status;
    }



    public void setIsdefault(Integer isdefault) {
        this.isdefault = isdefault;
    }



    public Integer getIsdefault() {
        return this.isdefault;
    }



    public void setShowindex(Integer showindex) {
        this.showindex = showindex;
    }



    public Integer getShowindex() {
        return this.showindex;
    }

    // TODO
}
