package com.jc.cz_index.api.web.webservice;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;


/**   
 * @ClassName:  AppSoapHandler   
 * @Description:TODO(webservice验证)   
 * @author: ZYX 
 * @date:   2017年12月27日 下午3:34:02     
 * @Copyright: 2017 ... Inc. All rights reserved. 
 * Note: 主要是合法性验证
 */
public class AppSoapHandler implements SOAPHandler<SOAPMessageContext> {

	/**
	 * <p>Title: handleMessage</p>   
	 * <p>Description: 逻辑处理对象 </p>   
	 * @param context
	 * @return   
	 * @see javax.xml.ws.handler.Handler#handleMessage(javax.xml.ws.handler.MessageContext)
	 */
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
//		Boolean out = (Boolean)context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);
//        if(!out){
//            SOAPMessage message = context.getMessage();
//            try {
//                SOAPBody body = message.getSOAPPart().getEnvelope().getBody();
//                if(body != null){
//                    Node node = body.getElementsByTagName("request").item(0);
//                    System.out.println("节点-"+node.getNodeName()+"，action-"+node.getAttributes().getNamedItem("action").getNodeValue());
//                    System.out.println(convertNodeToString(node));
//                }else{
//                    return false;
//                }
//            } catch (SOAPException e) {
//                e.printStackTrace();
//            }
//            
//        }
        return true;
	}

	/**
	 * <p>Title: handleFault</p>   
	 * <p>Description:失败处理</p>   
	 * @param context
	 * @return   
	 * @see javax.xml.ws.handler.Handler#handleFault(javax.xml.ws.handler.MessageContext)
	 */
	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return false;
	}
	/**
	 * <p>Title: close</p>   
	 * <p>Description: 关闭处理</p>   
	 * @param context   
	 * @see javax.xml.ws.handler.Handler#close(javax.xml.ws.handler.MessageContext)
	 */
	@Override
	public void close(MessageContext context) {
		
	}

	/**
	 * <p>Title: getHeaders</p>   
	 * <p>Description:头部处理</p>   
	 * @return   
	 * @see javax.xml.ws.handler.soap.SOAPHandler#getHeaders()
	 */
	@Override
	public Set<QName> getHeaders() {
		return null;
	}

}
