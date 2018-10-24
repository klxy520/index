package com.jc.cz_index.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IDictionaryDao;
import com.jc.cz_index.model.Dictionary;
import com.jc.cz_index.core.service.IDictionaryService;

/**
 * dictionary Service 实现 Created by
 */
@Service
public class DictionaryServiceImpl extends BaseService<Dictionary> implements IDictionaryService {
    @Autowired
    private IDictionaryDao dictionaryDao;



    @Override
    public IDataProvider<Dictionary, Long> getModelDao() {
        // TODO Auto-generated method stub
        return this.dictionaryDao;
    }
}
