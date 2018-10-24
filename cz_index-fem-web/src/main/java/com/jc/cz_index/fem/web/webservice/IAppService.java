package com.jc.cz_index.fem.web.webservice;

import java.io.IOException;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.Path;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**   
 * @ClassName:  IAppService   
 * @Description:TODO(前置机查询服务提供接口)   
 * @author: ZYX 
 * @date:   2017年12月29日 上午10:48:42     
 * @Copyright: 2017 ... Inc. All rights reserved. 
 * Note: this content is limited to individual research use, no leakage and other business purposes
 */
@WebService
public interface IAppService {
	
	@Path("/index")
	@WebResult(name ="response")
	public String excute(@WebParam(name="request") String request)  throws JAXBException, ParserConfigurationException, SAXException, IOException ; 
}
