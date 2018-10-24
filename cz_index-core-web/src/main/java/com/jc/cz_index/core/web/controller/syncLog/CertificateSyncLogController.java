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
import com.jc.cz_index.core.service.ICertificateSyncLogService;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.web.component.ManualSyncComponent;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.syncLog.CertificateSyncLogDto;
import com.jc.cz_index.model.Certificate;
import com.jc.cz_index.model.CertificateSyncLog;

@Controller
@RequestMapping(value = "/certificateSyncLog")
public class CertificateSyncLogController extends BaseController  {
	@Autowired
	private ICertificateSyncLogService       iCertificateSyncLogService;
	@Autowired
    private ManualSyncComponent    manualSyncComponent;
	@Autowired
    private IOperationLogService operationLogService;
    private static final Logger LOG = Logger.getLogger(CertificateSyncLogController.class);
	/**
	 * 
	 * 描述：证件同步首页
	 * @return
	 * @author yangjunhui 2018年1月4日 下午3:31:59 
	 * @version 1.0  
	 */
    @RequestMapping("/listPage")
	public String listPage() {
		return "/certificateSyncLog/certificateSyncLogList";
	}
	/**
	 * 
	 * 描述：证件同步日志查询
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
        PagedList<CertificateSyncLogDto> certificateSyncLogList = iCertificateSyncLogService.queryCertificateSyncLogList(params,
                StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
                StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("iTotalRecords", certificateSyncLogList.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", certificateSyncLogList.getTotalSize());
        result.put("aaData", certificateSyncLogList.getList());
        return result;
    }
	
	 /**
	   * 
	   * 描述：
	   * @param Ids
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
	            List<CertificateSyncLog> list = iCertificateSyncLogService.getObjectListByPKs(ids);
	            for (CertificateSyncLog syncLog : list) {
	                Map<String, Object> map = new HashMap<String, Object>();
	                List<Certificate> certificates = new ArrayList<Certificate>();
	                certificates.add(syncLog.getCertificate());
	                map.put("frontEndMachine", syncLog.getFem());
	                map.put("syncData", certificates);
	                map.put("logId", syncLog.getId());
	                listMaps.add(map);
	            }
	            List<Long> sync = manualSyncComponent.manualSync(listMaps, "doSendCertificate");
	            if (sync != null&&sync.size()>0) {
	                responseData.setStatus(ResponseData.SUCCESS_STATUS);
	                responseData.setMessage("同步成功[" + sync.size() + "]条，失败["+(list.size()-sync.size())+"]条证件信息");
	                String id = StringUtils.listToString(sync, ',');
	                operationLogService.addOperationLog("证件信息日志", "", "同步日志信息","同步了["+sync.size()+"]条证件信息; id分别为:"+id, "无");
	            } else {
	                responseData.setStatus(ResponseData.ERROR_STATUS);
	                responseData.setMessage("证件信息同步全部失败");
	            }
	        } catch (Exception e) {
	            responseData.setStatus(ResponseData.ERROR_STATUS);
	            responseData.setMessage("证件信息同步全部失败");
	            LOG.error("证件信息同步失败,异常信息为:" + e.getMessage());
	            e.printStackTrace();
	        }
	        return responseData;
	    }
}
