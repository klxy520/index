package com.jc.cz_index.model;

import java.util.ArrayList;
import java.util.List;


public class Nodes extends BaseBean{

    /**
     * 
     */
    private static final long serialVersionUID = 1839863541578287404L;
    
    // 主键id [主键]
    private Long              id;
    // 角色(权限)id
    private String             name  ;
    
    private Long             childId  ;
    private int             isNode =1 ;
    
    // 菜单id
    private List<Nodes> childNode=new ArrayList<Nodes>() ;
        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public List<Nodes> getChildNode() {
        return childNode;
    }

    public void setChildNode(List<Nodes> childNode) {
        this.childNode = childNode;
    }

    public int getIsNode() {
        return isNode;
    }

 

 

  

}
