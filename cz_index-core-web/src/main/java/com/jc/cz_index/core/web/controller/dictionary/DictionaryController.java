package com.jc.cz_index.core.web.controller.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Dictionary;
import com.jc.cz_index.core.service.IDictionaryService;

/**
 * 
 * 描述：数据字典管理
 * 
 * @author wangdeyou 2017年2月24日 下午4:10:05
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/dictionary")
public class DictionaryController extends BaseController {
    @Autowired
    IDictionaryService dictionaryService;



    @RequestMapping("/listPage")
    public String listPage() {
        return "/dictionary/dictionaryList";
    }



    /**
     * 
     * 描述：获取数据字典
     * 
     * @return
     * @author wangdeyou 2017年2月24日 下午4:27:32
     * @version 1.0
     */
    @RequestMapping("/topDictionary")
    @ResponseBody
    public ResponseData topDictionary() {
        QueryParams params = new QueryParams();
        params.addParameter("id", 1l);
        Dictionary dictionary = dictionaryService.queryOneDetail(params);
        return ResponseData.getSuccessResponse(dictionary);
    }



    /**
     * 
     * 描述：获取上海字典列表
     * 
     * @param request
     * @param aoData
     * @return
     * @author wangdeyou 2017年2月27日 上午10:23:04
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
        PagedList<Dictionary> DictionaryPageList = dictionaryService.queryPagedBaseList(params,
                StringUtils.StringToInt(aoDataMap.get("iDisplayStart").toString()),
                StringUtils.StringToInt(aoDataMap.get("iDisplayLength").toString()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("iTotalRecords", DictionaryPageList.getTotalSize());
        result.put("sEcho", StringUtils.StringToInt(aoDataMap.get("sEcho").toString()));
        result.put("iTotalDisplayRecords", DictionaryPageList.getTotalSize());
        result.put("aaData", DictionaryPageList.getList());
        return result;
    }



    /**
     * 
     * 描述：添加字典
     * 
     * @param dictionary
     * @return
     * @author wangdeyou 2017年2月27日 下午1:16:07
     * @version 1.0
     */
    @RequestMapping("/addDictionary")
    @ResponseBody
    public ResponseData addDictionary(Dictionary dictionary) {
        ResponseData responseData = new ResponseData();
        QueryParams params = new QueryParams();
        QueryParams params2 = new QueryParams();
        List<Dictionary> dictionariesList = new ArrayList<>();
        try {
            if (dictionary == null) {
                throw new BaseException("参数错误");
            }
            // 查询父类ID的子类个数
            params2.put("parentid", dictionary.getParentid());
            dictionariesList = dictionaryService.queryBaseList(params2);
            params.put("id", dictionary.getParentid());
            Dictionary dictionary2 = dictionaryService.queryOneBase(params);
            String key = dictionary2.getDictkey();
            if (key != null) {
                // 把父类的名字拼接上子类的总共长度
                dictionary.setDictkey(key + "_" + dictionariesList.size());
            }
            // 查询是否有默认的存在,有则添加失败,没有则可以添加
            if (dictionary.getIsdefault() == 1) {
                params2.put("isdefault", 1);
                dictionariesList = dictionaryService.queryBaseList(params2);
                if (dictionariesList.size() != 0) {
                    responseData.setStatus(2);
                    responseData.setMessage("添加失败!已经有默认的,请重新添加");
                } else {
                    dictionaryService.doAddModel(dictionary);
                    responseData.setStatus(0);
                    responseData = ResponseData.getSuccessResponse();
                }
            } else {
                dictionaryService.doAddModel(dictionary);
                responseData.setStatus(0);
                responseData = ResponseData.getSuccessResponse();
            }
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    /**
     * 
     * 描述：修改字典
     * 
     * @param dictionary
     * @return
     * @author wangdeyou 2017年2月27日 下午3:07:45
     * @version 1.0
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResponseData update(Dictionary dictionary) {
        ResponseData responseData = new ResponseData();
        QueryParams params = new QueryParams();
        List<Dictionary> dictionariesList = new ArrayList<>();
        try {
            if (dictionary == null) {
                throw new BaseException("参数错误");
            }
            // 查询是否有默认显示的
            if (dictionary.getIsdefault() == 1) {
                params.put("parentid", dictionary.getParentid());
                params.put("isdefault", 1);
                dictionariesList = dictionaryService.queryBaseList(params);
                if (dictionariesList.size() != 0) {
                    responseData.setStatus(2);
                    responseData.setMessage("修改失败!已经有默认的,请重新添加");
                } else {
                    dictionaryService.doUpdateModel(dictionary);
                    responseData.setStatus(0);
                    responseData = ResponseData.getSuccessResponse();
                }
            } else {
                dictionaryService.doUpdateModel(dictionary);
                responseData.setStatus(0);
                responseData = ResponseData.getSuccessResponse();
            }
        } catch (Exception e) {
            responseData.setStatus(1);
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }



    /**
     * 
     * 描述：启用字典
     * 
     * @param id
     * @return
     * @author wangdeyou 2017年2月27日 下午3:41:13
     * @version 1.0
     */
    @RequestMapping("/setDictionaryEnable")
    public ResponseData setDictionaryEnable(@RequestParam String id) {
        ResponseData responseData = new ResponseData();
        QueryParams params = new QueryParams();
        if (id == null) {
            responseData = ResponseData.getErrorResponse("参数错误");
        }
        params.put("id", id);
        Dictionary dictionary = dictionaryService.queryOneBase(params);
        dictionary.setStatus(1);
        dictionaryService.doUpdateModel(dictionary);
        responseData = ResponseData.getSuccessResponse();
        return responseData;
    }



    /**
     * 
     * 描述：禁用字典
     * 
     * @param id
     * @return
     * @author wangdeyou 2017年2月27日 下午4:02:23
     * @version 1.0
     */
    @RequestMapping("/disableDictionary")
    public ResponseData disableDictionary(@RequestParam String id) {
        ResponseData responseData = new ResponseData();
        QueryParams params = new QueryParams();
        if (id == null) {
            responseData = ResponseData.getErrorResponse("参数错误");
        }
        params.put("id", id);
        Dictionary dictionary = dictionaryService.queryOneBase(params);
        dictionary.setStatus(0);
        dictionaryService.doUpdateModel(dictionary);
        responseData = ResponseData.getSuccessResponse();
        return responseData;
    }



    /**
     * 
     * 描述：根据key查询data不存在于该key的数据字典下
     * 
     * @param parentKey
     * @param data
     * @return
     * @author chenkehong 2017年2月27日 下午4:02:23
     * @version 1.0
     */
    @RequestMapping("/directionNotExist")
    @ResponseBody
    public ResponseData directionNotExist(@RequestParam String parentKey, String data) {
        ResponseData responseData = new ResponseData();
        if (StringUtils.isEmpty(data)) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            return responseData;
        }
        if (StringUtils.isEmpty(parentKey)) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            return responseData;
        }
        // 根据key查询出list
        QueryParams params = new QueryParams();
        params.put("status", 1);
        params.put("dictKey_like", parentKey + "_%");
        params.put("_orderBy", "showIndex_asc");
        List<Dictionary> dictList = dictionaryService.queryBaseList(params);
        for (Dictionary dictionary : dictList) {
            if (dictionary.getName().equals(data)) {
                responseData.setStatus(ResponseData.SUCCESS_STATUS);
                return responseData;
            }
        }
        responseData.setStatus(ResponseData.ERROR_STATUS);
        return responseData;
    }



    /**
     * 
     * 描述：按字典key查询字典信息
     * 
     * @param dictkey
     * @return
     * @author wangdeyou 2017年4月12日 上午10:12:04
     * @version 1.0
     */
    @RequestMapping("/getDictkey")
    @ResponseBody
    public ResponseData getDictkey(@RequestParam String inputValue, String values) {
        ResponseData responseData = new ResponseData();
        Dictionary dictionary = new Dictionary();
        if (StringUtils.isEmpty(inputValue)) {
            responseData.setStatus(ResponseData.ERROR_STATUS);
            return responseData;
        }
        String[] dic = inputValue.split("\\_");
        // 输入的哪一个字段
        String dictkeyValue = "";
        // 如果list没有值，则传回所有的字段下面的值，并且

        QueryParams params = new QueryParams();

        if (dic[0].equals("marketing")) {
            // 营销方式

            dictkeyValue = "marketing_";

            // 判断是不是数字
            if (values.equals("int")) {
                params.addParameter("dictkey", inputValue);
            } else {
                // 获取输入的值
                params.addParameter("description_like", "%" + dic[1] + "%");
            }

        } else if (dic[0].equals("bill")) {
            // 账单寄送方式

            dictkeyValue = "bill_send_";

            // 判断是不是数字
            if (values.equals("int")) {
                params.addParameter("dictkey", inputValue);
            } else {
                // 获取输入的值
                params.addParameter("description_like", "%" + dic[2] + "%");
            }
        } else if (dic[0].equals("card")) {
            // 卡片领取方式

            dictkeyValue = "card_collection_";

            // 判断是不是数字
            if (values.equals("int")) {
                params.addParameter("dictkey", inputValue);
            } else {
                // 获取输入的值
                params.addParameter("description_like", "%" + dic[2] + "%");
            }
        } else if (dic[0].equals("project")) {
            // 专案码

            dictkeyValue = "project_code_";

            // 判断是不是数字
            if (values.equals("int")) {
                params.addParameter("dictkey", inputValue);
            } else {
                // 获取输入的值
                params.addParameter("description_like", "%" + dic[2] + "%");
            }
        } else if (dic[0].equals("entry")) {
            // 进件方式

            dictkeyValue = "entry_mode_";

            // 判断是不是数字
            if (values.equals("int")) {
                params.addParameter("dictkey", inputValue);
            } else {
                // 获取输入的值
                params.addParameter("description_like", "%" + dic[2] + "%");
            }
        }
        params.addParameter("dictkey_like", "%" + dictkeyValue + "%");
        List<Dictionary> list = dictionaryService.queryBaseList(params);
        if (list.size() == 0) {
            // 查出当前字段下面的所有值
            QueryParams params1 = new QueryParams();
            params1.addParameter("dictkey_like", "%" + dictkeyValue + "%");
            List<Dictionary> listDic = dictionaryService.queryBaseList(params1);
            responseData.setData(listDic);
            responseData.setStatus(1);
        } else {
            dictionary = list.get(0);
            responseData.setData(dictionary);
            responseData.setStatus(0);
        }
        return responseData;
    }
    
    /**
     * 
     * 描述：拉取所有启用的上级字典
     * @return
     * @author fengshengliang 2017年9月28日 下午1:15:29 
     * @version 1.0
     */
    @RequestMapping("/getAllDict")
    @ResponseBody
    public ResponseData getAllDict(){
        QueryParams params = new QueryParams();
        params.addParameter("parentid", 1);
        params.addParameter("status", 1);
        List<Dictionary> dictionaries = dictionaryService.queryDetailList(params);
        return ResponseData.getSuccessResponse(dictionaries);
    }
}
