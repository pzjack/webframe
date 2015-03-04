/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.dep.web;

import com.dinglicom.dep.domain.CompanyReq;
import com.dinglicom.dep.domain.DepReq;
import com.dinglicom.dep.domain.DepaddResp;
import com.dinglicom.dep.service.DepService;
import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.SysOranizagion;
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
@RequestMapping(value = "/api/v1/dep")
public class DepartmentController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(DepartmentController.class);
    @Resource
    protected SysTokenService sysTokenService;
    @Resource
    private DepService depService;

    @RequestMapping(value = "/addcom")
    public @ResponseBody
    BaseMsgBean addCompany(CompanyReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Add company,mid({}),token({})", req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || null == req.getCompany()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            depService.addCompany(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Add company fail.", e);
            msg.setCode(1);
            msg.setResult("添加公司信息失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }

    @RequestMapping(value = "/updatecom")
    public @ResponseBody
    BaseMsgBean updateCompany(CompanyReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Update company,mid({}),token({})", req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            depService.updateCompany(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Update company fail.", e);
            msg.setCode(1);
            msg.setResult("修改公司信息失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }

    @RequestMapping(value = "/adddep")
    public @ResponseBody
    BaseMsgBean addDep(DepReq req) {
        DepaddResp msg = new DepaddResp();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Add department,mid({}),token({})", req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || null == req.getDepartment()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            SysOranizagion dep = depService.addDep(req);
            if(null != dep) {
                msg.setDid(dep.getId());
            }
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Add department fail.", e);
            msg.setCode(1);
            msg.setResult("添加部门信息失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }

    @RequestMapping(value = "/updatedep")
    public @ResponseBody
    BaseMsgBean updateDep(DepReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Update department,mid({}),token({})", req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            depService.updateDep(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Update department fail.", e);
            msg.setCode(1);
            msg.setResult("修改部门信息失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }

    @RequestMapping(value = "deletedep")
    public @ResponseBody
    BaseMsgBean deleteDep(DepReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Delete department,mid({}),token({})", req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            depService.deleteDep(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Delete department fail.", e);
            msg.setCode(1);
            msg.setResult("删除部门信息失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }

    @RequestMapping(value = "deplist")
    public @ResponseBody
    BaseMsgBean getAllDepList(DepReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Get all department,mid({}),token({})", req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = depService.getDepList();
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Get all  fail.", e);
            msg.setCode(1);
            msg.setResult("删除部门信息失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }

    @RequestMapping(value = "depdetail")
    public @ResponseBody
    BaseMsgBean getDepDetail(DepReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Get department detail ,mid({}),token({}), dep id({})", req.getMid(), req.getToken(), req.getDid());
        if (null == admin || 0 >= req.getMid() || null == req.getDid() || 0 >= req.getDid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = depService.getDepDetail(req.getDid());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Get all  fail.", e);
            msg.setCode(1);
            msg.setResult("获取部门详细信息失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }

    @RequestMapping(value = "comdetail")
    public @ResponseBody
    BaseMsgBean getComDetail(DepReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Get company detail ,mid({}),token({}))", req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = depService.getCompanyDetail();
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Get all  fail.", e);
            msg.setCode(1);
            msg.setResult("获取公司信息失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }
}
