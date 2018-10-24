package com.jc.cz_index.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 描述：BeanUtils
 * 
 * @author yangyongchuan 2016年5月23日 下午5:00:11
 * @version 0.1
 * @since 0.1
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }



    /**
     * 
     * 描述：复制对象，过滤null
     * 
     * @param src
     * @param target
     * @author yangyongchuan 2016年5月23日 下午5:00:19
     * @version 0.1
     * @since 0.1
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }



    /**
     * 
     * 描述：实体类转换为Map
     * 
     * @param obj
     * @return
     * @author yangyongchuan 2016年11月11日 下午4:49:22
     * @version 1.0
     */
    public static Map<String, Object> obj2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }



    /**
     * 
     * 描述：遍历实体内所有不为空的参数
     * 
     * @param model
     *            实体
     * @param start
     *            开始下标
     * @param end
     *            结束下标
     * @return String 属性名称[属性值],
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @author yangjunhui 2017年9月14日 下午3:41:14
     * @version 1.0
     */
    public static String getObjectNotNullField(Object model, int start, int end)
            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String addStr = "";
        Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        Class clazz = model.getClass();
        Method m2 = clazz.getMethod("getDescribe");// 获取对象中getDescribe()方法
        String describe = (String) m2.invoke(model);// 取得字段的中文描述
        String[] fieldNameZn = {};
        fieldNameZn = describe.split(","); // 获取每个字段中文名称
        for (; start <= end; start++) {// 遍历所有属性，
            String name = field[start].getName(); // 获取属性的名字
            name = name.substring(0, 1).toUpperCase() + name.substring(1);// 将属性的首字符大写
            String type = field[start].getGenericType().toString(); // 获取属性的类型
            Method m = clazz.getMethod("get" + name);
            String value = "";
            if (m.invoke(model) != null) {
                if (type.equals("class java.util.Date")) {
                    Date date = (Date) m.invoke(model);
                    value = DateUtils.formatDateToString(date);
                    addStr += fieldNameZn[start] + ":[" + value + "],";
                    continue;
                }
                value = m.invoke(model).toString();
                if (value.trim().length() <= 0) {
                    continue;
                }
                addStr += fieldNameZn[start] + ":[" + value + "],";
            }
        }
        return addStr;
    }



    /**
     * 
     * 描述：获取实体不同属性
     * 
     * @param originalObj
     * @param newObj
     * @param fieldName
     * @return
     * @author yangjunhui 2017年9月21日 下午1:58:41
     * @version 1.0
     */
    public static String getDifferenceByFields(Object originalObj, Object newObj, String fieldNames) {
        JSONObject obj1 = (JSONObject) JSONObject.toJSON(originalObj);
        JSONObject obj2 = (JSONObject) JSONObject.toJSON(newObj);
        String addStr = "";// 添加属性
        String updateStr = "";// 修改属性
        String delStr = "";// 修改属性

        String key = "";// 属性key
        String describe = "";// 属性中文描述
        String[] field = fieldNames.split(";");// 比对字段
        int i = 0;
        int j = field.length;
        for (; i < j; i++) {// 遍历所有属性，
            key = field[i].split(",")[1];//
            describe = field[i].split(",")[0];
            if (obj1.get(key) == null && obj2.get(key) == null) {
                continue;// 同为忽略判断
            } else if (obj1.get(key) != null && obj2.get(key) != null) {
                if (obj1.get(key).toString().length() <= 0 && obj2.get(key).toString().length() <= 0) {
                    continue;
                }
            } else if (obj1.get(key) == null && obj2.get(key).toString().trim().length() <= 0) {
                continue;
            } else if (obj2.get(key) == null && obj1.get(key).toString().trim().length() <= 0) {
                continue;
            }
            if (obj1.get(key) == null || obj1.get(key).toString().length() <= 0) {// 第一个为空，判断属性新增
                if (obj2.get(key).getClass().equals(Date.class)) {
                    addStr += describe + "[" + DateUtils.formatDateToString(obj2.getDate(key)) + "],";
                    continue;
                }
                addStr += describe + "[" + obj2.get(key).toString() + "]";
            } else if (obj2.get(key) == null || obj2.get(key).toString().length() <= 0) {// 第二个为空，判断属性删除
                if (obj1.get(key).getClass().equals(Date.class)) {
                    delStr += describe + "[" + DateUtils.formatDateToString(obj1.getDate(key)) + "],";
                    continue;
                }
                delStr += describe + "[" + obj1.get(key).toString() + "]";
            } else {// 前后均布为空，判断为修改
                if (obj2.get(key).toString().equals(obj1.get(key).toString())) {
                    continue;
                }
                if (obj2.get(key).getClass().equals(Date.class)) {
                    updateStr += describe + "由[" + DateUtils.formatDateToString(obj1.getDate(key)) + "]" + "修改为["
                            + DateUtils.formatDateToString(obj2.getDate(key)) + "],";
                    continue;
                }
                updateStr += describe + "由[" + obj1.get(key) + "]" + "修改为[" + obj2.get(key) + "],";
            }
        }
        String returnStr = "";
        if (updateStr.trim().length() > 0) {
            returnStr += "修改内容为【" + updateStr + "】";
        }
        if (addStr.trim().length() > 0) {
            returnStr += "新增内容【" + addStr + "】";
        }
        if (delStr.trim().length() > 0) {
            returnStr += "删除内容【" + delStr + "】";
        }
        return returnStr;
    }



    public static String getBeanPropertiesByFields(Object obj, String Fields) {
        JSONObject obj1 = (JSONObject) JSONObject.toJSON(obj);
        String key = "";// 属性key
        String describe = "";// 属性中文描述
        String[] field = Fields.split(";");// 比对字段
        int i = 0;
        int j = field.length;
        String returnStr = "";
        for (; i < j; i++) {// 遍历所有属性，
            key = field[i].split(",")[1];//
            describe = field[i].split(",")[0];
            if (obj1.get(key) == null) {
                returnStr += describe + "[  ],";
                continue;
            }
            if (obj1.get(key).getClass().equals(Date.class)) {
                returnStr += describe + "[" + DateUtils.formatDateToString(obj1.getDate(key)) + "],";
                continue;
            }
            returnStr += describe + "[" + obj1.get(key).toString() + "],";
        }
        returnStr = returnStr.substring(0, returnStr.length() - 1);
        return returnStr;
    }
    
    /**
     * 
     * 描述：判断object是否为空
     * @param obj
     * @return
     * @author fengshengliang 2018年1月12日 上午11:13:34 
     * @version 1.0
     */
    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        }
        if ((obj instanceof List)) {
            return CollectionsUtils.isNull((Collection) obj);
        }
        if ((obj instanceof String)) {
            return StringUtils.isEmpty(((String) obj).trim());
        }
        return false;
    }

}
