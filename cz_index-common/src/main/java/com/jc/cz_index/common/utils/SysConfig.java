package com.jc.cz_index.common.utils;

import java.util.Properties;

import org.apache.log4j.Logger;

public class SysConfig {

    private static Properties  properties = new Properties();
    
    private static Properties routers = null;

    public static final Logger logger     = Logger.getLogger(SysConfig.class);

    static {
        try {
            logger.info("SysConfig static init start.");
            properties = ConfigFileUtil.propertiesReader("config/sysConfig.properties");
            routers = ConfigFileUtil.propertiesReader("config/router.properties");
        } catch (Exception e) {
            logger.error("fail to find config file sysConfig.properties", e);
        }
    }



    public static String getValue(String key) {

        return properties.getProperty(key);
    }
    public static String getRouter(String key){
    	 return routers.getProperty(key);
    }

}
