package com.jc.cz_index.model;

/**
 * 
 * 描述：医院实体类
 * 
 * @author sunxuefeng 2018年1月3日 下午1:22:52
 * @version 1.0
 */
public class Hospital extends BaseBean {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // 主键 [主键]
    private Long id;



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

    // 医院名称
    private String  name;
    // 医院地址
    private String  address;
    // 医院等级
    private String  level;
    // 医院key
    private String  hKey;
    // 医院的pyCode
    private String  pycode;
    // 医院的类型
    private String  type;
    // 医院类型名称
    private String  typeName;
    // 医院的状态(0启用,1禁用，)
    private Integer status;
    // 医院电话
    private String  phone;
    // 删除标记,(0,未删除1,已删除)
    private Integer delFalg;



    public void setName(String name) {
        this.name = name;
    }



    public String gethKey() {
        return hKey;
    }



    public void sethKey(String hKey) {
        this.hKey = hKey;
    }



    public String getName() {
        return this.name;
    }



    public void setAddress(String address) {
        this.address = address;
    }



    public String getAddress() {
        return this.address;
    }



    public void setLevel(String level) {
        this.level = level;
    }



    public String getLevel() {
        return this.level;
    }



    public void setHKey(String hKey) {
        this.hKey = hKey;
    }



    public String getHKey() {
        return this.hKey;
    }



    public void setPycode(String pycode) {
        this.pycode = pycode;
    }



    public String getPycode() {
        return this.pycode;
    }



    public void setType(String type) {
        this.type = type;
    }



    public String getType() {
        return this.type;
    }



    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }



    public String getTypeName() {
        return this.typeName;
    }



    public void setStatus(Integer status) {
        this.status = status;
    }



    public Integer getStatus() {
        return this.status;
    }



    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getPhone() {
        return this.phone;
    }



    public void setDelFalg(Integer delFalg) {
        this.delFalg = delFalg;
    }



    public Integer getDelFalg() {
        return this.delFalg;
    }
}
