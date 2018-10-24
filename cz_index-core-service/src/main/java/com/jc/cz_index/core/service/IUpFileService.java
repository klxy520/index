/**
 * 2016/9/28 14:22:29 Jack Liu created.
 */

package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.model.UpFile;

/**
 * Service 接口 Created by Jack Liu on 2016/09/28.
 */
public interface IUpFileService extends IBaseService<UpFile> {

    public List<UpFile> queryByIds(List<Long> ids);

}
