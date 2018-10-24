package com.jc.cz_index.core.web.controller.mainIndex;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.service.IContactService;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.IPersonService;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Contact;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.SystemUser;

/***
 * 
 * 描述：联系人管理
 * 
 * @author sunxuefeng 2018年1月2日 上午10:03:46
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/contact")
public class ContactController extends BaseController {

    private static final Logger  LOG = Logger.getLogger(ContactController.class);
    @Autowired
    private IContactService      contactService;
    @Autowired
    private IOperationLogService operationLogService;
    @Autowired
    private IPersonService       personService;



    /**
     * 
     * 描述：联系人管理--列表页面跳转
     * 
     * @param id
     * @param model
     * @return
     * @author sunxuefeng 2018年1月2日 上午10:04:28
     * @version 1.0
     */
    @RequestMapping("/listPage")
    public String listPage(@RequestParam Long id, Model model) {
        Person person = personService.getModelById(id);
        model.addAttribute("person", person);
        return "/mainIndex/contact-list";
    }



    /**
     * 
     * 描述：联系人管理-数据列表
     * 
     * @param request
     * @param aoData
     * @return
     * @author sunxuefeng 2018年1月2日 上午10:05:29
     * @version 1.0
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, @RequestParam String aoData) {
        Map<String, Object> aoDataMap = getAoDataMap(aoData);// 解析前台框架dataTable传过来的值，格式：Map
        QueryParams params = new QueryParams(); // 设置条件查询 QueryParam
        setFilterParam(aoDataMap, params);
        params.put("delFalg", 0);
        int start = StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString());// 分页信息
        int size = StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString());
        PagedList<Contact> list = contactService.queryPagedBaseList(params, start, size);
        Map<String, Object> result = new HashMap<String, Object>();// 封装页面显示信息
        result.put("iTotalRecords", list.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", list.getTotalSize());
        result.put("aaData", list.getList());
        return result;
    }



    /***
     * 
     * 描述：联系人管理--根据ID删除联系人
     * 
     * @param id
     * @return
     * @author sunxuefeng 2018年1月2日 下午1:28:28
     * @version 1.0
     */
    @RequestMapping(value = "/deleteContactById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteContactById(Long id) {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            responseData = contactService.deleteContactById(id, systemUser);
            operationLogService.addOperationLog("联系人信息", id + "", "删除联系人信息", "删除了一条联系人信息; id: [ " + id + "] ", "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("联系人删除失败");
            LOG.error("联系人删除 失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 
     * 描述：联系人管理--添加单个联系人
     * 
     * @param contact
     * @return
     * @author sunxuefeng 2018年1月2日 下午1:33:24
     * @version 1.0
     */
    @RequestMapping(value = "/addContact", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addContact(Contact contact) {
        ResponseData responseData = new ResponseData();
        try {
            SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
            responseData = contactService.addContact(contact, systemUser);
            Long id = contact.getId();
            operationLogService.addOperationLog("联系人信息", id + "", "添加联系人息",
                    "添加了一条地址信息; id: [ " + id + "],联系人类别为:[" + contact.getCertificateTypeCode() + "] ", "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("联系人添加失败");
            LOG.error("联系人添加 失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 
     * 描述：联系人管理--修改单个联系人
     * 
     * @param contact
     * @return
     * @author sunxuefeng 2018年1月2日 下午1:36:43
     * @version 1.0
     */
    @RequestMapping(value = "/updateContact", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateContact(Contact contact) {
        ResponseData responseData = new ResponseData();
        try {
            SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
            responseData = contactService.updateContact(contact, systemUser);
            Long id = contact.getId();
            operationLogService.addOperationLog("联系人信息", id + "", "修改联系人信息",
                    "修改了一条地址信息; id: [ " + id + "],联系人类别为:[" + contact.getCertificateTypeCode() + "] ", "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("联系人修改失败");
            LOG.error("联系人修改失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }
    /**
     * 
     * 描述：根据ID查询联系人的详细信息
     * @param id
     * @return
     * @author sunxuefeng 2018年1月2日 下午2:51:58 
     * @version 1.0
     */
    @RequestMapping("/queryContactDetails")
    public ModelAndView queryContactDetails(Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Contact contact = contactService.getModelDetailById(id);
        modelAndView.addObject("contact", contact);
        modelAndView.setViewName("/mainIndex/contact-details");
        return modelAndView;
    }
}
