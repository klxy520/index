package com.jc.cz_index.core.web.controller.mainIndex;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.service.IAddressService;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.IPersonService;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Address;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.SystemUser;

/**
 * 
 * 描述：地址管理
 * 
 * @author sunxuefeng 2017年12月28日 下午3:18:28
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/address")
public class AddressController extends BaseController {

    private static final Logger  LOG = Logger.getLogger(AddressController.class);
    @Autowired
    private IAddressService      addressService;
    @Autowired
    private IOperationLogService operationLogService;
    @Autowired
    private IPersonService       personService;



    /**
     * 
     * 描述：地址管理-列表页面跳转
     * 
     * @return
     * @author sunxuefeng 2017年12月25日 上午10:40:37
     * @version 1.0
     */
    @RequestMapping("/listPage")
    public String listPage(@RequestParam Long id, Model model) {
        Person person = personService.getModelById(id);
        model.addAttribute("person", person);
        return "/mainIndex/address-list";
    }



    /**
     * 
     * 描述：地址信息-数据列表
     * 
     * @param request
     * @param aoData
     * @return
     * @author sunxuefeng 2017年12月28日 下午4:57:57
     * @version 1.0
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, @RequestParam String aoData) {
        Map<String, Object> aoDataMap = getAoDataMap(aoData);// 解析前台框架dataTable传过来的值，格式：Map
        QueryParams params = new QueryParams(); // 设置条件查询 QueryParam
        setFilterParam(aoDataMap, params);
        params.put("delFalg", 0);
        int start = StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString());// 分页信息
        int size = StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString());
        PagedList<Address> list = addressService.queryPagedBaseList(params, start, size);
        Map<String, Object> result = new HashMap<String, Object>();// 封装页面显示信息
        result.put("iTotalRecords", list.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", list.getTotalSize());
        result.put("aaData", list.getList());
        return result;
    }



    /***
     * 
     * 描述：地址信息-根据id删除单个地址信息
     * 
     * @param id
     * @return
     * @author sunxuefeng 2017年12月28日 下午6:02:59
     * @version 1.0
     */
    @RequestMapping(value = "/deleteAddressById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteAddressById(Long id) {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            responseData = addressService.deleteAddressById(id, systemUser);
            operationLogService.addOperationLog("地址信息", id + "", "删除地址信息", "删除了一条地址信息; id: [ " + id + "] ", "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("地址信息删除失败");
            LOG.error("地址信息删除 失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 
     * 描述：地址信息-添加单个地址信息
     * 
     * @param person
     * @return
     * @author sunxuefeng 2017年12月29日 上午10:47:11
     * @version 1.0
     */
    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addAddress(Address address) {
        ResponseData responseData = new ResponseData();
        try {
            SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
            responseData = addressService.addAddress(address, systemUser);
            Long id = address.getId();
            operationLogService.addOperationLog("地址信息", id + "", "添加地址信息",
                    "添加了一条地址信息; id: [ " + id + "],地址类别为:[" + address.getAddressTypeCode() + "] ", "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("地址信息添加失败");
            LOG.error("地址信息添加 失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 描述： 地址信息-修改单个地址信息
     * 
     * @param person
     * @return
     * @author sunxuefeng 2017年12月28日 上午10:36:36
     * @version 1.0
     */
    @RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateAddress(Address address) {
        ResponseData responseData = new ResponseData();
        try {
            SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
            responseData = addressService.updateAddress(address, systemUser);
            Long id = address.getId();
            operationLogService.addOperationLog("地址信息", id + "", "修改地址信息",
                    "修改了一条地址信息; id: [ " + id + "] ,地址类别为:[" + address.getAddressTypeCode() + "]", "无");
            LOG.debug("成功信息:" + responseData.getMessage());
        } catch (Exception e) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("地址信息修改失败");
            LOG.error("地址信息修改失败,异常信息为:" + e.getMessage());
            e.printStackTrace();
        }
        return responseData;
    }



    /**
     * 
     * 描述：地址信息-根据id查询单个地址信息的详情信息
     * 
     * @param id
     * @return
     * @author sunxuefeng 2017年12月29日 下午1:31:45
     * @version 1.0
     */
    @RequestMapping(value="/queryAddressDetails")
    public ModelAndView queryAddressDetails(Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Address address = addressService.getModelDetailById(id);
        modelAndView.addObject("address", address);
        modelAndView.setViewName("/mainIndex/address-details");
        return modelAndView;
    }

}
