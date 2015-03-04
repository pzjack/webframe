/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.service;

import com.dinglicom.about.domain.FeedQueryReq;
import com.dinglicom.about.domain.FeedSelfRespItem;
import com.dinglicom.about.domain.FeedbackResp;
import com.dinglicom.about.entity.FeedBack;
import com.dinglicom.frame.sys.entity.UserInfo;
import java.util.List;

/**
 *
 * @author panzhen
 */
public interface FeedbackService {

    /**
     * 查找用户反馈
     * @param fid
     * @return 
     */
    FeedBack read(long fid);
    
    /**
     * 保存用户反馈
     * @param feedback
     * @return 
     */
    FeedBack save(FeedBack feedback);
    
    /**
     * 添加反馈回复
     * @param feedback
     * @return 
     */
    FeedBack update(FeedBack feedback);
    
    /**
     * 创建对应用户，对应内容的反馈信息
     * @param user
     * @param content
     * @return 
     */
    FeedBack createUserFeedback(UserInfo user, String content);
    
    /**
     * 创建答复信息为特定反馈信息
     * @param admin
     * @param fid
     * @param content
     * @return 
     */
    FeedBack createBackForFeed(UserInfo admin, long fid, String content);
    
    /**
     * 查找用户自己的所有反馈及答复信息
     * @param user
     * @return 
     */
    List<FeedSelfRespItem> findUserSelfFeedback(UserInfo user);
    
    /**
     * 根据反馈状态查找所有反馈答复消息
     * @param req
     * @return 
     */
    FeedbackResp findByQueryAllFeed(FeedQueryReq req);
}
