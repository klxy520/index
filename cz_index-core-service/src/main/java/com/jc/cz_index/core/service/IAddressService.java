package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.common.bean.ResponseData;
import com.jc.cz_index.model.Address;
import com.jc.cz_index.model.SystemUser;

/***
 * 
 * 描述：地址管理Service 接口
 * 
 * @author sunxuefeng 2017年12月28日 下午3:20:00
 * @version 1.0
 */
public interface IAddressService extends IBaseService<Address> {
    /**
     * 
     * 描述：根据基本ID删除地址信息信息
     * 
     * @param id
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年12月28日 下午5:58:33
     * @version 1.0
     */
    ResponseData deleteAddressById(Long id, SystemUser systemUser) throws Exception;



    /**
     * 
     * 描述：添加单个地址信息
     * 
     * @param address
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年12月29日 上午10:30:16
     * @version 1.0
     */
    ResponseData addAddress(Address address, SystemUser systemUser) throws Exception;



    /**
     * 
     * 描述：修改单个地址信息
     * 
     * @param address
     * @param systemUser
     * @return
     * @throws Exception
     * @author sunxuefeng 2017年12月29日 上午10:28:58
     * @version 1.0
     */
    ResponseData updateAddress(Address address, SystemUser systemUser) throws Exception;
    
    /**
     * 
     * 描述：获取同步地址
     * @param id
     * @return
     * @author wangdeyou 2018年1月4日 下午8:52:55 
     * @version 1.0
     */
    List<Address> excuteTask(Long id);

}
