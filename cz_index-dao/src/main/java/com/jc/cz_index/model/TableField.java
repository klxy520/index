package com.jc.cz_index.model;

public class TableField extends BaseBean{

    /**
     * 字段信息对应表field
     */
    private static final long serialVersionUID = 4308089568505336773L;
    
   /* id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `field_name` varchar(20) DEFAULT NULL COMMENT '字段名称',
    `alias` varchar(20) DEFAULT NULL COMMENT '字段别名',
    `table_name` varchar(20) DEFAULT NULL COMMENT '该字段来源表',
    `describe` varchar(40) DEFAULT NULL COMMENT '字段描述',
*/
    private Long id ;
    private String fieldName;
    private String alias ;
    private String tableName;
    private String describe;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getDescribe() {
        return describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }
    
}
