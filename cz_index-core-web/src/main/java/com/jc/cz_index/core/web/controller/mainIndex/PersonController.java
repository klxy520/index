package com.jc.cz_index.core.web.controller.mainIndex;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.IPersonService;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.SystemUser;

/**
 * 
 * 描述： 基本身份信息 Controller
 * 
 * @author sunxuefeng 2017年12月26日 下午6:07:02
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/person")
public class PersonController extends BaseController {
    private static final Logger  LOG = Logger.getLogger(PersonController.class);
    @Autowired
    private IPersonService       personService;
    @Autowired
    private IOperationLogService operationLogService;



    /**
     * 
     * 描述：基本身份信息-列表页面跳转
     * 
     * @return
     * @author sunxuefeng 2017年12月25日 上午10:40:37
     * @version 1.0
     */
    @RequestMapping("/listPage")
    public String listPage() {
        return "/mainIndex/person-list";
    }



    /**
     * 
     * 描述：基本身份信息-数据列表
     * 
     * @param request
     * @param aoData
     * @return
     * @author sunxuefeng 2017年12月25日 上午10:40:48
     * @version 1.0
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, @RequestParam String aoData) {
        Map<String, Object> aoDataMap = getAoDataMap(aoData);// 解析前台框架dataTable传过来的值，格式：Map
        QueryParams params = new QueryParams(); // 设置条件查询 QueryParam
        setFilterParam(aoDataMap, params);
        params.put("delFalg", ContentUtils.INFO_NOT_DELETE);
        int start = StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString());// 分页信息
        int size = StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString());
        PagedList<Person> list = personService.queryPagedBaseList(params, start, size);
        Map<String, Object> result = new HashMap<String, Object>();// 封装页面显示信息
        result.put("iTotalRecords", list.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", list.getTotalSize());
        result.put("aaData", list.getList());
        return result;
    }



    /***
     * 
     * 描述：基本身份信息-根据基本身份信息id删除基本身份信息
     * 
     * @param id
     * @param name
     * @param idCard
     * @return
     * @author sunxuefeng 2017年12月25日 下午3:35:40
     * @version 1.0
     */
    @RequestMapping(value = "/deletePersonById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deletePersonById(Long id, String name, String idCard) {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            responseData = personService.deletePersonById(id, systemUser);
            operationLogService.addOperationLog("基本身份信息", id + "", "删除基本身份信息",
                    "删除了一条基本身份信息; id: [ " + id + "] , 具体信息 :姓名为:[" + name + "]," + "身份证号为:[" + idCard + "]", "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage(e.getMessage());
            LOG.error("基本身份信息删除 失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /***
     * 
     * 描述：基本身份信息-根据基本身份信息id注销基本身份信息
     * 
     * @param id
     * @param name
     * @param idCard
     * @return
     * @author sunxuefeng 2017年12月25日 下午3:35:40
     * @version 1.0
     */
    @RequestMapping(value = "/cancelPersonById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData cancelPersonById(Long id, String name, String idCard) {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            responseData = personService.cancelAndRecoveryPersonById(id, systemUser, ContentUtils.PERSON_STATUS_DISABLE);
            operationLogService.addOperationLog("基本身份信息", id + "", "注销基本身份信息",
                    "注销了一条基本身份信息; id: [ " + id + "] , 具体信息 :姓名为:[" + name + "]," + "身份证号为:[" + idCard + "]", "无");
            responseData.setMessage("基本身份信息注销成功");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("基本身份信息注销失败");
            LOG.error("基本身份信息注销失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /***
     * 
     * 描述：基本身份信息-根据基本身份信息id恢复基本身份信息
     * 
     * @param id
     * @param name
     * @param idCard
     * @return
     * @author sunxuefeng 2017年12月25日 下午3:35:40
     * @version 1.0
     */
    @RequestMapping(value = "/recoveryPersonById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData recoveryPersonById(Long id, String name, String idCard) {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            responseData = personService.cancelAndRecoveryPersonById(id, systemUser, ContentUtils.PERSON_STATUS_ENABLE);
            operationLogService.addOperationLog("基本身份信息", id + "", "恢复基本身份信息",
                    "恢复了一条基本身份信息; id: [ " + id + "] , 具体信息 :姓名为:[" + name + "]," + "身份证号为:[" + idCard + "]", "无");
            responseData.setMessage("基本身份信息恢复成功");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("基本身份信息恢复失败");
            LOG.error("基本身份信息恢复失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 
     * 描述：基本身份信息-添加单个基本身份信息
     * 
     * @param person
     * @return
     * @author sunxuefeng 2017年12月27日 下午4:17:50
     * @version 1.0
     */
    @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addPerson(Person person) {
        ResponseData responseData = new ResponseData();
        try {
            SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
            responseData = personService.addPerson(person, systemUser);
            Long id = person.getId();
            operationLogService.addOperationLog("基本身份信息", id + "", "添加基本身份信息",
                    "添加了一条基本身份信息; id: [ " + id + "] , 具体信息 :姓名为:[" + person.getPersonName() + "]," + "身份证号为:[" + person.getIdCard() + "]",
                    "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("基本身份信息添加失败");
            LOG.error("基本身份添加 失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 基本身份信息-修改单个基本身份信息 描述：
     * 
     * @param residentAcquisition
     * @return
     * @author sunxuefeng 2017年12月28日 上午10:36:36
     * @version 1.0
     */
    @RequestMapping(value = "/updatePerson", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updatePerson(Person person) {
        ResponseData responseData = new ResponseData();
        try {
            SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
            responseData = personService.updatePerson(person, systemUser);
            Long id = person.getId();
            operationLogService.addOperationLog("基本身份信息", id + "", "修改基本身份信息",
                    "修改了一条基本身份信息; id: [ " + id + "] , 具体信息 :姓名为:[" + person.getPersonName() + "]," + "身份证号为:[" + person.getIdCard() + "]",
                    "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("基本身份信息修改失败");
            LOG.error("基本身份信息修改失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 
     * 描述：基本身份信息-根据id查询单个基本身份信息的详情信息
     * 
     * @param id
     * @return
     * @author sunxuefeng 2017年12月28日 上午11:11:10
     * @version 1.0
     */
    @RequestMapping("/queryPersonDetails")
    public ModelAndView queryPersonDetails(Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Person person = personService.getModelDetailById(id);
        modelAndView.addObject("person", person);
        modelAndView.setViewName("/mainIndex/person-details");
        return modelAndView;
    }
    /***
     * 
     * 描述：根据身份证号查询基本身份信息是是否存在
     * 
     * @param idCard
     * @return
     * @author sunxuefeng 2017年10月10日 上午10:14:31
     * @version 1.0
     */
    @RequestMapping("/queryPersonExistence")
    @ResponseBody
    public ResponseData queryPersonExistence(String idCard) {
        ResponseData responseData = new ResponseData();
        Boolean queryStaute =personService.queryPersonByidCardExistence(idCard);
        if (!queryStaute) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("该身份证号已存在");
        } else {
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        }
        return responseData;
    }
}
