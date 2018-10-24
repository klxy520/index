package com.jc.cz_index.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IUserLoginFailDao;
import com.jc.cz_index.model.UserLoginFail;
import com.jc.cz_index.core.service.IUserLoginFailService;

/**
 * userLoginFail Service 实现 Created by
 */
@Service
public class UserLoginFailServiceImpl extends BaseService<UserLoginFail> implements IUserLoginFailService {
    @Autowired
    private IUserLoginFailDao userLoginFailDao;



    @Override
    public IDataProvider<UserLoginFail, Long> getModelDao() {
        // TODO Auto-generated method stub
        return this.userLoginFailDao;
    }
}
