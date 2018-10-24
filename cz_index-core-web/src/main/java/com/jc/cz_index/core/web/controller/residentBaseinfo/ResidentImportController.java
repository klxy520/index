package com.jc.cz_index.core.web.controller.residentBaseinfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dto.resident.ResidentDataDto;
import com.jc.cz_index.dto.resident.ResidentExtendinfoExeclDto;
import com.jc.cz_index.model.ResidentBaseinfo;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.component.ImportExcelUtil;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.IResidentBaseinfoService;
import com.jc.cz_index.core.service.ISystemConfigService;

/**
 * * 描述：批量导入用户数据
 * 
 * @author sunxuefeng 2017年9月6日 下午2:32:23
 * @version 1.0
 */
@RequestMapping("/importResident")
@Controller
public class ResidentImportController extends BaseController {
    private static final Logger      LOG = Logger.getLogger(ResidentImportController.class);
    @Autowired
    private ISystemConfigService     systemConfigService;
    @Autowired
    private IResidentBaseinfoService residentBaseinfoService;
    @Autowired
    private IOperationLogService     operationLogService;



    /**
     * 
     * 描述：列表页面跳转(居民基本信息)
     * 
     * @return
     * @author sunxuefeng 2017年8月28日 下午1:17:10
     * @version 1.0
     */
    @RequestMapping("/baseListPage")
    public String baseListPage(String excelUrl, Model model) {
        model.addAttribute("excelUrl", excelUrl);
        return "/resident/BaseResident_import";
    }



    /**
     * 
     * 描述：判断Excel文件数据是否合法(基本信息)
     * 
     * @return
     * @author sunxuefeng 2017年9月12日 下午3:08:06
     * @version 1.0
     */
    @RequestMapping(value = "/judgmentResidentBaseinfoLegal", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData judgmentResidentBaseinfoLegal(String excelUrl, Integer number) {
        ResponseData responseData = new ResponseData();
        InputStream is = null;
        try {
            excelUrl = getSysConfigFileRootPath() + excelUrl;
            is = new FileInputStream(excelUrl);
            @SuppressWarnings("resource")
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            short s = xssfSheet.getRow(2).getLastCellNum();
            if (s != number) {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("解析失败,文件数据格式错误");
            }
        } catch (Exception e) {
            LOG.error("解析失败,文件有错误,异常信息为:" + e);
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("解析失败,文件有错误");
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseData;
    }



    /**
     * 
     * 描述：列表页面跳转(居民全部信息)
     * 
     * @return
     * @author sunxuefeng 2017年8月28日 下午1:17:10
     * @version 1.0
     */
    @RequestMapping("/listPage")
    public String listPage(String excelUrl, Model model) {
        model.addAttribute("excelUrl", excelUrl);
        return "/resident/allResident_import";
    }



    /**
     * 
     * 描述：列表页面跳转(居民全部信息)
     * 
     * @return
     * @author sunxuefeng 2017年8月28日 下午1:17:10
     * @version 1.0
     */
    @RequestMapping("/extendListPage")
    public String extendListPage(String excelUrl, Model model) {
        model.addAttribute("excelUrl", excelUrl);
        return "/resident/extendResident_import";
    }



    /**
     * 读取excel数据(居民基本信息)
     * 
     * @param request
     * @return
     * @throws IOException
     * @throws BaseException
     */
    @RequestMapping("/importDtaResidentBaseinfo")
    @ResponseBody
    public Map<String, Object> importDtaResidentBaseinfo(HttpServletRequest request) {
        // 解析前台框架dataTable传过来的值，格式：Map
        String excelUrl = request.getParameter("excelUrl");
        excelUrl = getSysConfigFileRootPath() + excelUrl;
        HttpSession session = WebUtils.getSession();
        session.setAttribute("excelUrl", excelUrl);
        Map<String, Object> result = new HashMap<String, Object>();
        List<ResidentBaseinfo> list = ImportExcelUtil.readBaseResidentXlsx(excelUrl);
        result.put("iTotalRecords", list.size()); // 实际的行数
        result.put("iTotalDisplayRecords", list.size()); // 显示的行数,这个要和上面写的一样
        result.put("aaData", list);
        return result;
    }



    /**
     * 读取excel数据(包含居民基本信息和扩展数据)
     * 
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/importDtaResidentDataDto")
    @ResponseBody
    public Map<String, Object> importDtaResidentDataDto(HttpServletRequest request) throws IOException {
        // 解析前台框架dataTable传过来的值，格式：Map
        String excelUrl = request.getParameter("excelUrl");
        excelUrl = getSysConfigFileRootPath() + excelUrl;
        if (StringUtils.isEmpty(excelUrl)) {
            throw new IOException("文件路径为空");
        }
        HttpSession session = WebUtils.getSession();
        session.setAttribute("excelUrl", excelUrl);
        Map<String, Object> result = new HashMap<String, Object>();
        List<ResidentDataDto> list = ImportExcelUtil.readResidentDataDtoXlsx(excelUrl);
        result.put("iTotalRecords", list.size()); // 实际的行数
        result.put("iTotalDisplayRecords", list.size()); // 显示的行数,这个要和上面写的一样
        result.put("aaData", list);
        return result;
    }



    /**
     * 
     * 描述：批量插入居民基本信息
     * 
     * @return
     * @author sunxuefeng 2017年9月7日 下午2:35:37
     * @version 1.0
     * @throws BaseException
     */
    @RequestMapping(value = "/batchAddResidentBaseinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData batchAddResidentBaseinfo() {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            HttpSession session = WebUtils.getSession();
            String excelUrl = (String) session.getAttribute("excelUrl");
            List<ResidentBaseinfo> list = ImportExcelUtil.readBaseResidentXlsx(excelUrl);
            if (list != null && list.size() > 0) {
                responseData = residentBaseinfoService.batchAddResidentBaseinfo(list, systemUser);
                operationLogService.addOperationLog("居民健康卡基本信息表", systemUser.getId() + "", "导入居民信息", "导入了" + list.size() + "条居民健康卡基本信息",
                        "无");
                LOG.debug("居民基本信息导入成功信息:" + responseData.getMessage());
            } else {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("居民基本信息为空,导入失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("居民基本信息导入失败,异常信息:" + e.getMessage());
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("居民基本信息导入失败");
        }
        return responseData;
    }



    /**
     * 
     * 描述：批量插入居民信息(包括基本信息和扩展信息)
     * 
     * @return
     * @author sunxuefeng 2017年9月7日 下午2:35:37
     * @version 1.0
     * @throws BaseException
     */
    @RequestMapping(value = "/batchAddResidentData", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData batchAddResidentData() {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            HttpSession session = WebUtils.getSession();
            String excelUrl = (String) session.getAttribute("excelUrl");
            List<ResidentDataDto> list = ImportExcelUtil.readResidentDataDtoXlsx(excelUrl);
            if (list != null && list.size() > 0) {
                responseData = residentBaseinfoService.batchAddResidentData(list, systemUser);
                operationLogService.addOperationLog("居民健康卡信息", systemUser.getId() + "", "导入居民信息", "导入了" + list.size() + "条居民健康卡信息", "无");
                LOG.debug("居民信息导入成功信息:" + responseData.getMessage());
            } else {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("居民信息为空,导入失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("居民信息导入失败,异常信息:" + e.getMessage());
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("居民信息导入失败");
        }
        return responseData;
    }



    /**
     * 读取excel数据(扩展数据)
     * 
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/importResidentExtendinfo")
    @ResponseBody
    public Map<String, Object> importResidentExtendinfo(HttpServletRequest request) throws IOException {
        // 解析前台框架dataTable传过来的值，格式：Map
        String excelUrl = request.getParameter("excelUrl");
        excelUrl = getSysConfigFileRootPath() + excelUrl;
        if (StringUtils.isEmpty(excelUrl)) {
            throw new IOException("文件路径为空");
        }
        HttpSession session = WebUtils.getSession();
        session.setAttribute("excelUrl", excelUrl);
        Map<String, Object> result = new HashMap<String, Object>();
        List<ResidentExtendinfoExeclDto> list = ImportExcelUtil.readResidentExtendinfoXlsx(excelUrl);
        result.put("iTotalRecords", list.size()); // 实际的行数
        result.put("iTotalDisplayRecords", list.size()); // 显示的行数,这个要和上面写的一样
        result.put("aaData", list);
        return result;
    }



    /**
     * 
     * 描述：批量插入居民信息(扩展信息)
     * 
     * @return
     * @author sunxuefeng 2017年9月7日 下午2:35:37
     * @version 1.0
     * @throws BaseException
     */
    @RequestMapping(value = "/batchAddExtendinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData batchAddExtendinfo() {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            HttpSession session = WebUtils.getSession();
            String excelUrl = (String) session.getAttribute("excelUrl");
            List<ResidentExtendinfoExeclDto> list = ImportExcelUtil.readResidentExtendinfoXlsx(excelUrl);
            if (list != null && list.size() > 0) {
                responseData = residentBaseinfoService.batchAddResidentExtendinfo(list, systemUser);
                operationLogService.addOperationLog("居民健康卡扩展信息表", systemUser.getId() + "", "导入居民扩展信息", "导入了" + list.size() + "条居民健康卡扩展信息",
                        "无");
                LOG.debug("居民扩展信息导入成功信息:" + responseData.getMessage());
            } else {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("居民扩展基本信息为空,导入失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("居民扩展信息导入失败,异常信息:" + e.getMessage());
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("居民扩展信息导入失败");
        }
        return responseData;
    }



    /**
     * 
     * 描述：通过居民健康卡号查询居民基本信息是否存在
     * 
     * @param healthNumber
     * @return
     * @author sunxuefeng 2017年9月11日 下午4:12:09
     * @version 1.0
     */
    @RequestMapping(value = "/queyResidentBasByexist")
    @ResponseBody
    public ResponseData queyResidentBasByexist(String healthNumber) {
        ResponseData responseData = residentBaseinfoService.queyResidentBasByexist(healthNumber);
        return responseData;
    }



    /**
     * 
     * 描述：获取配置库文件根路径
     * 
     * @return
     * @author sunxuefeng 2017年9月6日 下午3:36:20
     * @version 1.0
     */
    private String getSysConfigFileRootPath() {
        String rootPath = systemConfigService.getValue(ContentUtils.FILE_UPLOAD_ROOT_PATH);
        // 如果不为空并且最后一位为/
        if (StringUtils.isNotEmpty(rootPath) && rootPath.substring(rootPath.length() - 1).equals("/")) {
        } else {
            if (StringUtils.isEmpty(rootPath)) {
                rootPath = "";
            }
            rootPath = rootPath + "/";
        }
        return rootPath;
    }

}