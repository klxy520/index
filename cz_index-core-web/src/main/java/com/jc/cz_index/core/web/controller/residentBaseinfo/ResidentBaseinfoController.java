package com.jc.cz_index.core.web.controller.residentBaseinfo;

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
import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.resident.ResidentBaseinfoDto;
import com.jc.cz_index.model.AdministrativeDivision;
import com.jc.cz_index.model.ResidentBaseinfo;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.IAdministrativeDivisionService;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.IResidentBaseinfoService;

/***
 * 
 * 描述：居民健康卡基本信息Controller
 * 
 * @author sunxuefeng 2017年8月30日 上午9:28:09
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/residentBase")
public class ResidentBaseinfoController extends BaseController {
    private static final Logger      LOG = Logger.getLogger(ResidentBaseinfoController.class);
    @Autowired
    private IResidentBaseinfoService residentBaseinfoService;
    @Autowired
    private IOperationLogService     operationLogService;
    @Autowired
    private IAdministrativeDivisionService administrativeDivisionService;



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
        return "/resident/resident-list";
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
        PagedList<ResidentBaseinfoDto> list = residentBaseinfoService.queryResidentBaseinfoList(params, user, start, size);
        Map<String, Object> result = new HashMap<String, Object>();// 封装页面显示信息
        result.put("iTotalRecords", list.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", list.getTotalSize());
        result.put("aaData", list.getList());
        return result;
    }



    /***
     * 
     * 描述：添加/修改居民健康卡基本信息
     * 
     * @param residentBaseinfo
     * @return
     * @author sunxuefeng 2017年8月30日 下午2:19:03
     * @version 1.0
     */
    @RequestMapping(value = "/addResidentBaseinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addResidentBaseinfo(ResidentBaseinfo residentBaseinfo) {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        String tag = "";
        try {
            Long id = residentBaseinfo.getId();
            if (id != null && id != 0) {
                tag = "修改";
                responseData = residentBaseinfoService.updateResidentBaseinfo(residentBaseinfo, systemUser);
                operationLogService.addOperationLog("居民健康卡基本信息表", id + "", "修改居民信息", "修改了一条居民健康卡基本信息; id: [ " + id + "] , 具体信息 :居民姓名为:["
                        + residentBaseinfo.getRealName() + "]," + "身份证号为:[" + residentBaseinfo.getIdNumber() + "]", "无");
            } else {
                tag = "添加";
                responseData = residentBaseinfoService.addResidentBaseinfo(residentBaseinfo, systemUser);
                operationLogService.addOperationLog("居民健康卡基本信息表", residentBaseinfo.getId() + "", "保存居民信息",
                        " 增加了一条居民健康卡基本信息; id: [ " + residentBaseinfo.getId() + "] , 具体信息 :居民姓名为:[" + residentBaseinfo.getRealName() + "],"
                                + "身份证号为:[" + residentBaseinfo.getIdNumber() + "]",
                        "无");
            }
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (BaseException e) {
            LOG.error("居民健康卡基本信息" + tag + "失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("居民健康卡基本信息" + tag + "失败");
        }
        return responseData;
    }



    /***
     * 
     * 描述：根据ID删除居民健康卡基本信息
     * 
     * @param residentBaseinfo
     * @return
     * @author sunxuefeng 2017年8月30日 下午2:19:03
     * @version 1.0
     */
    @RequestMapping(value = "/deleteResidentBaseinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteResidentBaseinfo(Long id, String realName, String idNumber) {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            responseData = residentBaseinfoService.deleteResidentBaseinfoById(id, systemUser);
            operationLogService.addOperationLog("居民健康卡基本信息表", id + "", "删除居民信息",
                    "删除了一条居民健康卡基本信息; id: [ " + id + "] , 具体信息 :居民姓名为:[" + realName + "]," + "身份证号为:[" + idNumber + "]", "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (BaseException e) {
            LOG.error("居民健康卡基本信息删除 失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("居民健康卡基本信息删除失败");
        }
        return responseData;
    }



    /***
     * 
     * 描述：根据ID查询居民健康卡信息
     * 
     * @param id
     * @return
     * @author sunxuefeng 2017年8月30日 下午2:19:03
     * @version 1.0
     */
    @RequestMapping("/queryResidentBaseinfoById")
    @ResponseBody
    public ResponseData queryResidentBaseinfoById(Long id) {
        ResponseData responseData = new ResponseData();
        ResidentBaseinfo residentBaseinfo = residentBaseinfoService.getModelById(id);
        responseData.setData(residentBaseinfo);
        responseData.setStatus(ResponseData.SUCCESS_STATUS);
        return responseData;
    }



    /**
     * 
     * 描述：通过id查询居民健康卡的基本信息的详情
     * 
     * @param id
     * @return
     * @author sunxuefeng 2017年8月31日 下午4:58:48
     * @version 1.0
     */
    @RequestMapping("/queryResidentBaseinfoDetails")
    public ModelAndView queryResidentBaseinfoDetails(Long id) {
        ModelAndView modelAndView = new ModelAndView();
        ResidentBaseinfoDto residentBaseinfoDto = residentBaseinfoService.queryResidentBaseinfoDetailsById(id);
        modelAndView.addObject("residentBase", residentBaseinfoDto);
        modelAndView.setViewName("/resident/resident-details");
        return modelAndView;
    }



    /**
     * 
     * 描述：列表页面跳转(通过身份证号或者居民健康卡号查询居民信息)
     * 
     * @return
     * @author sunxuefeng 2017年8月28日 下午1:17:10
     * @version 1.0
     */
    @RequestMapping("/otherResidentQuery")
    public String otherResidentQuery() {
        return "/resident/other_resident_query";
    }



    /**
     * 
     * 描述：通过身份证号或者居民健康卡号查询居民信息列表
     * 
     * @param request
     * @param aoData
     * @return
     * @author sunxuefeng 2017年8月28日 下午2:44:52
     * @version 1.0
     */
    @RequestMapping("/queryOtherResidentList")
    @ResponseBody
    public Map<String, Object> queryOtherResidentList(HttpServletRequest request, @RequestParam String aoData) {
        Map<String, Object> aoDataMap = getAoDataMap(aoData);// 解析前台框架dataTable传过来的值，格式：Map
        QueryParams params = new QueryParams(); // 设置条件查询 QueryParam
        String number = aoDataMap.get("number").toString();
        params.put("idNumber", number);
        params.put("healthNumber", number);
        int start = StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString());// 分页信息
        int size = StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString());
        PagedList<ResidentBaseinfoDto> list = residentBaseinfoService.queryOtherResidentBaseinfoList(params, start, size);
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
            responseData = residentBaseinfoService.addManagementScope(systemUser, residentId);
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (BaseException e) {
            LOG.error("居民健康卡信息添加到我的管理权限范围之内失败,异常信息:" + e.getMessage());
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("添加失败");
        }
        return responseData;
    }
    /***
     * 
     * 描述：通过prantId获二级区域机构
     * @return
     * @author sunxuefeng 2017年9月1日 下午5:02:49 
     * @version 1.0
     */
    @RequestMapping(value = "/administrativeDivisionListByParentId")
    @ResponseBody
    public  List<AdministrativeDivision> administrativeDivisionListByParentId(long parantId) {
        QueryParams params = new QueryParams();
        params.put("parentid", parantId);
        List<AdministrativeDivision> administrativeDivisionList = administrativeDivisionService.queryBaseList(params);
        return administrativeDivisionList;
    }
}