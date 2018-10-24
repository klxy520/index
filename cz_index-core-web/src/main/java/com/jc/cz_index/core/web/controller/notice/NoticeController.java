package com.jc.cz_index.core.web.controller.notice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jc.cz_index.common.bean.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;

import com.jc.cz_index.model.Notice;
import com.jc.cz_index.model.Nodes;

import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.INoticeService;

/**
 * 
 * 描述：公告
 * 
 * @author yangjunhui 2017年8月25日 上午11:29:13
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/notice")
public class NoticeController extends BaseController {

    @Autowired
    private IOperationLogService operationLogService;
    @Autowired
    private INoticeService       INoticeService;



    @RequestMapping("/showNoticePage")
    public String infoManagementLogList(HttpServletRequest request) {
        return "/notice/noticeList";
    }



    /**
     * 
     * 描述：添加公告
     * 
     * @return
     * @author yangjunhui 2017年8月28日 下午3:16:32
     * @version 1.0
     */

    @RequestMapping("/addNotice")
    public ResponseData addNotice(Notice notice) {
        ResponseData responseData = new ResponseData();
        try {
            INoticeService.addNotice(notice);
           String detais= BeanUtils.getBeanPropertiesByFields(notice,"标题,title;内容,content;开始时间,startTime;结束时间,endTime;启用状态,status;");           
            operationLogService.addOperationLog("公告管理表", "公告", "公告添加", "公告管理表(notice)中添加[ " + detais + " ]", "无");
            responseData.setStatus(0);
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    /***
     * 描述：刪除
     * 
     * @return
     * @author yangjunhui 2017年8月28日 上午10:46:43
     * @version 1.0
     */

    @RequestMapping("/deleteNotice")
    public ResponseData deleteNotice(String ids, String titles) {
        ResponseData responseData = new ResponseData();
        try {
            INoticeService.deleteNotice(ids);
            List<String> id = StringUtils.getStringList(ids, ",");
            operationLogService.addOperationLog("公告管理表", "公告id" + ids + "", "公告删除",
                    "公告管理表(notice)中删除公告[ " + titles + " ]共计" + id.size() + "条记录", "无");
            responseData.setStatus(0);
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    /***
     * 
     * 描述：按条件查询所有公告
     * 
     * @return
     * @author yangjunhui 2017年8月28日 下午4:41:10
     * @version 1.0
     */
    @RequestMapping("/noticeList")
    @ResponseBody
    public Map<String, Object> listNotice(String aoData) {
        Map<String, Object> aoDataMap = getAoDataMap(aoData);
        Map<String, Object> result = new HashMap<String, Object>();
        QueryParams params = new QueryParams();
        setFilterParam(aoDataMap, params);

        params.addParameter("now", DateUtils.formatDateToString(new Date()));// 获取当前时间
        int start = StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString());
        int size = StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString());
        PagedList<Notice> list = INoticeService.queryPagedDetaiList(params, start, size);
        result.put("iTotalRecords", list.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", list.getTotalSize());
        result.put("aaData", list.getList());
        return result;

    }



    /**
     * 
     * 描述：获取登录用户公告，返回未读公告的标题和ID用于顶部显示
     * 
     * @return
     * @author yangjunhui 2017年9月5日 上午10:38:55
     * @version 1.0
     */
    @RequestMapping("/getMyNoticeList")
    @ResponseBody
    public ResponseData getMyNoticeList() {
        ResponseData responseData = new ResponseData();
        try {
            List<Notice> noticeList = new ArrayList<Notice>();
            noticeList = INoticeService.getMyNoticeList();
            responseData.setStatus(0);
            responseData.setData(noticeList);
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    @RequestMapping("/readMyNoticeDetail")
    @ResponseBody
    public ResponseData readMyNoticeDetail(Long nid) {
        ResponseData responseData = new ResponseData();
        try {
            Notice notice = new Notice();
            notice = INoticeService.queryNoticeById(nid);
            // INoticeService.updateReadStatus(nid);// 修改查看状态
            responseData.setStatus(0);
            responseData.setData(notice);
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    @RequestMapping("/queryNoticeDetails")
    public ModelAndView queryNoticeDetails(Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Notice notice = new Notice();
        notice = INoticeService.queryNoticeById(id);
        modelAndView.addObject("notice", notice);
        modelAndView.setViewName("/notice/noticeDetail");
        return modelAndView;
    }



    /**
     * 
     * 描述：获取公告
     * 
     * @return
     * @author yangjunhui 2017年9月5日 上午10:38:55
     * @version 1.0
     */
    @RequestMapping("/getMyNoticeById")
    @ResponseBody
    public ResponseData getMyNoticeById(Long id) {
        ResponseData responseData = new ResponseData();
        try {
            Notice notice = new Notice();
            notice = INoticeService.queryNoticeById(id);
            responseData.setStatus(0);
            responseData.setData(notice);
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    /**
     * 
     * 描述：修改公告
     * 
     * @return
     * @author yangjunhui 2017年9月5日 上午10:38:55
     * @version 1.0
     */

    @RequestMapping("/updateNoticeById")
    @ResponseBody
    public ResponseData updateNoticeById(Notice notice) {
        ResponseData responseData = new ResponseData();
        try {
            Notice noticeDB=new Notice();
            noticeDB=INoticeService.queryNoticeById(notice.getId());
           String details= BeanUtils.getDifferenceByFields(noticeDB, notice, "标题,title;内容,content;开始时间,startTime;结束时间,endTime;启用状态,status;");
            if(details.length()<=0){
                responseData.setMessage("修改内容与原有内容一致");;
                responseData.setStatus(1);
                return responseData;
            }
            INoticeService.updateNotice(notice);
            operationLogService.addOperationLog("公告管理表", "公告", "公告修改", "公告管理表(notice)中修改[ " + details + " ]", "无");
            responseData.setStatus(0);
            responseData.setMessage("公告修改成功");
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    /**
     * 
     * 描述：将所有机构和用户查出，构成机构树
     * 
     * @return
     * @author yangjunhui 2017年9月15日 上午11:25:00
     * @version 1.0
     */
    @RequestMapping("/getAllUserForTree")
    @ResponseBody
    public ResponseData getAllUserForTree() {
        ResponseData responseData = new ResponseData();
        try {

            Nodes areaNodes = INoticeService.getAreaNodesWithUser(1L);
            List<Nodes> orgNode = INoticeService.getOrgNodesWithUser();
            Nodes orgNodes = new Nodes();
            orgNodes.setId(-2L);
            orgNodes.setName("行政机构");
            orgNodes.setChildNode(orgNode);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("areaNodes", areaNodes);
            result.put("orgNodes", orgNodes);
            responseData.setData(result);
            responseData.setStatus(0);
            responseData.setMessage("用户获取成功");
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    /**
     * 
     * 描述： 根据公告id查有权看公告的人
     * 
     * @param id
     *            公告id
     * @return 该公告的所有查看人id
     * @author yangjunhui 2017年9月17日 下午4:01:32
     * @version 1.0
     */
    @RequestMapping("/getNoticeUserById")
    @ResponseBody
    public ResponseData getNoticeUserById(Long id) {
        ResponseData responseData = new ResponseData();
        try {

            List<Long> uidList = INoticeService.getNoticeUserByNid(id);
            responseData.setData(uidList);
            responseData.setStatus(0);
            responseData.setMessage("公告修改成功");
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    @RequestMapping("/setNoticeUser")
    @ResponseBody
    public ResponseData setNoticeUser(Long noticeId, String uids) {
        ResponseData responseData = new ResponseData();
        try {
            QueryParams params = new QueryParams();
            params.put("nid", noticeId);
            params.put("uids", uids);
            int n = INoticeService.setNoticeUser(params);
            responseData.setStatus(0);
            responseData.setMessage("成功授权" + n + "位用户");
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }
}
