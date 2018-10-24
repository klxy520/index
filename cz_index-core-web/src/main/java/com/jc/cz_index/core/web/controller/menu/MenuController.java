package com.jc.cz_index.core.web.controller.menu;

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
import com.jc.cz_index.core.web.security.MyInvocationSecurityMetadataSource;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.SystemMenu;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.ISystemMenuService;

/**
 * 
 * 描述：后台系统菜单控制
 * 
 * @author yangyongchuan 2016年10月11日 上午11:05:48
 * @version 1.0
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
    @Autowired
    private ISystemMenuService                 systemMenuService;
    @Autowired
    private MyInvocationSecurityMetadataSource securityMetadataSource;
    @Autowired
    private IOperationLogService               operationLogService;



    @RequestMapping("/listPage")
    public String listPage() {
        return "/menu/menuList";
    }



    /**
     * 
     * 描述：获取顶部菜单对象（关联：一对多）
     * 
     * @return
     * @author yangyongchuan 2016年10月16日 下午9:41:18
     * @version 1.0
     */
    @RequestMapping("/topMenu")
    @ResponseBody
    public ResponseData topMenu() {
        QueryParams params = new QueryParams();
        params.addParameter("id", 1l);
        SystemMenu topMenus = systemMenuService.queryOneDetail(params);
        return ResponseData.getSuccessResponse(topMenus);
    }



    /**
     * 
     * 描述：获取菜单列表
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
        PagedList<SystemMenu> systemMenuPagedList = systemMenuService.queryPagedBaseList(params,
                StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
                StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("iTotalRecords", systemMenuPagedList.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", systemMenuPagedList.getTotalSize());
        result.put("aaData", systemMenuPagedList.getList());
        return result;
    }



    /**
     * 
     * 描述：添加菜单
     * 
     * @param systemMenu
     * @return
     * @author yangyongchuan 2016年10月14日 上午11:15:06
     * @version 1.0
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData addMenu(SystemMenu systemMenu) {
        ResponseData responseData = new ResponseData();
        try {
            if (null == systemMenu) {
                throw new BaseException("参数有误");
            }
            systemMenuService.addMenu(systemMenu);
            operationLogService.addOperationLog("菜单表", systemMenu.getId() + "", "增加菜单",
                    "在菜单表中添加了一条记录; 菜单id: [ " + systemMenu.getId() + " ] , 菜单名称 : [ " + systemMenu.getName() + " ] , 菜单类型: [ "
                            + systemMenu.getType() + " ] , 上级菜单id: [ " + systemMenu.getParentId() + " ];",
                    "无");
            // 刷新菜单
            securityMetadataSource.initAllConfigAttributes();
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    /**
     * 
     * 描述：编辑菜单
     * 
     * @param systemMenu
     * @return
     * @author yangyongchuan 2016年10月14日 上午11:15:06
     * @version 1.0
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResponseData updateMenu(SystemMenu systemMenu) {
        ResponseData responseData = new ResponseData();
        try {
            if (null == systemMenu) {
                throw new BaseException("参数有误");
            }
            systemMenuService.updateMenu(systemMenu);
            operationLogService.addOperationLog("菜单表", systemMenu.getId() + "", "编辑菜单",
                    "在菜单表中编辑了一条记录; 菜单id: [ " + systemMenu.getId() + " ] , 菜单名称 : [ " + systemMenu.getName() + " ] , 菜单类型: [ "
                            + systemMenu.getType() + " ] , 上级菜单id: [ " + systemMenu.getParentId() + " ];",
                    "无");
            // 刷新菜单
            securityMetadataSource.initAllConfigAttributes();
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }

}
