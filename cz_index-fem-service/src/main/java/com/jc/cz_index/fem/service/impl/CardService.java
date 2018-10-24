package com.jc.cz_index.fem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.fem.service.ICardService;
import com.jc.cz_index.dao.ICardDao;
import com.jc.cz_index.model.Card;

/**
 * card Service 实现 Created by
 */
@Service
public class CardService extends BaseService<Card> implements ICardService {
    @Autowired
    private ICardDao cardDao;



    @Override
    public IDataProvider<Card, Long> getModelDao() {
        return this.cardDao;
    }



    @Override
    public int insertOrUpdate(Card card) {
        return cardDao.insertOrUpdate(card);
    }
}
