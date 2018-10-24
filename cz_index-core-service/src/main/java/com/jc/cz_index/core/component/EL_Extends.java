package com.jc.cz_index.core.component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.AdministrativeDivision;
import com.jc.cz_index.model.AdministrativeManagement;
import com.jc.cz_index.model.Dictionary;
import com.jc.cz_index.model.FrontEndMachine;
import com.jc.cz_index.model.Hospital;
import com.jc.cz_index.model.SystemMenu;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.common.utils.ApplicationUtils;
import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.service.IAdministrativeDivisionService;
import com.jc.cz_index.core.service.IAdministrativeManagementService;
import com.jc.cz_index.core.service.IDictionaryService;
import com.jc.cz_index.core.service.IFrontEndMachineService;
import com.jc.cz_index.core.service.IHospitalService;
import com.jc.cz_index.core.service.ISystemMenuService;

/**
 * 
 * 描述：el 扩展方法
 * 
 * @author wangdeyou 2016年5月25日 上午10:54:34
 * @version 0.1
 * @since 0.1
 */
public class EL_Extends {
    public static final DateFormat                        defaultDateFormat               = new SimpleDateFormat(ContentUtils.TIME_FORMAT1);
    // 字典service
    private static final IDictionaryService               dictionaryService               = (IDictionaryService) ApplicationUtils
            .getApplicationContext().getBean("dictionaryServiceImpl");

    private static final ISystemMenuService               systemMenuService               = (ISystemMenuService) ApplicationUtils
            .getApplicationContext().getBean("systemMenuService");

    private static final IAdministrativeManagementService administrativeManagementService = (IAdministrativeManagementService) ApplicationUtils
            .getApplicationContext().getBean("administrativeManagementService");
    private static final IAdministrativeDivisionService   administrativeDivisionService   = (IAdministrativeDivisionService) ApplicationUtils
            .getApplicationContext().getBean("administrativeDivisionService");
    private static final IHospitalService      hospitalService= (IHospitalService) ApplicationUtils.getApplicationContext().getBean("hospitalService");
    private static final IFrontEndMachineService      frontEndMachineService= (IFrontEndMachineService) ApplicationUtils.getApplicationContext().getBean("frontEndMachineService");



    /**
     * 测试
     * 
     * @param time
     * @return
     */
    public static Map<String, String> test() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name1", "value1");
        map.put("name2", "value2");
        return map;
    }



    /**
     * 测试
     * 
     * @param time
     * @return
     */
    public static List<String> test1() {
        List<String> list = new ArrayList<String>();
        list.add("v1");
        list.add("v2");
        return list;
    }



    /**
     * 毫秒数日期格式化
     * 
     * @param time
     * @return
     */
    public static String formatDefaultDate(long time) {
        return defaultDateFormat.format(time);
    }



    /**
     * 毫秒数日期格式化
     * 
     * @param time
     * @param formatStr
     * @return
     */
    public static String formatDate(long time, String formatStr) {
        DateFormat format = new SimpleDateFormat();
        if (StringUtils.isEmpty(formatStr)) {
            format = defaultDateFormat;
        } else {
            format = new SimpleDateFormat(formatStr);
        }
        return format.format(time);
    }



    /**
     * 
     * 描述：获取字典表数据，一条记录
     * 
     * @param dictKey
     * @return
     * @author yangyongchuan 2016年5月25日 下午1:48:24
     * @version 0.1
     * @since 0.1
     */
    public static Dictionary dict(String dictKey) {
        if (StringUtils.isEmpty(dictKey)) {
            return null;
        }
        QueryParams params = new QueryParams();
        params.put("dictKey", dictKey);
        List<Dictionary> dictList = dictionaryService.queryBaseList(params);
        if (!CollectionsUtils.isNull(dictList)) {
            return dictList.get(0);
        }
        return null;
    }



    /**
     * 
     * 描述：获取字典表数据，多条记录 dictKey 模糊查询 dictKey_%
     * 
     * @param dictKey
     * @return
     * @author yangyongchuan 2016年5月25日 下午1:48:24
     * @version 0.1
     * @since 0.1
     */
    public static List<Dictionary> dictList(String dictKey) {
        if (StringUtils.isEmpty(dictKey)) {
            return null;
        }
        QueryParams params = new QueryParams();
        params.put("dictKey_like", dictKey + "_%");
        params.put("_orderBy", "showIndex_asc");
        params.put("_status", 1);
        List<Dictionary> dictList = dictionaryService.queryBaseList(params);
        return dictList;
    }



    /**
     * 
     * 描述：检查用户是否具有该菜单，根据编号
     * 
     * @param menuSn
     * @return
     * @author yangyongchuan 2017年3月6日 下午5:23:57
     * @version 1.0
     */
    public static SystemMenu checkMenuBySn(String menuSn) {
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        // 用户未登录
        if (null != loginUser) {
            return systemMenuService.checkMenuBySn(loginUser, menuSn);
        }
        return null;
    }



    /***
     * 
     * 描述：获取行政机构列表
     * 
     * @return
     * @author sunxuefeng 2017年9月1日 下午5:02:49
     * @version 1.0
     */
    public static List<AdministrativeManagement> administrativeDivisionList() {
        QueryParams params = new QueryParams();
        params.put("_status", 1);
        params.put("isDelete", 0);
        List<AdministrativeManagement> administrativeDivisionList = administrativeManagementService.queryBaseList(params);
        return administrativeDivisionList;
    }



    /***
     * 
     * 描述：获取一级区域机构
     * 
     * @return
     * @author sunxuefeng 2017年9月1日 下午5:02:49
     * @version 1.0
     */
    public static List<AdministrativeDivision> administrativeDivisionListByParentId() {
        QueryParams params = new QueryParams();
        params.put("parentid", 1);
        params.put("isDelete", 0);
        List<AdministrativeDivision> administrativeDivisionList = administrativeDivisionService.queryBaseList(params);
        return administrativeDivisionList;
    }
    /**
     * 
     * 描述：获取所有医院机构
     * @return
     * @author sunxuefeng 2018年1月4日 下午1:41:36 
     * @version 1.0
     */
    public static List<Hospital> getHospital() {
        QueryParams params = new QueryParams();
        params.put("status", 0);
        params.put("delFalg", 0);
        List<Hospital> hospitalList = hospitalService.queryBaseList(params);
        return hospitalList;
    }
    /**
     * 
     * 描述：获取所有前置机
     * @return
     * @author sunxuefeng 2018年1月4日 下午1:41:36 
     * @version 1.0
     */
    public static List<FrontEndMachine> getFrontEndMachine() {
        QueryParams params = new QueryParams();
        List<FrontEndMachine> frontEndMachines = frontEndMachineService.queryBaseList(params);
        return frontEndMachines;
    }
}
