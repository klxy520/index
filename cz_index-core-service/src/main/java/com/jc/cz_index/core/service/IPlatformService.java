package com.jc.cz_index.core.service;

import com.jc.cz_index.model.Platform;

/**
 * platform Service 接口 Created by 
 */
public interface IPlatformService extends IBaseService<Platform>
{
    /**
     * 
     * 描述：编辑平台
     * @param platform
     * @author wangdeyou 2017年9月19日 下午2:40:30 
     * @version 1.0
     */
    void updatePlatform(Platform platform);
    
    /**
     * 
     * 描述：启用平台
     * @param platformId
     * @param dbFieldStatusEnable
     * @author wangdeyou 2017年9月19日 下午2:45:56 
     * @version 1.0
     */
    void setPlatformEnable(Long platformId, Integer dbFieldStatusEnable);
    
    /**
     * 
     * 描述：禁用平台
     * @param platformId
     * @param dbFieldStatusDisable
     * @author wangdeyou 2017年9月19日 下午2:48:58 
     * @version 1.0
     */
    void setPlatformDisable(Long platformId, Integer dbFieldStatusDisable);
}

