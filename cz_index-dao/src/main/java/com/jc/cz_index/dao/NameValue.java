package com.jc.cz_index.dao;

public class NameValue implements java.io.Serializable {

    private static final long serialVersionUID = -8916832542303234638L;

    private String            name;
    private String            value;



    /**
     * 创建一个新的名称值对象
     *
     * @param name
     *            名称
     * @param value
     *            值
     * @return 名称值对象
     */
    public static NameValue create(String name, String value) {
        NameValue result = new NameValue();
        result.setName(name);
        result.setValue(value);
        return result;
    }



    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }



    /**
     * 设置名称
     *
     * @param name
     *            名称
     */
    public void setName(String name) {
        this.name = name;
    }



    /**
     * 获取值
     *
     * @return 值
     */
    public String getValue() {
        return value;
    }



    /**
     * 设置值
     *
     * @param value
     *            值
     */
    public void setValue(String value) {
        this.value = value;
    }
}
