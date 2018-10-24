package com.jc.cz_index.core.web.controller.residentsInfo;

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
import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.resident.ResidentsInfoDto;
import com.jc.cz_index.model.ResidentsInfo;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.IResidentsInfoService;

/***
 * 
 * 描述：基本信息管理Controller
 * 
 * @author sunxuefeng 2017年9月27日 下午2:01:13
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/residentsInfo")
public class ResidentsInfoController extends BaseController {
    private static final Logger   LOG = Logger.getLogger(ResidentsInfoImportController.class);
    @Autowired
    private IResidentsInfoService residentsInfoService;
    @Autowired
    private IOperationLogService  operationLogService;



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
        return "/residentsInfo/residentsInfo-list";
    }



    /**
     * 
     * 描述：居民基本数据列表
     * 
     * @param request
     * @param aoData
     * @return
     * @author sunxuefeng 2017年8月28日 下午2:44:52
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
        SystemUser user = (SystemUser) WebUtils.getLoginUser();
        PagedList<ResidentsInfoDto> list = residentsInfoService.queryResidentBaseinfoList(params, user, start, size);
        Map<String, Object> result = new HashMap<String, Object>();// 封装页面显示信息
        result.put("iTotalRecords", list.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", list.getTotalSize());
        result.put("aaData", list.getList());
        return result;
    }



    /***
     * 
     * 描述：根据ID删除居民健康卡基本信息
     * 
     * @return
     * @author sunxuefeng 2017年8月30日 下午2:19:03
     * @version 1.0
     */
    @RequestMapping(value = "/deleteResidentsinfoById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteResidentsinfoById(Long id, String name, String identityNumber) {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            responseData = residentsInfoService.deleteResidentsinfoById(id, systemUser);
            operationLogService.addOperationLog("居民健康卡基本信息表", id + "", "删除居民信息",
                    "删除了一条居民健康卡基本信息; id: [ " + id + "] , 具体信息 :居民姓名为:[" + name + "]," + "身份证号为:[" + identityNumber + "]", "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            LOG.error("居民健康卡基本信息删除 失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("居民健康卡基本信息删除失败");
        }
        return responseData;
    }



    /**
     * 
     * 描述：列表页面跳转(通过身份证号或者居民健康卡号查询居民信息)
     * 
     * @return
     * @author sunxuefeng 2017年8月28日 下午1:17:10
     * @version 1.0
     */
    @RequestMapping("/otherResidentsInfoQuery")
    public String otherResidentsInfoQuery() {
        return "/residentsInfo/other_residentsInfo_query";
    }



    /***
     * 
     * 描述：通过身份证号或者居民健康卡号查询居民信息
     * 
     * @param params
     * @param start
     * @param size
     * @return
     * @author sunxuefeng 2017年9月1日 下午1:48:17
     * @version 1.0
     */
    @RequestMapping("/queryOtherResidentsInfoList")
    @ResponseBody
    public Map<String, Object> queryOtherResidentsInfoList(HttpServletRequest request, @RequestParam String aoData) {
        Map<String, Object> aoDataMap = getAoDataMap(aoData);// 解析前台框架dataTable传过来的值，格式：Map
        QueryParams params = new QueryParams(); // 设置条件查询 QueryParam
        String number = aoDataMap.get("number").toString();
        params.put("identityNumber", number);
        params.put("bankCardNumber", number);
        int start = StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString());// 分页信息
        int size = StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString());
        PagedList<ResidentsInfo> list = residentsInfoService.queryOtherResidentsInfoList(params, start, size);
        Map<String, Object> result = new HashMap<String, Object>();// 封装页面显示信息
        result.put("iTotalRecords", list.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", list.getTotalSize());
        result.put("aaData", list.getList());
        return result;
    }



    /**
     * 
     * 描述：把其他居民健康卡信息添加到我的管理权限范围之内
     * 
     * @param residentId
     * @return
     * @author sunxuefeng 2017年9月1日 下午2:48:57
     * @version 1.0
     */
    @RequestMapping(value = "/addManagementScope", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addManagementScope(Long residentId) {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            responseData = residentsInfoService.addManagementScope(systemUser, residentId);
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            LOG.error("居民健康卡信息添加到我的管理权限范围之内失败,异常信息:" + e.getMessage());
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("添加失败");
        }
        return responseData;
    }



    /**
     * 
     * 描述：根据id查询详细信息
     * 
     * @param id
     * @return
     * @author yangjunhui 2017年9月27日 下午5:02:54
     * @version 1.0
     */
    @RequestMapping("/queryResidentsInfoDetails")
    public ModelAndView queryResidentsInfoDetails(Long id) {
        ModelAndView modelAndView = new ModelAndView();
        ResidentsInfo residentInfo = residentsInfoService.getModelDetailById(id);
        modelAndView.addObject("residentInfo", residentInfo);
        modelAndView.setViewName("/residentsInfo/residentsInfo-details");
        return modelAndView;
    }



    /**
     * 
     * 描述：添加
     * 
     * @param residentInfo
     * @return
     * @throws BaseException
     * @author yangjunhui 2017年9月28日 下午1:22:58
     * @version 1.0
     */
    @ResponseBody
    @RequestMapping("/addResidentsInfo")
    public ResponseData addExtendInfo(ResidentsInfo residentInfo) throws BaseException {
        ResponseData responseData = new ResponseData();
        if (residentInfo.getId() == null) {
            responseData = residentsInfoService.addResidentsInfo(residentInfo);
        } else {
            responseData = residentsInfoService.updateResidentsInfo(residentInfo);
        }
        return responseData;
    }



    /**
     * 
     * 描述：根据ID查询居民基本信息
     * 
     * @param residentInfo
     * @return
     * @throws BaseException
     * @author yangjunhui 2017年9月28日 下午1:22:58
     * @version 1.0
     */
    @ResponseBody
    @RequestMapping("/queryResidentinfoForUpdate")
    public ResponseData queryResidentinfoForUpdate(Long id) {
        ResidentsInfo residentInfo = residentsInfoService.getModelDetailById(id);
        return ResponseData.getSuccessResponse(residentInfo);
    }



    /***
     * 
     * 描述：根据身份证号查询居民信息是是否存在
     * 
     * @param identityNumber
     * @return
     * @author sunxuefeng 2017年10月10日 上午10:14:31
     * @version 1.0
     */
    @RequestMapping("/queryResidentsInfoExistence")
    @ResponseBody
    public ResponseData queryResidentsInfoExistence(String identityNumber) {
        ResponseData responseData = new ResponseData();
        Boolean queryStaute = residentsInfoService.queryResidentsInfoByIdentityNumberExistence(identityNumber);
        if (!queryStaute) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("该身份证号已存在");
        } else {
            responseData.setStatus(ResponseData.SUCCESS_STATUS);
        }
        return responseData;
    }
}
