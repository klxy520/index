package com.jc.cz_index.core.web.controller.administrativeManagement;


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
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.AdministrativeManagement;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.IAdministrativeManagementService;
import com.jc.cz_index.core.service.IOperationLogService;

/**
 * 
 * 描述：行政机构管理
 * @author wangdeyou 2017年8月28日 下午2:37:51 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/administrativeManagement")
public class AdministrativeManagementController extends BaseController 
{	
    @Autowired
    IAdministrativeManagementService    administrativeManagementService;
    
    @Autowired
    IOperationLogService                operationLogService;
    
    /**
     * 
     * 描述：进入行政机构管理页面
     * @author wangdeyou 2017年8月28日 下午2:37:41 
     * @version 1.0
     */
    @RequestMapping("/listPage")
    public String listPage(){
        return "/administrativeManagement/administrativeManagementList";
    }
    
    /**
     * 
     * 描述：初始化行政机构管理页面
     * @param request
     * @param aoData
     * @return
     * @author wangdeyou 2017年8月28日 下午3:12:47 
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
        PagedList<AdministrativeManagement> AdministrativeManagementPageList = administrativeManagementService.queryPagedBaseList(params,
                StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
                StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("iTotalRecords", AdministrativeManagementPageList.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", AdministrativeManagementPageList.getTotalSize());
        result.put("aaData", AdministrativeManagementPageList.getList());
        return result;
    }
    
    /**
     * 
     * 描述：添加行政机构
     * @param administrativeManagement
     * @return
     * @author wangdeyou 2017年8月30日 上午10:24:47 
     * @version 1.0
     */
    @RequestMapping("/add")
    public ResponseData addAdministrativeManagement(AdministrativeManagement administrativeManagement){
        ResponseData responseData = new ResponseData();
        try {
            if(administrativeManagement == null){
                throw new BaseException("参数错误!");
            }
            //获取当前登录用户
            SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
            administrativeManagementService.addAdministrativeManagement(administrativeManagement);
            operationLogService.addOperationLog("行政机构管理表", loginUser.getId() + "", "添加行政机构",
                    "在行政机构管理表添加一条行政机构信息; 机构id: [ " + administrativeManagement.getId() + " ] ,认证id : [ " + loginUser.getAuthId() + " ];", "无");
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }
    
    /**
     * 
     * 描述：修改行政机构
     * @param administrativeManagement
     * @return
     * @author wangdeyou 2017年8月30日 上午11:51:17 
     * @version 1.0
     */
    @RequestMapping("/update")
    public ResponseData updateAdministrativeManagement(AdministrativeManagement administrativeManagement){
        ResponseData responseData = new ResponseData();
        try {
            if(administrativeManagement == null){
                throw new BaseException("参数错误!");
            }
            //获取当前登录用户
            SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
            administrativeManagementService.updateAdministrativeManagement(administrativeManagement);
            operationLogService.addOperationLog("行政机构管理表", loginUser.getId() + "", "修改行政机构",
                    "在行政机构管理表修改一条行政机构信息; 机构id: [ " + administrativeManagement.getId() + " ] ,认证id : [ " + loginUser.getAuthId() + " ];", "无");
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }
    
    /**
     * 
     * 描述：以ID来删除行政机构(软删除)
     * @param id
     * @return
     * @author wangdeyou 2017年8月30日 下午1:29:05 
     * @version 1.0
     */
    @RequestMapping("/del")
    public ResponseData deleteAdministrativeManagement(String id){
        ResponseData responseData = new ResponseData();
        try {
            if(id == null){
                throw new BaseException("行政机构ID不能为空");
            }
            SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
            administrativeManagementService.del(id);
            operationLogService.addOperationLog("行政机构管理表", loginUser.getId() + "", "删除行政机构",
                    "在行政机构管理表删除一条行政机构信息; 机构id: [ " + id + " ] ,认证id : [ " + loginUser.getAuthId() + " ];", "无");
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }
}

