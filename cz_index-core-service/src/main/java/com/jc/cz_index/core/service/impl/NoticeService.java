/**
 * 2016/9/26 21:27:44 Jack Liu created.
 */

package com.jc.cz_index.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.cz_index.common.utils.StringUtils;
import com.jc.cz_index.common.utils.WebUtils;
import com.jc.cz_index.dao.IDataProvider;
import com.jc.cz_index.dao.INoticeDao;
import com.jc.cz_index.dao.QueryParams;
import com.jc.cz_index.model.Nodes;
import com.jc.cz_index.model.Notice;
import com.jc.cz_index.model.NoticeUser;
import com.jc.cz_index.model.SystemUser;
import com.jc.cz_index.core.service.INoticeService;

/**
 * 公告管理Service
 */
@Service
public class NoticeService extends BaseService<Notice>implements INoticeService {

    @Autowired
    private INoticeDao noticeDao;



    @Override
    public IDataProvider<Notice, Long> getModelDao() {
        // TODO Auto-generated method stub
        return this.noticeDao;
    }



    @Override
    public void addNotice(Notice notice) {
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        notice.setCreateDate(new Date());// 添加創建時間
        notice.setCreator(loginUser.getId()); // 添加創建用戶
        noticeDao.insertObject(notice);

    }



    @Override
    public void deleteNotice(String ids) {
        List<Long> id = StringUtils.getLongList(ids, ",");
        for (int i = 0; i < id.size(); i++) {
            noticeDao.deleteNoticeObject(id.get(i));
            noticeDao.deleteNoticeLinkRoleObject(id.get(i));
        }
    }



    @Override
    public Notice queryNoticeById(Long id) {
        return noticeDao.queryNoticeById(id);
    }



    @Override
    public List<Notice> getMyNoticeList() {
        QueryParams params = new QueryParams();
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        params.addParameter("userId", loginUser.getId());
        params.addParameter("now", new Date());
        return noticeDao.getMyNoticeList(params);
    }



    @Override
    public void updateReadStatus(Long nid) {
        QueryParams params = new QueryParams();
        params.addParameter("nid", nid);
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        params.addParameter("uid", loginUser.getId());
        noticeDao.updateNoticeLinkRole(params);
    }



    @Override
    public void updateNotice(Notice notice) {
        SystemUser loginUser = (SystemUser) WebUtils.getLoginUser();
        notice.setUpdator(loginUser.getId());
        notice.setUpdateDate(new Date());
        noticeDao.updateNotice(notice);

    }



    @Override
    public Nodes getAreaNodesWithUser(Long id) {
        Nodes areaNodes = noticeDao.getAreaNodesWithUser(id);
        return areaNodes;
    }



    @Override
    public List<Long> getNoticeUserByNid(Long nid) {
        // TODO Auto-generated method stub
        return noticeDao.getNoticeUserByNid(nid);
    }



    @Override
    public int setNoticeUser(QueryParams params) {
        String uidsStr = params.get("uids").toString();
        noticeDao.deleteNoticeUserbyNid(params);
        int n = 0;
        if (StringUtils.isNotEmpty(uidsStr)) {
            NoticeUser nu = null;
            List<String> uids = StringUtils.getStringList(uidsStr, ",");
            for (String uid : uids) {
                nu = new NoticeUser();
                nu.setNid(StringUtils.StringToLong(params.get("nid").toString()));
                nu.setUid(StringUtils.StringToLong(uid));
                noticeDao.setNoticeUser(nu);
                n++;
            }
        }
        return n;
    }



    @Override
    public  List<Nodes> getOrgNodesWithUser() {
        List<Nodes> orgNodes = noticeDao.getOrgNodesWithUser();
        return orgNodes;
    }

}
