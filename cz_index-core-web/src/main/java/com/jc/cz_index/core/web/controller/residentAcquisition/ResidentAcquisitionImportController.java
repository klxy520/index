package com.jc.cz_index.core.web.controller.residentAcquisition;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.model.ResidentAcquisition;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.IResidentAcquisitionService;
import com.jc.cz_index.core.service.ISystemConfigService;

/**
 * 
 * 描述：居民信息采集导入Controller
 * 
 * @author sunxuefeng 2017年10月20日 上午10:12:04
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/residentacquisitionImport")
public class ResidentAcquisitionImportController extends BaseController {
    private static final Logger         LOG = Logger.getLogger(ResidentAcquisitionImportController.class);
    @Autowired
    private ISystemConfigService        systemConfigService;
    @Autowired
    private IOperationLogService        operationLogService;
    @Autowired
    private IResidentAcquisitionService residentAcquisitionService;



    /**
     * 
     * 描述：列表页面跳转(跳转到信息解析页面)
     * 
     * @return
     * @author sunxuefeng 2017年8月28日 下午1:17:10
     * @version 1.0
     */
    @RequestMapping("/listPage")
    public String ListPage(String excelUrl, Model model) {
        model.addAttribute("excelUrl", excelUrl);
        return "/residentAcquisition/residentAcquisition_import";
    }



    /**
     * 读取excel数据
     * 
     * @param request
     * @return
     * @throws IOException
     * @throws BaseException
     */
    @RequestMapping("/importResidentAcquisitionDta")
    @ResponseBody
    public Map<String, Object> importResidentAcquisitionDta(HttpServletRequest request) {
        // 解析前台框架dataTable传过来的值，格式：Map
        String excelUrl = request.getParameter("excelUrl");
        excelUrl = getSysConfigFileRootPath() + excelUrl;
        HttpSession session = WebUtils.getSession();
        session.setAttribute("excelUrl", excelUrl);
        Map<String, Object> result = new HashMap<String, Object>();
        List<ResidentAcquisition> list = readResidentAcquisition((excelUrl));
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
    @RequestMapping(value = "/judgmentResidentAcquisitionLegal")
    @ResponseBody
    public ResponseData judgmentResidentAcquisitionLegal(String excelUrl) {
        ResponseData responseData = new ResponseData();
        InputStream is = null;
        try {
            excelUrl = getSysConfigFileRootPath() + excelUrl;
            is = new FileInputStream(excelUrl);
            short s = 0;
            if (excelUrl.endsWith("xlsx")) {
                @SuppressWarnings("resource")
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                s = xssfSheet.getRow(1).getLastCellNum();
            } else {
                @SuppressWarnings("resource")
                HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
                HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
                s = sheet.getRow(1).getLastCellNum();
            }
            if (s != 20) {
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
     * 描述：向居民采集信息表中批量插入数据
     * 
     * @author sunxuefeng 2017年10月24日 下午1:33:19
     * @version 1.0
     */
    @RequestMapping(value = "/bathAddResidentAcquisitions", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData bathAddResidentAcquisitions() {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            HttpSession session = WebUtils.getSession();
            String excelUrl = (String) session.getAttribute("excelUrl");
            List<ResidentAcquisition> list = readResidentAcquisition(excelUrl);
            if (list != null && list.size() > 0) {
                responseData = residentAcquisitionService.bathAddResidentAcquisitions(list);
                operationLogService.addOperationLog("居民采集信息表", systemUser.getId() + "", "导入居民采集信息", "导入了" + list.size() + "条居民采集信息", "无");
                LOG.debug("居民采集信息导入成功信息:" + responseData.getMessage());
            } else {
                responseData.setStatus(ResponseData.ERROR_STATUS);
                responseData.setMessage("居民采集信息为空,导入失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("居民采集信息导入失败,异常信息:" + e.getMessage());
            responseData.setStatus(ResponseData.ERROR_STATUS);
            responseData.setMessage("居民采集信息导入失败");
        }
        return responseData;
    }



    /***
     * 
     * 描述：对导入Excel文件进行数据解析(居民采集信息)
     * 
     * @param inputStream
     * @return
     * @throws IOException
     * @author sunxuefeng 2017年9月7日 下午2:09:49
     * @version 1.0
     * @throws BaseException
     */
    private List<ResidentAcquisition> readResidentAcquisition(String excelUrl) {
        InputStream is = null;
        List<ResidentAcquisition> list = null;
        try {
            is = new FileInputStream(excelUrl);
            if (excelUrl.endsWith("xlsx")) {
                @SuppressWarnings("resource")
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                list = readExcleData(xssfSheet);
            } else {
                @SuppressWarnings("resource")
                HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
                HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
                list = readExcleData(sheet);
            }
        } catch (Exception e) {
            LOG.error("居民采集信息导入失败,异常信息为:" + e.getMessage());
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



    /**
     * 
     * 描述通过传入不版本的excel的sheet对数据进行解析(兼容2003/2007/2007以上)
     * 
     * @param sheet
     * @return
     * @author sunxuefeng 2017年10月25日 上午9:34:41
     * @version 1.0
     */
    private List<ResidentAcquisition> readExcleData(Sheet sheet) throws Exception {
        List<ResidentAcquisition> list = new ArrayList<ResidentAcquisition>();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        Date currentDate = DateUtils.getCurrentDate();
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 2; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                sheet.shiftRows(i + 1, lastRowNum, -1);
                continue;
            }
            ResidentAcquisition residentAcquisition = new ResidentAcquisition();
            Cell bidUtilCell = row.getCell(1);
            if (bidUtilCell != null) {
                bidUtilCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setBidUtil(bidUtilCell.getStringCellValue());
            }
            Cell nameCell = row.getCell(2);
            if (nameCell != null) {
                nameCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setName(nameCell.getStringCellValue());
            }
            Cell identityNumberCell = row.getCell(3);
            if (identityNumberCell != null) {
                identityNumberCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setIdentityNumber(identityNumberCell.getStringCellValue());
            }
            Cell issuersCertificateOrganCell = row.getCell(4);
            if (issuersCertificateOrganCell != null) {
                issuersCertificateOrganCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setIssuersCertificateOrgan(issuersCertificateOrganCell.getStringCellValue());
            }
            Cell certificateValidityPeriodCell = row.getCell(5);
            if (certificateValidityPeriodCell != null) {
                certificateValidityPeriodCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setCertificateValidityPeriod(certificateValidityPeriodCell.getStringCellValue());
            }
            Cell nationalCell = row.getCell(6);
            if (nationalCell != null) {
                nationalCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setNational(nationalCell.getStringCellValue());
            }
            Cell educationLevelCell = row.getCell(7);
            if (educationLevelCell != null) {
                educationLevelCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setEducationLevel(educationLevelCell.getStringCellValue());
            }
            Cell houseAddressCell = row.getCell(8);
            if (houseAddressCell != null) {
                houseAddressCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setHouseAddress(houseAddressCell.getStringCellValue());
            }
            Cell nowAddressCell = row.getCell(9);
            if (nowAddressCell != null) {
                nowAddressCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setNowAddress(nowAddressCell.getStringCellValue());
            }
            Cell postCodeCell = row.getCell(10);
            if (postCodeCell != null) {
                postCodeCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setPostCode(postCodeCell.getStringCellValue());
            }
            Cell contactPhoneCell = row.getCell(11);
            if (contactPhoneCell != null) {
                contactPhoneCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setContactPhone(contactPhoneCell.getStringCellValue());
            }
            Cell newRuralNumberCell = row.getCell(12);
            if (newRuralNumberCell != null) {
                newRuralNumberCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setNewRuralNumber(newRuralNumberCell.getStringCellValue());
            }
            Cell socialSecurityNumCell = row.getCell(13);
            if (socialSecurityNumCell != null) {
                socialSecurityNumCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setSocialSecurityNum(socialSecurityNumCell.getStringCellValue());
            }
            Cell salaryCardBankCell = row.getCell(14);
            if (salaryCardBankCell != null) {
                salaryCardBankCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setSalaryCardBank(salaryCardBankCell.getStringCellValue());
            }
            Cell healthCardBankCell = row.getCell(15);
            if (healthCardBankCell != null) {
                healthCardBankCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setHealthCardBank(healthCardBankCell.getStringCellValue());
            }
            Cell professionalCell = row.getCell(16);
            if (professionalCell != null) {
                residentAcquisition.setProfessional(professionalCell.getStringCellValue());
            }
            Cell industryCell = row.getCell(17);
            if (industryCell != null) {
                industryCell.setCellType(Cell.CELL_TYPE_STRING);
                residentAcquisition.setIndustry(industryCell.getStringCellValue());
            }
            residentAcquisition.setCreator(systemUser.getId());
            residentAcquisition.setCreateDate(currentDate);
            residentAcquisition.setDelFalg(0);
            list.add(residentAcquisition);
        }
        return list;
    }
}
