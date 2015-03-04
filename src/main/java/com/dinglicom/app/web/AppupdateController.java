/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.app.web;

import com.dinglicom.app.domain.AppupdateCheckReq;
import com.dinglicom.app.domain.AppupdateReq;
import com.dinglicom.app.domain.AppupdateResp;
import com.dinglicom.app.entity.AppUpdate;
import com.dinglicom.app.service.AppupdateService;
import com.dinglicom.frame.sys.domain.AdminReqBase;
import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.web.AppControllerBase;
import com.dinglicom.product.domain.ProductImgUploadResp;
import com.dinglicom.product.domain.ProductPicReq;
import java.io.File;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author panzhen
 */
@Controller
@RequestMapping(value = "/api/v1/sys/update")
public class AppupdateController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(AppupdateController.class);
    
    @Resource
    private SysTokenService sysTokenService;
    @Resource
    private AppupdateService appupdateService;
    
    
    @RequestMapping(value = "/create")
    public @ResponseBody
    BaseMsgBean create(AppupdateReq req) {
        AppupdateResp msg = new AppupdateResp();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Create app update ,mid({}),token({})", msg, req.getMid(), req.getToken());
        if (null == admin || 0 == req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            AppUpdate app = appupdateService.addAppupdate(req);
            if(null != app) {
                msg.setPid(app.getId());
            }
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Create app update fail:", e);
            msg.setCode(1);
            msg.setResult("保存失败");
            return msg;
        }
        return msg;
    }
    
    @RequestMapping(value = "/update")
    public @ResponseBody
    BaseMsgBean update(AppupdateReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Create app update ,mid({}),token({})", msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            appupdateService.updateAppupdate(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Create app update fail:", e);
            msg.setCode(1);
            msg.setResult("保存失败");
            return msg;
        }
        return msg;
    }
    
    @RequestMapping(value = "/delete")
    public @ResponseBody
    BaseMsgBean delete(AppupdateReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Create app update ,mid({}),token({})", msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getPid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            appupdateService.delete(req.getPid());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Create app update fail:", e);
            msg.setCode(1);
            msg.setResult("保存失败");
            return msg;
        }
        return msg;
    }
    
    @RequestMapping(value = "/list")
    public @ResponseBody
    BaseMsgBean list(AdminReqBase req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Create app update ,mid({}),token({})", msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = appupdateService.getAllAppupdate();
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Create app update fail:", e);
            msg.setCode(1);
            msg.setResult("保存失败");
            return msg;
        }
        return msg;
    }
    
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    BaseMsgBean uploadProductPic(HttpServletRequest request, ProductPicReq req) {
        ProductImgUploadResp msg = new ProductImgUploadResp();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        CommonsMultipartFile file = req.getFilename();
        LOG.info("File mid({}),token({}), file orginal name:{}", req.getMid(), req.getToken(), file.getOriginalFilename());
        if (!file.isEmpty()) {
            String url = getServerURL(request, "static/update/", file.getOriginalFilename());
            String path = request.getServletContext().getRealPath("/") + "static/update/" + file.getOriginalFilename();
            File localFile = new File(path);
            try {
                file.transferTo(localFile);
                msg.setUrl(url);
            } catch (IllegalStateException | IOException e) {
                LOG.warn("写文件失败", e);
                msg.setCode(1);
                msg.setResult(e.getMessage());
            }
        } else {
            msg.setCode(1);
            msg.setResult("没有上传文件数据！");
        }
        return msg;
    }
    
    
    
    @RequestMapping(value = "/check")
    public @ResponseBody
    BaseMsgBean check(AppupdateCheckReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        try {
            LOG.warn("Check app update current version:{}  type{}", req.getCurVersionCode(), req.getType());
            msg = appupdateService.checkVersion(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Check app update fail:", e);
            msg.setCode(1);
            msg.setResult("检查app更新失败:" + e.getMessage());
            return msg;
        }
        return msg;
    }

    private String getServerURL(HttpServletRequest request, String imgpath, String filename) {
        StringBuilder sb = new StringBuilder(request.getScheme());
        sb.append("://").append(request.getServerName());
        if (80 != request.getServerPort()) {
            sb.append(":").append(request.getServerPort());
        }
        sb.append(request.getContextPath()).append("/");
        if (null != imgpath) {
            sb.append(imgpath);
            if (!imgpath.endsWith("/")) {
                sb.append("/");
            }
            sb.append(filename);
        }
        return sb.toString();
    }
}
