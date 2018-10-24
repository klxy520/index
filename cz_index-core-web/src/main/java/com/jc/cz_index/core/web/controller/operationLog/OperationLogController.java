package com.jc.cz_index.core.web.controller.operationLog;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.OperationLog;
import com.jc.cz_index.core.service.IOperationLogService;

/**
 * 
 * 描述：操作日志控制器
 * 
 * @author fengshengliang 2016年11月1日 下午3:01:39
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/operationLog")
public class OperationLogController extends BaseController {

    @Autowired
    private IOperationLogService operationLogService;



    /**
     * 
     * 描述：加载操作日志列表页面
     * 
     * @param request
     * @return
     * @author fengshengliang 2016年11月1日 下午4:06:17
     * @version 1.0
     */
    @RequestMapping("/operationLogList")
    public String operationLogList(HttpServletRequest request) {
        return "/operationLog/operationLogList";
    }



    @RequestMapping("/operationLogView")
    public ModelAndView operationLogView(String id) {
        ModelAndView mv = new ModelAndView("/operationLog/operationLogView");
        if (StringUtils.isNotEmpty(id)) {
            OperationLog operationLog = operationLogService.getModelById(Long.valueOf(id));
            mv.addObject("operationLog", operationLog);
        }
        return mv;
    }



    /**
     * 
     * 描述：加载所有的数据
     * 
     * @param request
     * @param aoData
     * @return
     * @author fengshengliang 2016年11月1日 下午5:04:43
     * @version 1.0
     */
    @RequestMapping("/operationLogListAllData")
    @ResponseBody
    public Map<String, Object> operationLogListAllData(HttpServletRequest request, @RequestParam String aoData) {
        Map<String, Object> aoDataMap = getAoDataMap(aoData);
        QueryParams params = new QueryParams();
        int count = 0; // 查询出来的数量
        int startPage = StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString());
        int pageLength = StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString());
        // 设置条件查询 QueryParam
        setFilterParam(aoDataMap, params);
        PagedList<OperationLog> list = operationLogService.queryPagedDetaiList(params, startPage, pageLength);
        count = list.getTotalSize();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("iTotalRecords", count);
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", count);
        result.put("aaData", list.getList());
        return result;
    }



    /**
     * 
     * 描述：添加日志
     * 
     * @param operationLog
     * @return
     * @author fengshengliang 2016年11月2日 上午10:43:44
     * @version 1.0
     */
    @RequestMapping("/addOperationLog")
    @ResponseBody
    public ResponseData addOperationLog(HttpServletRequest request, OperationLog operationLog) {
        operationLogService.addOperationLog(operationLog.getFormName(), operationLog.getRecordId(), operationLog.getType(),
                operationLog.getDetail(), operationLog.getRemark());
        return ResponseData.getSuccessResponse();
    }

}
