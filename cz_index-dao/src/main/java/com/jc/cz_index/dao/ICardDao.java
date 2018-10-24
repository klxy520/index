package com.jc.cz_index.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.jc.cz_index.dto.card.CardDto;
import com.jc.cz_index.model.Card;
/**
 * card Ibatis Dao 接口 Created by
 */
@Repository
public interface ICardDao extends IDataProvider<Card, Long> {
    /**
     * 
     * 描述：查询所有卡
     * 
     * @param MpiId
     * @return
     * @author fengshengliang 2017年12月26日 下午7:51:58
     * @version 1.0
     */
    public List<Card> getObjectByMpiId(String mpiId);



    /**
     * 
     * 描述：查询所有卡列表数量
     * 
     * @param MpiId
     * @return
     * @author yangjunhui 2017年12月26日 下午7:51:58
     * @version 1.0
     */
    public int queryCountForTableList(QueryParams params);



    /**
     * 
     * 描述：所有人卡列表
     * 
     * @param MpiId
     * @return
     * @author yangjunhui 2017年12月26日 下午7:51:58
     * @version 1.0
     */
    public List<CardDto> queryTableList(QueryParams params);



    /**
     * 
     * 描述：获取未同步的数据
     * 
     * @param femId
     * @return
     * @author yangyongchuan 2018年1月4日 下午5:29:48
     * @version 1.0
     */
    List<Card> getUnSyncObjectList(Long femId);



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
