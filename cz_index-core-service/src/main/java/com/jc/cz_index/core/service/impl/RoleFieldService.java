package com.jc.cz_index.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.service.IRoleFieldService;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IRoleFieldDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.RoleField;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.model.TableField;

/**
 * RoleFieldService Service 实现 Created by
 */
@Service
public class RoleFieldService extends BaseService<TableField> implements IRoleFieldService {
    @Autowired
    private IRoleFieldDao roleFieldDao;



    @Override
    public IDataProvider<TableField, Long> getModelDao() {
        return this.roleFieldDao;
    }



    @Override
    public List<RoleField> getRoleFieldIds(Long roleId) {
        return roleFieldDao.getRoleFieldIds(roleId);
    }



    @Override
    public List<TableField> getAllFieldInfo() {

        return roleFieldDao.getAllFieldInfo();
    }



    @Override
    public void setRoleField(Long roleId, String fieldStr) {
        // 删除以前授权
        QueryParams params = new QueryParams();
        params.addParameter("roleId", roleId);
        roleFieldDao.deleteRoleLinkFieldByWhere(params);
        // 设置目前授权角色
        RoleField roleField = null;
        // 登录用户
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        Date currentDate = DateUtils.getCurrentDate();
        if (StringUtils.isNotEmpty(fieldStr)) {
            List<Long> roleIdList = StringUtils.getLongList(fieldStr, ",");
            for (Long fieldId : roleIdList) {
                roleField = new RoleField();
                roleField.setRoleId(roleId);
                roleField.setFieldId(fieldId);
                roleField.setUpdator(loginUser.getId());
                roleField.setUpdateDate(currentDate);
                roleFieldDao.insertRoleMenu(roleField);
            }
        }
    }



    // public String getFieldSql() {
    // String fieldSql="";
    // List<TableField> loginUserField= getloginUserField();
    // for (TableField tableField : loginUserField) {
    // System.out.println(tableField.getId());
    // if(null!=tableField.getId()){
    //// fieldSql+=",'-1' as "+tableField.getFieldName();
    //// }else {
    // fieldSql+=", re."+tableField.getFieldName()+" as
    // "+tableField.getFieldName();
    // }
    // }
    // System.err.println(fieldSql+"----------------fieldSql");
    // return fieldSql;
    // }

    @Override
    public List<TableField> getloginUserField(Long orgId) {
        List<TableField> loginUserField = roleFieldDao.getloginUserField(orgId);
        return loginUserField;

    }
}
