package com.jc.cz_index.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.common.utils.BeanUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.core.service.ICardService;
import com.jc.cz_index.core.service.ICardSyncLogService;
import com.jc.cz_index.core.service.IOperationLogService;
import com.jc.cz_index.dao.ICardDao;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.dto.card.CardDto;
import com.jc.cz_index.model.BaseBean;
import com.jc.cz_index.model.Card;
import com.jc.cz_index.model.SystemUser;

/**
 * card Service 实现 Created by
 */
@Service
public class CardService extends BaseService<Card> implements ICardService {

	@Autowired
	private ICardDao cardDao;
	@Autowired
	private IOperationLogService operationLogService;
	@Autowired
	private ICardSyncLogService iCardSyncLogService;

	private final String fields = "主键id,id;主索引id,mpiId;基本身份信息id,personId;卡类别代码,cardTypeCode;卡号,cardNo;修改机构,lastModifyUnit;卡内号,cardCode;发卡时间,createTime;发卡机构,createUnit;发卡人,createUser;卡有效期,validTime;卡状态,status;删除标记(0未删除1已删除),delFalg;删除人,deleteor;删除时间,deleteDate;创建人,creator;最后更新者,updator;创建时间,createDate;最后更新时间,updateDate;";

	@Override
	public IDataProvider<Card, Long> getModelDao() {
		return this.cardDao;
	}

	@Override
	public List<Card> excuteTask(Long femId) {
		return cardDao.getUnSyncObjectList(femId);
	}

	/**
	 * 查询所有人卡列表
	 */
	/**
	 * 查询所有人卡列表
	 */
	@Override
	public PagedList<CardDto> queryAllCardList(QueryParams params, int start, int size) {
		if (null == params) {
			params = new QueryParams();
		}
		if (start < 0)
			throw new IllegalArgumentException("pageIndex参数不能小于0.");
		if (size < 0)
			throw new IllegalArgumentException("pageSize参数不能小于0.");
		int total = cardDao.queryCountForTableList(params);
		params.setStart(start);
		params.setSize(size);
		List<CardDto> list = cardDao.queryTableList(params);
		PagedList<CardDto> result = new PagedList<CardDto>();
		result.getList().addAll(list);
		result.setPageIndex(start / size + 1);
		result.setPageSize(size);
		result.setTotalSize(total);
		result.setPageCount(total / size + 1);
		return result;
	}

	/**
	 * 删除卡
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseData deleteCardById(Long cardId) {
		ResponseData responseData = new ResponseData();
		SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
		Card cardDB = new Card();
		cardDB = cardDao.getBaseObject(cardId);
		try {
			String details = BeanUtils.getBeanPropertiesByFields(cardDB, fields);
			cardDB.setDeleteor(loginUser.getId());
			cardDB.setDeleteDate(new Date());
			cardDB.setDelFalg(1);
			cardDB.setId(cardId);
			cardDB.setLastModifyUser(loginUser.getName());
			cardDao.updateObject(cardDB);
			iCardSyncLogService.addCardSyncLog(cardId, "", loginUser.getId());
			operationLogService.addOperationLog("卡务信息", cardId + "", "删除卡务信息", "居民卡被删除原有详情：" + details, "无");
			responseData = ResponseData.getSuccessResponse();
		} catch (Exception e) {
			responseData = ResponseData.getErrorResponse(e.getMessage());
		}

		return responseData;

	}

	/**
	 * 添加卡
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseData addCard(Card card) {
		ResponseData responseData = new ResponseData();
		SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
		try {
			card.setCreator(loginUser.getId());
			card.setCreateDate(new Date());
			card.setDelFalg(0);
			card.setLastModifyUser(loginUser.getName());
			card= (Card) this.setCreate(card);
			cardDao.insertObject(card);
			String details = BeanUtils.getBeanPropertiesByFields(card, fields);
			iCardSyncLogService.addCardSyncLog(card.getId(), "", loginUser.getId());
			operationLogService.addOperationLog("卡务信息", card.getId() + "", "添加卡务信息", "被添加居民卡详情：" + details, "无");
			responseData = ResponseData.getSuccessResponse();
		} catch (Exception e) {
			responseData = ResponseData.getErrorResponse(e.getMessage());
			e.printStackTrace();
		}
		return responseData;
	}

	/**
	 * 修改卡
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseData updateCard(Card card) {
		ResponseData responseData = new ResponseData();
		try {
			Card cardDB = cardDao.getBaseObject(card.getId());
			Card cardNew = new Card();
			BeanUtils.copyPropertiesIgnoreNull(cardDB, cardNew);// 将数据库的内容拷贝到cardNew，记录原始信息
			BeanUtils.copyPropertiesIgnoreNull(card, cardNew);// 将数据库的内容拷贝到extendBefore，记录原始信息
			cardNew.setCardTypeCode(card.getCardTypeCode());
			cardNew.setCreateUnit(card.getCreateUnit());
			cardNew.setStatus(card.getStatus());
			String details = BeanUtils.getDifferenceByFields(cardDB, cardNew,
					"卡类别代码,cardTypeCode;卡号,cardNo;卡内号,cardCode;发卡机构,createUnit;修改机构,lastModifyUnit;卡有效期,validTime;卡状态,status;");
			SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
			cardNew.setUpdator(loginUser.getId());
			cardNew.setUpdateDate(new Date());
			cardNew.setLastModifyUser(loginUser.getName());
			cardDao.updateObject(cardNew);
			iCardSyncLogService.addCardSyncLog(card.getId(), "", loginUser.getId());
			operationLogService.addOperationLog("卡务信息", card.getId() + "", "修改卡务信息", "被修改居民卡详情：" + details, "无");
			responseData = ResponseData.getSuccessResponse();
			responseData.setMessage("修改成功");
		} catch (Exception e) {
			responseData = ResponseData.getErrorResponse(e.getMessage());
			e.printStackTrace();
		}
		return responseData;
	}
}
