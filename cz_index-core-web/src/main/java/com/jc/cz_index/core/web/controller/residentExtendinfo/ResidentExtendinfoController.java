package com.jc.cz_index.core.web.controller.residentExtendinfo;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.core.web.controller.residentBaseinfo.ResidentBaseinfoController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.ResidentExtendinfo;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.IResidentExtendinfoService;

@Controller
@RequestMapping(value = "/residentExtendinfo")
public class ResidentExtendinfoController extends BaseController {
    private static final Logger        LOG = Logger.getLogger(ResidentBaseinfoController.class);
    @Autowired
    private IResidentExtendinfoService iextendInfo;
    @Autowired
    private IOperationLogService       operationLogService;



    /**
     * 
     * 描述：
     * 
     * @return 扩展信息跳转
     * @author yangjunhui 2017年9月18日 下午1:31:39
     * @version 1.0
     */
    @RequestMapping("/listPage")
    public String listPage() {
        return "/residentExtend/residentExtendList";
    }



    /**
     * 
     * 描述： 获取初始化表格信息
     * 
     * @param aoData
     * @return
     * @author yangjunhui 2017年9月18日 下午1:32:15
     * @version 1.0
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(String aoData) {
        // 解析前台框架dataTable传过来的值，格式：Map
        Map<String, Object> aoDataMap = getAoDataMap(aoData);
        QueryParams params = new QueryParams();
        int start = StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString());
        int size = StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString());
        // 设置条件查询 QueryParam
        setFilterParam(aoDataMap, params);
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        params.put("area", loginUser.getAreaId());
        PagedList<ResidentExtendinfo> pagedList = iextendInfo.queryPagedListForTable(params, start, size);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("iTotalRecords", pagedList.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", pagedList.getTotalSize());
        result.put("aaData", pagedList.getList());
        return result;
    }



    /**
     * 
     * 描述：根据id查询用户扩展信息
     * 
     * @param id
     * @return
     * @author yangjunhui 2017年9月13日 上午9:21:25
     * @version 1.0
     */
    @ResponseBody
    @RequestMapping("/getExtendDetailById")
    public ResponseData queryResidentBaseinfoDetails(Long id) {
        ResidentExtendinfo ResidentExtendinfo = iextendInfo.queryResidentExtendinfoDetailsById(id);
        return ResponseData.getSuccessResponse(ResidentExtendinfo);

    }



    /**
     * 
     * 描述：添加扩展信息
     * 
     * @param extendInfo
     * @return
     * @throws BaseException
     * @author yangjunhui 2017年9月18日 下午1:33:07
     * @version 1.0
     */
    @ResponseBody
    @RequestMapping("/addExtendInfo")
    public ResponseData addExtendInfo(ResidentExtendinfo extendInfo) throws BaseException {
        ResponseData responseData = new ResponseData();

        Long baseId = extendInfo.getBaseId();
        if (baseId != null && baseId != 0) {
            operationLogService.addOperationLog("居民健康卡扩展信息表", baseId + "", "修改居民扩展信息", "修改了一条居民健康卡扩展信息; id: [ " + baseId
                    + "] , 具体信息 :居民姓名为:[" + extendInfo.getRealName() + "]," + "身份证号为:[" + extendInfo.getIdNumber() + "]", "无");
            responseData = iextendInfo.updateResidenExtendinfo(extendInfo);
        } else {
            Long id = extendInfo.getId();
            operationLogService.addOperationLog("居民健康卡扩展信息表", id + "", "添加居民信息", "添加了一条居民健康卡扩展信息; id: [ " + id + "] , 具体信息 :居民姓名为:["
                    + extendInfo.getRealName() + "]," + "身份证号为:[" + extendInfo.getIdNumber() + "]", "无");
            extendInfo.setBaseId(id);
            extendInfo.setHealthNumber(extendInfo.getIdNumber());
            responseData = iextendInfo.addResidenExtendinfo(extendInfo);
        }

        LOG.debug("成功信息:" + responseData.getMessage());
        return responseData;
    }
}