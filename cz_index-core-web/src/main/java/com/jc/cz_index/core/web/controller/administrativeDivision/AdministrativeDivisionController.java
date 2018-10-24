package com.jc.cz_index.core.web.controller.administrativeDivision;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.jc.cz_index.model.AdministrativeDivision;
import com.jc.cz_index.model.AdministrativeManagement;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.IAdministrativeDivisionService;
import com.jc.cz_index.core.service.IAdministrativeManagementService;
import com.jc.cz_index.core.service.IOperationLogService;

/**
 * 
 * 描述：区域机构管理
 * @author wangdeyou 2017年8月30日 下午3:23:58 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/administrativeDivision")
public class AdministrativeDivisionController extends BaseController 
{	
    @Autowired
    private IAdministrativeDivisionService    administrativeDivisionService;
    
    @Autowired
    private IOperationLogService                operationLogService;
    
    @Autowired
    private IAdministrativeManagementService    administrativeManagementService;
    
    /**
     * 
     * 描述：进入区域机构管理页面
     * @return
     * @author wangdeyou 2017年8月30日 下午3:25:39 
     * @version 1.0
     */
    @RequestMapping("/listPage")
    public String listPage(){
        return "/administrativeDivision/administrativeDivision";
    }
    
    /**
     * 
     * 描述：获取数据字典
     * @return
     * @author wangdeyou 2017年8月30日 下午3:29:18 
     * @version 1.0
     */
    @RequestMapping("/topAdministrativeDivision")
    @ResponseBody
    public ResponseData topAdministrativeDivision() {
        QueryParams params = new QueryParams();
        params.put("isDelete", 0);
        /*params.addParameter("id", 1l);
        AdministrativeDivision administrativeDivision = administrativeDivisionService.queryOneDetail(params);*/
        List<AdministrativeDivision> administrativeDivision = administrativeDivisionService.queryBaseList(params);
        return ResponseData.getSuccessResponse(administrativeDivision);
    }
    
    /**
     * 
     * 描述：初始化区域机构机构数据
     * @param request
     * @param aoData
     * @return
     * @author wangdeyou 2017年8月30日 下午3:36:39 
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
        PagedList<AdministrativeDivision> administrativeDivisionPageList = administrativeDivisionService.queryPagedBaseList(params,
                StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
                StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("iTotalRecords", administrativeDivisionPageList.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", administrativeDivisionPageList.getTotalSize());
        result.put("aaData", administrativeDivisionPageList.getList());
        return result;
    }
    
    /**
     * 
     * 描述：添加区域机构
     * @param administrativeDivision
     * @return
     * @author wangdeyou 2017年8月31日 下午3:33:48 
     * @version 1.0
     */
    @RequestMapping("/add")
    public ResponseData addAdministrativeDivision(AdministrativeDivision administrativeDivision){
        ResponseData responseData = new ResponseData();
        try {
            if(administrativeDivision == null){
                throw new BaseException("参数错误!");
            }
            SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
            administrativeDivisionService.addAdministrativedivision(administrativeDivision);
            operationLogService.addOperationLog("区域机构管理表", loginUser.getId() + "", "添加区域机构",
                    "在区域机构管理表添加一条区域机构信息; 区域id: [ " + administrativeDivision.getId() + " ] ,认证id : [ " + loginUser.getAuthId() + " ];", "无");
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);;
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }
    
    /**
     * 
     * 描述：修改区域机构
     * @param administrativeDivision
     * @return
     * @author wangdeyou 2017年8月31日 下午5:31:05 
     * @version 1.0
     */
    @RequestMapping("/update")
    public ResponseData updateAdministrativeDivision(AdministrativeDivision administrativeDivision){
        ResponseData responseData = new ResponseData();
        try {
            if (administrativeDivision == null) {
                throw new BaseException("参数错误!");
            }
            SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
            administrativeDivisionService.updateAdministrativedivision(administrativeDivision);
            operationLogService.addOperationLog("区域机构管理表", loginUser.getId() + "", "修改区域机构",
                   "在区域机构管理表修改一条区域机构信息; 区域id: [ " + administrativeDivision.getId() + " ] ,认证id : [ " + loginUser.getAuthId() + " ];", "无");
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }
    
    /**
     * 
     * 描述：删除区域机构(软删除)
     * @param id
     * @return
     * @author wangdeyou 2017年8月31日 下午5:38:10 
     * @version 1.0
     */
    @RequestMapping("/del")
    public ResponseData del(String id){
        ResponseData responseData = new ResponseData();
        try {
            if(id == null){
                throw new BaseException("区域机构ID不能为空");
            }
            SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
            administrativeDivisionService.del(id);
            responseData = ResponseData.getSuccessResponse();
            operationLogService.addOperationLog("区域机构管理表", loginUser.getId() + "", "删除区域机构",
                    "在区域机构管理表删除一条区域机构信息; 区域id: [ " + id + " ] ,认证id : [ " + loginUser.getAuthId() + " ];", "无");
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }
    
    /**
     * 
     * 描述：通过父级ID查询所有机构区域
     * @param id
     * @return
     * @author wangdeyou 2017年9月5日 下午1:39:45 
     * @version 1.0
     */
    @RequestMapping("/getAdministrativeDivision")
    @ResponseBody
    public ResponseData getAdministrativeDivision(Long id){
        ResponseData responseData = new ResponseData();
        try {
            if(null == id || id == 0){
                throw new BaseException("父级ID不能为空");
            }
            List<AdministrativeDivision> list = administrativeDivisionService.getAdministrativeDivisionList(id);
            responseData = ResponseData.getSuccessResponse(list);
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }
    
    /**
     * 
     * 描述：获取所有行政机构
     * @return
     * @author wangdeyou 2017年9月5日 下午2:47:36 
     * @version 1.0
     */
    @RequestMapping("/getManagement")
    public ResponseData getManagement(Long id){
        ResponseData responseData = new ResponseData();
        QueryParams params = new QueryParams();
        List<AdministrativeManagement> list = new ArrayList<>();
        try {
            if (id != 0) {
                params.put("id", id);
                params.put("isDelete", 0);
                list = administrativeManagementService.queryBaseList(params);
                responseData = ResponseData.getSuccessResponse(list);
            }else{
                params.put("isDelete", 0);
                list = administrativeManagementService.queryBaseList(params);
                responseData = ResponseData.getSuccessResponse(list);
            }
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }
    
    /**
     * 
     * 描述：通过ID获取机构区域
     * @param id
     * @return
     * @author wangdeyou 2017年9月6日 下午3:38:25 
     * @version 1.0
     */
    @RequestMapping("/getAdminDiv")
    public ResponseData getAdminDiv(Long id){ 
        ResponseData responseData = new ResponseData();
        try {
            if (id == null) {
                throw new BaseException("id不能为空!");
            }
            List<Object> list = administrativeDivisionService.getAdminDiv(id);
            responseData = ResponseData.getSuccessResponse(list);
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }
}
