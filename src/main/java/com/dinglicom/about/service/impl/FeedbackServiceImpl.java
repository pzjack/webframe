/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.about.service.impl;

import com.dinglicom.about.domain.FeedQueryReq;
import com.dinglicom.about.domain.FeedRespItem;
import com.dinglicom.about.domain.FeedSelfRespItem;
import com.dinglicom.about.domain.FeedbackResp;
import com.dinglicom.about.entity.FeedBack;
import com.dinglicom.about.repository.FeedbackDao;
import com.dinglicom.about.service.FeedbackService;
import com.dinglicom.frame.sys.entity.UserInfo;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackDao feedbackDao;

    @Override
    @Transactional(readOnly = true)
    public FeedBack read(long fid) {
        return feedbackDao.findOne(fid);
    }

    @Override
    public FeedBack save(FeedBack feedback) {
        return feedbackDao.save(feedback);
    }

    @Override
    public FeedBack update(FeedBack feedback) {
        return feedbackDao.save(feedback);
    }

    @Override
    public FeedBack createUserFeedback(UserInfo user, String content) {
        FeedBack f = new FeedBack();
        f.setFeedtime(new Date());
        f.setFeedname(user.getRealname());
        f.setFeedtel(user.getPhone());
        f.setContent(content);
        f.setFeeduser(user);

        return save(f);
    }

    @Override
    public FeedBack createBackForFeed(UserInfo admin, long fid, String content) {
        FeedBack f = read(fid);
        if (null == f) {
            throw new RuntimeException("Feed Id nof found record.");
        }
        if(f.isFeedback()) {
            throw new RuntimeException("Feed Id already answer.");
        }
        f.setBackcontent(content);
        f.setBacktime(new Date());
        f.setBackuser(admin);
        f.setBackusername(admin.getRealname());
        f.setFeedback(Boolean.TRUE);

        return update(f);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedSelfRespItem> findUserSelfFeedback(UserInfo user) {
        Page<FeedSelfRespItem> page = feedbackDao.findByFeeduserId(buildPageRequest(1, 20), user.getId(), Boolean.FALSE);
        return page.getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public FeedbackResp findByQueryAllFeed(FeedQueryReq req) {
        Page<FeedRespItem> page;
        FeedbackResp resp = new FeedbackResp();
        if (null == req.getStatus()) {
            page = feedbackDao.findByPage(buildPageRequest(req.getPage(), req.getNum()), Boolean.FALSE);
        } else {
            page = feedbackDao.findByPageByFeedback(null, 0 != req.getStatus(), Boolean.FALSE);
        }
        resp.setAnswerednum(feedbackDao.findFeedbackCount(Boolean.TRUE, Boolean.FALSE));
        resp.setUnanswernum(feedbackDao.findFeedbackCount(Boolean.FALSE, Boolean.FALSE));
        resp.setData(page.getContent());
        return resp;
    }
    
    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(final int page, final int size) {
        return new PageRequest(page - 1, size);
    }
}
