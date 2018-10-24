package com.jc.cz_index.fem.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IPersonDao;
import com.jc.cz_index.fem.service.IPersonService;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.ResponseEntity;

/**
 * person Service 实现 Created by
 */
/**
 * 
 * 描述：
 * 
 * @author fengshengliang 2017年12月28日 上午10:19:12
 * @version 1.0
 */
@Service("personService")
public class PersonService extends BaseService<Person> implements IPersonService {
    private static final Logger LOG = Logger.getLogger(PersonService.class);
    @Autowired
    private IPersonDao          personDao;

    @Override
    public IDataProvider<Person, Long> getModelDao() {
        return this.personDao;
    }

    @Override
    public int saveOrUpdate(Person person) {
        return personDao.saveOrUpdate(person);
    }
    
    
}
