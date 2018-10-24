package com.jc.cz_index.model;

public class NoticeUser extends BaseBean{
    
    /**
     * 
     */
    private static final long serialVersionUID = 4987339375530184653L;
    // 主键id [主键]
    private Long              id;
    // 角色(权限)id
    private Long              nid;
    // 菜单id
    private Long              uid;

    private Long             isRead;
    private Long              isDelete;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getNid() {
        return nid;
    }
    public void setNid(Long nid) {
        this.nid = nid;
    }
    public Long getUid() {
        return uid;
    }
    public void setUid(Long uid) {
        this.uid = uid;
    }
    public Long getIsRead() {
        return isRead;
    }
    public void setIsRead(Long isRead) {
        this.isRead = isRead;
    }
    public Long getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(Long isDelete) {
        this.isDelete = isDelete;
    }
    
    

}
