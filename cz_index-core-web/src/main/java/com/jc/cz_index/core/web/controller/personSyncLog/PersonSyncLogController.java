package com.jc.cz_index.core.web.controller.personSyncLog;

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
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.IPersonSyncLogService;
import com.jc.cz_index.core.web.component.ManualSyncComponent;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.mainIndex.PersonSyncLogDto;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.PersonSyncLog;

/**
 * 
 * 描述：基本身份信息同步日志
 * 
 * @author sunxuefeng 2018年1月4日 下午2:26:31
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/personSyncLog")
public class PersonSyncLogController extends BaseController {
    private static final Logger   LOG = Logger.getLogger(PersonSyncLogController.class);
    @Autowired
    private IPersonSyncLogService personSyncLogService;
    @Autowired
    private ManualSyncComponent   manualSyncComponent;
    @Autowired
    private IOperationLogService  operationLogService;



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
        return "/personSyncLog/personSyncLog-list";
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
        PagedList<PersonSyncLogDto> list = personSyncLogService.queryPersonSyncLogList(params, start, size);
        Map<String, Object> result = new HashMap<String, Object>();// 封装页面显示信息
        result.put("iTotalRecords", list.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", list.getTotalSize());
        result.put("aaData", list.getList());
        return result;
    }



    /***
     * 
     * 描述：同步基本身份信息日志到前置机
     * 
     * @return
     * @author sunxuefeng 2018年1月5日 下午4:10:48
     * @version 1.0
     */
    @RequestMapping(value = "/syncLogPerson", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData syncLogPerson(String syncLogPersonId) {
        ResponseData responseData = new ResponseData();
        try {
            syncLogPersonId = syncLogPersonId.replace("[", "").replace("]", "").replace("\"", "");
            List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();
            List<PersonSyncLog> list = personSyncLogService.getObjectListByPKs(syncLogPersonId);
            for (PersonSyncLog personSyncLog : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                List<Person> persons = new ArrayList<Person>();
                persons.add(personSyncLog.getPerson());
                map.put("frontEndMachine", personSyncLog.getFrontEndMachine());
                map.put("syncData", persons);
                map.put("logId", personSyncLog.getId());
                listMaps.add(map);
            }
            int listsize = list.size();
            List<Long> sync = manualSyncComponent.manualSync(listMaps, "doSendPersons");
            if (sync != null && sync.size() > 0) {
                int size = sync.size();
                responseData.setStatus(ResponseData.SUCCESS_STATUS);
                responseData.setMessage("同步成功[" + size + "]条,失败[" + (listsize - size) + "]条基本身份信息");
                String ids = StringUtils.listToString(sync, ',');
                operationLogService.addOperationLog("基本身份信息日志", "", "同步日志信息", "同步了[" + size + "]条基本身份信息;id分别为:" + ids, "无");
            } else {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("同步成功[0]条,失败[" + listsize + "]条基本身份信息");
            }
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("出现错误,基本身份信息全部同步失败");
            LOG.error("基本身份信息同步失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }
}
