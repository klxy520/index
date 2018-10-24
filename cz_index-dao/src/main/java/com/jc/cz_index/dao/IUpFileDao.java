/**
 * 2016/9/28 14:22:29 Jack Liu created.
 */

package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.cz_index.model.UpFile;

/**
 * Ibatis Dao 接口 Created by Jack Liu on 2016/09/28.
 */
@Repository
public interface IUpFileDao extends IDataProvider<UpFile, Long> {

    public List<UpFile> queryByIds(QueryParams wheres);

}
