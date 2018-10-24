package com.jc.cz_index.core.web.controller.user;

import java.util.ArrayList;
import java.util.Date;
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
import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.EncodeAndDecodeUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.SystemConfig;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.model.SystemUserAuth;
import com.jc.cz_index.model.SystemUserRole;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.ISystemConfigService;
import com.jc.cz_index.core.service.ISystemUserAuthService;
import com.jc.cz_index.core.service.ISystemUserRoleService;
import com.jc.cz_index.core.service.ISystemUserService;

/**
 * 
 * 描述：后台系统用户控制
 * 
 * @author yangyongchuan 2016年10月11日 上午11:05:48
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private ISystemUserService     systemUserService;

    @Autowired
    private ISystemUserAuthService systemUserAuthService;

    @Autowired
    private ISystemUserRoleService systemUserRoleService;

    @Autowired
    private IOperationLogService   operationLogService;

    @Autowired
    private ISystemConfigService   sysConfigService;



    @RequestMapping("/listPage")
    public String listPage() {
        return "/user/userList";
    }



    /**
     * 
     * 描述：获取用户列表
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
        params.addParameter("id_not_eq", 1L);
        PagedList<SystemUser> systemUserPagedList = systemUserService.queryPagedDetaiList(params,
                StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
                StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("iTotalRecords", systemUserPagedList.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", systemUserPagedList.getTotalSize());
        result.put("aaData", systemUserPagedList.getList());
        return result;
    }



    /**
     * 
     * 描述：添加系统用户
     * 
     * @param systemUser
     * @return
     * @author yangyongchuan 2016年10月14日 上午11:15:06
     * @version 1.0
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData addUser(SystemUser systemUser) {
        ResponseData responseData = new ResponseData();

        try {
            if (null == systemUser) {
                throw new BaseException("参数有误");
            }
            if ("1".equals(systemUser.getUserType() + "")) {// 系统管理员没有所属机构
                systemUser.setOrganizationId(0l);
            }
            systemUserService.addUser(systemUser);
            operationLogService.addOperationLog("用户认证表,系统用户表", systemUser.getId() + "", "增加用户",
                    "在用户认证表,系统用户表添加一条用户信息; 用户id: [ " + systemUser.getId() + " ] , 用户名称 : [ " + systemUser.getName() + " ] , 用户类型 : [ "
                            + systemUser.getUserType() + " ] , 认证id : [ " + systemUser.getAuthId() + " ];",
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
     * 描述：编辑系统用户
     * 
     * @param systemUser
     * @return
     * @author yangyongchuan 2016年10月14日 上午11:15:06
     * @version 1.0
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResponseData updateUser(SystemUser systemUser) {
        ResponseData responseData = new ResponseData();
        try {
            if (null == systemUser) {
                throw new BaseException("参数有误");
            }
            systemUserService.updateUser(systemUser);
            SystemUser systemUser2 = systemUserService.getModelById(systemUser.getId());
            operationLogService.addOperationLog("用户认证表,系统用户表", systemUser.getId() + "", "修改", "在用户认证表,系统用户表修改一条用户信息; 用户id: [ "
                    + systemUser.getId() + " ] , 用户名称 : [ " + systemUser.getName() + " ] , 用户类型 : [ " + systemUser2.getUserType() + " ];",
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
     * 描述：编辑当前登录的系统用户
     * 
     * @param systemUser
     * @return
     * @author yangyongchuan 2016年10月14日 上午11:15:06
     * @version 1.0
     */
    @RequestMapping("/updateUserForLogin")
    @ResponseBody
    public ResponseData updateUserForLogin(SystemUser systemUser) {
        ResponseData responseData = new ResponseData();
        try {
            if (null == systemUser) {
                throw new BaseException("参数有误");
            }
            SystemUser loginSystemUser = (SystemUser) WebUtils.getLoginUser();
            if (null == loginSystemUser) {
                throw new BaseException("请先登录!");
            }
            if (loginSystemUser.getId() != systemUser.getId()) {
                throw new BaseException("只能修改自己的个人信息!");
            }
            systemUserService.updateUser(systemUser);
            SystemUser systemUser2 = systemUserService.getModelById(systemUser.getId());
            operationLogService.addOperationLog("用户认证表,系统用户表", systemUser.getId() + "", "修改", "在用户认证表,系统用户表修改一条用户信息; 用户id: [ "
                    + systemUser.getId() + " ] , 用户名称 : [ " + systemUser.getName() + " ] , 用户类型 : [ " + systemUser2.getUserType() + " ];",
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
     * 描述：重置密码
     * 
     * @param systemUserAuth
     * @return
     * @author wangdeyou 2017年2月22日 上午10:50:10
     * @version 1.0
     */
    @RequestMapping("/resetPassword")
    @ResponseBody
    public ResponseData resetPassword(SystemUser systemUser) {
        ResponseData responseData = new ResponseData();
        SystemUserAuth systemUserAuth = new SystemUserAuth();
        try {
            if (systemUser == null) {
                throw new BaseException("参数错误");
            }
            String pwd = systemUser.getSystemUserAuth().getPassword();
            Date date = DateUtils.getCurrentDate();
            systemUserAuth = systemUserAuthService.getModelById(systemUser.getSystemUserAuth().getId());
            // 密码加密
            systemUserAuth.setPassword(EncodeAndDecodeUtils.MD5Encode(pwd));
            systemUserAuth.setUpdatePassDate(date);
            systemUserAuthService.doUpdateModel(systemUserAuth);
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    /**
     * 
     * 描述：检查登录名是否有效
     * 
     * @param loginName
     * @return
     * @author yangyongchuan 2016年10月14日 下午1:18:25
     * @version 1.0
     */
    @RequestMapping("/checkLoginName")
    @ResponseBody
    public ResponseData checkLoginName(String loginName) {
        ResponseData responseData = new ResponseData();
        boolean result = hasLoginName(loginName);
        responseData.setStatus(result ? 1 : 0);
        return responseData;

    }



    /**
     * 
     * 描述：检查登录名是否有效
     * 
     * @param loginName
     * @return
     * @author yangyongchuan 2016年10月14日 下午1:18:25
     * @version 1.0
     */
    @RequestMapping("/checkSn")
    @ResponseBody
    public ResponseData checkSn(String sn) {
        ResponseData responseData = new ResponseData();
        boolean result = hasSn(sn);
        responseData.setStatus(result ? 1 : 0);
        return responseData;

    }



    /**
     * 
     * 描述：获取用户
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
            responseData = ResponseData.getErrorResponse("用户id不能为null");
        }
        SystemUser systemUser = systemUserService.getModelDetailById(id);
        systemUser.getSystemUserAuth().setPassword(null);
        responseData = ResponseData.getSuccessResponse(systemUser);
        return responseData;

    }



    /**
     * 
     * 描述：启用用户
     * 
     * @param setUserEnable
     * @return
     * @author yangyongchuan 2016年10月14日 下午1:18:25
     * @version 1.0
     */
    @RequestMapping("/setUserEnable")
    @ResponseBody
    public ResponseData setUserEnable(Long systemUserAuthId) {
        ResponseData responseData = null;
        if (null == systemUserAuthId || systemUserAuthId == 0) {
            responseData = ResponseData.getErrorResponse("用户id不能为null");
        }
        systemUserAuthService.updateAuthStatus(systemUserAuthId, ContentUtils.DB_FIELD_STATUS_ENABLE);
        operationLogService.addOperationLog("系统用户认证信息表", systemUserAuthId + "", "修改(启用用户)",
                "在系统用户认证信息表中修改一条用户信息; 认证id: [ " + systemUserAuthId + " ] , 用户状态 : [ " + ContentUtils.DB_FIELD_STATUS_ENABLE + " ];", "无");
        responseData = ResponseData.getSuccessResponse();
        return responseData;

    }



    /**
     * 
     * 描述：禁用用户
     * 
     * @param setUserEnable
     * @return
     * @author yangyongchuan 2016年10月14日 下午1:18:25
     * @version 1.0
     */
    @RequestMapping("/setUserDisable")
    @ResponseBody
    public ResponseData setUserDisable(Long systemUserAuthId) {
        ResponseData responseData = null;
        if (null == systemUserAuthId || systemUserAuthId == 0) {
            responseData = ResponseData.getErrorResponse("用户id不能为null");
        }
        systemUserAuthService.updateAuthStatus(systemUserAuthId, ContentUtils.DB_FIELD_STATUS_DISABLE);
        operationLogService.addOperationLog("系统用户认证信息表", systemUserAuthId + "", "修改(禁用用户)",
                "在系统用户认证信息表中修改一条用户信息; 认证id: [ " + systemUserAuthId + " ] , 用户状态 : [ " + ContentUtils.DB_FIELD_STATUS_DISABLE + " ];", "无");
        responseData = ResponseData.getSuccessResponse();
        return responseData;

    }



    /**
     * 
     * 描述：登录名是否存在
     * 
     * @param loginName
     * @return
     * @author yangyongchuan 2016年10月14日 下午1:26:08
     * @version 1.0
     */
    private boolean hasLoginName(String loginName) {
        if (null != loginName) {
            QueryParams params = new QueryParams();
            params.addParameter("loginName", loginName);
            List<SystemUserAuth> list = systemUserAuthService.queryBaseList(params);
            return CollectionsUtils.isNotNull(list);
        }
        return true;

    }



    /**
     * 
     * 描述：员工号是否存在
     * 
     * @param sn
     * @return
     * @author yangyongchuan 2016年10月14日 下午1:26:08
     * @version 1.0
     */
    private boolean hasSn(String sn) {
        if (null != sn) {
            QueryParams params = new QueryParams();
            params.addParameter("sn", sn);
            List<SystemUser> list = systemUserService.queryBaseList(params);
            return CollectionsUtils.isNotNull(list);
        }
        return true;

    }



    /**
     * 
     * 描述：获取用户角色
     * 
     * @param getUsrRole
     * @return
     * @author yangyongchuan 2016年10月14日 下午1:18:25
     * @version 1.0
     */
    @RequestMapping("/getUsrRole")
    @ResponseBody
    public ResponseData getUsrRole(Long systemUserAuthId) {
        ResponseData responseData = null;
        if (null == systemUserAuthId || systemUserAuthId == 0) {
            responseData = ResponseData.getErrorResponse("用户id不能为null");
        }
        QueryParams params = new QueryParams();
        params.addParameter("authId", systemUserAuthId);
        List<SystemUserRole> userRoleList = systemUserRoleService.queryBaseList(params);
        responseData = ResponseData.getSuccessResponse(userRoleList);
        return responseData;

    }



    /**
     * 
     * 描述：设置用户角色
     * 
     * @param getUsrRole
     * @return
     * @author yangyongchuan 2016年10月14日 下午1:18:25
     * @version 1.0
     */
    @RequestMapping("/setUsrRole")
    @ResponseBody
    public ResponseData setUsrRole(HttpServletRequest request, Long systemUserAuthId, String roleIdStr) {
        ResponseData responseData = null;
        if (null == systemUserAuthId || systemUserAuthId == 0) {
            return ResponseData.getErrorResponse("用户id不能为null");
        }
        try {
            systemUserRoleService.setUsrRole(systemUserAuthId, roleIdStr);
            List<String> roleIdList = StringUtils.getStringList(roleIdStr, ",");
            if (roleIdList != null && roleIdList.size() > 0) {
                operationLogService
                        .addOperationLog("系统用户角色表",
                                "用户认证id:" + systemUserAuthId, "修改(用户授权)", "在系统用户角色表中增加了"
                                        + roleIdList.size() + "条记录; 用户认证id: [ " + systemUserAuthId + " ] , 角色id : [ " + roleIdStr + " ];",
                                "无");
            } else {
                operationLogService.addOperationLog("系统用户角色表", "用户认证id:" + systemUserAuthId, "修改(用户授权)",
                        "在系统用户角色表中增加了0条记录;", "无");
            }
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData = ResponseData.getErrorResponse(e.getMessage());
        }
        return responseData;

    }



    /**
     * 
     * 描述：初次登录或两个月未登录修改密码
     * 
     * @param newPassword
     * @param systemUserAuthId
     * @param oldPassword
     * @return
     * @author wangdeyou 2017年2月23日 下午4:54:29
     * @version 1.0
     */
    @RequestMapping("/passwordChange")
    public ResponseData passwordChange(@RequestParam String newPassword, @RequestParam String systemUserAuthId,
            @RequestParam String oldPassword) {
        ResponseData responseData = new ResponseData();
        QueryParams params = new QueryParams();
        SystemUserAuth systemUserAuth = new SystemUserAuth();
        Date currenDate = DateUtils.getCurrentDate();
        if (StringUtils.isEmpty(systemUserAuthId) || StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            return ResponseData.getErrorResponse("操作错误，请重新进入！");
        }
        params.put("id", systemUserAuthId);
        systemUserAuth = systemUserAuthService.queryOneBase(params);
        // 新密码加密
        String newPwd = EncodeAndDecodeUtils.MD5Encode(newPassword);
        // 查询新密码是否和数据库密码一致
        if (newPwd.equals(systemUserAuth.getPassword())) {
            return ResponseData.getErrorResponse("不能和原始密码一致,请重新输入！");
        }
        // 旧密码加密
        String oldPwd = EncodeAndDecodeUtils.MD5Encode(oldPassword);
        // 查询数据库密码是否和用户输入的旧密码一样
        if (oldPwd.equals(systemUserAuth.getPassword())) {
            systemUserAuth.setPassword(newPwd);
            systemUserAuth.setUpdatePassDate(currenDate);
            systemUserAuthService.doUpdateModel(systemUserAuth);
            responseData = ResponseData.getSuccessResponse();
        } else {
            responseData = ResponseData.getErrorResponse("原始密码错误,请重新输入");
        }
        return responseData;
    }



    /**
     * 
     * 描述：检查用户登录是否第一次登录或两个月未登录
     * 
     * @param userId
     * @return
     * @author wangdeyou 2017年2月23日 上午10:21:30
     * @version 1.0
     */
    @RequestMapping("/firstLanding")
    @ResponseBody
    public ResponseData firstLanding(@RequestParam String id) {
        ResponseData responseData = new ResponseData();
        QueryParams params = new QueryParams();
        SystemUserAuth systemUserAuth = new SystemUserAuth();
        SystemConfig systemConfig = new SystemConfig();
        List<SystemConfig> systemConfigList = new ArrayList<>();
        Date currenDate = DateUtils.getCurrentDate();
        if (StringUtils.isEmpty(id)) {
            return ResponseData.getErrorResponse("操作错误，请重新进入！");
        }
        params.put("id", id);
        systemUserAuth = systemUserAuthService.queryOneBase(params);
        QueryParams queryParams = new QueryParams();
        // 获取密码太久没修改的时间
        queryParams.put("systemKey", "update_pass_date_interval");
        systemConfigList = sysConfigService.queryBaseList(queryParams);
        systemConfig = systemConfigList.get(0);
        // 设置的修改密码过期天数
        Long OverTime = Long.parseLong(systemConfig.getSystemValue());
        Date time = systemUserAuth.getUpdatePassDate();
        if (time == null) {
            responseData.setStatus(1);// 1表示第一次登陆
            responseData.setData(systemUserAuth);
        }
        if (time != null) {
            Long day = (currenDate.getTime() - time.getTime()) / (24 * 60 * 60 * 1000);
            if (day >= OverTime) {
                responseData.setStatus(2);// 2表示两个月没登录过
                responseData.setData(systemUserAuth);
                responseData.setMessage(OverTime + "");
            }
        }
        return responseData;
    }
}
