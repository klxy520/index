/**
 * 2016/9/29 15:08:25 Jack Liu created.
 */

package com.jc.cz_index.model;

/**
 * 2016/09/29 Created by Jack Liu.
 */
public class SystemConfig extends BaseBean {

    // 序列化版本
    private static final long serialVersionUID = -9223372036854775808L;

    // id [主键]
    private Long              id;
    // 参数-键
    private String            systemKey;
    // 参数-值
    private String            systemValue;
    // 参数-描述
    private String            description;



    /**
     * 获取id [主键]
     * 
     * @return id
     */
    public Long getId() {
        return id;
    }



    /**
     * 设置id [主键]
     * 
     * @param id
     *            id
     */
    public void setId(Long id) {
        this.id = id;
    }



    /**
     * 获取参数-键
     * 
     * @return 参数-键
     */
    public String getSystemKey() {
        return systemKey;
    }



    /**
     * 设置参数-键
     * 
     * @param systemKey
     *            参数-键
     */
    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }



    /**
     * 获取参数-值
     * 
     * @return 参数-值
     */
    public String getSystemValue() {
        return systemValue;
    }



    /**
     * 设置参数-值
     * 
     * @param systemValue
     *            参数-值
     */
    public void setSystemValue(String systemValue) {
        this.systemValue = systemValue;
    }



    /**
     * 获取参数-描述
     * 
     * @return 参数-描述
     */
    public String getDescription() {
        return description;
    }



    /**
     * 设置参数-描述
     * 
     * @param description
     *            参数-描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
