package com.jc.cz_index.core.web.controller.mainIndex;

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
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.core.service.IContactWayService;
import com.jc.cz_index.core.service.IPersonService;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Certificate;
import com.jc.cz_index.model.ContactWay;
import com.jc.cz_index.model.Person;

@Controller
@RequestMapping(value = "/contactWay")
public class ContactWayController extends BaseController {	
	@Autowired
	private IContactWayService iContactWayService;
	@Autowired
	private IPersonService       personService;
	

    private static final Logger LOG = Logger.getLogger(ContactWayController.class);
    /**
	 * 
	 * 描述：进入此人证件详情列表页面
	 * @param id
	 * @return
	 * @author yangjunhui 2018年1月1日 下午5:57:50 
	 * @version 1.0
	 */
	  @RequestMapping("/manageContactWay")
	    public ModelAndView manageContactWayPage(Long id) {
	        ModelAndView modelAndView = new ModelAndView();
	        Person person = personService.getModelDetailById(id);
	        modelAndView.addObject("person", person);
	        modelAndView.setViewName("/mainIndex/manageContactWay_list");
	        return modelAndView;
	    }
	  
	 
		@RequestMapping("/QueryPersonContactWay")
	    @ResponseBody
	    public Map<String, Object> personContactWay(HttpServletRequest request, @RequestParam String aoData) {
	        // 解析前台框架dataTable传过来的值，格式：Map
	        Map<String, Object> aoDataMap = getAoDataMap(aoData);
	        QueryParams params = new QueryParams();
	        // 设置条件查询 QueryParam
	        setFilterParam(aoDataMap, params);
	        params.put("delFalg", 0);
	        PagedList<ContactWay> contactWayList = iContactWayService.queryPagedBaseList(params,
	                StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
	                StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
	        Map<String, Object> result = new HashMap<String, Object>();
	        result.put("iTotalRecords", contactWayList.getTotalSize());
	        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
	        result.put("iTotalDisplayRecords", contactWayList.getTotalSize());
	        result.put("aaData", contactWayList.getList());
	        return result;
	    }
		
		
		
	    @RequestMapping(value = "/deleteContactWayById")  
	    @ResponseBody
	    public ResponseData deletePersonById(Long id) {
	        ResponseData responseData = new ResponseData();
	      
	        responseData = iContactWayService.deleteContactWayById(id);
	      
	        return responseData;
	    }
	    
	    
	    @RequestMapping(value = "/addContactWay", method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseData addContactWay(ContactWay contactWay) {
	        ResponseData responseData = new ResponseData();
	        try {
	            responseData = iContactWayService.addContactWay(contactWay);
	            LOG.debug("成功信息:" + responseData.getMessage());
	        } catch (Exception e) {
	            responseData.setStatus(ResponseData.ERROR_STATUS);
	            responseData.setMessage("添加失败");
	            LOG.error("联系方式添加 失败,异常信息为:" + e.getMessage());
	            e.printStackTrace();
	        }
	        return responseData;
	    }
	    
	    @RequestMapping(value = "/updateContactWay", method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseData updateContactWay(ContactWay contactWay) {
	        ResponseData responseData = new ResponseData();
	        try {
	            responseData = iContactWayService.updateContactWay(contactWay);
	            LOG.debug("成功信息:" + responseData.getMessage());
	        } catch (Exception e) {
	            responseData.setStatus(ResponseData.ERROR_STATUS);
	            responseData.setMessage("添加失败");
	            LOG.error("联系方式修改失败,异常信息为:" + e.getMessage());
	            e.printStackTrace();
	        }
	        return responseData;
	    }
	    @RequestMapping("/queryContactWayById")
	    public ModelAndView queryContactWayById(Long id) {
	        ModelAndView modelAndView = new ModelAndView();
	        ContactWay contactWay = iContactWayService.getModelDetailById(id);
	        modelAndView.addObject("contactWay", contactWay);
	        modelAndView.setViewName("/mainIndex/contactWayDetails");
	        return modelAndView;
	    }
	    /**
	     * 
	     * 描述：联系方式判断重复
	     * @param aoData
	     * @return
	     * @author yangjunhui 2018年1月14日 下午8:14:42 
	     * @version 1.0
	     */
	    @RequestMapping("/isExistNo")
	    public Map<String, Object> isExistNo(String no,String type,String personId) {

	       Map<String, Object> result = new HashMap<String, Object>();
	       QueryParams params = new QueryParams();
	       params.put("personId", personId);
	       params.put("contactNo", no);
	       params.put("contactTypeCode", type);
	       params.put("delFalg", 0);
	        List<ContactWay> list = iContactWayService.queryBaseList(params);
	        if(list.size()<=0){
	        	result.put("id", -1);
	        }else{
	        	result.put("id", list.get(0).getId());
	        }
	        return result;
	    }
}

