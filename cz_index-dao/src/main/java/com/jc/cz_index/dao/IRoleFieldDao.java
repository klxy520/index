package com.jc.cz_index.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.jc.cz_index.model.ResidentExtendinfo;
import com.jc.cz_index.model.RoleField;
import com.jc.cz_index.model.TableField;

/**
 * residentExtendinfo Ibatis Dao 接口 Created by
 */
@Repository
public interface IRoleFieldDao extends IDataProvider<TableField, Long> {
    public List<TableField> getAllFieldInfo();



    public List<RoleField> getRoleFieldIds(Long roleId);



    public void deleteRoleLinkFieldByWhere(QueryParams params);



    public void insertRoleMenu(RoleField roleField);



    public List<TableField> getloginUserField(Long id);

}
