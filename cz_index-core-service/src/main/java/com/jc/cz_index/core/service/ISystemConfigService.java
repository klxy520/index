/**
 * 2016/9/29 15:08:26 Jack Liu created.
 */

package com.jc.cz_index.core.service;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.model.SystemConfig;

/**
 * 系统参数 Service 接口 Created by Jack Liu on 2016/09/29.
 */
public interface ISystemConfigService extends IBaseService<SystemConfig> {
    /**
     * 
     * 描述：获取系统参数值
     * 
     * @param key
     * @return
     * @author yangyongchuan 2016年9月29日 下午3:19:37
     * @version 1.0
     */
    public String getValue(String key);



    /**
     * 
     * 描述：添加系统参数
     * 
     * @param systemConfig
     * @author yangyongchuan 2016年10月19日 上午9:28:42
     * @version 1.0
     */
    public void addSystemConfig(SystemConfig systemConfig);



    /**
     * 
     * 描述：编辑系统参数
     * 
     * @param systemConfig
     * @author yangyongchuan 2016年10月19日 上午9:29:00
     * @version 1.0
     */
    public void updateSystemConfig(SystemConfig systemConfig) throws BaseException;
}
