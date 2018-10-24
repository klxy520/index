package com.jc.cz_index.common.exception;

/**
 * 
 * 描述：项目报错异常对象
 * 
 * @author yangyongchuan 2016年9月26日 下午4:46:01
 * @version 1.0
 */
public class BaseException extends Exception {
    private static final long serialVersionUID = -1122011682948817794L;



    public BaseException() {
        super();
    }



    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }



    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }



    public BaseException(String message) {
        super(message);
    }



    public BaseException(Throwable cause) {
        super(cause);
    }

}