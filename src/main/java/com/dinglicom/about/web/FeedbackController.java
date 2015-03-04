/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.about.web;

import com.dinglicom.about.domain.AnswerReq;
import com.dinglicom.about.domain.FeedQueryReq;
import com.dinglicom.about.domain.FeedSelfResp;
import com.dinglicom.about.domain.FeedSelfRespItem;
import com.dinglicom.about.domain.FeedbackResp;
import com.dinglicom.about.domain.FeedputContent;
import com.dinglicom.about.service.FeedbackService;
import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.web.AppControllerBase;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author panzhen
 */
@Controller
@RequestMapping(value = "/api/v1/feedback")
public class FeedbackController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(FeedbackController.class);
    @Resource
    protected SysTokenService sysTokenService;
    @Resource
    protected FeedbackService feedbackService;
    
    
    /**
     * 用户自己意见反馈列表获取
     * @param req
     * @return 
     */
    @RequestMapping(value = "/get")
    public @ResponseBody
    FeedSelfResp get(FeedQueryReq req) {
        FeedSelfResp msg = new FeedSelfResp();
        UserInfo user = validateToken(sysTokenService, req, msg);
        LOG.info("get ower feed record,uid({}),token({})", req.getUid(), req.getToken());
        if (null == user || 0 == req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            List<FeedSelfRespItem> list = feedbackService.findUserSelfFeedback(user);
            if(null != list) {
                msg.setData(list);
            }
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Get ower feed record fail.", e);
            msg.setCode(1);
            msg.setResult("保存失败");
            return msg;
        }
        return msg;
    }

    /**
     * 用户意见反馈
     * @param req
     * @return 
     */
    @RequestMapping(value = "/feed")
    public @ResponseBody
    BaseMsgBean feed(FeedputContent req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo user = validateToken(sysTokenService, req, msg);
        LOG.info("Add Feed content,uid({}),token({})", req.getUid(), req.getToken());
        if (null == user || 0 == req.getUid() || null == req.getContent()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            feedbackService.createUserFeedback(user, req.getContent());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Feed content fail.", e);
            msg.setCode(1);
            msg.setResult("保存失败");
            return msg;
        }
        return msg;
    }

    /**
     * 答复用户反馈
     * @param req
     * @return 
     */
    @RequestMapping(value = "/answer")
    public @ResponseBody
    BaseMsgBean answer(AnswerReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, req, msg);
        LOG.info("Add Feed content,uid({}),token({})", req.getUid(), req.getToken());
        if (null == admin || 0 == req.getUid() || null == req.getContent() || 0 >= req.getFid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            feedbackService.createBackForFeed(admin, req.getFid(), req.getContent());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Feed content fail.", e);
            msg.setCode(1);
            msg.setResult("保存失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }
    
    /**
     * 用户意见反馈及答复列表查询
     * @param req
     * @return 
     */
    @RequestMapping(value = "/query")
    public @ResponseBody
    FeedbackResp query(FeedQueryReq req) {
        FeedbackResp msg = new FeedbackResp();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("get ower feed record,mid({}),token({})", req.getMid(), req.getToken());
        if (null == admin || 0 == req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = feedbackService.findByQueryAllFeed(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Get ower feed record fail.", e);
            msg.setCode(1);
            msg.setResult("保存失败");
            return msg;
        }
        return msg;
    }
}
