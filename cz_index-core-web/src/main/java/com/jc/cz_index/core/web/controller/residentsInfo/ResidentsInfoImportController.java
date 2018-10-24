package com.jc.cz_index.core.web.controller.residentsInfo;

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
import com.jc.cz_index.model.ResidentsInfo;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.IResidentsInfoService;
import com.jc.cz_index.core.service.ISystemConfigService;

/**
 * 
 * 描述：居民基本信息导入
 * 
 * @author sunxuefeng 2017年9月28日 上午9:37:25
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/residentsInfoImport")
public class ResidentsInfoImportController {

    private static final Logger   LOG = Logger.getLogger(ResidentsInfoImportController.class);
    @Autowired
    private ISystemConfigService  systemConfigService;
    @Autowired
    private IOperationLogService  operationLogService;
    @Autowired
    private IResidentsInfoService residentsInfoService;



    /**
     * 
     * 描述：列表页面跳转(跳转到信息解析页面)
     * 
     * @return
     * @author sunxuefeng 2017年8月28日 下午1:17:10
     * @version 1.0
     */
    @RequestMapping("/listPage")
    public String baseListPage(String excelUrl, Model model) {
        model.addAttribute("excelUrl", excelUrl);
        return "/residentsInfo/residentsInfo_import";
    }



    /**
     * 读取excel数据
     * 
     * @param request
     * @return
     * @throws IOException
     * @throws BaseException
     */
    @RequestMapping("/importDtaresidentsInfo")
    @ResponseBody
    public Map<String, Object> importDtaresidentsInfo(HttpServletRequest request) {
        // 解析前台框架dataTable传过来的值，格式：Map
        String excelUrl = request.getParameter("excelUrl");
        excelUrl = getSysConfigFileRootPath() + excelUrl;
        HttpSession session = WebUtils.getSession();
        session.setAttribute("excelUrl", excelUrl);
        Map<String, Object> result = new HashMap<String, Object>();
        List<ResidentsInfo> list = readResidentsInfo((excelUrl));
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
    @RequestMapping(value = "/batchAddResidentsinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData batchAddResidentsinfo() {
        ResponseData responseData = new ResponseData();
        SystemUser systemUser = (SystemUser) WebUtils.getLoginUser();
        try {
            HttpSession session = WebUtils.getSession();
            String excelUrl = (String) session.getAttribute("excelUrl");
            List<ResidentsInfo> list = readResidentsInfo(excelUrl);
            if (list != null && list.size() > 0) {
                responseData = residentsInfoService.batchAddResidentsinfo(list, systemUser);
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
            short s = xssfSheet.getRow(0).getLastCellNum();
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
    private List<ResidentsInfo> readResidentsInfo(String excelUrl) {
        InputStream is = null;
        List<ResidentsInfo> list = null;
        try {
            list = new ArrayList<ResidentsInfo>();
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
                ResidentsInfo residentsInfo = new ResidentsInfo();
                XSSFCell bidUtilCell = row.getCell(0);
                if (bidUtilCell != null) {
                    bidUtilCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setBidUtil(bidUtilCell.getStringCellValue());
                }
                XSSFCell bankCardNumbeCell = row.getCell(1);
                if (bankCardNumbeCell != null) {
                    bankCardNumbeCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setBankCardNumber(bankCardNumbeCell.getStringCellValue());
                }
                XSSFCell cardTypeCell = row.getCell(2);
                if (cardTypeCell != null) {
                    cardTypeCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setCardType(cardTypeCell.getStringCellValue());
                }
                XSSFCell issuersCardNameCell = row.getCell(3);
                if (issuersCardNameCell != null) {
                    issuersCardNameCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setIssuersCardName(issuersCardNameCell.getStringCellValue());
                }
                XSSFCell issuersCodeCell = row.getCell(4);
                if (issuersCodeCell != null) {
                    issuersCodeCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setIssuersCardCode(issuersCodeCell.getStringCellValue());
                }
                XSSFCell issuingSerialNumberCell = row.getCell(5);
                if (issuingSerialNumberCell != null) {
                    issuingSerialNumberCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setIssuingSerialNumber(issuingSerialNumberCell.getStringCellValue());
                }
                XSSFCell issuersCardCertificateCell = row.getCell(6);
                if (issuersCardCertificateCell != null) {
                    issuersCardCertificateCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setIssuersCardCertificate(issuersCardCertificateCell.getStringCellValue());
                }
                XSSFCell issuingTimeCell = row.getCell(7);
                if (issuingTimeCell != null) {
                    issuingTimeCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setIssuingTime(issuingTimeCell.getStringCellValue());
                }
                XSSFCell nameCell = row.getCell(8);
                if (nameCell != null) {
                    nameCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setName(nameCell.getStringCellValue());
                }
                XSSFCell sexCell = row.getCell(9);
                if (sexCell != null) {
                    sexCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setSex(sexCell.getStringCellValue());
                }
                XSSFCell nationalCodeCell = row.getCell(10);
                if (nationalCodeCell != null) {
                    nationalCodeCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setNationalCode(nationalCodeCell.getStringCellValue());
                }
                XSSFCell birthdayCodeCell = row.getCell(11);
                if (birthdayCodeCell != null) {
                    birthdayCodeCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setBirthday(birthdayCodeCell.getStringCellValue());
                }
                XSSFCell identityNumberCell = row.getCell(12);
                if (identityNumberCell != null) {
                    identityNumberCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setIdentityNumber(identityNumberCell.getStringCellValue());
                }
                XSSFCell cardValidityPeriodCell = row.getCell(13);
                if (cardValidityPeriodCell != null) {
                    cardValidityPeriodCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setCardValidityPeriod(cardValidityPeriodCell.getStringCellValue());
                }
                XSSFCell selfPhoneCell = row.getCell(14);
                if (selfPhoneCell != null) {
                    selfPhoneCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setSelfPhone(selfPhoneCell.getStringCellValue());
                }
                XSSFCell medicalPaymentCell = row.getCell(15);
                if (medicalPaymentCell != null) {
                    medicalPaymentCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setMedicalPayment(medicalPaymentCell.getStringCellValue());
                }
                XSSFCell houseAddressCell = row.getCell(16);
                if (houseAddressCell != null) {
                    houseAddressCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setHouseAddress(houseAddressCell.getStringCellValue());
                }
                XSSFCell nowAddressCell = row.getCell(17);
                if (nowAddressCell != null) {
                    nowAddressCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setNowAddress(nowAddressCell.getStringCellValue());
                }
                XSSFCell contactNameCell = row.getCell(18);
                if (contactNameCell != null) {
                    contactNameCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setContactName(contactNameCell.getStringCellValue());
                }
                XSSFCell contactRelationCell = row.getCell(19);
                if (contactRelationCell != null) {
                    contactRelationCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setContactRelation(contactRelationCell.getStringCellValue());
                }
                XSSFCell contactPhoneCell = row.getCell(20);
                if (contactPhoneCell != null) {
                    contactPhoneCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setContactPhone(contactPhoneCell.getStringCellValue());
                }
                XSSFCell educationLevelCodeCell = row.getCell(21);
                if (educationLevelCodeCell != null) {
                    educationLevelCodeCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setEducationLevelCode(educationLevelCodeCell.getStringCellValue());
                }
                XSSFCell maritalStatusCodeCell = row.getCell(22);
                if (maritalStatusCodeCell != null) {
                    maritalStatusCodeCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setMaritalStatusCode(maritalStatusCodeCell.getStringCellValue());
                }
                XSSFCell professionalCodeCell = row.getCell(23);
                if (professionalCodeCell != null) {
                    professionalCodeCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setProfessionalCode(professionalCodeCell.getStringCellValue());
                }
                XSSFCell socialSecurityNumCell = row.getCell(24);
                if (socialSecurityNumCell != null) {
                    socialSecurityNumCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setSocialSecurityNum(socialSecurityNumCell.getStringCellValue());
                }

                XSSFCell issuingBankCell = row.getCell(25);
                if (issuingBankCell != null) {
                    issuingBankCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setIssuingBank(issuingBankCell.getStringCellValue());
                }
                XSSFCell cardStatusCell = row.getCell(26);
                if (cardStatusCell != null) {
                    cardStatusCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setCardStatus(cardStatusCell.getStringCellValue());
                }
                XSSFCell chipNumCell = row.getCell(27);
                if (chipNumCell != null) {
                    chipNumCell.setCellType(Cell.CELL_TYPE_STRING);
                    residentsInfo.setChipNum(chipNumCell.getStringCellValue());
                }
                list.add(residentsInfo);
            }
        } catch (Exception e) {
            LOG.error("基本信息导入失败,异常信息为:" + e.getMessage());
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
