package com.jc.cz_index.core.web.controller.hospital;

import java.util.HashMap;
import java.util.List;
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
import com.jc.cz_index.core.service.IHospitalService;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Hospital;
import com.jc.cz_index.model.SystemUser;

/***
 * 
 * 描述：医院机构管理Controller
 * 
 * @author sunxuefeng 2018年1月3日 下午1:37:24
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/hospital")
public class HospitalController extends BaseController {

    private static final Logger  LOG = Logger.getLogger(HospitalController.class);
    @Autowired
    private IHospitalService     hospitalService;
    @Autowired
    private IOperationLogService operationLogService;



    /**
     * 
     * 描述：页面跳转
     * 
     * @return
     * @author sunxuefeng 2018年1月3日 下午1:39:55
     * @version 1.0
     */
    @RequestMapping("/listPage")
    public String listPage() {
        return "/hospital/hospital-list";
    }



    /**
     * 
     * 描述：数据列表
     * 
     * @param request
     * @param aoData
     * @return
     * @author sunxuefeng 2018年1月3日 下午1:40:02
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
        PagedList<Hospital> list = hospitalService.queryPagedBaseList(params, start, size);
        Map<String, Object> result = new HashMap<String, Object>();// 封装页面显示信息
        result.put("iTotalRecords", list.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", list.getTotalSize());
        result.put("aaData", list.getList());
        return result;
    }



    /***
     * 
     * 描述：根据ID删除机构
     * 
     * @param id
     * @param name
     * @param idCard
     * @return
     * @author sunxuefeng 2018年1月3日 下午4:33:55
     * @version 1.0
     */
    @RequestMapping(value = "/deleteHospitalById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteHospitalById(Long id, String name) {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            responseData = hospitalService.deleteHospitalById(id, systemUser);
            operationLogService.addOperationLog("机构管理", id + "", "删除医院信息", "删除了一条医院信息; id: [ " + id + "] , 具体信息 :医院名称为:[" + name + "],",
                    "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("医院信息删除失败");
            LOG.error("医院信息删除 失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /***
     * 
     * 描述：禁用医院信息
     * 
     * @param id
     * @param name
     * @return
     * @author sunxuefeng 2018年1月3日 下午5:33:07
     * @version 1.0
     */
    @RequestMapping(value = "/disableHospitalById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData disableHospitalById(Long id, String name) {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            responseData = hospitalService.disableAndEnableHospitalById(id, systemUser, ContentUtils.DB_FIELD_STATUS_DISABLE);
            operationLogService.addOperationLog("机构管理", id + "", "禁用医院信息", "禁用了一条医院信息; id: [ " + id + "] , 具体信息 :医院名称为:[" + name + "],",
                    "无");
            responseData.setMessage("禁用医院信息成功");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("禁用医院信息失败");
            LOG.error("禁用医院信息失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 
     * 描述：启用医院信息
     * 
     * @param id
     * @param name
     * @return
     * @author sunxuefeng 2018年1月3日 下午5:33:50
     * @version 1.0
     */
    @RequestMapping(value = "/EnableHospitalById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData EnableHospitalById(Long id, String name) {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            responseData = hospitalService.disableAndEnableHospitalById(id, systemUser, ContentUtils.DB_FIELD_STATUS_ENABLE);
            operationLogService.addOperationLog("机构管理", id + "", "启用医院信息", "启用了一条医院信息; id: [ " + id + "] , 具体信息 :医院名称为:[" + name + "],",
                    "无");
            responseData.setMessage("启用医院信息成功");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("启用医院信息成功失败");
            LOG.error("启用医院信息成功失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 
     * 描述：新增医院信息
     * 
     * @param hospital
     * @return
     * @author sunxuefeng 2018年1月4日 上午10:16:09
     * @version 1.0
     */
    @RequestMapping(value = "/addHospital", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addHospital(Hospital hospital) {
        ResponseData responseData = new ResponseData();
        try {
            SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
            responseData = hospitalService.addHospital(hospital, systemUser);
            Long id = hospital.getId();
            operationLogService.addOperationLog("机构管理", id + "", "新增医院信息",
                    "新增了一条医院信息; id: [ " + id + "] , 具体信息 :医院名称为:[" + hospital.getName() + "],", "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("医院信息添加失败");
            LOG.error("医院信息添加 失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 
     * 描述 编辑单个医院信息
     * 
     * @param hospital
     * @return
     * @author sunxuefeng 2018年1月4日 上午10:50:32
     * @version 1.0
     */
    @RequestMapping(value = "/updateHospital", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateHospital(Hospital hospital) {
        ResponseData responseData = new ResponseData();
        try {
            SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
            responseData = hospitalService.updateHospital(hospital, systemUser);
            Long id = hospital.getId();
            operationLogService.addOperationLog("机构管理", id + "", "修改医院信息",
                    "修改了一条医院信息; id: [ " + id + "] , 具体信息 :医院名称为:[" + hospital.getName() + "],", "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("医院信息修改失败");
            LOG.error("医院信息修改失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 
     * 描述:查询单个医院的详细信息
     * 
     * @param id
     * @return
     * @author sunxuefeng 2018年1月4日 上午9:55:12
     * @version 1.0
     */
    @RequestMapping("/queryHospitalDetails")
    public ModelAndView queryHospitalDetails(Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Hospital hospital = hospitalService.getModelDetailById(id);
        modelAndView.addObject("hospital", hospital);
        modelAndView.setViewName("/hospital/hospital-details");
        return modelAndView;
    }



    /**
     * 
     * 描述：查询所有医院机构
     * 
     * @return
     * @author sunxuefeng 2018年1月5日 下午1:52:16
     * @version 1.0
     */
    @RequestMapping(value = "/loadAllHospital", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData loadAllHospital() {
        ResponseData responseData = new ResponseData();
        QueryParams params = new QueryParams();
        List<Hospital> list = hospitalService.queryBaseList(params);
        if (list != null && list.size() > 0) {
            responseData.setData(list);
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        } 
        return responseData;
    }
    /***
     * 
     * 描述：根据医院名称查询查询医院否存在
     * 
     * @param name
     * @return
     * @author sunxuefeng 2017年10月10日 上午10:14:31
     * @version 1.0
     */
    @RequestMapping("/queryHospitalByNameExistence")
    @ResponseBody
    public ResponseData queryHospitalByNameExistence(String name) {
        ResponseData responseData = new ResponseData();
        Boolean queryStaute =hospitalService.queryHospitalByNameExistence(name);
        if (!queryStaute) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("该医院已存在");
        } else {
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        }
        return responseData;
    }
    /***
     * 
     * 描述：根据医院key查询查询医院否存在
     * 
     * @param key
     * @return
     * @author sunxuefeng 2017年10月10日 上午10:14:31
     * @version 1.0
     */
    @RequestMapping("/queryHospitalByKeyExistence")
    @ResponseBody
    public ResponseData queryHospitalByKeyExistence(String key) {
        ResponseData responseData = new ResponseData();
        Boolean queryStaute =hospitalService.queryHospitalByKeyExistence(key);
        if (!queryStaute) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("该key已存在");
        } else {
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        }
        return responseData;
    }
}
