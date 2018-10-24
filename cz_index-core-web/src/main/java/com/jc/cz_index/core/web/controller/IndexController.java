package com.jc.cz_index.core.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.model.SystemMenu;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.impl.SystemMenuService;

/**
 * 
 * 描述：首页
 * 
 * @author yangyongchuan 2016年5月18日 下午4:56:08
 * @version 0.1
 * @since 0.1
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private SystemMenuService systemMenuService;



    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        SystemUser systemUser = (SystemUser) request.getSession().getAttribute(ContentUtils.SESSION_USER);
        List<SystemMenu> menuList = systemMenuService.queryMenu(systemUser);
        request.setAttribute("menuList", menuList);
        return "/index";
    }



    @RequestMapping("/testIndex")
    public String index2(HttpServletRequest request) {
        return "/index";
    }
}
