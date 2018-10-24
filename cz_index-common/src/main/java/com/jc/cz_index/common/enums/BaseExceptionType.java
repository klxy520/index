package com.jc.cz_index.common.enums;

import com.jc.cz_index.common.exception.ExceptionType;

public enum BaseExceptionType implements ExceptionType {
    SUCCESS, TIME_OUT, OK, BAD_REQUEST, UNAUTHORIZED, FORBIDDEN, NOT_FOUND, METHOD_NOT_ALLOWED, UNKOWN, LOGIN, SESSION_TIMEOUT, AUTHCODE, NEED_LOGINED, QUERY, QUERY_TYPE, DATASOURCE_TYPE, QUERY_OPERATOR, QUERY_GROUPOPERATOR, QUERY_CONFIG, QUERY_SQL, EXPORT, ROWS_TOO_MUCH, FIELUPDOWN, SYSTEM_CONFIG, TIMED_TASK;

    private int    code;
    private String describe;



    public int getCode() {
        return this.code;
    }



    public String getDescribe() {
        return this.describe;
    }



    public String toString() {
        return "{0x" + Integer.toHexString(getCode()) + "," + getDescribe() + "}";
    }
}