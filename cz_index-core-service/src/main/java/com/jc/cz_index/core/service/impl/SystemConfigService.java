/**
 * 2016/9/29 15:08:26 Jack Liu created.
 */

package com.jc.cz_index.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.ISystemConfigDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.SystemConfig;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.ISystemConfigService;

/**
 * 系统参数 Service 实现 Created by Jack Liu on 2016/09/29.
 */
@Service
public class SystemConfigService extends BaseService<SystemConfig> implements ISystemConfigService {

    @Autowired
    private ISystemConfigDao systemConfigDao;



    @Override
    public IDataProvider<SystemConfig, Long> getModelDao() {
        return this.systemConfigDao;
    }



    @Override
    public String getValue(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        QueryParams params = new QueryParams();
        params.addParameter("systemKey", key);
        List<SystemConfig> SystemConfigList = systemConfigDao.queryBaseList(params);
        if (CollectionsUtils.isNotNull(SystemConfigList)) {
            return SystemConfigList.get(0).getSystemValue();
        }
        return null;
    }



    @Override
    public void addSystemConfig(SystemConfig systemConfig) {
        // 登录用户
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        systemConfig.setCreator(loginUser.getId());
        systemConfig.setCreateDate(DateUtils.getCurrentDate());
        systemConfigDao.insertObject(systemConfig);
    }



    @Override
    public void updateSystemConfig(SystemConfig systemConfig) throws BaseException {
        SystemConfig systemConfigFormDB = systemConfigDao.getBaseObject(systemConfig.getId());
        if (null == systemConfigFormDB) {
            throw new BaseException("该系统参数不存在");
        }
        // 登录用户
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        BeanUtils.copyPropertiesIgnoreNull(systemConfig, systemConfigFormDB);
        systemConfigFormDB.setUpdator(loginUser.getId());
        systemConfigFormDB.setUpdateDate(DateUtils.getCurrentDate());
        systemConfigFormDB.setDescription(systemConfig.getDescription());
        systemConfigDao.updateObject(systemConfigFormDB);
    }

}
