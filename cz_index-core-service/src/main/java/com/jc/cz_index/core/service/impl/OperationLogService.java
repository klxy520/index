/**
 * 2016/11/1 14:49:15 Jack Liu created.
 */

package com.jc.cz_index.core.service.impl;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IOperationLogDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.OperationLog;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.model.SystemUserAuth;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.core.service.ISystemUserAuthService;

/**
 * 操作日志 Service 实现 Created by Jack Liu on 2016/11/01.
 */
@Service
public class OperationLogService extends BaseService<OperationLog> implements IOperationLogService {

    @Autowired
    private IOperationLogDao       operationLogDao;

    @Autowired
    private ISystemUserAuthService systemUserAuthService;



    @Override
    public IDataProvider<OperationLog, Long> getModelDao() {
        return this.operationLogDao;
    }



    /**
     * 添加日志
     */
    @Override
    public void addOperationLog(String formName, String recordId, String type, String detail, String remark) {
        SystemUser user = (SystemUser) WebUtils.getLoginUser();
        SystemUserAuth userAuth = systemUserAuthService.getModelById(user.getAuthId());
        Long userId = user.getId();
        Integer userType = user.getUserType();
        String userName = user.getName();
        OperationLog operationLog = new OperationLog();
        operationLog.setUserId(userId);
        operationLog.setUserType(userType);
        operationLog.setUserName(userName);
        operationLog.setLoginName(userAuth.getLoginName());
        operationLog.setFormName(formName);
        operationLog.setRecordId(recordId);
        operationLog.setType(type);
        operationLog.setDetail(userName + "(" + userAuth.getLoginName() + ")" + detail);
        operationLog.setRemark(remark);
        operationLog.setOperationDate(DateUtils.getCurrentDate());
        operationLogDao.insertObject(operationLog);
    }



    @Override
    public void exportFile(HttpServletResponse response, List<String> headers, String time1, String time2, String excelName, int length) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        OutputStream os = null;
        XSSFWorkbook xWorkbook = null;
        QueryParams params = new QueryParams();
        if (time1 != "") {
            params.put("operationDate_r_geq", time1);
        }
        if (time2 != "") {
            params.put("operationDate_r_leq", time2);
        }
        List<OperationLog> operationLogList = operationLogDao.queryBaseList(params);
        try {
            String fileName = excelName + df.format(new Date()) + ".xlsx";
            os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename = " + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/octet-streem");
            xWorkbook = new XSSFWorkbook();
            XSSFSheet xSheet = xWorkbook.createSheet("employeePrintLogList");
            // set Sheet页头部
            setSheetHeader(xWorkbook, xSheet, headers);
            // set Sheet页内容
            setSheetContent(xWorkbook, xSheet, operationLogList, length);
            xWorkbook.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }



    /**
     * set Sheet页头部
     * 
     * @param xWorkbook
     * @param xSheet
     */
    private void setSheetHeader(XSSFWorkbook xWorkbook, XSSFSheet xSheet, List<String> list) {
        // 设置样式
        CellStyle cs = xWorkbook.createCellStyle();
        // 设置水平垂直居中
        cs.setAlignment(CellStyle.ALIGN_CENTER);
        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 设置字体
        Font headerFont = xWorkbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setFontName("宋体");
        cs.setFont(headerFont);
        cs.setWrapText(false);// 是否自动换行

        // 第一行头部内容
        XSSFRow xRow0 = xSheet.createRow(0);
        for (int i = 0, length = list.size(); i < length; i++) {
            XSSFCell xCell = xRow0.createCell(i);
            xCell.setCellStyle(cs);
            xCell.setCellValue(list.get(i));
        }
    }



    /**
     * set Sheet页内容
     * 
     * @param xWorkbook
     * @param xSheet
     */
    private void setSheetContent(XSSFWorkbook xWorkbook, XSSFSheet xSheet, List<OperationLog> OperationLogList, int length) {
        // 设置样式
        CellStyle cs = xWorkbook.createCellStyle();
        // 设置水平垂直居中
        cs.setAlignment(CellStyle.ALIGN_CENTER);
        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs.setWrapText(false); // 是否自动换行
        // 填充内容
        if (null != OperationLogList && OperationLogList.size() > 0) {
            for (int k = 0; k < OperationLogList.size(); k++) { // 总目录
                XSSFRow xRow = xSheet.createRow(k + 1);
                for (int j = 0; j < length; j++) { // 控制列
                    XSSFCell xCell = xRow.createCell(j);
                    xCell.setCellStyle(cs);
                    switch (j) {
                    case 0:
                        xCell.setCellValue(OperationLogList.get(k).getUserName()); // 姓名
                        break;
                    case 1:
                        xCell.setCellValue(OperationLogList.get(k).getType()); // 操作
                        break;
                    case 2:
                        xCell.setCellValue(OperationLogList.get(k).getDetail()); // 操作详情
                        break;
                    case 3:
                        xCell.setCellValue(DateUtils.formatDateToString(OperationLogList.get(k).getOperationDate())); // 操作时间
                        break;
                    default:
                        break;
                    }
                    // 宽度自适应
                    xSheet.autoSizeColumn(j, true);
                }
            }
        }
    }

}
