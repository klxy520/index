package com.jc.cz_index.api.web.webservice.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jc.cz_index.api.service.impl.BaseService;
import com.jc.cz_index.api.web.webservice.IMpiServiceForward2Core;
import com.jc.cz_index.api.web.webserviceClient.IAppServiceProxy;
import com.jc.cz_index.common.utils.SysConfig;
import com.jc.cz_index.common.utils.WSUtils;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.model.RequestEntity;
import com.jc.cz_index.model.ResponseEntity;

@Component
public class MpiServiceForward2Core extends BaseService<Object> implements IMpiServiceForward2Core {
    private static final Logger LOG = Logger.getLogger(MpiServiceForward2Core.class);



    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public IDataProvider getModelDao() {
        return null;
    }



    @Override
    public ResponseEntity requestForward2Core(String request) {
        String response = null;
        try {
            String coreMpiApiUrl = SysConfig.getValue("core_mpi_api_url");
            IAppServiceProxy proxy = new IAppServiceProxy(coreMpiApiUrl);
            response = proxy.excute(request);
            ResponseEntity resp = WSUtils.xmlConvertToBean(response, ResponseEntity.class);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public ResponseEntity excute(RequestEntity re) {
        try {
            String request = WSUtils.BeanConvertToXML(re, RequestEntity.class);
            ResponseEntity resp = requestForward2Core(request);
            return resp;
        } catch (Exception e) {
            LOG.error("请求转发到核心主索引接口服务失败：" + e.getMessage());
            e.printStackTrace();
        }
        return super.excute(re);
    }

}
