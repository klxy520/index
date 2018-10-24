package com.jc.cz_index.model;

public class Demo extends BaseBean {
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

    // 姓名
    private String  name;
    // 性别
    private Integer sex;



    public void setName(String name) {
        this.name = name;
    }



    public String getName() {
        return this.name;
    }



    public void setSex(Integer sex) {
        this.sex = sex;
    }



    public Integer getSex() {
        return this.sex;
    }

}
