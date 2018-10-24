package com.jc.cz_index.api.service;

import com.jc.cz_index.model.Card;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.ResponseEntity;

/**
 * card Service 接口 Created by
 */
public interface ICardService extends IBaseService<Card> {

    /**
     * 
     * 描述：注册卡：将一张卡关联到一份MPI记录上，同时更新索引，之后通过该卡就可以检索到卡的持有者的信息。
     * 
     * @param person
     * @return
     * @author yangyongchuan 2017年12月29日 上午11:44:41
     * @version 1.0
     * @throws Exception
     */
    ResponseEntity registerCards(Person person) throws Exception;



    /**
     * 
     * 描述： 卡状态:0:正常1:挂失2:注销3:失效
     * 
     * 注销卡：根据请求中的卡类型代码以及卡号找到目标信息，将目标卡打上注销标记，并修改索引内容
     * 卡挂失：根据请求中的卡类型代码以及卡号找到目标卡，将目标卡打上挂失标记
     * 卡解挂：根据请求中的卡信息找到目标卡，将目标卡的挂失标记取消，卡状态恢复为正常
     * 
     * @param person
     * @param cardStatus
     * @return
     * @throws Exception
     * @author yangyongchuan 2018年1月2日 上午9:19:48
     * @version 1.0
     */

    ResponseEntity updateCardStatus(Person person, Integer cardStatus) throws Exception;



    /**
     * 
     * 描述：根据MPIID获取所有卡
     * 
     * @param person
     * @return
     * @throws Exception
     * @author yangyongchuan 2018年1月2日 上午11:43:32
     * @version 1.0
     */
    ResponseEntity getCards(Person person) throws Exception;

}
