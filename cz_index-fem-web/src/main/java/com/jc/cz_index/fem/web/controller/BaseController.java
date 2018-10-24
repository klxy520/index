package com.jc.cz_index.fem.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.MultiDateParseEditor;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.dao.QueryParams;

@Controller
public class BaseController {

    /**
     * 
     * 获取请求ip
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }



    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        /*
         * DateFormat fmt = new SimpleDateFormat(ContentUtils.TIME_FORMAT1);
         * CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
         * binder.registerCustomEditor(Date.class, dateEditor);
         */

        List<DateFormat> fmtList = new ArrayList<DateFormat>();
        DateFormat fmt1 = new SimpleDateFormat(ContentUtils.TIME_FORMAT1);
        DateFormat fmt2 = new SimpleDateFormat(ContentUtils.TIME_FORMAT2);
        DateFormat fmt3 = new SimpleDateFormat(ContentUtils.TIME_FORMAT_SIMPLE);
        DateFormat fmt4 = new SimpleDateFormat(ContentUtils.TIME_FORMAT_MONTH);
        DateFormat fmt5 = new SimpleDateFormat(ContentUtils.TIME_FORMAT_YEAR);
        fmtList.add(fmt1);
        fmtList.add(fmt2);
        fmtList.add(fmt3);
        fmtList.add(fmt4);
        fmtList.add(fmt5);
        MultiDateParseEditor dateEditor = new MultiDateParseEditor(fmtList, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }



    /**
     * 
     * 描述：解析前台框架dataTable传过来的值，格式：Map
     * 
     * @param aoData
     * @return
     * @author yangyongchuan 2016年5月24日 下午5:46:46
     * @version 0.1
     * @since 0.1
     */
    public Map<String, Object> getAoDataMap(String aoData) {
        if (StringUtils.isEmpty(aoData)) {
            return null;
        }

        // 分别为关键的参数String 转换为map
        Map<String, Object> aoDataMap = new HashMap<String, Object>();
        JSONArray ja = (JSONArray) JSONArray.parse(aoData);
        // 分别为关键的参数赋值
        for (int i = 0; i < ja.size(); i++) {
            JSONObject obj = (JSONObject) ja.get(i);
            aoDataMap.put(obj.get("name").toString(), obj.get("value").toString());
        }
        // 为操作次数加1
        aoDataMap.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()) + 1);
        // 封装排序字段 orderByStr
        Object sColumns = aoDataMap.get("sColumns");
        Object iSortCol_0 = aoDataMap.get("iSortCol_0");
        Object sSortDir_0 = aoDataMap.get("sSortDir_0");
        if (null != sColumns && null != iSortCol_0 && null != sSortDir_0) {
            String[] columnArray = StringUtils.split(sColumns.toString(), ",");
            String orderByStr = columnArray[StringUtils.StringToInt(iSortCol_0.toString())];
            aoDataMap.put("orderParam", orderByStr);
            aoDataMap.put("orderIsAsc", "asc".equalsIgnoreCase(sSortDir_0.toString()));
        }
        return aoDataMap;
    }



    /**
     * 
     * 描述：设置条件查询 QueryParam
     * 
     * @param aoDataMap
     * @param params
     * @author yangyongchuan 2016年10月13日 上午10:12:59
     * @version 1.0
     */
    public void setFilterParam(Map<String, Object> aoDataMap, QueryParams params) {
        if (CollectionsUtils.isNull(aoDataMap)) {
            return;
        }
        // 条件查询 参数生成
        Set<String> keySet = aoDataMap.keySet();
        for (String param : keySet) {
            String value = aoDataMap.get(param).toString();
            if (param.startsWith(ContentUtils.FILTER_QUERY_FLAG) && StringUtils.isNotEmpty(value)) {
                String[] keyArray = param.split("_");
                if (keyArray.length != 3) {
                    continue;
                }
                switch (keyArray[2]) {
                case "EQ":
                    // 全匹配 e.g. key=filter_createDate_EQ value="AAA"
                    params.addParameter(keyArray[1], value);
                    break;
                case "LIKE":
                    // 模糊匹配 e.g. key=filter_createDate_LIKE value="AAA"
                    // 需添加 whereSql <if test="param_like != null"><![CDATA[ and
                    // param like #{param_like} ]]></if>
                    params.addParameter(keyArray[1] + "_like", "%" + value + "%");
                    break;
                case "GEQ":
                    // 大于等于e.g. key=filter_createDate_GEQ value="2016-10-10
                    // 00:00:00"
                    // 需添加 whereSql <if test="param_r_geq != null"><![CDATA[ and
                    // param >= #{param_r_geq} ]]></if>
                    params.addParameter(keyArray[1] + "_r_geq", value);
                    break;
                case "LEQ":
                    // 小于等于e.g. key=filter_createDate_LEQ value="2016-10-10
                    // 00:00:00"
                    // 需添加 whereSql <if test="param_r_geq != null"><![CDATA[ and
                    // param <= #{param_r_geq} ]]></if>
                    params.addParameter(keyArray[1] + "_r_leq", value);
                    break;
                case "BE":
                    // 之间e.g. key=filter_createDate_BE value="2016-10-10
                    // 00:00:00_2016-10-30 23:59:59"
                    String[] valueArray = value.split("_");
                    params.addParameter(keyArray[1] + "_r_min", valueArray[0]);
                    params.addParameter(keyArray[1] + "_r_max", valueArray[1]);
                    break;
                default:
                    break;
                }
            }
        }
        // 排序
        Object orderParam = aoDataMap.get("orderParam");
        if (null != orderParam && StringUtils.isNotBlank(orderParam.toString())) {
            params.addOrderBy(orderParam.toString(), (Boolean) aoDataMap.get("orderIsAsc"));
        }

    }

}
