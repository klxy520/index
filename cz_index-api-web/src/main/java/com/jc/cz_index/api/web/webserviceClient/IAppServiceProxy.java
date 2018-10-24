package com.jc.cz_index.api.web.webserviceClient;

import java.rmi.RemoteException;

import com.jc.cz_index.api.web.webserviceClient.impl.AppServiceClientLocator;

public class IAppServiceProxy implements IAppServiceClient {
    private String            _endpoint         = null;
    private IAppServiceClient iAppServiceClient = null;



    public IAppServiceProxy() {
        _initIAppServiceProxy();
    }



    public IAppServiceProxy(String endpoint) {
        _endpoint = endpoint;
        _initIAppServiceProxy();
    }



    private void _initIAppServiceProxy() {
        try {
            iAppServiceClient = (new AppServiceClientLocator()).getAppServicePort();
            if (iAppServiceClient != null) {
                if (_endpoint != null)
                    ((javax.xml.rpc.Stub) iAppServiceClient)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
                else
                    _endpoint = (String) ((javax.xml.rpc.Stub) iAppServiceClient)._getProperty("javax.xml.rpc.service.endpoint.address");
            }

        } catch (javax.xml.rpc.ServiceException serviceException) {
        }
    }



    public String getEndpoint() {
        return _endpoint;
    }



    public void setEndpoint(String endpoint) {
        _endpoint = endpoint;
        if (iAppServiceClient != null)
            ((javax.xml.rpc.Stub) iAppServiceClient)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

    }



    public IAppServiceClient getIAppService() {
        if (iAppServiceClient == null)
            _initIAppServiceProxy();
        return iAppServiceClient;
    }



    public String excute(String request) throws JAXBException, IOException, ParserConfigurationException, SAXException, RemoteException {
        if (iAppServiceClient == null)
            _initIAppServiceProxy();
        return iAppServiceClient.excute(request);
    }

}