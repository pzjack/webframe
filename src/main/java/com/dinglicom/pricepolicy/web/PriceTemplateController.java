/*
 * Copyright 2015 Jack.Alexander
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dinglicom.pricepolicy.web;

import com.dinglicom.frame.sys.domain.AdminReqBase;
import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.sys.service.UserInfoService;
import com.dinglicom.frame.web.AppControllerBase;
import com.dinglicom.pricepolicy.demain.OrgDealarReq;
import com.dinglicom.pricepolicy.demain.PriceDetailReq;
import com.dinglicom.pricepolicy.demain.PricePolicyHistoryReq;
import com.dinglicom.pricepolicy.demain.PriceTemplateApplyReq;
import com.dinglicom.pricepolicy.demain.PriceTemplateReq;
import com.dinglicom.pricepolicy.demain.PriceTemplateSaveResp;
import com.dinglicom.pricepolicy.demain.PriceTemplateUpdateReq;
import com.dinglicom.pricepolicy.entity.PriceTemplate;
import com.dinglicom.pricepolicy.service.PriceTemplateService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author panzhen
 */
@Controller
@RequestMapping(value = "/api/v1/pricepolicy")
public class PriceTemplateController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(PriceTemplateController.class);

    @Resource
    private SysTokenService sysTokenService;
    @Resource
    private PriceTemplateService priceTemplateService;

    /**
     * 保存价格模板
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/create")
    public @ResponseBody
    BaseMsgBean save(PriceTemplateUpdateReq req) {
        PriceTemplateSaveResp msg = new PriceTemplateSaveResp();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || null == req.getName() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType())) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("Save pricetemplate ,mid({}),token({}), name({})", req.getMid(), req.getToken(), req.getName());
        try {
            PriceTemplate t = priceTemplateService.save(req, admin);
            if (null != t) {
                msg.setId(t.getId());
            }
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Save pricetemplate fail:", e);
            msg.setCode(1);
            msg.setResult("保存价格模板失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }
    
    /**
     * 修改价格模板
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/update")
    public @ResponseBody
    BaseMsgBean update(PriceTemplateUpdateReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || null == req.getName() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType())) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("Update pricetemplate ,mid({}),token({}),id({}), name({})", req.getMid(), req.getToken(), req.getId(), req.getName());
        try {
            priceTemplateService.update(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Update pricetemplate fail:", e);
            msg.setCode(1);
            msg.setResult("修改价格模板失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 删除价格模板
     * @param req
     * @return
     */
    @RequestMapping(value = "/delete")
    public @ResponseBody
    BaseMsgBean delete(PriceTemplateUpdateReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || null == req.getId() || 0 >= req.getId() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType())) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("Delete pricetemplate ,mid({}),token({}),id({})", req.getMid(), req.getToken(), req.getId());
        try {
            priceTemplateService.delete(req.getId(), admin);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Delete pricetemplate fail:", e);
            msg.setCode(1);
            msg.setResult("删除价格模板失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 获取价格模板信息
     * @param req
     * @return
     */
    @RequestMapping(value = "/get")
    public @ResponseBody
    BaseMsgBean get(PriceTemplateUpdateReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || null == req.getId() || 0 >= req.getId() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType())) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("Get pricetemplate ,mid({}),token({}),id({})", req.getMid(), req.getToken(), req.getId());
        try {
            msg = priceTemplateService.get(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Get pricetemplate fail:", e);
            msg.setCode(1);
            msg.setResult("获取价格模板失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 保存或修改价格模板明细
     * @param req
     * @return
     */
    @RequestMapping(value = "/savedetail")
    public @ResponseBody
    BaseMsgBean saveDetails(@RequestBody PriceDetailReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getTid() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType()) || null == req.getData() || req.getData().size() <= 0) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("Add price template details,mid({}),token({}),tid({})", req.getMid(), req.getToken(), req.getTid());
        try {
            priceTemplateService.saveDetails(req, admin);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Get pricetemplate fail:", e);
            msg.setCode(1);
            msg.setResult("保存价格模板明细数据失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }
    
    /**
     * 删除价格模板明细
     * @param req
     * @return
     */
    @RequestMapping(value = "/deletedetail")
    public @ResponseBody
    BaseMsgBean deleteDetail(PriceTemplateUpdateReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || null == req.getId() || 0 >= req.getId() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType())) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("Delete price template detail,mid({}),token({}),id({})", req.getMid(), req.getToken(), req.getId());
        try {
            priceTemplateService.deleteDetail(req.getId(), admin);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Delete price template detail fail:", e);
            msg.setCode(1);
            msg.setResult("删除价格模板明细失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }
    
    /**
     * 获取价格模板明细
     * @param req
     * @return
     */
    @RequestMapping(value = "/getdetails")
    public @ResponseBody
    BaseMsgBean getDetails(PriceTemplateUpdateReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || null == req.getId() || 0 >= req.getId() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType())) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("Find price template detail by id,mid({}),token({}),id({})", req.getMid(), req.getToken(), req.getId());
        try {
            msg = priceTemplateService.findDetalsByTemplateId(req.getId());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Find price template detail by id fail:", e);
            msg.setCode(1);
            msg.setResult("获取价格模板明细失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }
    
    /**
     * 查询价格模板列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/query")
    public @ResponseBody
    BaseMsgBean query(PriceTemplateReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getNum() || 0 >=  req.getPage() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType())) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("Find price template list by id or name,mid({}),token({}),id({}), name({})", req.getMid(), req.getToken(), req.getId(), req.getTname());
        try {
            msg = priceTemplateService.findTemplate(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Find price template by id or name fail:", e);
            msg.setCode(1);
            msg.setResult("查询价格模板失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }
    
    /**
     * 获取所有商品列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/products")
    public @ResponseBody
    BaseMsgBean products(AdminReqBase req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType())) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("Find all product,mid({}),token({})", req.getMid(), req.getToken());
        try {
            msg = priceTemplateService.getAllProducts();
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Find all product fail:", e);
            msg.setCode(1);
            msg.setResult("查找商品信息失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }
    
    /**
     * 查找经销商和无经销商奶站
     * @param req
     * @return
     */
    @RequestMapping(value = "/dealar")
    public @ResponseBody
    BaseMsgBean dealar(OrgDealarReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType())) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("Find all dealar,mid({}),token({}), query({})", req.getMid(), req.getToken(), req.getQuery());
        try {
            msg = priceTemplateService.getAllDealar(req.getQuery());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Find all dealar fail:", e);
            msg.setCode(1);
            msg.setResult("查找经销商信息失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }
    
    /**
     * 应用价格模板到所有经销商
     * @param req
     * @return
     */
    @RequestMapping(value = "/applyall")
    public @ResponseBody
    BaseMsgBean applyall(PriceTemplateApplyReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || null == req.getId() || 0 >= req.getId() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType())) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("Apply price template to all dealar,mid({}),token({}), tid({})", req.getMid(), req.getToken(), req.getId());
        try {
            priceTemplateService.doApplyDealarAll(req.getId(), admin);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Apply price template to all dealar fail:", e);
            msg.setCode(1);
            msg.setResult("应用价格模板到所有经销商失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }
    
    /**
     * 应用价格模板到经销商
     * @param req
     * @return
     */
    @RequestMapping(value = "/apply")
    public @ResponseBody
    BaseMsgBean apply(PriceTemplateApplyReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || null == req.getId() || 0 >= req.getId() || null == req.getIds() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType())) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("Apply price template to all dealar,mid({}),token({}), tid({}), dealars({})", req.getMid(), req.getToken(), req.getId(), req.getIds());
        try {
            priceTemplateService.doApplyDealar(req.getId(), req.getIds(), admin);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Apply price template to dealar fail:", e);
            msg.setCode(1);
            msg.setResult("应用价格模板到经销商失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }
    
    /**
     * 使已经应用的价格模板失效
     * @param req
     * @return
     */
    @RequestMapping(value = "/disable")
    public @ResponseBody
    BaseMsgBean disable(PriceTemplateApplyReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || null == req.getId() || 0 >= req.getId() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType())) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("Disable price template,mid({}),token({}), tid({})", req.getMid(), req.getToken(), req.getId());
        try {
            priceTemplateService.doDisablePriceTemplate(req.getId());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Disable price template fail:", e);
            msg.setCode(1);
            msg.setResult("价格模板失效失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }
    
    /**
     * 查询价格策略历史列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/historyquery")
    public @ResponseBody
    BaseMsgBean queryHistory(PricePolicyHistoryReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getNum() || 0 >=  req.getPage() || !UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType())) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        LOG.info("Find price policy history list by id or query,mid({}),token({}),id({}), query({})", req.getMid(), req.getToken(), req.getId(), req.getQuery());
        try {
            msg = priceTemplateService.findPricePolicyHistory(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Find price policy history by id or query fail:", e);
            msg.setCode(1);
            msg.setResult("查询价格策略历史记录失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }
}
