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
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.InfoManagementLog;
import com.jc.cz_index.core.service.IinfoManagementLogService;

/***
 * 
 * 描述：信息操作日志
 * 
 * @author yangjunhui 2017年8月29日 下午5:38:02
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/infoManagementLog")
public class InfoManagementLogController extends BaseController {

    @Autowired
    private IinfoManagementLogService infoManagementLog;



    /**
     * 
     * 描述：加载操作日志列表页面
     * 
     * @param request
     * @return
     * @author fengshengliang 2016年11月1日 下午4:06:17
     * @version 1.0
     */
    @RequestMapping("/infoLogList")
    public String infoManagementLogList(HttpServletRequest request) {
        return "/infoManagementLog/infoLogList";
    }



    /***
     * 
     * 描述：指定日志详细查询
     * 
     * @param id
     * @return
     * @author yangjunhui 2017年8月29日 下午5:35:28
     * @version 1.0
     */
    @RequestMapping("/QueryLog")
    public ModelAndView infoManagementLogView(String id) {
        ModelAndView mv = new ModelAndView("/infoManagementLog/logDetail");
        if (StringUtils.isNotEmpty(id)) {
            InfoManagementLog Log = infoManagementLog.getLogById(Long.valueOf(id));
            mv.addObject("Log", Log);
        }
        return mv;
    }



    /**
     * 
     * 描述：按照指定条件查询日志
     * 
     * @param request
     * @param aoData
     * @return
     * @author yangjunhui 2017年8月29日 下午7:04:21
     * @version 1.0
     */
    @RequestMapping("/QueryLogByParams")
    @ResponseBody
    public Map<String, Object> QueryAllLogByParams(HttpServletRequest request, @RequestParam String aoData) {
        Map<String, Object> aoDataMap = getAoDataMap(aoData);
        QueryParams params = new QueryParams();
        int start = StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString());
        int size = StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()); 
        // 设置条件查询 QueryParam
        setFilterParam(aoDataMap, params);
        PagedList<InfoManagementLog> list = infoManagementLog.queryPagedDetaiList(params, start, size);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("iTotalRecords", list.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", list.getTotalSize());
        result.put("aaData", list.getList());
        return result;
    }

}
