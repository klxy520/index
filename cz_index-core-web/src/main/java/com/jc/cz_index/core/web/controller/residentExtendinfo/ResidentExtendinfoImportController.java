package com.jc.cz_index.core.web.controller.residentExtendinfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
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
import com.jc.cz_index.model.ResidentExtendinfo;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.IResidentExtendinfoService;
import com.jc.cz_index.core.service.ISystemConfigService;

/**
 * 
 * 描述:居民扩展信息导入Controller
 * 
 * @author sunxuefeng 2017年10月11日 上午9:37:29
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/residentExtendinfoImport")
public class ResidentExtendinfoImportController {
    private static final Logger        LOG = Logger.getLogger(ResidentExtendinfoImportController.class);
    @Autowired
    private ISystemConfigService       systemConfigService;
    @Autowired
    private IOperationLogService       operationLogService;
    @Autowired
    private IResidentExtendinfoService residentExtendinfoService;



    /**
     * 
     * 描述：列表页面跳转(跳转到信息解析页面)
     * 
     * @return
     * @author sunxuefeng 2017年8月28日 下午1:17:10
     * @version 1.0
     */
    @RequestMapping("/listPage")
    public String listPage(String excelUrl, Model model) {
        model.addAttribute("excelUrl", excelUrl);
        return "/residentExtend/residentExtendinfo_import";
    }



    /**
     * 读取excel数据
     * 
     * @param request
     * @return
     * @throws IOException
     * @throws BaseException
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request) {
        // 解析前台框架dataTable传过来的值，格式：Map
        String excelUrl = request.getParameter("excelUrl");
        excelUrl = getSysConfigFileRootPath() + excelUrl;
        HttpSession session = WebUtils.getSession();
        session.setAttribute("excelUrl", excelUrl);
        Map<String, Object> result = new HashMap<String, Object>();
        List<ResidentExtendinfo> list = readResidentExtendinfo(excelUrl);
        result.put("iTotalRecords", list.size()); // 实际的行数
        result.put("iTotalDisplayRecords", list.size()); // 显示的行数,这个要和上面写的一样
        result.put("aaData", list);
        return result;
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
    public ResponseData judgmentResidentBaseinfoLegal(String excelUrl) {
        ResponseData responseData = new ResponseData();
        InputStream is = null;
        try {
            excelUrl = getSysConfigFileRootPath() + excelUrl;
            is = new FileInputStream(excelUrl);
            @SuppressWarnings("resource")
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            short s = xssfSheet.getRow(0).getLastCellNum();
            if (s != 3) {
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
     * 描述：批量插入居民扩展信息
     * 
     * @return
     * @author sunxuefeng 2017年9月7日 下午2:35:37
     * @version 1.0
     * @throws BaseException
     */
    @RequestMapping(value = "/batchAddResidentExtendinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData batchAddResidentExtendinfo() {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            HttpSession session = WebUtils.getSession();
            String excelUrl = (String) session.getAttribute("excelUrl");
            List<ResidentExtendinfo> list = readResidentExtendinfo(excelUrl);
            if (list != null && list.size() > 0) {
                responseData = residentExtendinfoService.batchAddResidentExtendinfo(list, systemUser);
                operationLogService.addOperationLog("居民健康卡扩展信息表", systemUser.getId() + "", "导入居民信息", "导入了" + list.size() + "条居民健康卡扩展信息",
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



    /***
     * 
     * 描述：对导入Excel文件进行数据解析(居民基本信息)
     * 
     * @param inputStream
     * @return
     * @throws IOException
     * @author sunxuefeng 2017年9月7日 下午2:09:49
     * @version 1.0
     * @throws BaseException
     */
    private List<ResidentExtendinfo> readResidentExtendinfo(String excelUrl) {
        InputStream is = null;
        List<ResidentExtendinfo> list = null;
        try {
            list = new ArrayList<ResidentExtendinfo>();
            is = new FileInputStream(excelUrl);
            @SuppressWarnings("resource")
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            int lastRowNum = xssfSheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                XSSFRow row = xssfSheet.getRow(i);
                if (row == null) {
                    xssfSheet.shiftRows(i + 1, lastRowNum, -1);
                    continue;
                }
                ResidentExtendinfo residentExtendinfo = new ResidentExtendinfo();
                XSSFCell healthNumberCell = row.getCell(0);
                if (healthNumberCell != null) {
                    healthNumberCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentExtendinfo.setHealthNumber(healthNumberCell.getStringCellValue());
                }
                XSSFCell isDisableFederationCell = row.getCell(1);
                if (isDisableFederationCell != null) {
                    isDisableFederationCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentExtendinfo.setIsDisableFederation(isDisableFederationCell.getStringCellValue());
                }
                
                XSSFCell isCivilAffairsCell = row.getCell(2);
                if (isCivilAffairsCell != null) {
                    isCivilAffairsCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentExtendinfo.setIsCivilAffairs(isCivilAffairsCell.getStringCellValue());
                }
                list.add(residentExtendinfo);
            }
        } catch (Exception e) {
            LOG.error("扩展信息导入失败,异常信息为:" + e.getMessage());
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
        return list;
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
