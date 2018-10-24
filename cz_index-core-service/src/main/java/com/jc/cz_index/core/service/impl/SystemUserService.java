/**
 * 2016/9/26 21:27:46 Jack Liu created.
 */

package com.jc.cz_index.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.common.exception.BaseException;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.EncodeAndDecodeUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.ISystemUserAuthDao;
import com.jc.cz_index.dao.ISystemUserDao;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.model.SystemUserAuth;
import com.jc.cz_index.core.service.ISystemUserService;

/**
 * 系统用户信息 Service 实现 Created by Jack Liu on 2016/09/26.
 */
@Service
public class SystemUserService extends BaseService<SystemUser> implements ISystemUserService {

    @Autowired
    private ISystemUserDao     systemUserDao;

    @Autowired
    private ISystemUserAuthDao systemUserAuthDao;



    @Override
    public IDataProvider<SystemUser, Long> getModelDao() {
        return this.systemUserDao;
    }



    @Override
    public void addUser(SystemUser systemUser) {
        // 创建用户登录凭证
        // 登录用户
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        Date currentDate = DateUtils.getCurrentDate();
        SystemUserAuth systemUserAuth = systemUser.getSystemUserAuth();
        // 默认密码：111111
        systemUserAuth.setPassword(EncodeAndDecodeUtils.MD5Encode("111111"));
        systemUserAuth.setStatus(ContentUtils.DB_FIELD_STATUS_ENABLE);
        systemUserAuth.setCreator(loginUser.getId());
        systemUserAuth.setCreateDate(currentDate);
        systemUserAuthDao.insertObject(systemUserAuth);
        // 创建用户信息
        systemUser.setAuthId(systemUserAuth.getId());
        systemUser.setCreator(loginUser.getId());
        systemUser.setCreateDate(currentDate);
        systemUserDao.insertObject(systemUser);
    }



    @Override
    public void updateUser(SystemUser systemUser) throws BaseException {
        SystemUser systemUserFormDB = systemUserDao.getBaseObject(systemUser.getId());
        if (null == systemUserFormDB) {
            throw new BaseException("用户不存在");
        }
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        Date currentDate = DateUtils.getCurrentDate();
        BeanUtils.copyPropertiesIgnoreNull(systemUser, systemUserFormDB);
        systemUserFormDB.setUpdator(loginUser.getId());
        systemUserFormDB.setUpdateDate(currentDate);
        systemUserDao.updateObject(systemUserFormDB);
        // 修改密码
        String password = systemUser.getSystemUserAuth().getPassword();
        if (StringUtils.isNotEmpty(password)) {
            SystemUserAuth systemUserAuth = systemUserAuthDao.getBaseObject(systemUser.getSystemUserAuth().getId());
            systemUserAuth.setPassword(EncodeAndDecodeUtils.MD5Encode(password));
            systemUserAuth.setUpdator(loginUser.getId());
            systemUserAuth.setUpdateDate(currentDate);
            systemUserAuth.setUpdatePassDate(currentDate);
            systemUserAuthDao.updateObject(systemUserAuth);
        }

    }

}
