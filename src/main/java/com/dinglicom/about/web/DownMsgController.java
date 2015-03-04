/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.web;

import com.dinglicom.about.domain.DownMsgReq;
import com.dinglicom.about.service.DownMsgService;
import com.dinglicom.frame.sys.domain.AdminPageReqBase;
import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.web.AppControllerBase;
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
@RequestMapping(value = "/api/v1/downmsg")
public class DownMsgController extends AppControllerBase {
    private final static Logger LOG = LoggerFactory.getLogger(FeedbackController.class);
    @Resource
    protected SysTokenService sysTokenService;
    @Resource
    protected DownMsgService downMsgService;
    
    /**
     * 推送消息
     * @param req
     * @return 
     */
    @RequestMapping(value = "/down")
    public @ResponseBody
    BaseMsgBean downmsgsave(DownMsgReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Down msg mid({}),token({}), title({})", req.getMid(), req.getToken(), req.getTitle());
        if (null == admin || 0 >= req.getMid() || null == req.getContent() || null == req.getTitle()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            downMsgService.sendMsg(req, admin);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Down msg fail.", e);
            msg.setCode(1);
            msg.setResult("推送消息失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }
    
    /**
     * 获取消息
     * @param req
     * @return 
     */
    @RequestMapping(value = "/page")
    public @ResponseBody
    BaseMsgBean page(AdminPageReqBase req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("find page msg mid({}),token({})", req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getNum() || 0 >= req.getPage()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = downMsgService.findPageDownMsg(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Down msg fail.", e);
            msg.setCode(1);
            msg.setResult("获取推送消息失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }
   
}
