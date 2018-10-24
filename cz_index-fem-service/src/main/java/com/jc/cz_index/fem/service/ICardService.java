package com.jc.cz_index.fem.service;

import com.jc.cz_index.model.Card;

/**
 * card Service 接口 Created by
 */
public interface ICardService extends IBaseService<Card> {
    /**
     * 
     * 描述：添加或修改对象
     * 
     * @param card
     * @return
     * @author yangyongchuan 2018年1月4日 下午6:02:59
     * @version 1.0
     */
    int insertOrUpdate(Card card);
}
