package com.jc.cz_index.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * 描述：请求参数实体类
 * 
 * @author yangyongchuan 2016年11月14日 下午4:23:08
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestData {
    // 平台id(用户中心平台id)
    private String appid;

    // 请求参数，对含有签名的基本业务数据 JSON 串加密后形成的密文。
    private String data;

    // 密钥密文，使用 RSA 加密AESKey 后生成的密钥密文
    private String encryptkey;



    public String getAppid() {
        return appid;
    }



    public void setAppid(String appid) {
        this.appid = appid;
    }



    public String getData() {
        return data;
    }



    public void setData(String data) {
        this.data = data;
    }



    public String getEncryptkey() {
        return encryptkey;
    }



    public void setEncryptkey(String encryptkey) {
        this.encryptkey = encryptkey;
    }

}
