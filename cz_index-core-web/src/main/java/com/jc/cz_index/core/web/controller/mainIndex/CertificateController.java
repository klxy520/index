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
import com.jc.cz_index.core.service.ICertificateService;
import com.jc.cz_index.core.service.IPersonService;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Certificate;
import com.jc.cz_index.model.Person;

@Controller
@RequestMapping(value = "/certificate")
public class CertificateController extends BaseController {	
	@Autowired
	private ICertificateService iCertificateService;
	@Autowired
	private IPersonService       personService;
	

    private static final Logger LOG = Logger.getLogger(CertificateController.class);
    /**
	 * 
	 * 描述：进入此人证件详情列表页面
	 * @param id
	 * @return
	 * @author yangjunhui 2018年1月1日 下午5:57:50 
	 * @version 1.0
	 */
	  @RequestMapping("/manageCertificate")
	    public ModelAndView manageCertificatePage(Long id) {
	        ModelAndView modelAndView = new ModelAndView();
	        Person person = personService.getModelDetailById(id);
	        modelAndView.addObject("person", person);
	        modelAndView.setViewName("/mainIndex/manageCertificate_list");
	        return modelAndView;
	    }
	  
	 
		@RequestMapping("/QueryPersonCertificate")
	    @ResponseBody
	    public Map<String, Object> personCertificate(HttpServletRequest request, @RequestParam String aoData) {
	        // 解析前台框架dataTable传过来的值，格式：Map
	        Map<String, Object> aoDataMap = getAoDataMap(aoData);
	        QueryParams params = new QueryParams();
	        // 设置条件查询 QueryParam
	        setFilterParam(aoDataMap, params);
	        params.put("delFalg", 0);
	        PagedList<Certificate> certificateList = iCertificateService.queryPagedBaseList(params,
	                StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
	                StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
	        Map<String, Object> result = new HashMap<String, Object>();
	        result.put("iTotalRecords", certificateList.getTotalSize());
	        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
	        result.put("iTotalDisplayRecords", certificateList.getTotalSize());
	        result.put("aaData", certificateList.getList());
	        return result;
	    }
		
		
		
	    @RequestMapping(value = "/deleteCertificateById")  
	    @ResponseBody
	    public ResponseData deletePersonById(Long id) {
	        ResponseData responseData = new ResponseData();
	        responseData = iCertificateService.deleteCertificateById(id);
	        return responseData;
	    }
	    
	    
	    @RequestMapping(value = "/addCertificate", method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseData addCertificate(Certificate certificate) {
	        ResponseData responseData = new ResponseData();
	        try {
	            responseData = iCertificateService.addCertificate(certificate);
	            LOG.debug("成功信息:" + responseData.getMessage());
	        } catch (Exception e) {
	            responseData.setStatus(ResponseData.ERROR_STATUS);
	            responseData.setMessage("添加失败");
	            LOG.error("证书添加 失败,异常信息为:" + e.getMessage());
	            e.printStackTrace();
	        }
	        return responseData;
	    }
	    
	    @RequestMapping(value = "/updateCertificate", method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseData updateCertificate(Certificate certificate) {
	        ResponseData responseData = new ResponseData();
	        try {
	            responseData = iCertificateService.updateCertificate(certificate);
	            LOG.debug("成功信息:" + responseData.getMessage());
	        } catch (Exception e) {
	            responseData.setStatus(ResponseData.ERROR_STATUS);
	            responseData.setMessage("添加失败");
	            LOG.error("证书修改失败,异常信息为:" + e.getMessage());
	            e.printStackTrace();
	        }
	        return responseData;
	    }
	    @RequestMapping("/queryCertificateById")
	    public ModelAndView queryCertificateById(Long id) {
	        ModelAndView modelAndView = new ModelAndView();
	        Certificate certificate = iCertificateService.getModelDetailById(id);
	        modelAndView.addObject("certificate", certificate);
	        modelAndView.setViewName("/mainIndex/certificateDetails");
	        return modelAndView;
	    }
	    
	  
	    @RequestMapping("/isExistNo")
	    public Map<String, Object> isExistNo(String no,String type) {
	    	  Map<String, Object> result = new HashMap<String, Object>();
	        QueryParams params = new QueryParams();
	        params.put("delFalg", 0);
	        params.put("certificateNo", no); 
	        params.put("certificateTypeCode", type);
	        List<Certificate> list = iCertificateService.queryBaseList(params);
	        if(list.size()<=0){
	        	result.put("id", -1);
	        }else{
	        	result.put("id", list.get(0).getId());
	        }
	        return result;
	    }
		
}

