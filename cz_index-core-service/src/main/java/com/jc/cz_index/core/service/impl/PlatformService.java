package com.jc.cz_index.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.core.service.IPlatformService;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.dao.IPlatformDao;
import com.jc.cz_index.model.Platform;

/**
 * platform Service 实现 Created by 
 */
@Service
public class PlatformService extends BaseService<Platform> implements IPlatformService
{
	@Autowired
	private IPlatformDao platformDao;

	@Override
	public IDataProvider<Platform, Long> getModelDao() {
		// TODO Auto-generated method stub
		return this.platformDao;
	}

    @Override
    public void updatePlatform(Platform platform) {
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        Date currentDate = DateUtils.getCurrentDate();
        long platformId = platform.getId(); // 获取当前平台的ID
        // 从数据库中获取该平台对象
        Platform platformDB = platformDao.getBaseObject(platformId);
        if (platformDB != null) {
            Long creatorDB = platformDB.getCreator();
            Date createDateDB = platformDB.getCreateDate();
            platform.setCreator(creatorDB); // 将创建者和创建时间设置到编辑的对象中
            platform.setCreateDate(createDateDB);
        }
        platform.setUpdator(loginUser.getId()); // 修改
        platform.setUpdateDate(currentDate); // 修改时间
        platformDao.updateObject(platform);
        
    }

    @Override
    public void setPlatformEnable(Long platformId, Integer dbFieldStatusEnable) {
        QueryParams params = new QueryParams();
        params.addParameter("id", platformId);
        params.addParameter("status", dbFieldStatusEnable);
        platformDao.updateObjectByFields(params);
        
    }

    @Override
    public void setPlatformDisable(Long platformId, Integer dbFieldStatusDisable) {
        QueryParams params = new QueryParams();
        params.addParameter("id", platformId);
        params.addParameter("status", dbFieldStatusDisable);
        platformDao.updateObjectByFields(params);
        
    }
}

