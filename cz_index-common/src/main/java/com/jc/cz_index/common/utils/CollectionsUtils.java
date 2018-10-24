package com.jc.cz_index.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 
 * CollectionsUtil
 *
 * @author: 杨永川
 * @version: 1.0, 2015年10月26日
 */
public class CollectionsUtils {

    /**
     * 
     * @Title: isNull @Description:判断集合是null @param @param
     *         collection @param @return @return boolean @throws
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNull(Collection collection) {
        boolean result = true;
        if (null != collection && collection.size() > 0) {
            result = false;
        }
        return result;
    }



    @SuppressWarnings("rawtypes")
    public static boolean isNotNull(Collection collection) {
        return !isNull(collection);
    }



    /**
     * 
     * @Title: isNull @Description:判断map是null @param @param
     *         map @param @return @return boolean @throws
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNull(Map map) {
        boolean result = true;
        if (null != map && map.size() > 0) {
            result = false;
        }
        return result;
    }



    /**
     * 
     * @Title: isNull @Description:判断数组是null @param @param
     *         Object @param @return @return boolean @throws
     */
    public static boolean isNull(Object[] Object) {
        boolean result = true;
        if (null != Object && Object.length > 0) {
            result = false;
        }
        return result;
    }
}