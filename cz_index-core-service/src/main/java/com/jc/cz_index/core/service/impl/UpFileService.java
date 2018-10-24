/**
 * 2016/9/28 14:38:58 Jack Liu created.
 */

package com.jc.cz_index.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IUpFileDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.UpFile;
import com.jc.cz_index.core.service.IUpFileService;

/**
 * Service 实现 Created by Jack Liu on 2016/09/28.
 */
@Service
public class UpFileService extends BaseService<UpFile> implements IUpFileService {

    @Autowired
    private IUpFileDao upFileDao;



    @Override
    public IDataProvider<UpFile, Long> getModelDao() {
        return this.upFileDao;
    }



    @Override
    public List<UpFile> queryByIds(List<Long> ids) {
        QueryParams qp = new QueryParams();
        qp.put("ids", ids);
        return upFileDao.queryByIds(qp);
    }

}
