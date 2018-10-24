package com.jc.cz_index.fem.service.impl;

import java.util.List;

import com.jc.cz_index.common.utils.CollectionsUtils;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.PagedList;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.fem.service.IBaseService;

public abstract class BaseService<T> implements IBaseService<T> {

    @Override
    public T getModelById(Long id) {
        return getModelDao().getBaseObject(id);
    }



    @Override
    public T getModelDetailById(Long id) {
        return getModelDao().getDetailObject(id);
    }



    @Override
    public T doUpdateModel(T model) {
        getModelDao().updateObject(model);
        return model;
    }



    @Override
    public T doAddModel(T model) {
        getModelDao().insertObject(model);
        return model;
    }



    @Override
    public boolean doDeleteModel(Long id) {
        int res = getModelDao().deleteObject(id);
        return res > 0;
    }



    @Override
    public boolean doDeleteModelByWhere(QueryParams params) {
        int res = getModelDao().deleteObjectByWhere(params);
        return res > 0;
    }



    @Override
    public List<T> queryBaseList(QueryParams params) {
        if (null == params) {
            params = new QueryParams();
        }
        params.setStart(0);
        params.setSize(-1);
        return getModelDao().queryBaseList(params);
    }



    @Override
    public T queryOneBase(QueryParams params) {
        if (null == params) {
            params = new QueryParams();
        }
        params.setStart(0);
        params.setSize(1);
        List<T> list = getModelDao().queryBaseList(params);
        return CollectionsUtils.isNotNull(list) ? list.get(0) : null;
    }



    @Override
    public List<T> queryDetailList(QueryParams params) {
        if (null == params) {
            params = new QueryParams();
        }
        params.setStart(0);
        params.setSize(-1);
        return getModelDao().queryDetailList(params);
    }



    @Override
    public T queryOneDetail(QueryParams params) {
        if (null == params) {
            params = new QueryParams();
        }
        params.setStart(0);
        params.setSize(1);
        List<T> list = getModelDao().queryDetailList(params);
        return CollectionsUtils.isNotNull(list) ? list.get(0) : null;
    }



    @Override
    public PagedList<T> queryPagedBaseList(QueryParams params, int start, int size) {
        if (null == params) {
            params = new QueryParams();
        }
        if (start < 0)
            throw new IllegalArgumentException("pageIndex参数不能小于0.");
        if (size < 0)
            throw new IllegalArgumentException("pageSize参数不能小于0.");
        int total = queryCount(params);
        params.setStart(start);
        params.setSize(size);
        List<T> list = getModelDao().queryBaseList(params);
        PagedList<T> result = new PagedList<>();
        result.getList().addAll(list);
        result.setPageIndex(start / size + 1);
        result.setPageSize(size);
        result.setTotalSize(total);
        result.setPageCount(total / size + 1);
        return result;
    }



    @Override
    public PagedList<T> queryPagedDetaiList(QueryParams params, int start, int size) {
        if (null == params) {
            params = new QueryParams();
        }
        if (start < 0)
            throw new IllegalArgumentException("pageIndex参数不能小于0.");
        if (size < 0)
            throw new IllegalArgumentException("pageSize参数不能小于0.");
        int total = queryCount(params);
        params.setStart(start);
        params.setSize(size);
        List<T> list = getModelDao().queryDetailList(params);
        PagedList<T> result = new PagedList<>();
        result.getList().addAll(list);
        result.setPageIndex(start / size + 1);
        result.setPageSize(size);
        result.setTotalSize(total);
        result.setPageCount(total / size + 1);
        return result;
    }



    @Override
    public int queryCount(QueryParams params) {
        return getModelDao().queryCount(params);
    }



    @Override
    public int insertList(List<T> list) {
        return getModelDao().insertList(list);
    }



    @Override
    public int deleteList(List<Long> list) {
        return getModelDao().deleteList(list);
    }



    @Override
    public int deleteListByPKs(String PKs) {
        return getModelDao().deleteListByPKs(PKs);
    }



    @Override
    public List<T> getObjectList(List<Long> list) {
        return getModelDao().getObjectList(list);
    }



    @Override
    public List<T> getObjectListByPKs(String PKs) {
        return getModelDao().getObjectListByPKs(PKs);
    }



    public abstract IDataProvider<T, Long> getModelDao();
}
