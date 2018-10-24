package com.jc.cz_index.dao;

import java.util.List;

/**
 * 数据访问接口
 * <p/>
 * Created by Caven on 2015/1/21.
 */
public interface IDataProvider<T, PK> {
    /**
     * 插入一个对象
     *
     * @param object
     *            要插入的对象
     * @return 影响的行数
     */
    int insertObject(T object);



    /**
     * 根据对象主键更新对象
     *
     * @param object
     *            要更新的对象
     * @return 影响的行数
     */
    int updateObject(T object);



    /**
     * 根据对象主键更新对象
     *
     * @param map
     *            要更新的对象
     * @return 影响的行数
     */
    int updateObject(QueryParams params);



    /**
     * 根据对象主键更新对象
     *
     * @param map
     *            要更新的对象
     * @return 影响的行数
     */
    int updateObjectByFields(QueryParams params);



    /**
     * 删除对象
     *
     * @param key
     *            主键
     * @return 影响的行数
     */
    int deleteObject(PK key);



    /**
     * 获取对象
     *
     * @param key
     *            主键
     * @param detail
     *            是否要获取对象的详细信息
     * @return 对象
     */
    T getBaseObject(PK key);



    /**
     * 获取对象 详情
     *
     * @param key
     *            主键
     * @param detail
     *            是否要获取对象的详细信息
     * @return 对象
     */
    T getDetailObject(PK key);



    /**
     * 插入一组对象
     *
     * @param list
     *            对象list
     * @return 影响的行数
     */
    int insertList(List<T> list);



    /**
     * 删除一组对象
     *
     * @param list
     *            主键list
     * @return 影响的行数
     */
    int deleteList(List<PK> list);



    /**
     * 删除一组对象
     *
     * @param PKs
     *            主键字符串，如：1,2,3
     * @return 影响的行数
     */
    int deleteListByPKs(String PKs);



    /**
     * 获取一组对象
     *
     * @param list
     *            主键List
     * @return 对象List
     */
    List<T> getObjectList(List<PK> list);



    /**
     * 获取一组对象
     *
     * @param PKs
     *            主键字符串，如：1,2,3
     * @return 对象List
     */
    List<T> getObjectListByPKs(String PKs);



    /**
     * 根据条件删除对象
     *
     * @param wheres
     *            条件
     * @return 影响的行数
     */
    int deleteObjectByWhere(QueryParams wheres);



    /**
     * 查询对象数
     *
     * @param wheres
     *            条件
     * @return 符合条件的对象个数
     */
    int queryCount(QueryParams wheres);



    /**
     * 查询对象列表
     *
     * @param wheres
     *            条件
     * @param skip
     *            在结果是跳过的数目
     * @param size
     *            返回的最大数目,小于0则返回所有记录
     * @param detail
     *            是还返回对象详细信息
     * @return 对象列表
     */
    List<T> queryBaseList(QueryParams wheres);



    /**
     * 查询对象列表 详情
     *
     * @param wheres
     *            条件
     * @param skip
     *            在结果是跳过的数目
     * @param size
     *            返回的最大数目,小于0则返回所有记录
     * @param detail
     *            是还返回对象详细信息
     * @return 对象列表
     */
    List<T> queryDetailList(QueryParams wheres);

}
