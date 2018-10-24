/**
 * 2016/11/1 14:49:15 Jack Liu created.
 */

package com.jc.cz_index.core.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.jc.cz_index.model.OperationLog;

/**
 * 操作日志 Service 接口 Created by Jack Liu on 2016/11/01.
 */
public interface IOperationLogService extends IBaseService<OperationLog> {
    /**
     * 
     * 描述：添加日志
     * 
     * @param session
     * @param formName
     * @param formId
     * @param type
     * @param detail
     * @param remark
     * @author fengshengliang 2016年11月2日 上午10:35:01
     * @version 1.0
     */
    void addOperationLog(String formName, String recordId, String type, String detail, String remark);



    public void exportFile(HttpServletResponse response, List<String> headers, String time1, String time2, String excelName, int length);
}
