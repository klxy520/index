package com.jc.cz_index.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PagedList<T> implements Serializable {
    private static final long serialVersionUID = -4169096025868641406L;
    private List<T>           list             = new ArrayList<T>();
    // 页码
    private int               pageIndex;
    // 总页数
    private int               pageCount;
    // 页大小(每页多少条记录)
    private int               pageSize;
    // 总记录数
    private int               totalSize;



    /**
     * 获取当前页列表
     *
     * @return 对象列表
     */
    public List<T> getList() {
        return list;
    }



    /**
     * 设置当前页列表
     *
     * @param list
     *            要设置的当前页列表
     */
    public void setList(List<T> list) {
        this.list = list;
    }



    /**
     * 获取页码
     *
     * @return 页码
     */
    public int getPageIndex() {
        return pageIndex;
    }



    /**
     * 设置页码
     *
     * @param pageIndex
     *            要设置的页码
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }



    /**
     * 获取页数
     *
     * @return 页页数
     */
    public int getPageCount() {
        return pageCount;
    }



    /**
     * 设置页大小
     *
     * @param pageCount
     *            要设置的页大小
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }



    /**
     * 获取页大小
     *
     * @return 页大小
     */
    public int getPageSize() {
        return pageSize;
    }



    /**
     * 设置页大小
     *
     * @param pageSize
     *            要设置的页大小
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }



    /**
     * 获取总记录数
     *
     * @return 总记录数
     */
    public int getTotalSize() {
        return totalSize;
    }



    /**
     * 设置总记录数
     *
     * @param totalSize
     *            要设置的总记录数
     */
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

}
