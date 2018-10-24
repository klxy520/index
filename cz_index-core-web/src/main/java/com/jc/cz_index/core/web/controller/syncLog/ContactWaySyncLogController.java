package com.jc.cz_index.core.web.controller.syncLog;
import java.util.ArrayList;
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

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.core.service.IContactWaySyncLogService;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.web.component.ManualSyncComponent;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.syncLog.ContactWaySyncLogDto;
import com.jc.cz_index.model.ContactWay;
import com.jc.cz_index.model.ContactWaySyncLog;

@Controller
@RequestMapping(value = "/contactWaySyncLog")
public class ContactWaySyncLogController extends BaseController  {
	
	@Autowired
	private IContactWaySyncLogService       iContactWaySyncLogService;
	@Autowired
    private ManualSyncComponent    manualSyncComponent;
	@Autowired
    private IOperationLogService operationLogService;
    private static final Logger LOG = Logger.getLogger(CardSyncLogController.class);
	/**
	 * 
	 * 描述：卡同步首页
	 * @return
	 * @author yangjunhui 2018年1月4日 下午3:31:59 
	 * @version 1.0
	 */
    @RequestMapping("/listPage")
	public String listPage() {
		return "/contactWaySyncLog/contactWaySyncLogList";
	}
	/**
	 * 
	 * 描述：卡同步日志查询
	 * @param request
	 * @param aoData
	 * @return
	 * @author yangjunhui 2018年1月4日 下午3:57:25 
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
        PagedList<ContactWaySyncLogDto> contactWaySyncLogList = iContactWaySyncLogService.queryContactWaySyncLogList(params,
                StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
                StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("iTotalRecords", contactWaySyncLogList.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", contactWaySyncLogList.getTotalSize());
        result.put("aaData", contactWaySyncLogList.getList());
        return result;
    }

	 /**
	   * 
	   * 描述：
	   * @param cardSyncLogIds
	   * @return
	   * @author yangjunhui 2018年1月8日 下午5:53:34 
	   * @version 1.0
	   */
	    @RequestMapping(value = "/updateSyncStatus", method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseData updateSyncStatus(String ids) {
	        ResponseData responseData = new ResponseData();
	        try {
	        	ids = ids.replace("[", "").replace("]", "").replace("\"", "");
	            List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();
	            List<ContactWaySyncLog> list = iContactWaySyncLogService.getObjectListByPKs(ids);
	            for (ContactWaySyncLog syncLog : list) {
	                Map<String, Object> map = new HashMap<String, Object>();
	                List<ContactWay> contactWays = new ArrayList<ContactWay>();
	                contactWays.add(syncLog.getContactWay());
	                map.put("frontEndMachine",syncLog.getFem());
	                map.put("syncData", contactWays);
	                map.put("logId", syncLog.getId());
	                listMaps.add(map);
	            }
	            List<Long> sync = manualSyncComponent.manualSync(listMaps, "doSendContactWay");
	            if (sync != null) {
	                responseData.setStatus(ResponseData.SUCCESS_STATUS);
	                String id = StringUtils.listToString(sync, ',');
	                responseData.setMessage("同步成功[" + sync.size() + "]条，失败["+(list.size()-sync.size())+"]条联系方式信息");
	                operationLogService.addOperationLog("联系方式信息日志", "", "同步日志信息","同步了["+sync.size()+"]条联系方式信息; id分别为:"+id, "无");
	            } else {
	                responseData.setStatus(ResponseData.ERROR_STATUS);
	                responseData.setMessage("联系方式全部同步失败");
	            }
	        } catch (Exception e) {
	            responseData.setStatus(ResponseData.ERROR_STATUS);
	            responseData.setMessage("联系方式全部同步失败");
	            LOG.error("联系方式同步失败,异常信息为:" + e.getMessage());
	            e.printStackTrace();
	        }
	        return responseData;
	    }  

}
