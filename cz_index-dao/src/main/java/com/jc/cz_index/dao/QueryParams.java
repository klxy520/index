package com.jc.cz_index.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 查询参数
 */
public class QueryParams extends HashMap<String, Object> {

    private static final long   serialVersionUID         = -8916984542395986638L;

    private final static String DEFAULT_MULATTR_PAR_NAME = "_default_mulattr";



    /**
     * 添加一个多属性查询参数，将在Map中创建名为“name”的项，
     * 值为List&lt;NameValue&gt;类型，将使用attrName和attrValue使用NameValue对象
     *
     * @param name
     *            参数名称
     * @param attrName
     *            属性名
     * @param attrValue
     *            属性值
     */
    @SuppressWarnings({ "unchecked" })
    public void addMulAttrParameter(String name, String attrName, String attrValue) {
        if (!containsKey(name))
            this.put(name, new ArrayList<NameValue>());
        ((List<NameValue>) this.get(name)).add(NameValue.create(attrName, attrValue));
    }



    /**
     * 添加一个多属性查询参数，将在Map中创建名为_default_mulattr的项，
     * 值为List&lt;NameValue&gt;类型，将使用attrName和attrValue使用NameValue对象
     *
     * @param attrName
     *            属性名
     * @param attrValue
     *            属性值
     */
    public void addMulAttrParameter(String attrName, String attrValue) {
        addMulAttrParameter(DEFAULT_MULATTR_PAR_NAME, attrName, attrValue);
    }



    /**
     * 添加一个查询参数，将在Map中创建名为“name”的项
     *
     * @param name
     *            参数名称
     * @param value
     *            参数值
     */
    public void addParameter(String name, Object value) {
        this.put(name, value);
    }



    /**
     * 添加一个查询参数，将在Map中创建名为“name”的项
     *
     * @param name
     *            参数名称
     * @param value
     *            参数值
     */
    public void addParameter(String name, String value) {
        this.put(name, value);
    }



    /**
     * 添加一个查询参数，将在Map中创建名为“name”的项
     *
     * @param name
     *            参数名称
     * @param value
     *            参数值
     */
    public void addParameter(String name, int value) {
        this.put(name, value);
    }



    /**
     * 添加一个查询参数，将在Map中创建名为“name”的项
     *
     * @param name
     *            参数名称
     * @param value
     *            参数值
     */
    public void addParameter(String name, long value) {
        this.put(name, value);
    }



    /**
     * 添加一个查询参数，将在Map中创建名为“name”的项
     *
     * @param name
     *            参数名称
     * @param value
     *            参数值
     */
    public void addParameter(String name, short value) {
        this.put(name, value);
    }



    /**
     * 添加一个查询参数，将在Map中创建名为“name”的项
     *
     * @param name
     *            参数名称
     * @param value
     *            参数值
     */
    public void addParameter(String name, float value) {
        this.put(name, value);
    }



    /**
     * 添加一个查询参数，将在Map中创建名为“name”的项
     *
     * @param name
     *            参数名称
     * @param value
     *            参数值
     */
    public void addParameter(String name, double value) {
        this.put(name, value);
    }



    /**
     * 添加一个查询参数，将在Map中创建名为“name”的项
     *
     * @param name
     *            参数名称
     * @param value
     *            参数值
     */
    public void addParameter(String name, boolean value) {
        this.put(name, value);
    }



    /**
     * 添加一个查询参数，将在Map中创建名为“name”的项
     *
     * @param name
     *            参数名称
     * @param value
     *            参数值
     */
    public void addParameter(String name, Date value) {
        this.put(name, value);
    }



    /**
     * 添加一个查询参数，将在Map中创建名为“name”的项
     *
     * @param name
     *            参数名称
     * @param value
     *            参数值
     */
    public void addParameter(String name, Enum<?> value) {
        this.put(name, value);
    }



    /**
     * 添加一个范围查询参数，将在Map中创建名为“name_r_min”、“name_r_max”的项
     *
     * @param name
     *            参数名称
     * @param min
     *            最小值
     * @param max
     *            最大值
     */
    public void addParameterByRange(String name, int min, int max) {
        this.put(name + "_r_min", min);
        this.put(name + "_r_max", max);
    }



    /**
     * 添加一个范围查询参数，将在Map中创建名为“name_r_min”、“name_r_max”的项
     *
     * @param name
     *            参数名称
     * @param min
     *            最小值
     * @param max
     *            最大值
     */
    public void addParameterByRange(String name, long min, long max) {
        this.put(name + "_r_min", min);
        this.put(name + "_r_max", max);
    }



    /**
     * 添加一个范围查询参数，将在Map中创建名为“name_r_min”、“name_r_max”的项
     *
     * @param name
     *            参数名称
     * @param min
     *            最小值
     * @param max
     *            最大值
     */
    public void addParameterByRange(String name, short min, short max) {
        this.put(name + "_r_min", min);
        this.put(name + "_r_max", max);
    }



    /**
     * 添加一个范围查询参数，将在Map中创建名为“name_r_min”、“name_r_max”的项
     *
     * @param name
     *            参数名称
     * @param min
     *            最小值
     * @param max
     *            最大值
     */
    public void addParameterByRange(String name, float min, float max) {
        this.put(name + "_r_min", min);
        this.put(name + "_r_max", max);
    }



    /**
     * 添加一个范围查询参数，将在Map中创建名为“name_r_min”、“name_r_max”的项
     *
     * @param name
     *            参数名称
     * @param min
     *            最小值
     * @param max
     *            最大值
     */
    public void addParameterByRange(String name, double min, double max) {
        this.put(name + "_r_min", min);
        this.put(name + "_r_max", max);
    }



    /**
     * 添加一个范围查询参数，将在Map中创建名为“name_r_min”、“name_r_max”的项
     *
     * @param name
     *            参数名称
     * @param min
     *            最小值
     * @param max
     *            最大值
     */
    public void addParameterByRange(String name, Date min, Date max) {
        this.put(name + "_r_min", min);
        this.put(name + "_r_max", max);
    }



    /**
     * 添加一个枚举查询参数，将在Map中创建名为“name_enum”的项
     *
     * @param name
     *            参数名称
     * @param values
     *            枚举值数组
     */
    public void addParameterByEnum(String name, int... values) {
        this.put(name + "_enum", values);
    }



    /**
     * 添加一个枚举查询参数，将在Map中创建名为“name_enum”的项
     *
     * @param name
     *            参数名称
     * @param values
     *            枚举值数组
     */
    public void addParameterByEnum(String name, long... values) {
        this.put(name + "_enum", values);
    }



    /**
     * 添加一个枚举查询参数，将在Map中创建名为“name_enum”的项
     *
     * @param name
     *            参数名称
     * @param values
     *            枚举值数组
     */
    public void addParameterByEnum(String name, short... values) {
        this.put(name + "_enum", values);
    }



    /**
     * 添加一个枚举查询参数，将在Map中创建名为“name_enum”的项
     *
     * @param name
     *            参数名称
     * @param values
     *            枚举值数组
     */
    public void addParameterByEnum(String name, float... values) {
        this.put(name + "_enum", values);
    }



    /**
     * 添加一个枚举查询参数，将在Map中创建名为“name_enum”的项
     *
     * @param name
     *            参数名称
     * @param values
     *            枚举值数组
     */
    public void addParameterByEnum(String name, double... values) {
        this.put(name + "_enum", values);
    }



    /**
     * 添加一个枚举查询参数，将在Map中创建名为“name_enum”的项
     *
     * @param name
     *            参数名称
     * @param values
     *            枚举值数组
     */
    public void addParameterByEnum(String name, String... values) {
        this.put(name + "_enum", values);
    }



    /**
     * 添加一个枚举查询参数，将在Map中创建名为“name_enum”的项
     *
     * @param name
     *            参数名称
     * @param values
     *            枚举值数组
     */
    public void addParameterByEnum(String name, Enum<?>... values) {
        this.put(name + "_enum", values);
    }



    /**
     * 添加一个查询排序条件,将在Map中创建名训“name_order”的项
     *
     * @param name
     *            名称
     * @param isAsc
     *            true为升序，false为降序
     */
    public void addOrderBy(String name, boolean isAsc) {
        this.put("_orderBy", name + (isAsc ? "_asc" : "_desc"));
    }



    /**
     * 【分页查询相关】 设置分页起始位置
     *
     * @param start
     *            分页起始位置
     */
    public void setStart(int start) {
        if (start < 0) {
            start = 0;
        }
        this.put("__start", start);
    }



    /**
     * 【分页查询相关】 设置分页大小
     *
     * @param start
     *            分页大小
     */
    public void setSize(int size) {
        if (size == 0) {
            size = 1;
        } else if (size < 0) {
            size = Integer.MAX_VALUE;
        }
        this.put("__size", size);
    }

}
