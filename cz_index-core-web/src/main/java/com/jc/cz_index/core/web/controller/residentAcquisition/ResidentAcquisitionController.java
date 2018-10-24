package com.jc.cz_index.core.web.controller.residentAcquisition;

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
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.ResidentAcquisition;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.IResidentAcquisitionService;

/**
 * 
 * 描述：居民信息采集Controller
 * 
 * @author sunxuefeng 2017年10月20日 上午10:12:04
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/residentacquisition")
public class ResidentAcquisitionController extends BaseController {
    private static final Logger         LOG = Logger.getLogger(ResidentAcquisitionController.class);
    @Autowired
    private IResidentAcquisitionService residentAcquisitionService;
    @Autowired
    private IOperationLogService        operationLogService;



    /**
     * 
     * 描述：列表页面跳转
     * 
     * @return
     * @author sunxuefeng 2017年10月20日 上午10:29:47
     * @version 1.0
     */
    @RequestMapping("/listPage")
    public String listPage() {
        return "/residentAcquisition/residentAcquisition_list";
    }



    /**
     * 
     * 描述：查询居民信息采集列表页面的数据
     * 
     * @param request
     * @param aoData
     * @return
     * @author sunxuefeng 2017年10月20日 上午11:31:18
     * @version 1.0
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, @RequestParam String aoData) {
        Map<String, Object> aoDataMap = getAoDataMap(aoData);// 解析前台框架dataTable传过来的值，格式：Map
        QueryParams params = new QueryParams(); // 设置条件查询 QueryParam
        setFilterParam(aoDataMap, params);
        int start = StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString());// 分页信息
        int size = StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString());
        PagedList<ResidentAcquisition> list = residentAcquisitionService.queryResidentAcquisitionList(params, start, size);
        Map<String, Object> result = new HashMap<String, Object>();// 封装页面显示信息
        result.put("iTotalRecords", list.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", list.getTotalSize());
        result.put("aaData", list.getList());
        return result;
    }



    /**
     * 
     * 描述:根据ID查询单个居民信息的详细信息
     * 
     * @param id
     * @return
     * @author sunxuefeng 2017年10月23日 上午11:04:21
     * @version 1.0
     */
    @RequestMapping("/queryresidentAcquisitionDetails")
    public ModelAndView queryresidentAcquisitionDetails(Long id) {
        ModelAndView modelAndView = new ModelAndView();
        ResidentAcquisition residentAcquisition = residentAcquisitionService.getModelDetailById(id);
        modelAndView.addObject("residentAcquisition", residentAcquisition);
        modelAndView.setViewName("/residentAcquisition/residentAcquisition-details");
        return modelAndView;
    }



    /**
     * 描述：根据id删除居民采集信息
     * 
     * @param id
     * @return
     * @author sunxuefeng 2017年10月23日 下午2:49:34
     * @version 1.0
     */
    @RequestMapping(value = "/deleteResidentAcquisitionById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteResidentAcquisitionById(Long id, String name, String identityNumber) {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            responseData = residentAcquisitionService.deleteResidentAcquisitionById(id, systemUser);
            operationLogService.addOperationLog("居民采集信息表", id + "", "删除居民采集信息",
                    "删除了一条居民采集信息; id: [ " + id + "] , 具体信息 :居民姓名为:[" + name + "]," + "身份证号为:[" + identityNumber + "]", "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("居民采集信息删除失败");
            LOG.error("居民采集信息删除 失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 
     * 描述：添加单个居民采集信息
     * 
     * @param residentAcquisition
     * @return
     * @author sunxuefeng 2017年10月25日 下午2:48:09
     * @version 1.0
     */
    @RequestMapping(value = "/addResidentAcquisition", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addResidentAcquisition(ResidentAcquisition residentAcquisition) {
        ResponseData responseData = new ResponseData();
        try {
            SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
            responseData = residentAcquisitionService.addResidentAcquisition(residentAcquisition, systemUser);
            Long id = residentAcquisition.getId();
            operationLogService.addOperationLog("居民采集信息表", id + "", "添加居民采集信息", "添加了一条居民采集信息; id: [ " + id + "] , 具体信息 :居民姓名为:["
                    + residentAcquisition.getName() + "]," + "身份证号为:[" + residentAcquisition.getIdentityNumber() + "]", "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("居民采集信息添加失败");
            LOG.error("居民采集信息添加 失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /***
     * 
     * 描述：修改单个居民采集信息
     * 
     * @param residentAcquisition
     * @return
     * @author sunxuefeng 2017年10月25日 下午4:05:07
     * @version 1.0
     */
    @RequestMapping(value = "/updateResidentAcquisition", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateResidentAcquisition(ResidentAcquisition residentAcquisition) {
        ResponseData responseData = new ResponseData();
        try {
            SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
            responseData = residentAcquisitionService.updateResidentAcquisition(residentAcquisition, systemUser);
            Long id = residentAcquisition.getId();
            operationLogService.addOperationLog("居民采集信息表", id + "", "修改居民采集信息", "修改了一条居民采集信息; id: [ " + id + "] , 具体信息 :居民姓名为:["
                    + residentAcquisition.getName() + "]," + "身份证号为:[" + residentAcquisition.getIdentityNumber() + "]", "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("居民采集信息修改失败");
            LOG.error("居民采集信息修改失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 
     * 描述：根据身份证号查询居民采集信息是否存在
     * 
     * @param identityNumber
     * @return
     * @author sunxuefeng 2017年10月26日 上午10:24:07
     * @version 1.0
     */
    @RequestMapping("/queryResidentsInfoExistence")
    @ResponseBody
    public ResponseData queryResidentsInfoExistence(String identityNumber) {
        ResponseData responseData = new ResponseData();
        Boolean falg = residentAcquisitionService.queryResidentsInfoByIdentityNumberExistence(identityNumber);
        if (falg) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("该身份证号已存在");
        } else {
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        }
        return responseData;
    }
}
