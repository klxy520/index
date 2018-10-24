package com.jc.cz_index.core.service;

import java.util.List;

import com.jc.cz_index.model.Notice;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Nodes;

public interface INoticeService extends IBaseService<Notice> {

    public void addNotice(Notice notice);



    public void deleteNotice(String ids);



    public Notice queryNoticeById(Long id);



    public List<Notice> getMyNoticeList();



    public void updateReadStatus(Long nid);



    public void updateNotice(Notice notice);



 



    public Nodes getAreaNodesWithUser(Long id);



    public List<Long> getNoticeUserByNid(Long id);



    public int setNoticeUser(QueryParams params);



    public List<Nodes> getOrgNodesWithUser();

}
