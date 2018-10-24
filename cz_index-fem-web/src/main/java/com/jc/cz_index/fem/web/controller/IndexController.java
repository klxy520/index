package com.jc.cz_index.fem.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        return "/index";
    }

}
