package com.jc.cz_index.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.cz_index.core.service.ICardSyncLogService;
import com.jc.cz_index.dao.ICardSyncLogDao;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IFrontEndMachineDao;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.syncLog.CardSyncLogDto;
import com.jc.cz_index.model.CardSyncLog;
import com.jc.cz_index.model.FrontEndMachine;

/**
 * cardSyncLog Service 实现 Created by
 */
@Service
public class CardSyncLogService extends BaseService<CardSyncLog> implements ICardSyncLogService {
    @Autowired
    private ICardSyncLogDao     cardSyncLogDao;
    @Autowired
    private IFrontEndMachineDao frontEndMachineDao;



    @Override
    public IDataProvider<CardSyncLog, Long> getModelDao() {
        return this.cardSyncLogDao;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCardSyncLog(Long cardId, String details, Long userid) {
        List<FrontEndMachine> femList = frontEndMachineDao.queryBaseList(null);  
        List<CardSyncLog> list= new ArrayList<CardSyncLog>();
        for (FrontEndMachine frontEndMachine : femList) {
        	CardSyncLog cardlog = new CardSyncLog();
            cardlog.setCardId(cardId);
            cardlog.setSyncStatus("0");
            cardlog.setCreator(userid);
            cardlog.setCreateDate(new Date());
            cardlog.setRemark(details);
            cardlog.setFrontId(frontEndMachine.getId());
            cardlog.setUpdateDate(new Date());
            cardlog.setUpdator(userid);;
            list.add(cardlog);
        }
        cardSyncLogDao.insertOrUpdatetList(list);
    }



    /**
     * 
     * 描述：所有人卡同步日志列表查询
     * 
     * @param params
     * @param start
     * @param size
     * @return
     * @author yangjunhui 2018年1月3日 下午5:10:38
     * @version 1.0
     */
    @Override
    public PagedList<CardSyncLogDto> queryCardSyncLogList(QueryParams params, int start, int size) {
        if (null == params) {
            params = new QueryParams();
        }
        if (start < 0)
            throw new IllegalArgumentException("pageIndex参数不能小于0.");
        if (size < 0)
            throw new IllegalArgumentException("pageSize参数不能小于0.");
        int total = cardSyncLogDao.querySyncLogCount(params);
        params.setStart(start);
        params.setSize(size);
        List<CardSyncLogDto> list = cardSyncLogDao.queryCardSyncLogList(params);
        PagedList<CardSyncLogDto> result = new PagedList<CardSyncLogDto>();
        result.getList().addAll(list);
        result.setPageIndex(start / size + 1);
        result.setPageSize(size);
        result.setTotalSize(total);
        result.setPageCount(total / size + 1);
        return result;
    }



    @Override
    public int inserOrUpdatetList(List<CardSyncLog> list) {
        return cardSyncLogDao.insertOrUpdatetList(list);
    }
}
