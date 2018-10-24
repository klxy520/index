package com.jc.cz_index.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.cz_index.api.service.ICardService;
import com.jc.cz_index.common.exception.ParamsException;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.common.utils.ContentUtils;
import com.jc.cz_index.common.utils.DateUtils;
import com.jc.cz_index.common.utils.SpringConfigTool;
import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.dao.ICardDao;
import com.jc.cz_index.dao.ICardSyncLogDao;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.IFrontEndMachineDao;
import com.jc.cz_index.dao.IPersonDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Card;
import com.jc.cz_index.model.CardSyncLog;
import com.jc.cz_index.model.FrontEndMachine;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.RequestEntity;
import com.jc.cz_index.model.ResponseEntity;

/**
 * card Service 实现 Created by
 */
@Service
public class CardService extends BaseService<Card> implements ICardService {
    private static final Logger LOG = Logger.getLogger(PersonService.class);
    @Autowired
    private ICardDao            cardDao;
    @Autowired
    private ICardSyncLogDao     cardSyncLogDao;
    @Autowired
    private IPersonDao          personDao;
    @Autowired
    private IFrontEndMachineDao frontEndMachineDao;



    @Override
    public IDataProvider<Card, Long> getModelDao() {
        return this.cardDao;
    }



    public ResponseEntity excute(RequestEntity re) {
        CardService thisCardService = SpringConfigTool.getBean("cardService", CardService.class);
        ResponseEntity rep = new ResponseEntity();
        // 访问目标
        String action = re.getAction();
        Person person = re.getPerson();
        try {
            switch (action) {
            case "registerCards":
                // 注册卡
                rep = thisCardService.registerCards(person);
                break;
            case "writeOffCard":
                // 注销卡 cardStatus=2
                rep = thisCardService.updateCardStatus(person, ContentUtils.CARD_STATUS_CANCEL);
                break;
            case "lockCard":
                // 卡挂失 cardStatus=1
                rep = thisCardService.updateCardStatus(person, ContentUtils.CARD_STATUS_LOSS);
                break;
            case "unlockCard":
                // 卡解挂 cardStatus=0
                rep = thisCardService.updateCardStatus(person, ContentUtils.CARD_STATUS_NORMAL);
                break;
            case "getCards":
                // 根据MPIID获取所有卡
                rep = thisCardService.getCards(person);
                break;
            case "activationCard":
                // 激活卡 cardStatus=4,用于激活卡
                rep = thisCardService.updateCardStatus(person, ContentUtils.CARD_STATUS_ACTIVATION);
                break;
            default:
                LOG.error("访问地址不存在：" + action);
                rep = ResponseEntity.getNotFoundErrorResponse();
                break;
            }
        } catch (ParamsException pe){
            LOG.error("请求action:" + action + " 异常：" + pe.getMessage());
            rep = ResponseEntity.getParamsErrorResponse(pe.getMessage());
        } catch (Exception e) {
            LOG.error("请求action:" + action + " 异常：" + e.getMessage());
            rep = ResponseEntity.getErrorResponse(e.getMessage());
        }
        return rep;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity registerCards(Person person) throws Exception {
        if (null == person) {
            throw new ParamsException("person参数不能为空!");
        }
        List<Card> cards = person.getCards();
        if (CollectionsUtils.isNull(cards)) {
            throw new ParamsException("cards参数不能为空!");
        }
        Date nowDate = DateUtils.getCurrentDate();
        List<CardSyncLog> cardSyncLogList = new ArrayList<CardSyncLog>();
        for (Card card : cards) {
            // 检查卡片信息
            checkCardInfo(card);
            // 获取主索引对应person
            Person personFromDB = personDao.getEnableObjectByMpiId(card.getMpiId());
            if (null == personFromDB) {
                throw new Exception("mpiId错误，没有对应居民主索引!");
            }
            // 检查是否已存在card
            boolean result = isExist(card);
            if (result) {
                throw new Exception("卡已存在!");
            }
            card.setCreator(ContentUtils.ADMIN_USER_ID);
            card.setCreateDate(nowDate);
            card.setPersonId(personFromDB.getId());
            card.setDelFalg(ContentUtils.INFO_NOT_DELETE);
            card.setStatus(ContentUtils.CARD_STATUS_NORMAL);
            cardDao.insertObject(card);

            // 卡的同步log 创建
            CardSyncLog cardSyncLog = new CardSyncLog();
            cardSyncLog.setCardId(card.getId());
            cardSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
            cardSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
            cardSyncLog.setCreateDate(nowDate);
            cardSyncLogList.add(cardSyncLog);
        }
        // 添加同步记录（前置机）
        addSyncLog(cardSyncLogList);
        return ResponseEntity.getSuccessResponse(null, 0);
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity updateCardStatus(Person person, Integer cardStatus) throws Exception {
        if (null == person) {
            throw new ParamsException("person参数不能为空!");
        }
        List<Card> cards = person.getCards();
        if (CollectionsUtils.isNull(cards)) {
            throw new ParamsException("cards参数不能为空!");
        }
        if (cards.size() != 1) {
            throw new Exception("只能注销一张卡!");
        }
        Date nowDate = DateUtils.getCurrentDate();
        Card card = cards.get(0);
        // 检查卡片信息
        checkCardInfo(card);
        // 获取主索引对应person
        Person personFromDB = personDao.getEnableObjectByMpiId(card.getMpiId());
        if (null == personFromDB) {
            throw new Exception("mpiId错误，没有对应居民主索引!");
        }
        // 个人唯一号
        String mpiId = card.getMpiId();
        // 卡类型
        String cadTypeCode = card.getCardTypeCode();
        // 卡号
        String cardNo = card.getCardNo();
        QueryParams params = new QueryParams();
        params.addParameter("mpiId", mpiId);
        params.addParameter("cardTypeCode", cadTypeCode);
        params.addParameter("cardNo", cardNo);
        Card cardFromDB = queryOneBase(params);
        // 检查是否已存在card
        if (null == cardFromDB) {
            throw new Exception("卡不存在!");
        }
        // 如果是"注销操作" 且 原状态是"注销状态" 返回禁止重复注销
        if(ContentUtils.CARD_STATUS_CANCEL.equals(cardStatus) && ContentUtils.CARD_STATUS_CANCEL.equals(cardFromDB.getStatus())){
            return ResponseEntity.getErrorResponse("请不要重复注销");
        }
        // 如果是"挂失操作" 且 原状态不是"正常状态"
        if(ContentUtils.CARD_STATUS_LOSS.equals(cardStatus) && !ContentUtils.CARD_STATUS_NORMAL.equals(cardFromDB.getStatus())){
            // 原卡必须是正常状态
            return ResponseEntity.getErrorResponse("只有正常状态下的卡,才允许挂失操作!");
        }
        // 如果"解挂操作" 且 原状态不是"挂失操作"
        if(ContentUtils.CARD_STATUS_NORMAL.equals(cardStatus) && !ContentUtils.CARD_STATUS_LOSS.equals(cardFromDB.getStatus())){
         // 原卡必须是正常状态
            return ResponseEntity.getErrorResponse("只有挂失状态下的卡,才允许解挂操作!");
        }
        if (ContentUtils.CARD_STATUS_ACTIVATION.equals(cardStatus)) {
			if (!ContentUtils.CARD_STATUS_CANCEL.equals(cardFromDB.getStatus())) {
				// 原卡状态必须为注销状态才能被激活
	        	return ResponseEntity.getErrorResponse("只有注销状态下的卡,才允许激活操作");
			}
			cardStatus = ContentUtils.CARD_STATUS_NORMAL;
		}
        // 设置卡的状态
        cardFromDB.setStatus(cardStatus);
        cardFromDB.setUpdator(ContentUtils.ADMIN_USER_ID);
        cardFromDB.setUpdateDate(nowDate);
        cardDao.updateObject(cardFromDB);

        // 卡的同步log 创建
        List<CardSyncLog> cardSyncLogList = new ArrayList<CardSyncLog>();
        CardSyncLog cardSyncLog = new CardSyncLog();
        cardSyncLog.setCardId(cardFromDB.getId());
        cardSyncLog.setSyncStatus(ContentUtils.INFO_SYNC_STATUS_NO);
        cardSyncLog.setCreator(ContentUtils.ADMIN_USER_ID);
        cardSyncLog.setCreateDate(nowDate);
        cardSyncLog.setUpdator(ContentUtils.ADMIN_USER_ID);
        cardSyncLog.setUpdateDate(nowDate);
        cardSyncLogList.add(cardSyncLog);
        // 添加同步记录（前置机）
        addSyncLog(cardSyncLogList);
        return ResponseEntity.getSuccessResponse(null, 0);
    }



    @Override
    public ResponseEntity getCards(Person person) throws Exception {
        if (null == person) {
            throw new ParamsException("person参数不能为空!");
        }
        String mpiId = person.getMpiId();
        if (StringUtils.isEmpty(mpiId)) {
            throw new ParamsException("mpiId参数不能为空!");
        }
        QueryParams params = new QueryParams();
        params.addParameter("mpiId", mpiId);
        params.addOrderBy("updateDate", false);
        List<Card> cards = cardDao.queryDetailList(params);
        if (CollectionsUtils.isNull(cards)) {
            return ResponseEntity.getSuccessResponse(person, 0);
        } else {
            person.setCards(cards);
            return ResponseEntity.getSuccessResponse(person, cards.size());
        }
    }



    /**
     * 
     * 描述：添加同步记录（前置机）
     * 
     * @param cardSyncLogList
     * @author yangyongchuan 2017年12月29日 下午2:21:06
     * @version 1.0
     */
    private void addSyncLog(List<CardSyncLog> cardSyncLogList) {
        List<FrontEndMachine> frontEndMachineList = frontEndMachineDao.queryBaseList(null);
        if (CollectionsUtils.isNull(frontEndMachineList)) {
            return;
        }
        List<CardSyncLog> addCardSyncLogList = new ArrayList<CardSyncLog>();
        // 设置前置id
        for (CardSyncLog cardSyncLog : cardSyncLogList) {
            for (FrontEndMachine fem : frontEndMachineList) {
                CardSyncLog cardSyncLogClone = new CardSyncLog();
                BeanUtils.copyProperties(cardSyncLog, cardSyncLogClone);
                cardSyncLogClone.setFrontId(fem.getId());
                addCardSyncLogList.add(cardSyncLogClone);
            }
        }
        cardSyncLogDao.insertOrUpdatetList(addCardSyncLogList);
    }



    /**
     * 
     * 描述：检查相同卡是否存在
     * 
     * @param mpiId
     * @param cadTypeCode
     * @param cardNo
     * @return
     * @author yangyongchuan 2017年12月29日 下午5:12:37
     * @version 1.0
     */
    private boolean isExist(Card card) {
        // 个人唯一号
        String mpiId = card.getMpiId();
        // 卡类型
        String cadTypeCode = card.getCardTypeCode();
        // 卡号
        String cardNo = card.getCardNo();
        QueryParams params = new QueryParams();
        params.addParameter("mpiId", mpiId);
        params.addParameter("cardTypeCode", cadTypeCode);
        params.addParameter("cardNo", cardNo);
        Card cardFromDB = queryOneBase(params);
        return cardFromDB != null ? true : false;
    }



    /**
     * 
     * 描述：检查卡片信息
     * 
     * @param card
     * @throws Exception
     * @author yangyongchuan 2018年1月2日 上午9:36:34
     * @version 1.0
     */
    private boolean checkCardInfo(Card card) throws Exception {
        // 个人唯一号
        String mpiId = card.getMpiId();
        // 卡类型
        String cadTypeCode = card.getCardTypeCode();
        // 卡号
        String cardNo = card.getCardNo();
        if (StringUtils.isEmpty(mpiId)) {
            throw new ParamsException("mpiId参数不能为空!");
        }
        if (StringUtils.isEmpty(cadTypeCode)) {
            throw new ParamsException("cadTypeCode参数不能为空!");
        }
        if (StringUtils.isEmpty(cardNo)) {
            throw new ParamsException("cardNo参数不能为空!");
        }
        return true;
    }
}
