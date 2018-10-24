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
import com.jc.cz_index.core.service.IAddressSyncLogService;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.web.component.ManualSyncComponent;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.syncLog.AddressSyncLogDto;
import com.jc.cz_index.model.Address;
import com.jc.cz_index.model.AddressSyncLog;

@Controller
@RequestMapping(value = "/addressSyncLog")
public class AddressSyncLogController extends BaseController {

    private static final Logger    LOG = Logger.getLogger(AddressSyncLogController.class);
    @Autowired
    private IAddressSyncLogService addressSyncLogService;
    @Autowired
    private ManualSyncComponent    manualSyncComponent;
    @Autowired
    private IOperationLogService   operationLogService;



    /**
     * 
     * 描述：列表页面跳转
     * 
     * @return
     * @author sunxuefeng 2017年12月25日 上午10:40:37
     * @version 1.0
     */
    @RequestMapping("/listPage")
    public String listPage() {
        return "/addressSyncLog/addressSyncLog-list";
    }



    /**
     * 
     * 描述：数据列表
     * 
     * @param request
     * @param aoData
     * @return
     * @author sunxuefeng 2017年12月25日 上午10:40:48
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
        PagedList<AddressSyncLogDto> list = addressSyncLogService.queryAddressSyncLog(params, start, size);
        Map<String, Object> result = new HashMap<String, Object>();// 封装页面显示信息
        result.put("iTotalRecords", list.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", list.getTotalSize());
        result.put("aaData", list.getList());
        return result;
    }



    /***
     * 
     * 描述：同步地址信息日志到前置机
     * 
     * @return
     * @author sunxuefeng 2018年1月5日 下午4:10:48
     * @version 1.0
     */
    @RequestMapping(value = "/syncLogAddress", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData syncLogAddress(String addressSyncLogId) {
        ResponseData responseData = new ResponseData();
        try {
            addressSyncLogId = addressSyncLogId.replace("[", "").replace("]", "").replace("\"", "");
            List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();
            List<AddressSyncLog> list = addressSyncLogService.getObjectListByPKs(addressSyncLogId);
            for (AddressSyncLog addressSyncLog : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                List<Address> addresses = new ArrayList<Address>();
                addresses.add(addressSyncLog.getAddress());
                map.put("frontEndMachine", addressSyncLog.getFrontEndMachine());
                map.put("syncData", addresses);
                map.put("logId", addressSyncLog.getId());
                listMaps.add(map);
            }
            int listsize = list.size();
            List<Long> sync = manualSyncComponent.manualSync(listMaps, "doSendAddress");
            if (sync != null && sync.size() > 0) {
                int size = sync.size();
                responseData.setStatus(ResponseData.SUCCESS_STATUS);
                responseData.setMessage("同步成功[" + size + "]条,失败[" + (listsize - size) + "]条地址信息");
                String ids = StringUtils.listToString(sync, ',');
                operationLogService.addOperationLog("地址信息日志", "", "同步日志信息", "同步了[" + size + "]条地址信息; id分别为:" + ids, "无");
            } else {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("同步成功[0]条,失败[" + listsize + "]条地址信息");
            }
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("出现错误,地址信息全部同步失败");
            LOG.error("地址信息同步失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }
}
