package com.jc.cz_index.api.web.webservice.impl;

import java.io.IOException;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.jc.cz_index.api.service.impl.BaseService;
import com.jc.cz_index.api.web.webservice.IAppService;
import com.jc.cz_index.common.utils.SpringConfigTool;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.SysConfig;
import com.jc.cz_index.common.utils.WSUtils;
import com.jc.cz_index.model.RequestEntity;
import com.jc.cz_index.model.ResponseEntity;
import com.jc.encrypt.MD5;

@WebService(endpointInterface = "com.jc.cz_index.api.web.webservice.IAppService")
@SOAPBinding(style = Style.RPC)
public class AppService implements IAppService {

    // <![CDATA[<request action='11111'
    // client='192.168.1.1'><order><id>1</id><code>test-code</code></order></request>]]>
    /**
     * <p>
     * Title: excute
     * </p>
     * <p>
     * Description:webservice统一入口
     * </p>
     * 
     * @param request
     * @return
     * @throws JAXBException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @see com.jc.cz_index.api.web.webservice.IAppService#excute(java.lang.String)
     */
    @Override
    public String excute(String request) throws JAXBException, ParserConfigurationException, SAXException, IOException {
        // 返回数据
        ResponseEntity rs = null;
        // 根据参数获取基本信息并验证合法性
        RequestEntity re = WSUtils.xmlConvertToBean(request, RequestEntity.class);

        // 验签
        boolean result = checkSign(request, re.getSign());
        // 通过验签
        if (result) {
            // 在执行相关更新操作时候,获取updateData和currentData
            String updateData = StringUtils.getInsideString(request, "<updateData>", "</updateData>");
            if (!StringUtils.isEmpty(updateData)) {
                re.getPerson().setUpdateData(updateData);
            }
            String currentData = StringUtils.getInsideString(request, "<currentData>", "</currentData>");
            if (!StringUtils.isEmpty(currentData)) {
                re.getPerson().setCurrentData(currentData);
            }

            // 获取service并执行
            BaseService<?> baseService = SpringConfigTool.getBean(SysConfig.getRouter(re.getAction()), BaseService.class);
            if (null != baseService && baseService instanceof MpiServiceForward2Core) {
                rs = ((MpiServiceForward2Core) baseService).requestForward2Core(request);
            } else {
                rs = baseService.excute(re);
            }
        } else {
            // 验签失败
            rs = ResponseEntity.getSignErrorResponse("验签失败");
        }
        // 返回处理结果信息
        return handleResponseData(rs);
    }



    /**
     * 
     * 描述：获取请求、返回xml数据子节点
     * 
     * @param xml
     * @return
     * @author yangyongchuan 2018年1月12日 下午5:49:49
     * @version 1.0
     */
    private String getAllChildXml(String xml) {
        String allChildXml = xml.substring(xml.indexOf(">") + 1, xml.lastIndexOf("</"));
        return allChildXml.trim();
    }



    /**
     * 
     * 描述：获取签名
     * 
     * @param xmlData
     * @return
     * @author yangyongchuan 2018年1月12日 下午5:59:11
     * @version 1.0
     */
    private String getSign(String xmlData) {
        String md5Salt = SysConfig.getValue("hc_mpi_api_md5_salt");
        return MD5.encode(xmlData + md5Salt);
    }



    /**
     * 
     * 描述：验签
     * 
     * @param request
     * @param sign
     * @return
     * @author yangyongchuan 2018年1月12日 下午5:58:47
     * @version 1.0
     */
    private boolean checkSign(String request, String sign) {
        if (StringUtils.isEmpty(sign)) {
            return false;
        }
        // 获取子节点xml字符串
        String allCHildxml = getAllChildXml(request);
        // 加密
        String currentSign = getSign(allCHildxml);
        if (sign.equals(currentSign)) {
            return true;
        }
        // 验签mock
        String signMock = SysConfig.getValue("hc_mpi_api_sign_mock");
        String signMockValue = SysConfig.getValue("hc_mpi_api_sign_mock_value");
        if ("true".equals(signMock)) {
            if (sign.equals(signMockValue)) {
                return true;
            }
        }
        return false;
    }



    /**
     * 
     * 描述：处理返回数据：加签、对象转字符串
     * 
     * @param rs
     * @return
     * @throws JAXBException
     * @author yangyongchuan 2018年1月12日 下午6:12:55
     * @version 1.0
     */
    private String handleResponseData(ResponseEntity rs) throws JAXBException {
        String responseXml = WSUtils.BeanConvertToXML(rs, ResponseEntity.class);
        // 获取子节点xml字符串
        String allCHildxml = getAllChildXml(responseXml);
        String sign = getSign(allCHildxml);
        rs.setSign(sign);
        return WSUtils.BeanConvertToXML(rs, ResponseEntity.class);
    }

}