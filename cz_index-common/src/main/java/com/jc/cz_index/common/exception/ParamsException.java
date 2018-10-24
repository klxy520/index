package com.jc.cz_index.common.exception;

/**
 * 
 * 描述：参数报错异常对象
 * 
 * @author yangyongchuan 2016年9月26日 下午4:46:01
 * @version 1.0
 */
public class ParamsException extends BaseException {


    /**
     * 
     */
    private static final long serialVersionUID = 5933124224242930571L;



    public ParamsException() {
        super();
    }

    public ParamsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }



    public ParamsException(String message, Throwable cause) {
        super(message, cause);
    }



    public ParamsException(String message) {
        super(message);
    }



    public ParamsException(Throwable cause) {
        super(cause);
    }
    

}