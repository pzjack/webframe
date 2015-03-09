/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.about.web;

import com.dinglicom.about.domain.AboutusItemResp;
import com.dinglicom.about.domain.AboutusResp;
import com.dinglicom.about.domain.AboutusUpdate;
import com.dinglicom.about.entity.Aboutus;
import com.dinglicom.about.service.AboutusServcice;
import com.dinglicom.frame.sys.domain.AppRequestBase;
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
@RequestMapping(value = "/api/v1/about")
public class AboutusController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(AboutusController.class);
    @Resource
    private SysTokenService sysTokenService;
    @Resource
    private AboutusServcice aboutusServcice;

    @RequestMapping(value = "/get")
    public @ResponseBody
    AboutusResp get(AppRequestBase req) {
        AboutusResp msg = new AboutusResp();
        try {
            Aboutus aboutus = aboutusServcice.get();
            AboutusItemResp item;
            if (null != aboutus) {
                item = new AboutusItemResp(aboutus.getOrder_tel(), aboutus.getComplaint_tel(), aboutus.getOfficial_site(), aboutus.getOrder_site(), aboutus.getWechat(), aboutus.getEmail(), aboutus.getAddress());
            } else {
                item = new AboutusItemResp();
            }
            msg.setData(item);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("worker current day task page", e);
            msg.setCode(1);
            msg.setResult("查询失败");
            return msg;
        }
        return msg;
    }

    @RequestMapping(value = "/update")
    public @ResponseBody
    BaseMsgBean update(AboutusUpdate req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, req, msg);
        LOG.info("Update about us,uid({}),token({})", req.getUid(), req.getToken());
        if (null == admin || 0 == req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            Aboutus aboutus = aboutusServcice.get();
            if (null != aboutus) {
                setAboutFromReq(aboutus, req);
                aboutusServcice.update(aboutus);
            } else {
                aboutus = new Aboutus();
                setAboutFromReq(aboutus, req);
                aboutusServcice.save(aboutus);
            }
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("worker current day task page", e);
            msg.setCode(1);
            msg.setResult("保存失败");
            return msg;
        }
        return msg;
    }

    private void setAboutFromReq(Aboutus aboutus, AboutusUpdate req) {
        if (null != req.getOrder_tel()) {
            aboutus.setOrder_tel(req.getOrder_tel());
        }
        if (null != req.getComplaint_tel()) {
            aboutus.setComplaint_tel(req.getComplaint_tel());
        }
        if (null != req.getOrder_site()) {
            aboutus.setOrder_site(req.getOrder_site());
        }
        if (null != req.getAddress()) {
            aboutus.setAddress(req.getAddress());
        }
        if (null != req.getEmail()) {
            aboutus.setEmail(req.getEmail());
        }
        if (null != req.getOfficial_site()) {
            aboutus.setOfficial_site(req.getOfficial_site());
        }
        if (null != req.getWechat()) {
            aboutus.setWechat(req.getWechat());
        }
    }
}
