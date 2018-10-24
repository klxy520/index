package com.jc.cz_index.core.web.controller.app;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.cz_index.common.bean.RequestData;
import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.JsonUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.SysConfig;
import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.ResidentsInfo;
import com.jc.cz_index.core.component.InterfaceVerifyUtils;
import com.jc.cz_index.core.service.IResidentsInfoService;

/**
 * 
 * 描述：居民身份实名认证
 * 
 * @author yangyongchuan 2017年9月20日 下午3:49:16
 * @version 1.0
 */
@Controller
@RequestMapping("/resident")
public class ResidentAuthController extends BaseController {
    private static Log            log = LogFactory.getLog(ResidentAuthController.class);

    @Autowired
    private IResidentsInfoService residentsInfoService;



    /**
     * 
     * 描述：居民身份实名认证
     * 
     * @param request
     * @return
     * @author yangyongchuan 2016年11月8日 下午3:31:54
     * @version 1.0
     */
    @RequestMapping("/auth")
    @ResponseBody
    public Map<String, String> auth(HttpServletRequest request, @RequestBody RequestData requestData) {
        ResponseData responseData = new ResponseData();
        // 卡管平台appid
        final String CZ_CMS_APPID = SysConfig.getValue("cz_cms_appid");
        Map<String, Object> paramMap = InterfaceVerifyUtils.verify(requestData, CZ_CMS_APPID);
        if (!CollectionsUtils.isNull(paramMap)) {
            try {
                String realName = (String) paramMap.get("name");
                String idNumber = (String) paramMap.get("identityNumber");
                if (StringUtils.isEmpty(realName)) {
                    throw new BaseException("居民姓名不能为空");
                }
                if (StringUtils.isEmpty(idNumber)) {
                    throw new BaseException("身份证号不能为空");
                }
                QueryParams params = new QueryParams();
                params.addParameter("identityNumber", idNumber);
                params.addParameter("delFalg", 0);
                ResidentsInfo residentBaseinfo = residentsInfoService.queryOneBase(params);
                if (residentBaseinfo == null) {
                    residentBaseinfo = new ResidentsInfo();
                    residentBaseinfo.setIdentityNumber(idNumber);
                    ;
                    residentBaseinfo.setName(realName);
                    residentsInfoService.addResidentsInfoByApp(residentBaseinfo);
                } else if (!realName.equals(residentBaseinfo.getName())) {
                    log.info("居民身份认证未通过，居民姓名：" + realName + ",身份证号：" + idNumber);
                    throw new BaseException("居民身份实名认证未通过");
                }
                if (responseData.getStatus() == 0) {
                    List<String> paramNameList = StringUtils.getStringList("name,identityNumber,bankCardNumber", ",");
                    responseData = ResponseData.getSuccessResponse(JsonUtils.toJSONStringByParamNameList(residentBaseinfo, paramNameList));
                } else {
                    responseData = ResponseData.getErrorResponse(responseData.getMessage());
                }
            } catch (Exception e) {
                log.error("居民身份实名认证失败，异常：" + e.getMessage());
                responseData = ResponseData.getErrorResponse(e.getMessage());
            }
        } else {
            responseData = ResponseData.getErrorResponse("验签未通过！");
        }
        log.debug("返回数据：" + responseData);
        return InterfaceVerifyUtils.encodeParamToResponse(CZ_CMS_APPID, requestData.getAppid(), responseData.toMap());

    }



    /**
     * 
     * 描述：解密返回数据
     * 
     * @param request
     * @param requestData
     * @return
     * @author yangyongchuan 2017年9月28日 下午7:15:00
     * @version 1.0
     */
    @RequestMapping("/decryptResponseData")
    @ResponseBody
    public Map<String, Object> decryptResponseData(HttpServletRequest request, @RequestBody RequestData requestData) {
        // 卡管平台appid
        final String CZ_CMS_APPID = SysConfig.getValue("cz_cms_appid");
        String app_appid = requestData.getAppid();
        requestData.setAppid(CZ_CMS_APPID);
        Map<String, Object> decryptResponseData = InterfaceVerifyUtils.verify(requestData, app_appid);
        System.err.println();
        log.debug("返回数据：" + decryptResponseData.toString());
        return decryptResponseData;

    }
}
