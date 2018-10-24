package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.model.Contact;
import com.jc.cz_index.model.SystemUser;

/***
 * 
 * 描述：联系人管理 Service 接口
 * 
 * @author sunxuefeng 2018年1月2日 上午11:44:10
 * @version 1.0
 */
public interface IContactService extends IBaseService<Contact> {

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
    List<Contact> excuteTask(Long femId);



    /**
     * 
     * 描述：根据ID删除联系人信息信息
     * 
     * @param id
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2018年1月2日 上午11:45:14
     * @version 1.0
     */
    ResponseData deleteContactById(Long id, SystemUser systemUser) throws Exception;



    /**
     * 添加单个联系人 描述：
     * 
     * @param contact
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2018年1月2日 上午11:46:23
     * @version 1.0
     */
    ResponseData addContact(Contact contact, SystemUser systemUser) throws Exception;



    /**
     * 
     * 描述：修改单个联系人
     * 
     * @param contact
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2018年1月2日 上午11:46:49
     * @version 1.0
     */
    ResponseData updateContact(Contact contact, SystemUser systemUser) throws Exception;
}
