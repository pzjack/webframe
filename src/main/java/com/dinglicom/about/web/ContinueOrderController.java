/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.about.web;

import com.dinglicom.about.domain.ContinueOrderReq;
import com.dinglicom.about.domain.ContinueOrderResp;
import com.dinglicom.about.entity.ContinueOrderPush;
import com.dinglicom.about.service.ContinueOrderService;
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
@RequestMapping(value = "/api/v1/ctnorder")
public class ContinueOrderController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(ContinueOrderController.class);
    @Resource
    protected SysTokenService sysTokenService;
    @Resource
    protected ContinueOrderService continueOrderService;

    /**
     * 保存提醒时间
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/save")
    public @ResponseBody
    BaseMsgBean save(ContinueOrderReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Continue order push save mid({}),token({}), days({})", req.getMid(), req.getToken(), req.getDays());
        if (null == admin || 0 >= req.getMid() || null == req.getDays()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            continueOrderService.save(req.getDays());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Continue order push save fail.", e);
            msg.setCode(1);
            msg.setResult("保存订单续订提醒时间失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 获取提醒时间
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/get")
    public @ResponseBody
    BaseMsgBean get(ContinueOrderReq req) {
        ContinueOrderResp msg = new ContinueOrderResp();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Continue order push get mid({}),token({}), days({})", req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            ContinueOrderPush cop = continueOrderService.get();
            if(null != cop) {
                msg.setDays(cop.getDays());
            }
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Continue order push get fail.", e);
            msg.setCode(1);
            msg.setResult("获取订单续订提醒时间失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }
}
