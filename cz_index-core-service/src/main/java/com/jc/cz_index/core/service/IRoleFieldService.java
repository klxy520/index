package com.jc.cz_index.core.service;

import java.util.List;
import com.jc.cz_index.model.RoleField;
import com.jc.cz_index.model.TableField;

/**
 * residentExtendinfo Service 接口 Created by 
 */
public interface IRoleFieldService extends IBaseService<TableField>{

    public  List<RoleField> getRoleFieldIds(Long roleId);

    public List<TableField> getAllFieldInfo();

    public void setRoleField(Long roleId, String fieldStr);

    public List<TableField> getloginUserField(Long orgId);
 
}

