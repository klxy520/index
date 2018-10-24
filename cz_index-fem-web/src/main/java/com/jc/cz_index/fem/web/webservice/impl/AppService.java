package com.jc.cz_index.fem.web.webservice.impl;

import java.io.IOException;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import com.jc.cz_index.fem.web.webservice.IAppService;

/**   
 * @ClassName:  AppService   
 * @Description:TODO(前置机查询服务实现类)   
 * @author: ZYX 
 * @date:   2017年12月29日 上午10:49:36     
 * @Copyright: 2017 ... Inc. All rights reserved. 
 * Note: this content is limited to individual research use, no leakage and other business purposes
 */
@WebService(endpointInterface = "com.jc.cz_index.fem.web.webservice.IAppService")
@SOAPBinding(style = Style.RPC)
public class AppService implements IAppService {

	@Override
	public String excute(String request) throws JAXBException, ParserConfigurationException, SAXException, IOException {
//		  // 根据参数获取基本信息并验证合法性
//        RequestEntity re = WSUtils.xmlConvertToBean(request, RequestEntity.class);
//        // 获取service并执行
//        ResponseEntity rs = SpringConfigTool.getBean(SysConfig.getRouter(re.getAction()), BaseService.class).excute(re);
//        // 返回处理结果信息
//        return WSUtils.BeanConvertToXML(rs, ResponseEntity.class);
		return "服务测试成功";
	}

}
