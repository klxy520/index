package com.jc.cz_index.core.service;

import java.util.List;

import org.springframework.beans.factory.BeanClassLoaderAware;

import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.BaseBean;

public interface IBaseService<T> {
    public T getModelById(Long id);



    public T getModelDetailById(Long id);



    public T doUpdateModel(T model);



    public T doAddModel(T model);



    public boolean doDeleteModel(Long id);



    public boolean doDeleteModelByWhere(QueryParams params);



    public List<T> queryBaseList(QueryParams params);



    public T queryOneBase(QueryParams params);



    public List<T> queryDetailList(QueryParams params);



    public T queryOneDetail(QueryParams params);



    public PagedList<T> queryPagedBaseList(QueryParams params, int start, int size);



    public PagedList<T> queryPagedDetaiList(QueryParams params, int start, int size);



    public int queryCount(QueryParams params);



    public int insertList(List<T> list);



    public int deleteList(List<Long> list);



    public int deleteListByPKs(String PKs);



    public List<T> getObjectList(List<Long> list);



    public List<T> getObjectListByPKs(String PKs);
    
    public BaseBean setCreate(BaseBean baseBean);

}
