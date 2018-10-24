package com.jc.cz_index.core.component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.dto.resident.ResidentDataDto;
import com.jc.cz_index.dto.resident.ResidentExtendinfoExeclDto;
import com.jc.cz_index.model.ResidentBaseinfo;

/***
 * 
 * 描述：居民基本数据导入数据解析
 * 
 * @author sunxuefeng 2017年9月6日 下午1:30:25
 * @version 1.0
 */
public class ImportExcelUtil {
    private static final Logger LOG = Logger.getLogger(ImportExcelUtil.class);



    /***
     * 
     * 描述：全部数据导入(包括基本数据和扩展数据,将Excel文件中一行基本数据转换为一个实体(ResidentData))
     * 
     * @param inputStream
     * @return
     * @throws IOException
     * @author sunxuefeng 2017年9月7日 下午2:09:49
     * @version 1.0
     */
    public static List<ResidentDataDto> readResidentDataDtoXlsx(String excelUrl) {
        InputStream is = null;
        try {
            is = new FileInputStream(excelUrl);
            List<ResidentDataDto> residentDataDtolist = new ArrayList<ResidentDataDto>();
            @SuppressWarnings("resource")
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            int lastRowNum = xssfSheet.getLastRowNum() + 1;
            for (int i = 2; i < lastRowNum; i++) {
                XSSFRow row = xssfSheet.getRow(i);
                if (row == null) {
                    xssfSheet.shiftRows(i + 1, lastRowNum, -1);
                    continue;
                }
                ResidentDataDto residentDataDto = new ResidentDataDto();
                residentDataDto.setRealName(row.getCell(1).getStringCellValue());
                residentDataDto.setHealthNumber(row.getCell(2).getStringCellValue());
                residentDataDto.setSocialNumber(row.getCell(3).getStringCellValue());
                residentDataDto.setIdNumber(row.getCell(4).getStringCellValue());
                XSSFCell xssfCell = row.getCell(5);
                if (xssfCell != null) {
                    String date = parseExcel(xssfCell);
                    residentDataDto.setPeriodValidityDate(DateUtils.stringTODate(date));
                }
                residentDataDto.setSex(row.getCell(6).getStringCellValue());
                double age = row.getCell(7).getNumericCellValue();
                residentDataDto.setAge((int) age);
                residentDataDto.setNation(row.getCell(8).getStringCellValue());
                residentDataDto.setHouseAddress(row.getCell(9).getStringCellValue());
                residentDataDto.setNowAddress(row.getCell(10).getStringCellValue());
                XSSFCell cell = row.getCell(11);
                if (cell != null) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    residentDataDto.setPostCode(cell.getStringCellValue());
                }
                XSSFCell cellPhone = row.getCell(12);
                if (cellPhone != null) {
                    cellPhone.setCellType(Cell.CELL_TYPE_STRING);
                    residentDataDto.setPhone(cellPhone.getStringCellValue());
                }
                residentDataDto.setWrokUnit(row.getCell(13).getStringCellValue());
                residentDataDto.setEducation(row.getCell(14).getStringCellValue());
                residentDataDto.setInsuranceType(row.getCell(15).getStringCellValue());
                residentDataDto.setDisabilityType(row.getCell(16).getStringCellValue());
                residentDataDto.setUnionFeature(row.getCell(17).getStringCellValue());
                residentDataDto.setRetiredCadres(row.getCell(18).getStringCellValue());
                residentDataDto.setHelpHouse(row.getCell(19).getStringCellValue());
                residentDataDto.setLowType(row.getCell(20).getStringCellValue());
                residentDataDto.setIllnessType(row.getCell(21).getStringCellValue());
                residentDataDtolist.add(residentDataDto);
            }

            return residentDataDtolist;
        } catch (Exception e) {
            LOG.error("Excel文件导入失败:" + e.getMessage());
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
        return null;
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
    public static List<ResidentBaseinfo> readBaseResidentXlsx(String excelUrl) {
        InputStream is = null;
        List<ResidentBaseinfo> list = null;
        try {
            list = new ArrayList<ResidentBaseinfo>();
            is = new FileInputStream(excelUrl);
            @SuppressWarnings("resource")
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            for (int i = 2; i <= xssfSheet.getLastRowNum(); i++) {
                XSSFRow row = xssfSheet.getRow(i);
                ResidentBaseinfo residentBaseinfo = new ResidentBaseinfo();
                residentBaseinfo.setRealName(row.getCell(1).getStringCellValue());
                residentBaseinfo.setHealthNumber(row.getCell(2).getStringCellValue());
                residentBaseinfo.setSocialNumber(row.getCell(3).getStringCellValue());
                residentBaseinfo.setIdNumber(row.getCell(4).getStringCellValue());
                XSSFCell xssfCell = row.getCell(5);
                if (xssfCell != null) {
                    String date = parseExcel(xssfCell);
                    residentBaseinfo.setPeriodValidityDate(DateUtils.stringTODate(date));
                }
                residentBaseinfo.setSex(row.getCell(6).getStringCellValue());
                double age = row.getCell(7).getNumericCellValue();
                residentBaseinfo.setAge((int) age);
                residentBaseinfo.setNation(row.getCell(8).getStringCellValue());
                residentBaseinfo.setHouseAddress(row.getCell(9).getStringCellValue());
                residentBaseinfo.setNowAddress(row.getCell(10).getStringCellValue());
                XSSFCell cell = row.getCell(11);
                if (cell != null) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    residentBaseinfo.setPostCode(cell.getStringCellValue());
                }
                XSSFCell cellPhone = row.getCell(12);
                if (cellPhone != null) {
                    cellPhone.setCellType(Cell.CELL_TYPE_STRING);
                    residentBaseinfo.setPhone(cellPhone.getStringCellValue());
                }
                residentBaseinfo.setWrokUnit(row.getCell(13).getStringCellValue());
                residentBaseinfo.setEducation(row.getCell(14).getStringCellValue());
                list.add(residentBaseinfo);
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



    /***
     * 
     * 描述：对导入Excel文件进行数据解析(居民扩展信息)
     * 
     * @param inputStream
     * @return
     * @throws IOException
     * @author sunxuefeng 2017年9月7日 下午2:09:49
     * @version 1.0
     */
    public static List<ResidentExtendinfoExeclDto> readResidentExtendinfoXlsx(String excelUrl) {
        InputStream is = null;
        try {
            List<ResidentExtendinfoExeclDto> list = new ArrayList<ResidentExtendinfoExeclDto>();
            is = new FileInputStream(excelUrl);
            @SuppressWarnings("resource")
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            int lastRowNum = xssfSheet.getLastRowNum() + 1;
            for (int i = 2; i < lastRowNum; i++) {
                XSSFRow row = xssfSheet.getRow(i);
                if (row == null) {
                    xssfSheet.shiftRows(i + 1, lastRowNum, -1);
                    continue;
                }
                ResidentExtendinfoExeclDto dto = new ResidentExtendinfoExeclDto();
                dto.setHealthNumber(row.getCell(1).getStringCellValue());
                dto.setInsuranceType(row.getCell(2).getStringCellValue());
                dto.setDisabilityType(row.getCell(3).getStringCellValue());
                dto.setUnionFeature(row.getCell(4).getStringCellValue());
                dto.setRetiredCadres(row.getCell(5).getStringCellValue());
                dto.setHelpHouse(row.getCell(6).getStringCellValue());
                dto.setLowType(row.getCell(7).getStringCellValue());
                dto.setIllnessType(row.getCell(8).getStringCellValue());
                list.add(dto);
            }
            return list;
        } catch (Exception e) {
            LOG.error("Excel文件导入失败:" + e.getMessage());
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
        return null;
    }



    private static String parseExcel(Cell cell) {
        String result = new String();
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
            if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                SimpleDateFormat sdf = null;
                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                    sdf = new SimpleDateFormat("HH:mm");
                } else {// 日期
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                }
                Date date = cell.getDateCellValue();
                result = sdf.format(date);
            } else if (cell.getCellStyle().getDataFormat() == 58) {
                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                double value = cell.getNumericCellValue();
                Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                result = sdf.format(date);
            } else {
                double value = cell.getNumericCellValue();
                CellStyle style = cell.getCellStyle();
                DecimalFormat format = new DecimalFormat();
                String temp = style.getDataFormatString();
                // 单元格设置成常规
                if (temp.equals("General")) {
                    format.applyPattern("#");
                }
                result = format.format(value);
            }
            break;
        case HSSFCell.CELL_TYPE_STRING:// String类型
            result = cell.getRichStringCellValue().toString();
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            result = "";
        default:
            result = "";
            break;
        }
        return result;
    }
}
