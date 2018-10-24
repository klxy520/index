package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.card.CardDto;
import com.jc.cz_index.model.Card;

/**
 * card Service 接口 Created by
 */
public interface ICardService extends IBaseService<Card> {

    /**
     * 
     * 描述：执行定时任务
     * 
     * @param femId
     *            前置机ID
     * @return
     * @author yangyongchuan 2018年1月4日 下午5:24:28
     * @version 1.0
     */
    List<Card> excuteTask(Long femId);



    /**
     * 
     * 描述：所有人卡务列表查询
     * 
     * @param params
     * @param start
     * @param size
     * @return
     * @author yangjunhui 2018年1月3日 下午5:10:38
     * @version 1.0
     */
    public PagedList<CardDto> queryAllCardList(QueryParams params, int start, int size);



    /**
     * 
     * 描述：删除
     * 
     * @param cardId
     * @return
     * @author yangjunhui 2018年1月3日 下午5:10:46
     * @version 1.0
     */
    public ResponseData deleteCardById(Long cardId);



    /**
     * 
     * 描述：添加
     * 
     * @param card
     * @return
     * @author yangjunhui 2018年1月3日 下午5:10:51
     * @version 1.0
     */
    public ResponseData addCard(Card card);



    /**
     * 
     * 描述：修改
     * 
     * @param card
     * @return
     * @author yangjunhui 2018年1月3日 下午5:10:55
     * @version 1.0
     */
    public ResponseData updateCard(Card card);

}
