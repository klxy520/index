/**
 * 2016/10/9 14:15:57 Jack Liu created.
 */

package com.jc.cz_index.model;

import java.util.Date;

/**
 * 2016/10/09 Created by Jack Liu.
 */
public class UpFile extends BaseBean {

    // 序列化版本
    private static final long serialVersionUID = -5894451088222884864L;

    // 主键id [主键]
    private Long              id;
    // 原文件名
    private String            oldFileName;
    // 新文件名
    private String            newFileName;
    // 文件大小
    private Integer           fileSize;
    // 文件类型
    private Integer           fileType;
    // 上传者
    private Long              createor;
    // 状态
    private Integer           state;
    // 文件路径
    private String            filePath;
    // 上传时间
    private Date              createDate;



    /**
     * 获取主键id [主键]
     * 
     * @return 主键id
     */
    public Long getId() {
        return id;
    }



    /**
     * 设置主键id [主键]
     * 
     * @param id
     *            主键id
     */
    public void setId(Long id) {
        this.id = id;
    }



    /**
     * 获取原文件名
     * 
     * @return 原文件名
     */
    public String getOldFileName() {
        return oldFileName;
    }



    /**
     * 设置原文件名
     * 
     * @param oldFileName
     *            原文件名
     */
    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
    }



    /**
     * 获取新文件名
     * 
     * @return 新文件名
     */
    public String getNewFileName() {
        return newFileName;
    }



    /**
     * 设置新文件名
     * 
     * @param newFileName
     *            新文件名
     */
    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }



    /**
     * 获取文件大小
     * 
     * @return 文件大小
     */
    public Integer getFileSize() {
        return fileSize;
    }



    /**
     * 设置文件大小
     * 
     * @param fileSize
     *            文件大小
     */
    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }



    /**
     * 获取文件类型
     * 
     * @return 文件类型
     */
    public Integer getFileType() {
        return fileType;
    }



    /**
     * 设置文件类型
     * 
     * @param fileType
     *            文件类型
     */
    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }



    /**
     * 获取上传者
     * 
     * @return 上传者
     */
    public Long getCreateor() {
        return createor;
    }



    /**
     * 设置上传者
     * 
     * @param createor
     *            上传者
     */
    public void setCreateor(Long createor) {
        this.createor = createor;
    }



    /**
     * 获取状态
     * 
     * @return 状态
     */
    public Integer getState() {
        return state;
    }



    /**
     * 设置状态
     * 
     * @param state
     *            状态
     */
    public void setState(Integer state) {
        this.state = state;
    }



    /**
     * 获取文件路径
     * 
     * @return 文件路径
     */
    public String getFilePath() {
        return filePath;
    }



    /**
     * 设置文件路径
     * 
     * @param filePath
     *            文件路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }



    /**
     * 获取上传时间
     * 
     * @return 上传时间
     */
    public Date getCreateDate() {
        return createDate;
    }



    /**
     * 设置上传时间
     * 
     * @param createTimestamp
     *            上传时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
