package com.jc.cz_index.api.web.webservice;

import com.jc.cz_index.model.ResponseEntity;

public interface IMpiServiceForward2Core {
    /**
     * 
     * 描述：将前置机主索引接口服务器转发到核心主索引接口服务
     * 
     * @param request
     * @return
     * @author yangyongchuan 2018年1月23日 上午11:36:43
     * @version 1.0
     * @throws Exception
     */
    ResponseEntity requestForward2Core(String request);
}
