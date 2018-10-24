package com.jc.cz_index.core.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IInfoManagementLogDao;
import com.jc.cz_index.model.InfoManagementLog;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.IinfoManagementLogService;

/**
 * 
 * 描述：
 * 
 * @author yangjunhui 2017年8月30日 上午9:36:51
 * @version 1.0
 */
@Service
public class InfoManagementLogService extends BaseService<InfoManagementLog>implements IinfoManagementLogService {
    @Autowired
    private IInfoManagementLogDao InfoLogDao;



    @Override
    public IDataProvider<InfoManagementLog, Long> getModelDao() {
        return this.InfoLogDao;
    }



    @Override
    public void addInfoManagementLog(String formName, Long recordId, Integer type, String detail, String remark) {
        InfoManagementLog log = new InfoManagementLog();
        SystemUser user = (SystemUser) WebUtils.getLoginUser();
        log.setCreateDate(new Date());
        log.setCreator(user.getId());
        log.setCreatorName(user.getName());
        log.setDetails(detail);
        log.setFormName(formName);
        log.setRecordId(recordId);
        log.setRemark(remark);
        log.setType(type);
        InfoLogDao.insertObject(log);
    }



    @Override
    public InfoManagementLog getLogById(Long id) {
        return InfoLogDao.getBaseObject(id);
    }

}
