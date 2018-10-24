package com.jc.cz_index.model;

public class Platform extends BaseBean 
{
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
	
    //平台id
    private String appid;
    //应用名称
    private String appName;
    //app对应公钥
    private String appSecret;
    //app对应私钥
    private String appPrivateKey;
    //平台名称
    private String platName;
    //兑换规则：1K币=多少积分
    private Integer exchangeRule;
    //交付公钥(第三方平台)
    private String publicKey;
    //交付私钥(ukepay)
    private String privateKey;
    //状态:0启用,1禁用
    private Integer status;
    //创建者
    private java.lang.Long creator;
    //修改者
    private java.lang.Long updator;
    //最后更新时间
    private java.util.Date updateDate;
    //创建时间
    private java.util.Date createDate;
    public void setAppid(String appid)
    {
        this.appid=appid;
    }
    public String getAppid()
    {
        return this.appid;
    }
    public void setAppName(String appName)
    {
        this.appName=appName;
    }
    public String getAppName()
    {
        return this.appName;
    }
    public void setAppSecret(String appSecret)
    {
        this.appSecret=appSecret;
    }
    public String getAppSecret()
    {
        return this.appSecret;
    }
    public void setAppPrivateKey(String appPrivateKey)
    {
        this.appPrivateKey=appPrivateKey;
    }
    public String getAppPrivateKey()
    {
        return this.appPrivateKey;
    }
    public void setPlatName(String platName)
    {
        this.platName=platName;
    }
    public String getPlatName()
    {
        return this.platName;
    }
    public void setExchangeRule(Integer exchangeRule)
    {
        this.exchangeRule=exchangeRule;
    }
    public Integer getExchangeRule()
    {
        return this.exchangeRule;
    }
    public void setPublicKey(String publicKey)
    {
        this.publicKey=publicKey;
    }
    public String getPublicKey()
    {
        return this.publicKey;
    }
    public void setPrivateKey(String privateKey)
    {
        this.privateKey=privateKey;
    }
    public String getPrivateKey()
    {
        return this.privateKey;
    }
    public void setStatus(Integer status)
    {
        this.status=status;
    }
    public Integer getStatus()
    {
        return this.status;
    }
    public void setCreator(java.lang.Long creator)
    {
        this.creator=creator;
    }
    public java.lang.Long getCreator()
    {
        return this.creator;
    }
    public void setUpdator(java.lang.Long updator)
    {
        this.updator=updator;
    }
    public java.lang.Long getUpdator()
    {
        return this.updator;
    }
    public void setUpdateDate(java.util.Date updateDate)
    {
        this.updateDate=updateDate;
    }
    public java.util.Date getUpdateDate()
    {
        return this.updateDate;
    }
    public void setCreateDate(java.util.Date createDate)
    {
        this.createDate=createDate;
    }
    public java.util.Date getCreateDate()
    {
        return this.createDate;
    }

}

