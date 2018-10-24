/**
 * IAppService.java
 *
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006
 * (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jc.cz_index.api.web.webserviceClient;

import java.rmi.RemoteException;

public interface IAppServiceClient extends java.rmi.Remote {
    public java.lang.String excute(java.lang.String request)
            throws RemoteException, JAXBException, IOException, ParserConfigurationException, SAXException;
}
