/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.salesample.web;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.web.AppControllerBase;
import com.dinglicom.salesample.domain.WebProductSaleReq;
import com.dinglicom.salesample.domain.WebRoleuserProductReq;
import com.dinglicom.salesample.domain.WebSaleSampleReq;
import com.dinglicom.salesample.service.WebSaleSampleService;
import com.dinglicom.salesman.web.SalesmanController;
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
@RequestMapping(value = "/api/v1/websample")
public class SaleSampleController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(SalesmanController.class);
    @Resource
    private SysTokenService sysTokenService;
    @Resource
    private WebSaleSampleService webSaleSampleService;

    /**
     * 按照月、季度、年份 统计商品销售数量情况
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/product")
    public @ResponseBody
    BaseMsgBean sampleProduct(WebProductSaleReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Sample product sale count mid({}),token({})", req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || null == req.getType()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = webSaleSampleService.queryByAllProductsample(req, admin);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Sample product sale count query fail.", e);
            msg.setCode(1);
            msg.setResult("商品销量查询统计失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 按照不同的角色 月、季度、年份 统计商品销售数量情况
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/role")
    public @ResponseBody
    BaseMsgBean sampleRole(WebSaleSampleReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Sample role sale count  role({}), type({}), query({})",  req.getRole(), req.getType(), req.getQuery());
        if (null == admin || 0 >= req.getMid() || null == req.getType() || null == req.getRole() || 0 >= req.getPage() || 0 >= req.getNum()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = webSaleSampleService.queryByRolesample(req, admin);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Sample role sale count query fail.", e);
            msg.setCode(1);
            msg.setResult("按照角色的时间查询统计失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 按照月、季度、年份 统计不同的角色商品销售详细数量情况
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/roledetail")
    public @ResponseBody
    BaseMsgBean sampleRoleDetail(WebRoleuserProductReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Sample role sale detail mid({}),token({}),uid({})", req.getMid(), req.getToken(), req.getUid());
        if (null == admin || 0 >= req.getMid() || null == req.getType() || 0 >= req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = webSaleSampleService.queryByRoledetail(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Sample role sale detail query fail.", e);
            msg.setCode(1);
            msg.setResult("按照角色的时间查询统计失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 按照月、季度、年份 统计不同的角色商品销售详细数量情况
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/rolequery")
    public @ResponseBody
    BaseMsgBean sampleRoleQuery(WebSaleSampleReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Sample role query field mid({}),token({})", req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || null == req.getType() || null == req.getQuery() || null == req.getRole()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            req.setPage(1);
            req.setNum(20);
            msg = webSaleSampleService.queryByQueryfieldsample(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Sample role query field query fail.", e);
            msg.setCode(1);
            msg.setResult("按照关键字查询统计失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }
}
