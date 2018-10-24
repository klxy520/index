package com.jc.cz_index.core.web.controller.systemConfig;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.SystemConfig;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.ISystemConfigService;

/**
 * 
 * 描述：系统参数管理
 * 
 * @author yangyongchuan 2016年10月16日 下午12:17:00
 * @version 1.0
 */
@Controller
@RequestMapping("/systemConfig")
public class SystemConfigController extends BaseController {
    @Autowired
    private ISystemConfigService systemConfigService;
    @Autowired
    private IOperationLogService operationLogService;



    @RequestMapping("/listPage")
    public String listPage() {
        return "/systemConfig/systemConfigList";
    }



    /**
     * 
     * 描述：获取系统参数列表
     * 
     * @param request
     * @param aoData
     * @return
     * @author yangyongchuan 2016年10月11日 下午2:12:53
     * @version 1.0
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, @RequestParam String aoData) {
        // 解析前台框架dataTable传过来的值，格式：Map
        Map<String, Object> aoDataMap = getAoDataMap(aoData);
        QueryParams params = new QueryParams();
        // 设置条件查询 QueryParam
        setFilterParam(aoDataMap, params);
        PagedList<SystemConfig> systemConfigPagedList = systemConfigService.queryPagedBaseList(params,
                StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
                StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("iTotalRecords", systemConfigPagedList.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", systemConfigPagedList.getTotalSize());
        result.put("aaData", systemConfigPagedList.getList());
        return result;
    }



    /**
     * 
     * 描述：添加系统参数
     * 
     * @param systemConfig
     * @return
     * @author yangyongchuan 2016年10月14日 上午11:15:06
     * @version 1.0
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData addUser(SystemConfig systemConfig) {
        ResponseData responseData = new ResponseData();
        try {
            if (null == systemConfig) {
                throw new BaseException("参数有误");
            }
            systemConfigService.addSystemConfig(systemConfig);
            operationLogService.addOperationLog("系统参数表", systemConfig.getId() + "",
                    "增加系统参数", "在系统参数表添加了一条记录; 参数id: [ " + systemConfig.getId() + " ] , 参数名称 : [ " + systemConfig.getSystemKey()
                            + " ] , 参数值: [ " + systemConfig.getSystemValue() + " ] , 参数描述: [ " + systemConfig.getDescription() + " ];",
                    "无");
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    /**
     * 
     * 描述：编辑系统参数
     * 
     * @param SystemConfig
     * @return
     * @author yangyongchuan 2016年10月14日 上午11:15:06
     * @version 1.0
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResponseData updateUser(SystemConfig systemConfig) {
        ResponseData responseData = new ResponseData();
        try {
            if (null == systemConfig) {
                throw new BaseException("参数有误");
            }
            systemConfigService.updateSystemConfig(systemConfig);
            operationLogService.addOperationLog("系统参数表", systemConfig.getId() + "",
                    "修改系统参数", "在系统参数表修改了一条记录; 参数id: [ " + systemConfig.getId() + " ] , 参数名称 : [ " + systemConfig.getSystemKey()
                            + " ] , 参数值: [ " + systemConfig.getSystemValue() + " ] , 参数描述: [ " + systemConfig.getDescription() + " ];",
                    "无");
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    /**
     * 
     * 描述：获取系统参数
     * 
     * @param id
     * @return
     * @author yangyongchuan 2016年10月14日 下午1:18:25
     * @version 1.0
     */
    @RequestMapping("/get")
    @ResponseBody
    public ResponseData get(Long id) {
        ResponseData responseData = null;
        if (null == id || id == 0) {
            responseData = ResponseData.getErrorResponse("系统参数id不能为null");
        }
        SystemConfig systemConfig = systemConfigService.getModelDetailById(id);
        responseData = ResponseData.getSuccessResponse(systemConfig);
        return responseData;

    }

}
