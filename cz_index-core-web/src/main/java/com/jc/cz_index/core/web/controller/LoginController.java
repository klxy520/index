package com.jc.cz_index.core.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.SystemUserAuth;
import com.jc.cz_index.model.UserLoginFail;
import com.jc.cz_index.core.service.ISystemUserAuthService;
import com.jc.cz_index.core.service.IUserLoginFailService;

/**
 * 
 * 描述：登录控制器
 * 
 * @author yangyongchuan 2016年9月27日 下午4:57:29
 * @version 1.0
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private IUserLoginFailService  userLoginFailService;

    @Autowired
    private ISystemUserAuthService systemUserAuthService;



    @RequestMapping("/loginPage")
    public String index(HttpServletRequest request) {
        return "/login";
    }



    /**
     * 
     * 描述：登录失败
     * 
     * @param request
     * @return
     * @author wangdeyou 2017年2月21日 下午4:37:36
     * @version 1.0
     */
    @RequestMapping("/login/invalid")
    public String invalid(HttpServletRequest request) {
        UserLoginFail userLoginFail = new UserLoginFail();
        SystemUserAuth userAuth = new SystemUserAuth();
        List<UserLoginFail> userLoginFailList = new ArrayList<UserLoginFail>();
        List<SystemUserAuth> systemUserAuthDaoList = new ArrayList<SystemUserAuth>();
        String result = "用户名或密码错误！";
        String errorFive = "错误五次账户已锁定,请联系管理员";
        Date currenDate = DateUtils.getCurrentDate();
        AuthenticationException exception = (AuthenticationException) request.getAttribute(ContentUtils.SPRING_SECURITY_LAST_EXCEPTION);
        if (null != exception) {
            QueryParams params = new QueryParams();
            params.put("loginName", exception.getAuthentication().getPrincipal());
            // 查询登录失败表
            userLoginFailList = userLoginFailService.queryBaseList(params);
            // 查询系统用户认证信息表
            systemUserAuthDaoList = systemUserAuthService.queryBaseList(params);
            // 如果没有查到则添加新的记录，有则修改错误次数
            if (userLoginFailList.size() == 0 && systemUserAuthDaoList.size() != 0) {
                userLoginFail.setLoginName(exception.getAuthentication().getPrincipal() + "");
                userLoginFail.setFailTimes(1);
                userLoginFail.setLastFailTime(currenDate);
                userLoginFailService.doAddModel(userLoginFail);
            }
            // 修改错误次数和判断是否是同一天操作
            if (userLoginFailList.size() != 0 && systemUserAuthDaoList.size() != 0) {
                UserLoginFail LoginFail = userLoginFailList.get(0);
                // 获取最后修改日期
                Date lastFailTime = LoginFail.getLastFailTime();
                SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                String lastFailDateStr = s.format(lastFailTime.getTime());
                // 获取系统现在日期
                Date nowTime = new Date((new Date()).getTime());
                String currDateStr = s.format(nowTime.getTime());
                // 日期不是同一天则清零错误次数
                if (!currDateStr.equals(lastFailDateStr)) {
                    LoginFail.setFailTimes(0);
                    userLoginFailService.doUpdateModel(LoginFail);
                }
                // 在原来的失败次数上+1
                int failTimes = LoginFail.getFailTimes();
                if (failTimes < 5 && failTimes >= 0) {
                    LoginFail.setFailTimes(failTimes + 1);
                    LoginFail.setLastFailTime(currenDate);
                    userLoginFailService.doUpdateModel(LoginFail);
                }
                // 错误5次，账户锁定
                if (failTimes >= 5) {
                    userAuth = systemUserAuthDaoList.get(0);
                    userAuth.setStatus(1);
                    systemUserAuthService.doUpdateModel(userAuth);
                    request.setAttribute("errorFive", errorFive);
                    return "/login";
                }
            }
        }
        request.setAttribute("errorFive", result);
        return "/login";
    }
}
