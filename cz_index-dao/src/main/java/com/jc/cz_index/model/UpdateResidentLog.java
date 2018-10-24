package com.jc.cz_index.model;

/***
 * 
 * 描述：居民健康卡基本信息用户更新记录实体
 * 
 * @author sunxuefeng 2017年8月30日 下午2:03:30
 * @version 1.0
 */
public class UpdateResidentLog extends BaseBean {

    private static final long serialVersionUID = 1L;

    // 主键 [主键]
    private Long              id;



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

    // 居民基本信息id
    private java.lang.Long residentId;
    // 修改居民信息人的ID
    private java.lang.Long userId;



    public void setResidentId(java.lang.Long residentId) {
        this.residentId = residentId;
    }



    public java.lang.Long getResidentId() {
        return this.residentId;
    }



    public void setUserId(java.lang.Long userId) {
        this.userId = userId;
    }



    public java.lang.Long getUserId() {
        return this.userId;
    }
}