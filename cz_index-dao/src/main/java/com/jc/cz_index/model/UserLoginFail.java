package com.jc.cz_index.model;

public class UserLoginFail extends BaseBean {
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

    // 登陆名称
    private String         loginName;
    // 登陆失败次数
    private Integer        failTimes;
    // 最后登陆失败时间
    private java.util.Date lastFailTime;
    // 预留字段
    private String         reverse;



    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }



    public String getLoginName() {
        return this.loginName;
    }



    public void setFailTimes(Integer failTimes) {
        this.failTimes = failTimes;
    }



    public Integer getFailTimes() {
        return this.failTimes;
    }



    public void setLastFailTime(java.util.Date lastFailTime) {
        this.lastFailTime = lastFailTime;
    }



    public java.util.Date getLastFailTime() {
        return this.lastFailTime;
    }



    public void setReverse(String reverse) {
        this.reverse = reverse;
    }



    public String getReverse() {
        return this.reverse;
    }

    // TODO
}
