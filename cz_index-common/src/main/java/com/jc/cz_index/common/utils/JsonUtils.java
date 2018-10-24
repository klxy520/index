package com.jc.cz_index.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * JsonUtils
 *
 * @author: 杨永川
 * @version: 1.0, 2015年10月28日
 */
public class JsonUtils {
    public final static ObjectMapper mapper = new ObjectMapper();



    /**
     * json string to Object
     * 
     * @param content
     * @param clazz
     * @return
     */
    public static <T> T parse(String content, Class<T> clazz) {
        if (StringUtils.isEmpty(content)) {
            content = "{}";
        }

        try {
            return JSON.parseObject(content, clazz);
        } catch (Throwable e) {
            throw new RuntimeException("JSON解析错误, " + e.getMessage());
        }
    }



    /**
     * json string to List Object
     * 
     * @param content
     * @param clazz
     * @return
     */
    public static <T> T parse2List(String content, Class<?> collectionClass, Class<T> elementClasses) {
        if (StringUtils.isEmpty(content)) {
            content = "{}";
        }
        try {
            JavaType javaType = getCollectionType(collectionClass, elementClasses);
            return mapper.readValue(content, javaType);
        } catch (Throwable e) {
            throw new RuntimeException("JSON解析错误, " + e.getMessage());
        }
    }



    /**
     * 
     * Json string to JsonObject
     *
     * @param content
     * @return
     */
    public static JSONObject toJsonObject(String content) {
        if (StringUtils.isEmpty(content)) {
            content = "{}";
        }
        return (JSONObject) JSON.parse(content);
    }



    /**
     * 
     * json string to map
     *
     * @param content
     * @return
     */
    public static Map<String, Object> parse(String content) {
        if (StringUtils.isEmpty(content)) {
            content = "{}";
        }

        try {
            return JSON.parseObject(content);
        } catch (Throwable e) {
            throw new RuntimeException("JSON解析错误, " + e.getMessage());
        }
    }



    /**
     * 
     * Object to json String
     *
     * @param object
     * @return String
     */
    public static String toJSONString(Object object) {
        if (null == object) {
            object = new Object();
        }

        try {
            return JSONObject.toJSONString(object);
        } catch (Throwable e) {
            throw new RuntimeException("JSON解析错误, " + e.getMessage());
        }
    }



    /**
     * 
     * Object to jsonObject
     *
     * @param object
     * @return
     */
    public static Object toJSON(Object object) {
        if (null == object) {
            object = new Object();
        }
        try {
            return JSONObject.toJSON(object);
        } catch (Throwable e) {
            throw new RuntimeException("JSON解析错误, " + e.getMessage());
        }
    }



    /**
     * 
     * 获取GET请求传入的参数
     *
     * @param request
     * @param content
     * @return
     */
    public static String getNoKeyParamValue(HttpServletRequest request, String content) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Iterator<Entry<String, String[]>> iterator = parameterMap.entrySet().iterator(); iterator.hasNext();) {
            Entry<String, String[]> entry = iterator.next();
            if (entry.getValue().length == 1 && "".equalsIgnoreCase(entry.getValue()[0])) {
                content = entry.getKey();
                break;
            }
            if (!entry.getKey().endsWith("}") && !"".equalsIgnoreCase(entry.getValue()[0]) && entry.getValue()[0].startsWith("\"")) {// 处理{"op":"="}串
                content = entry.getKey() + "=" + entry.getValue()[0];
                break;
            }
        }
        return content;
    }



    public static String inputStream2String(InputStream inputStream) throws IOException {
        byte[] buff = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int count;
        while ((count = inputStream.read(buff)) != -1) {
            baos.write(buff, 0, count);
        }
        String result = new String(baos.toByteArray());
        baos.close();
        return result;
    }



    /**
     * 
     * 过滤多余的字段
     *
     * @param propertyList
     * @return
     */
    public static PropertyFilter getNullPropertyFilter(final List<String> propertyList) {
        PropertyFilter propertyFilter = new PropertyFilter() {
            @Override
            public boolean apply(Object paramObject1, String paramString, Object paramObject2) {
                boolean reault = true;
                if (!CollectionsUtils.isNull(propertyList) && !propertyList.contains(paramString)) {
                    reault = false;
                } else if (null == paramObject1 || StringUtils.isEmpty(paramObject1.toString())) {
                    reault = false;
                }
                return reault;
            }
        };
        return propertyFilter;
    }



    /**
     * 
     * @Title: objectFormatToString @Description:生成json对象 @param
     *         object @param @return @return String @throws
     */
    public static String objectFormatToString(Object object) {
        String result = null;
        if (null != object) {
            result = JSONObject.toJSONString(object, getNullPropertyFilter(null));
        }
        return result;
    }



    /**
     * 
     * @Title: objectFormatToString @Description:可以 指定返回的属性
     *         来生成json对象 @param @param object @param @param
     *         propertyList @param @return @return String @throws
     */
    public static String objectFormatToString(Object object, List<String> propertyList) {
        String result = null;
        if (null != object) {
            result = JSONObject.toJSONString(object, getNullPropertyFilter(propertyList));
        }
        return result;
    }



    /**
     * 
     * @Title: listFormatToString @Description:生成json对象 @param @param
     *         list @param @return @return String @throws
     */
    @SuppressWarnings("rawtypes")
    public static String listFormatToString(List list) {
        String result = null;
        if (!CollectionsUtils.isNull(list)) {
            result = JSONArray.toJSONString(list, getNullPropertyFilter(null));
        }
        return result;

    }



    /**
     * 
     * 描述：可以 指定返回的属性 来生成json
     * 
     * @param list
     * @param paramNameList
     * @return
     * @author yangyongchuan 2016年11月16日 下午1:52:35
     * @version 1.0
     */
    @SuppressWarnings("rawtypes")
    public static String toJSONStringByParamNameList(List list, List<String> paramNameList) {
        String result = null;
        if (!CollectionsUtils.isNull(list)) {
            result = JSONArray.toJSONString(list, getNullPropertyFilter(paramNameList));
        }
        return result;
    }



    /**
     * 
     * 描述：可以 指定返回的属性 来生成json
     * 
     * @param list
     * @param paramNameList
     * @return
     * @author yangyongchuan 2016年11月16日 下午1:52:35
     * @version 1.0
     */
    public static String toJSONStringByParamNameList(Object object, List<String> paramNameList) {
        String result = null;
        if (null != object) {
            result = JSONObject.toJSONString(object, getNullPropertyFilter(paramNameList));
        }
        return result;
    }



    /**
     * 
     * 描述：可以 指定返回的属性 来生成json
     * 
     * @param list
     * @param paramNameList
     * @return
     * @author yangyongchuan 2016年11月16日 下午1:52:35
     * @version 1.0
     */
    @SuppressWarnings("rawtypes")
    public static String toJSONStringByParamNameStr(List list, String paramNameStr) {
        String result = null;
        if (!CollectionsUtils.isNull(list)) {
            result = JSONArray.toJSONString(list, getNullPropertyFilter(StringUtils.getStringList(paramNameStr, ",")));
        }
        return result;
    }



    /**
     * 
     * 描述：可以 指定返回的属性 来生成json
     * 
     * @param list
     * @param paramNameList
     * @return
     * @author yangyongchuan 2016年11月16日 下午1:52:35
     * @version 1.0
     */
    public static String toJSONStringByParamNameStr(Object object, String paramNameStr) {
        String result = null;
        if (null != object) {
            result = JSONObject.toJSONString(object, getNullPropertyFilter(StringUtils.getStringList(paramNameStr, ",")));
        }
        return result;
    }



    public static Map<String, Object> getValueToMap(Object obj, List<String> propertyList) {
        if (null == obj) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            if (!propertyList.contains(varName)) {
                continue;
            }
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null) {
                    map.put(varName, o);
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }



    public static String compress(String str) {
        String result = null;
        if (null == str || str.length() <= 0) {
            return str;
        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            // 将 b.length 个字节写入此输出流
            gzip.write(str.getBytes("UTF-8"));
            gzip.close();
            result = out.toString("UTF-8");
            // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }



    /**
     * 字符串的解压
     * 
     * @param str
     *            对字符串解压
     * @return 返回解压缩后的字符串
     * @throws IOException
     */
    public static String unCompress(String str) throws IOException {
        if (null == str || str.length() <= 0) {
            return str;
        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        // 使用默认缓冲区大小创建新的输入流
        GZIPInputStream gzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n = 0;
        while ((n = gzip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组
            // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
            out.write(buffer, 0, n);
        }
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("GBK");
    }



    /**
     * 
     * @Title: readJsonFile @Description:read json file @param @param
     *         filePath @param @return @return String @throws
     */
    public static String readJsonFile(String filePath) {
        StringBuffer jsonStringBuffer = new StringBuffer(100000);
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        FileReader fileReader = null;
        BufferedReader reader = null;

        try {
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while (line != null) {
                jsonStringBuffer.append(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fileReader) {
                    fileReader.close();
                }
                if (null != reader) {
                    reader.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return jsonStringBuffer.toString();
    }



    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
