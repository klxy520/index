package com.jc.cz_index.common.bean;

import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.SysConfig;

/**
 * 
 * ResponseData:ajax 返回数据结构
 *
 * @author: 杨永川
 * @version: 1.0, 2015年11月8日
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseData {
    // 状态 0=success 1=failed
    final static public int SUCCESS_STATUS = 0;
    final static public int ERROR_STATUS   = 1;
    private int             status;
    private String          message;
    private Object          data;



    public ResponseData() {
    }



    public ResponseData(int status, String message, Object data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }



    public int getStatus() {
        return status;
    }



    public void setStatus(int status) {
        this.status = status;
    }



    public String getMessage() {
        return message;
    }



    public void setMessage(String message) {
        this.message = message;
    }



    public Object getData() {
        return data;
    }



    public void setData(Object data) {
        this.data = data;
    }



    /**
     * 
     * 操作成功
     *
     * @param obj
     * @return
     */
    public static ResponseData getSuccessResponse() {
        return new ResponseData(0, "成功", null);
    }



    /**
     * 
     * 操作成功
     *
     * @param object
     * @return
     */
    public static ResponseData getSuccessResponse(Object object) {
        return new ResponseData(0, "成功", object);
    }



    /**
     * 
     * 服务器异常了
     *
     * @param obj
     * @return
     */
    public static ResponseData getServiceErrorResponse() {
        return new ResponseData(1, SysConfig.getValue(ContentUtils.SERVICE_ERROR), null);
    }



    /**
     * 
     * 获取错误
     *
     * @param message
     * @return
     */
    public static ResponseData getErrorResponse(String message) {
        return new ResponseData(1, message, null);
    }
    /**
     * 
     * 获取错误
     *
     * @param message
     * @return
     */
    public static ResponseData getErrorResponse(String message,Object data) {
        return new ResponseData(1, message, data);
    }



    /**
     * 
     * 描述：转换为map
     * 
     * @return
     * @author yangyongchuan 2016年11月8日 下午3:52:30
     * @version 1.0
     */
    public TreeMap<String, Object> toMap() {
        TreeMap<String, Object> map = new TreeMap<String, Object>();

        map.put("status", this.status);
        if (StringUtils.isNotEmpty(message)) {
            map.put("message", this.message);
        }
        if (null != data) {
            map.put("data", this.data);
        }
        return map;
    }



    @Override
    public String toString() {
        return "ResponseData [status=" + status + ", message=" + message + ", data=" + data + "]";
    }

}
