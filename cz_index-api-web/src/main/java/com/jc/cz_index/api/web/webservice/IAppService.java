package com.jc.cz_index.api.web.webservice;

import java.io.IOException;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.Path;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


/**   
 * @ClassName:  AppService   
 * @Description:TODO(定义入口接口方法)   
 * @author: ZYX 
 * @date:   2017年12月27日 下午4:09:38     
 * @Copyright: 2017 ... Inc. All rights reserved. 
 * Note: 程序webservice入口接口
 */
@WebService
public interface IAppService {
	
	/**
	 * @Title: excute   
	 * @Description: TODO(接口方法)   
	 * @param: @param request
	 * @param: @return
	 * @param: @throws JAXBException
	 * @param: @throws ParserConfigurationException
	 * @param: @throws SAXException
	 * @param: @throws IOException      
	 * @return: String      
	 * @throws
	 */
	@Path("/index")
	@WebResult(name ="response")
	public String excute(@WebParam(name="request") String request)  throws JAXBException, ParserConfigurationException, SAXException, IOException ; 
}
