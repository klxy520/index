package com.jc.cz_index.core.web.controller.frontEndMachine;

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
import com.jc.cz_index.core.service.IFrontEndMachineService;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.FrontEndMachine;
import com.jc.cz_index.model.SystemUser;

/**
 * 
 * 描述：前置机管理
 * @author wangdeyou 2017年12月25日 下午3:22:47 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/frontEndMachine")
public class FrontEndMachineController extends BaseController {	
	
	@Autowired
	IFrontEndMachineService			frontEndMachineService;
	
	@Autowired
	IOperationLogService			operationLogService;
	/**
	 * 
	 * 描述：进入前置机页面
	 * @return
	 * @author wangdeyou 2017年12月25日 下午3:22:39 
	 * @version 1.0
	 */
	@RequestMapping("/listPage")
	public String listPage(){
		return "/frontEndMachine/frontEndMachineList";
	}
	
	/**
	 * 
	 * 描述：初始化前置机页面
	 * @param request
	 * @param aoData
	 * @return
	 * @author wangdeyou 2017年12月26日 上午10:09:34 
	 * @version 1.0
	 */
	@RequestMapping("/frontEndMachineData")
	@ResponseBody
	public Map<String, Object> frontEndMachineData(HttpServletRequest request, @RequestParam String aoData){
		// 解析前台框架dataTable传过来的值，格式：Map
        Map<String, Object> aoDataMap = getAoDataMap(aoData);
        QueryParams params = new QueryParams();
        // 设置条件查询 QueryParam
        setFilterParam(aoDataMap, params);
        PagedList<FrontEndMachine> frontEndMachineList = frontEndMachineService.queryPagedDetaiList(params,
                StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
                StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("iTotalRecords", frontEndMachineList.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", frontEndMachineList.getTotalSize());
        result.put("aaData", frontEndMachineList.getList());
        return result;
	}
	 
	/**
	 * 
	 * 描述：添加前置机
	 * @param frontEndMachine
	 * @return
	 * @author wangdeyou 2017年12月26日 下午3:20:28 
	 * @version 1.0
	 */
	@RequestMapping("/add")
	public ResponseData add(FrontEndMachine frontEndMachine){
		ResponseData responseData = new ResponseData();
		try {
			if (frontEndMachine==null) {
				throw new BaseException("参数错误!");
			}
			SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
			frontEndMachineService.addFronEndMachine(frontEndMachine);
			operationLogService.addOperationLog("前置机管理表", loginUser.getId() + "", "添加前置机",
                    "在前置机管理表添加一条前置机信息; 前置机id: [ " + frontEndMachine.getId() + " ] ,认证id : [ " + loginUser.getAuthId() + " ];", "无");
            responseData = ResponseData.getSuccessResponse();
		} catch (Exception e) {
			responseData.setStatus(1);
			responseData.setMessage(e.getMessage());
		}
		return responseData;
	}
	
	/**
	 * 
	 * 
	 * 描述：修改前置机
	 * @param frontEndMachine
	 * @return
	 * @author wangdeyou 2017年12月26日 下午6:12:18 
	 * @version 1.0
	 */
	@RequestMapping("/update")
	public ResponseData update(FrontEndMachine frontEndMachine){
		ResponseData responseData = new ResponseData();
		try {
			if (frontEndMachine==null) {
				throw new BaseException("参数错误!");
			}
			SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
			frontEndMachineService.updateFronEndMachine(frontEndMachine);
			operationLogService.addOperationLog("前置机管理表", loginUser.getId() + "", "添加前置机",
                    "在前置机管理表添加一条前置机信息; 前置机id: [ " + frontEndMachine.getId() + " ] ,认证id : [ " + loginUser.getAuthId() + " ];", "无");
            responseData = ResponseData.getSuccessResponse();
		} catch (Exception e) {
			responseData.setStatus(1);
			responseData.setMessage(e.getMessage());
		}
		return responseData;
	}
	
	/**
	 * 
	 * 描述：启用前置机
	 * @param id
	 * @return
	 * @author wangdeyou 2017年12月26日 下午6:33:54 
	 * @version 1.0
	 */
	@RequestMapping("/enable")
	public ResponseData enable(String id){
		ResponseData responseData = new ResponseData();
        try {
            if(id == null){
                throw new BaseException("前置机ID不能为空");
            }
            SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
            frontEndMachineService.enable(id);
            operationLogService.addOperationLog("前置机管理表", loginUser.getId() + "", "启用前置机",
                    "在前置机管理表启用一条前置机; 前置机id: [ " + id + " ] ,认证id : [ " + loginUser.getAuthId() + " ];", "无");
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
	}
	
	/**
	 * 
	 * 描述：禁用前置机
	 * @param id
	 * @return
	 * @author wangdeyou 2017年12月26日 下午7:00:58 
	 * @version 1.0
	 */
	@RequestMapping("/disable")
	public ResponseData disable(String id){
		ResponseData responseData = new ResponseData();
        try {
            if(id == null){
                throw new BaseException("前置机ID不能为空");
            }
            SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
            frontEndMachineService.disable(id);
            operationLogService.addOperationLog("前置机管理表", loginUser.getId() + "", "禁用前置机",
                    "在前置机管理表禁用一条前置机; 前置机id: [ " + id + " ] ,认证id : [ " + loginUser.getAuthId() + " ];", "无");
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
	}
	
	/**
	 * 
	 * 描述：删除前置机
	 * @param id
	 * @return
	 * @author wangdeyou 2017年12月27日 上午10:37:42 
	 * @version 1.0
	 */
	@RequestMapping("/del")
	public ResponseData del(String id){
		ResponseData responseData = new ResponseData();
		try {
			if (id == null) {
				throw new BaseException("前置机ID不能为空");
			}
			SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
			frontEndMachineService.del(id);
			operationLogService.addOperationLog("前置机管理表", loginUser.getId() + "", "删除前置机",
                    "在前置机管理表删除一条前置机; 前置机id: [ " + id + " ] ,认证id : [ " + loginUser.getAuthId() + " ];", "无");
			
		} catch (Exception e) {
			responseData.setStatus(1);
			responseData.setMessage(e.getMessage());
		}
		return responseData;
	}
}

