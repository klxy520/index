package com.jc.cz_index.common.bean;

import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestParam {
    private String                  appid;

    private TreeMap<String, Object> params;

    private String                  data;
    private String                  encryptkey;



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



    public String getAppid() {
        return appid;
    }



    public void setAppid(String appid) {
        this.appid = appid;
    }



    public TreeMap<String, Object> getParams() {
        return params;
    }



    public void setParams(TreeMap<String, Object> params) {
        this.params = params;
    }

}
