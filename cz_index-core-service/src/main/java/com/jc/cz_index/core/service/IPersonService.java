package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.model.Person;
import com.jc.cz_index.model.SystemUser;

/**
 * 
 * 描述：基本身份信息Service
 * 
 * @author sunxuefeng 2017年12月26日 下午6:10:49
 * @version 1.0
 */
public interface IPersonService extends IBaseService<Person> {
    /***
     * 
     * 描述：根据基本身份信息ID删除某个基本身份信息
     * 
     * @param id
     * @param systemUser
     * @return
     * @author sunxuefeng 2017年12月25日 下午3:29:27
     * @version 1.0
     */
    ResponseData deletePersonById(Long id, SystemUser systemUser) throws Exception;



    /***
     * 
     * 描述：根据基本身份信息ID注销或者恢复某个基本身份信息
     * 
     * @param id
     * @param systemUser
     * @param falg
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年12月25日 下午4:19:14
     * @version 1.0
     */
    ResponseData cancelAndRecoveryPersonById(Long id, SystemUser systemUser, String falg) throws Exception;



    /**
     * 
     * 描述：添加单个基本身份信息
     * 
     * @param person
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年12月27日 下午4:04:05
     * @version 1.0
     */
    ResponseData addPerson(Person person, SystemUser systemUser) throws Exception;



    /**
     * 描述：修改单个基本身份信息
     * 
     * @param person
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年12月28日 上午10:29:35
     * @version 1.0
     */
    ResponseData updatePerson(Person person, SystemUser systemUser) throws Exception;


    /**
     * 
     * 描述：定时同步任务
     * @param id
     * @return
     * @author fengshengliang 2018年1月4日 下午8:56:26 
     * @version 1.0
     */
    List<Person> excuteTask(Long id);
    /***
     * 
     * 描述：根据身份证号查询居基本身份信息是是否存在
     * 
     * @param identityNumber
     * @return
     * @author sunxuefeng 2017年10月10日 上午10:14:31
     * @version 1.0
     */
    public Boolean queryPersonByidCardExistence(String idCard);

}
