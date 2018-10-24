package com.jc.cz_index.dao;

import java.util.List;

import com.jc.cz_index.model.Nodes;
import com.jc.cz_index.model.Notice;
import com.jc.cz_index.model.NoticeUser;

public interface INoticeDao extends IDataProvider<Notice, Long> {

    public Notice queryNoticeById(Long id);

    public List<Notice> getMyNoticeList(QueryParams params);

    public void updateReadStatus(Long nid);

    public void updateNoticeLinkRole(QueryParams params);

    public void deleteNoticeObject(Long long1);

    public void deleteNoticeLinkRoleObject(Long long1);


    public void updateNotice(Notice notice);

    public Nodes getAreaNodesWithUser(Long id);

    public List<Long> getNoticeUserByNid(Long nid);

    public void setNoticeUser(NoticeUser nu);

    public void deleteNoticeUserbyNid(QueryParams params);

    public List<Nodes> getOrgNodesWithUser();


    

  

}
