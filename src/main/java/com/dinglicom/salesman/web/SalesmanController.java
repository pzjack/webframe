/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.salesman.web;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.web.AppControllerBase;
import com.dinglicom.salesman.service.SalesmanService;
import com.dinglicom.salesman.domain.OrgSaleDetailSampleReq;
import com.dinglicom.salesman.domain.OrgSaleSampleReq;
import com.dinglicom.salesman.domain.ProductSaleSampleReq;
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
@RequestMapping(value = "/api/v1/sale")
public class SalesmanController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(SalesmanController.class);
    @Resource
    private SysTokenService sysTokenService;
    @Resource
    private SalesmanService salesmanService;

    /**
     * 业务员按照月、季度、年份 按照商品统计销售数量情况s
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/sampleproduct")
    public @ResponseBody
    BaseMsgBean querySaleByProduct(ProductSaleSampleReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo salesman = validateToken(sysTokenService, req, msg);
        if (null == salesman || 0 >= req.getUid() || 0 >= req.getNum() || 0 >= req.getPage() || null == req.getType()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = salesmanService.queryByProductsample(req, salesman);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Salesman sample produce list query fail.", e);
            msg.setCode(1);
            msg.setResult("查询失败");
            return msg;
        }
        return msg;
    }

    /**
     * 业务员按照月、季度、年份 按照奶站或者经销商统计销售数量情况s
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/sampleorg")
    public @ResponseBody
    BaseMsgBean querySaleByOrg(OrgSaleSampleReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo salesman = validateToken(sysTokenService, req, msg);
        if (null == salesman || 0 >= req.getUid() || 0 >= req.getNum() || 0 >= req.getPage() || null == req.getType() || null == req.getRole()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = salesmanService.queryByOrgsample(req, salesman);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Salesman sample org list query fail.", e);
            msg.setCode(1);
            msg.setResult("查询失败");
            return msg;
        }
        return msg;
    }

    /**
     * 业务员特定单位的标识 按照商品统计销售数量情况s
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/orgdetails")
    public @ResponseBody
    BaseMsgBean queryOrgDeatails(OrgSaleDetailSampleReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo salesman = validateToken(sysTokenService, req, msg);
        if (null == salesman || 0 >= req.getUid() || null == req.getType()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = salesmanService.queryByOrgDetailsample(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Salesman query org details fail.", e);
            msg.setCode(1);
            msg.setResult("查询失败");
            return msg;
        }
        return msg;
    }

    /**
     * 销售部门按照月、季度、年份 按照商品统计销售数量情况s
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/dep/sampleproduct")
    public @ResponseBody
    BaseMsgBean queryDepSaleByProduct(ProductSaleSampleReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo depman = validateToken(sysTokenService, req, msg);
        if (null == depman || 0 >= req.getUid() || null == req.getType()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = salesmanService.queryByDepProductsample(req, depman);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Department sample produce list query fail.", e);
            msg.setCode(1);
            msg.setResult("查询失败." + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 销售部门按照月、季度、年份 按照统计销售员数量情况s
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/dep/sampleman")
    public @ResponseBody
    BaseMsgBean queryDepSaleBySaleman(OrgSaleSampleReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo depman = validateToken(sysTokenService, req, msg);
        if (null == depman || 0 >= req.getUid() || null == req.getType()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = salesmanService.queryDepBySalesman(req, depman);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Department sample sale by salesman fail.", e);
            msg.setCode(1);
            msg.setResult("查询失败." + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 销售部门统计具体销售员的销售数量情况s
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/dep/samplemandetail")
    public @ResponseBody
    BaseMsgBean queryDepSaleBySaleman(OrgSaleDetailSampleReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo depman = validateToken(sysTokenService, req, msg);
        if (null == depman || 0 >= req.getUid() || null == req.getType()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = salesmanService.queryDepBySalemansample(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Department sample sale by salesman fail.", e);
            msg.setCode(1);
            msg.setResult("查询失败." + e.getMessage());
            return msg;
        }
        return msg;
    }
    

    /**
     * 销售总监统计所有产品的销售数量情况s
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/com/productsample")
    public @ResponseBody
    BaseMsgBean queryAllProductSample(ProductSaleSampleReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo depman = validateToken(sysTokenService, req, msg);
        if (null == depman || 0 >= req.getUid() || null == req.getType()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }

        try {
            msg = salesmanService.queryByAllProductsample(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Department sample sale by salesman fail.", e);
            msg.setCode(1);
            msg.setResult("查询失败." + e.getMessage());
            return msg;
        }
        return msg;
    }
    

    /**
     * 销售总监统计所有销售部门的销售数量情况s
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/com/managersample")
    public @ResponseBody
    BaseMsgBean querySaleManagerSample(OrgSaleSampleReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo depman = validateToken(sysTokenService, req, msg);
        if (null == depman || 0 >= req.getUid() || null == req.getType()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }

        try {
            msg = salesmanService.querySaleManagerSample(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Company sample sale manager fail.", e);
            msg.setCode(1);
            msg.setResult("查询失败." + e.getMessage());
            return msg;
        }
        return msg;
    }
    
    

    /**
     * 销售总监统计具体销售部门的产品销售数量情况s
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/com/managerdetail")
    public @ResponseBody
    BaseMsgBean querySaleManagerSampleDeatail(OrgSaleDetailSampleReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo depman = validateToken(sysTokenService, req, msg);
        if (null == depman || 0 >= req.getUid() || null == req.getType()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = salesmanService.querySaleManagerDetailSample(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Company sample sale by sale manage fail.", e);
            msg.setCode(1);
            msg.setResult("查询失败." + e.getMessage());
            return msg;
        }
        return msg;
    }
}
