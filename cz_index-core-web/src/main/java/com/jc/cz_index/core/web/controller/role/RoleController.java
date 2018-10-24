package com.jc.cz_index.core.web.controller.role;

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
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.core.web.security.MyInvocationSecurityMetadataSource;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Role;
import com.jc.cz_index.model.RoleMenu;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.IRoleMenuService;
import com.jc.cz_index.core.service.IRoleService;

/**
 * 
 * 描述：角色管理
 * 
 * @author yangyongchuan 2016年10月16日 下午12:17:00
 * @version 1.0
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Autowired
    private IRoleService                       roleService;

    @Autowired
    private IRoleMenuService                   roleMenuService;

    @Autowired
    private IOperationLogService               operationLogService;

    @Autowired
    private MyInvocationSecurityMetadataSource securityMetadataSource;



    @RequestMapping("/listPage")
    public String listPage() {
        return "/role/roleList";
    }



    /**
     * 
     * 描述：获取角色列表
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
        PagedList<Role> rolePagedList = roleService.queryPagedDetaiList(params,
                StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
                StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("iTotalRecords", rolePagedList.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", rolePagedList.getTotalSize());
        result.put("aaData", rolePagedList.getList());
        return result;
    }



    /**
     * 
     * 描述：获取所有角色
     * 
     * @param request
     * @param aoData
     * @return
     * @author yangyongchuan 2016年10月11日 下午2:12:53
     * @version 1.0
     */
    @RequestMapping("/allList")
    @ResponseBody
    public ResponseData allList(HttpServletRequest request) {
        QueryParams params = new QueryParams();
        params.addParameter("status", 0);
        List<Role> roleList = roleService.queryBaseList(params);
        return ResponseData.getSuccessResponse(roleList);
    }



    /**
     * 
     * 描述：添加角色
     * 
     * @param systemUser
     * @return
     * @author yangyongchuan 2016年10月14日 上午11:15:06
     * @version 1.0
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData addUser(Role role) {
        ResponseData responseData = new ResponseData();
        try {
            if (null == role) {
                throw new BaseException("参数有误");
            }
            roleService.addRole(role);
            operationLogService.addOperationLog("角色表", role.getId() + "", "增加角色", "在角色兼权限表(role)中添加了一条记录; 角色id: [ " + role.getId()
                    + " ] , 角色名称 : [ " + role.getName() + " ] , 角色状态: [ " + role.getStatus() + " ];", "无");
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    /**
     * 
     * 描述：编辑角色
     * 
     * @param Role
     * @return
     * @author yangyongchuan 2016年10月14日 上午11:15:06
     * @version 1.0
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResponseData updateUser(Role role) {
        ResponseData responseData = new ResponseData();
        try {
            if (null == role) {
                throw new BaseException("参数有误");
            }
            roleService.updateRole(role);
            operationLogService.addOperationLog("角色表", role.getId() + "", "编辑角色", "在角色兼权限表(role)中编辑了一条记录; 角色id: [ " + role.getId()
                    + " ] , 角色名称 : [ " + role.getName() + " ] , 角色状态: [ " + role.getStatus() + " ];", "无");
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    /**
     * 
     * 描述：获取角色
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
            responseData = ResponseData.getErrorResponse("角色id不能为null");
        }
        Role role = roleService.getModelDetailById(id);
        responseData = ResponseData.getSuccessResponse(role);
        return responseData;

    }



    /**
     * 
     * 描述：获取角色菜单
     * 
     * @param id
     * @return
     * @author yangyongchuan 2016年10月14日 下午1:18:25
     * @version 1.0
     */
    @RequestMapping("/getRoleMenu")
    @ResponseBody
    public ResponseData getRoleMenu(Long roleId) {
        ResponseData responseData = null;
        if (null == roleId || roleId == 0) {
            responseData = ResponseData.getErrorResponse("角色id不能为null");
        }
        QueryParams params = new QueryParams();
        params.addParameter("roleId", roleId);

        List<RoleMenu> roleMenuList = roleMenuService.queryBaseList(params);
        responseData = ResponseData.getSuccessResponse(roleMenuList);
        return responseData;
    }



    /**
     * 
     * 描述：设置角色菜单
     * 
     * @param roleId
     * @param menuIdStr
     * @return
     * @author yangyongchuan 2016年10月18日 下午3:54:29
     * @version 1.0
     */
    @RequestMapping("/setRoleMenu")
    @ResponseBody
    public ResponseData setRoleMenu(Long roleId, String menuIdStr) {
        ResponseData responseData = null;
        if (null == roleId || roleId == 0) {
            responseData = ResponseData.getErrorResponse("角色id不能为null");
        }
        try {
            roleMenuService.setRoleMenu(roleId, menuIdStr);
            List<String> roleIdList = StringUtils.getStringList(menuIdStr, ",");
            if (roleIdList != null && roleIdList.size() > 0) {
                operationLogService.addOperationLog("权限与菜单表", "菜单id" + roleId + "", "角色授权", "在权限与菜单表(role_menu)中为角色[ " + roleId + " ]添加了"
                        + roleIdList.size() + "条记录; 角色id : [ " + roleId + " ] , 菜单id: [ " + menuIdStr + " ]", "无");
            } else {
                operationLogService.addOperationLog("权限与菜单表", "菜单id" + roleId + "", "角色授权",
                        "在权限与菜单表(role_menu)中为角色[ " + roleId + " ]取消了所有菜单权限;", "无");
            }
            // 刷新菜单
            securityMetadataSource.initAllConfigAttributes();
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData = ResponseData.getErrorResponse(e.getMessage());
        }
        return responseData;
    }
}
