package com.jc.cz_index.core.web.controller.roleField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.core.web.controller.residentBaseinfo.ResidentBaseinfoController;
import com.jc.cz_index.core.web.security.MyInvocationSecurityMetadataSource;
import com.jc.cz_index.model.RoleField;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.model.TableField;
import com.jc.cz_index.core.service.IOperationLogService;

import com.jc.cz_index.core.service.IRoleFieldService;

@Controller
@RequestMapping(value = "/roleField")
public class RoleFieldController extends BaseController {
    private static final Logger                LOG = Logger.getLogger(ResidentBaseinfoController.class);
    @Autowired
    private IRoleFieldService                  roleFieldService;
    @Autowired
    private IOperationLogService               operationLogService;
    @Autowired
    private MyInvocationSecurityMetadataSource securityMetadataSource;



    /**
     * 
     * 描述：列表页面跳转
     * 
     * @return
     * @author sunxuefeng 2017年8月28日 下午1:17:10
     * @version 1.0
     */
    @RequestMapping("/listPage")
    public String listPage() {
        return "/residentExtend/residentExtendList";
    }



    /**
     * 
     * 描述 获取角色查看信息字段权限
     * 
     * @param roleId
     * @return
     * @author yangjunhui 2017年9月10日 下午5:07:51
     * @version 1.0
     */
    @RequestMapping("/topField")
    @ResponseBody
    public ResponseData getRoleField(Long roleId) {
        List<RoleField> roleField = roleFieldService.getRoleFieldIds(roleId);// 获取角色可以查看的字段
        List<TableField> allFields = roleFieldService.getAllFieldInfo();// 获取所有字段信息
        Map<String, Object> result = new HashMap<String, Object>();// 封装页面显示信息
        result.put("roleField", roleField);
        result.put("allFields", allFields);
        return ResponseData.getSuccessResponse(result);
    }



    /**
     * 
     * 描述： 设置角色查看字段权限
     * 
     * @param roleId
     *            角色id
     * @param fieldStr
     *            角色可以查看的字段 id字符串“1,2,3，”
     * @return
     * @author yangjunhui 2017年9月10日 下午5:08:57
     * @version 1.0
     */
    @RequestMapping("/setRoleField")
    @ResponseBody
    public ResponseData setRoleField(Long roleId, String fieldStr) {
        ResponseData responseData = null;
        if (null == roleId || roleId == 0) {
            responseData = ResponseData.getErrorResponse("角色id不能为null");
        }
        try {
            roleFieldService.setRoleField(roleId, fieldStr);
            List<String> fieldIdList = StringUtils.getStringList(fieldStr, ",");
            if (fieldIdList != null && fieldIdList.size() > 0) {
                operationLogService.addOperationLog("权限与看查看字段权限表", "角色id" + roleId + "", "机构授权", "在权限与字段查看权限表(role_field)中为角色[ " + roleId
                        + " ]添加了" + fieldIdList.size() + "条记录; 角色id : [ " + roleId + " ] , 课查看信息字段id: [ " + fieldStr + " ]", "无");
            } else {
                operationLogService.addOperationLog("权限与看查看字段权限表", "角色id" + roleId + "", "机构授权",
                        "在权限与字段查看权限表(role_field)中为机构[ " + roleId + " ]添加了0条记录;", "无");
            }
            // 刷新菜单
            securityMetadataSource.initAllConfigAttributes();
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData = ResponseData.getErrorResponse(e.getMessage());
        }
        return responseData;
    }



    /**
     * 
     * 描述：
     * 
     * @param roleId
     * @return
     * @author yangjunhui 2017年9月12日 下午10:22:08
     * @version 1.0
     */
    @RequestMapping("/getloginUserField")
    @ResponseBody
    public ResponseData getloginUserField() {
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        Long orgId = loginUser.getOrganizationId();
        List<TableField> roleField = roleFieldService.getloginUserField(orgId);// 获取角色可以查看的字段
        Map<String, Object> result = new HashMap<String, Object>();// 封装页面显示信息
        boolean isOrg = ((loginUser.getOrganizationId() == null) || (loginUser.getOrganizationId() == 0) ? false : true);
        result.put("isOrg", isOrg);
        result.put("roleField", roleField);
        return ResponseData.getSuccessResponse(result);
    }
}
