package com.jc.cz_index.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Log4jUtils {
    public static Log getLog() {
        return LogFactory.getLog(Log4jUtils.class);
    }



    public static Log getLog(Class<?> clazz) {
        return LogFactory.getLog(clazz);
    }



    public static Log getLog(String loggerName) {
        return LogFactory.getLog(loggerName);
    }
}