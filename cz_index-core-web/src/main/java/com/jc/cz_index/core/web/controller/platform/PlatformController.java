package com.jc.cz_index.core.web.controller.platform;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Platform;
import com.jc.cz_index.core.component.InterfaceVerifyUtils;
import com.jc.cz_index.core.service.IPlatformService;
import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.common.utils.StringUtils;

/**
 * 
 * 描述：平台管理
 * @author wangdeyou 2017年9月19日 下午2:51:39 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/platform")
public class PlatformController extends BaseController 
{	
    @Autowired
    private IPlatformService        platformService;
    
    /**
     * 
     * 描述：进入平台管理页面
     * @return
     * @author wangdeyou 2017年9月19日 下午2:51:51 
     * @version 1.0
     */
    @RequestMapping("/platformListPage")
    public String platFormListPage() {
        return "/platform/platformList";
    }
    
    /**
     * 
     * 描述：初始化平台管理页面数据
     * @param request
     * @param aoData
     * @return
     * @author wangdeyou 2017年9月19日 下午2:52:03 
     * @version 1.0
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, @RequestParam String aoData) {
        // 解析前台框架dataTable传过来的值，格式：Map
        Map<String, Object> aoDataMap = getAoDataMap(aoData);
        QueryParams params = new QueryParams();
        // 设置条件查询 QueryParam
        setFilterParam(aoDataMap, params);
        PagedList<Platform> platformPagedList = platformService.queryPagedDetaiList(params,
                StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
                StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("iTotalRecords", platformPagedList.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", platformPagedList.getTotalSize());
        result.put("aaData", platformPagedList.getList());
        return result;
    }
    
    /**
     * 
     * 描述：添加平台
     * @param platform
     * @return
     * @author wangdeyou 2017年9月19日 下午2:53:01 
     * @version 1.0
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData addPlatform(Platform platform) {
        ResponseData responseData = new ResponseData();
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        Date currentDate = DateUtils.getCurrentDate();
        try {
            if (null == platform) {
                throw new BaseException("参数有误");
            }
            platform.setCreator(loginUser.getId()); // 创建人
            platform.setCreateDate(currentDate); // 创建时间
            platform.setStatus(ContentUtils.DB_FIELD_STATUS_ENABLE); // 默认状态：0
                                                                     // 启用
            platformService.doAddModel(platform);
            InterfaceVerifyUtils.loadPaltform();
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }

    /**
     * 
     * 描述：获取平台，在编辑平台时将该平台的信息进行初始化
     * @param id
     * @return
     * @author wangdeyou 2017年9月19日 下午2:53:30 
     * @version 1.0
     */
    @RequestMapping("/get")
    @ResponseBody
    public ResponseData get(Long id) {
        ResponseData responseData = null;
        if (null == id || id == 0) {
            responseData = ResponseData.getErrorResponse("平台id不能为null");
        }
        Platform platform = platformService.getModelDetailById(id);
        responseData = ResponseData.getSuccessResponse(platform);
        return responseData;
    }
    
    /**
     * 
     * 描述：编辑平台
     * @param platform
     * @return
     * @author wangdeyou 2017年9月19日 下午2:53:41 
     * @version 1.0
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResponseData updatePlatform(Platform platform) {
        ResponseData responseData = new ResponseData();
        try {
            if (null == platform) {
                throw new BaseException("参数有误");
            }
            platformService.updatePlatform(platform);
            InterfaceVerifyUtils.loadPaltform();
            responseData = ResponseData.getSuccessResponse();
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }
    
    /**
     * 
     * 描述：启用平台
     * @param platformId
     * @return
     * @author wangdeyou 2017年9月19日 下午2:54:16 
     * @version 1.0
     */
    @RequestMapping("/setPlatformEnable")
    @ResponseBody
    public ResponseData setPlatformEnable(Long platformId) {
        ResponseData responseData = null;
        if (null == platformId || platformId == 0) {
            responseData = ResponseData.getErrorResponse("平台id不能为null");
        }
        platformService.setPlatformEnable(platformId, ContentUtils.DB_FIELD_STATUS_ENABLE);
        responseData = ResponseData.getSuccessResponse();
        return responseData;

    }
    
    /**
     * 
     * 描述：禁用平台
     * @param platformId
     * @return
     * @author wangdeyou 2017年9月19日 下午2:54:28 
     * @version 1.0
     */
    @RequestMapping("/setPlatformDisable")
    @ResponseBody
    public ResponseData setPlatformDisable(Long platformId) {
        ResponseData responseData = null;
        if (null == platformId || platformId == 0) {
            responseData = ResponseData.getErrorResponse("平台id不能为null");
        }
        platformService.setPlatformDisable(platformId, ContentUtils.DB_FIELD_STATUS_DISABLE);
        responseData = ResponseData.getSuccessResponse();
        return responseData;

    }
    
    /**
     * 
     * 描述：获取所有可用平台
     * @return
     * @author wangdeyou 2017年9月19日 下午2:54:50 
     * @version 1.0
     */
    @RequestMapping("/allList")
    @ResponseBody
    public ResponseData setPlatformDisable() {
        QueryParams params = new QueryParams();
        params.addParameter("status", ContentUtils.DB_FIELD_STATUS_ENABLE);
        List<Platform> platformList = platformService.queryBaseList(params);
        return ResponseData.getSuccessResponse(platformList);

    }
}

