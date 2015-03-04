/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.product.web;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.domain.AppPageReqBase;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.GeneratorUUID;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.web.AppControllerBase;
import com.dinglicom.product.domain.AddProductReq;
import com.dinglicom.product.domain.AddProductResp;
import com.dinglicom.product.domain.AppProductDetail;
import com.dinglicom.product.domain.AppProductDetailReq;
import com.dinglicom.product.domain.AppUserProduct;
import com.dinglicom.product.domain.PageUserProductResult;
import com.dinglicom.product.domain.ProductImgUploadResp;
import com.dinglicom.product.domain.ProductPicReq;
import com.dinglicom.product.domain.WebProductDetailResp;
import com.dinglicom.product.domain.WebProductPageResp;
import com.dinglicom.product.domain.WebUserProduct;
import com.dinglicom.product.entity.UserProduct;
import com.dinglicom.product.service.UserProductService;
import java.io.File;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 商品信息信息web接口
 *
 * @author panzhen
 */
@Controller
@RequestMapping(value = "/api/v1/product")
public class UserProductController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(UserProductController.class);
    @Resource
    private SysTokenService sysTokenService;
    @Resource
    private UserProductService userProductService;

    /**
     * 商品信息保存
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/save")
    public @ResponseBody
    BaseMsgBean save(AddProductReq req) {
        AddProductResp msg = new AddProductResp();
        LOG.info("Mid:{},Token:{},create product :{}", req.getMid(), req.getToken(), req.getProduct_name());
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || null == req.getProduct_name() || 0 >= req.getPrice()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            UserProduct product = new UserProduct();
            copyProperty(req, product);
            product = userProductService.save(product);
            msg.setProduct_id(product.getId());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("save produce msg", e);
            msg.setCode(1);
            msg.setResult("保存失败");
            return msg;
        }
        return msg;
    }

    /**
     * 商品信息修改
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/update")
    public @ResponseBody
    BaseMsgBean update(AddProductReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Mid:{},Token:{},update product :{}", req.getMid(), req.getToken(), req.getProduct_id());
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getProduct_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        UserProduct product = userProductService.read(req.getProduct_id());
        if (null == product) {
            msg.setCode(1);
            msg.setResult("未找到需要修改的商品");
            return msg;
        }
        try {
            copyProperty(req, product);
            userProductService.update(product);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("update produce msg", e);
            msg.setCode(1);
            msg.setResult("保存失败");
            return msg;
        }
        return msg;
    }

    /**
     * 商品信息删除
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/delete")
    public @ResponseBody
    BaseMsgBean delete(AddProductReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Mid:{},Token:{},delete product :{}", req.getMid(), req.getToken(), req.getProduct_id());
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getProduct_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        UserProduct product = userProductService.read(req.getProduct_id());
        if (null == product) {
            msg.setCode(1);
            msg.setResult("未找到需要删除的商品");
            return msg;
        }
        product.setSignDelete(Boolean.TRUE);
        try {
            userProductService.update(product);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("delete produce msg", e);
            msg.setCode(1);
            msg.setResult("删除失败");
            return msg;
        }
        return msg;
    }

    private void copyProperty(AddProductReq src, UserProduct dest) {
        if (null != src.getProduct_name()) {
            dest.setName(src.getProduct_name());
        }
        if (null != src.getBig_pic()) {
            dest.setBigPic(src.getBig_pic());
        }
        if (null != src.getSmall_pic()) {
            dest.setSmallPic(src.getSmall_pic());
        }
        if (null != src.getShort_desc()) {
            dest.setShortDesc(src.getShort_desc());
        }
        if (null != src.getBrand()) {
            dest.setBrand(src.getBrand());
        }
        if (null != src.getModel()) {
            dest.setModel(src.getModel());
        }
        if (0 < src.getWeight()) {
            dest.setWeight(src.getWeight());
        }
        if (0 < src.getPrice()) {
            dest.setPrice(src.getPrice());
        }
        if (0 < src.getHome_price()) {
            dest.setHomePrice(src.getHome_price());
        }
        if(null != src.getType_desc()) {
            dest.setProducttype(src.getType_desc());
        }
        if(null != src.getView_range()) {
            dest.setViewrange(src.getView_range());
        }
        if(null != src.getShort_name()) {
            dest.setShortname(src.getShort_name());
        }
    }

    /**
     * app分页请求商品信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/page")
    public @ResponseBody
    PageUserProductResult findByPage(AppPageReqBase req) {
        PageUserProductResult msg = new PageUserProductResult();
        if(0 >= req.getNum() || 0 >= req.getPage()) {
            msg.setCode(1);
            msg.setResult("请求消息无效");
            return msg;
        }
        try {
            Page<AppUserProduct> page = userProductService.findByDefinePage(req.getPage(), req.getNum());
            msg.setData(page.getContent());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("get produce msg page", e);
            msg.setCode(1);
            msg.setResult("查询失败");
            return msg;
        }
        return msg;
    }

    /**
     * app分页请求商品信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/webpage")
    public @ResponseBody
    WebProductPageResp findAllByPage(AppPageReqBase req) {
        WebProductPageResp msg = new WebProductPageResp();
        if(0 >= req.getNum() || 0 >= req.getPage()) {
            msg.setCode(1);
            msg.setResult("请求消息无效");
            return msg;
        }
        try {
            Page<WebUserProduct> page = userProductService.findByAllPage(req.getPage(), req.getNum());
            msg.setData(page.getContent());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("get produce msg page", e);
            msg.setCode(1);
            msg.setResult("查询失败");
            return msg;
        }
        return msg;
    }

    /**
     * app商品详情请求应答
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/detail")
    public @ResponseBody
    AppProductDetail findById(AppProductDetailReq req) {
        AppProductDetail msg = new AppProductDetail();
        if(0 >= req.getProduct_id()) {
            msg.setCode(1);
            msg.setResult("请求消息无效");
        }
        try {
            msg = userProductService.getAppDetail(req.getProduct_id());
        } catch (Exception e) {
            LOG.warn("get produce detail msg", e);
            msg.setCode(1);
            msg.setResult("查询失败");
            return msg;
        }
        return msg;
    }

    /**
     * web商品详情请求应答
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/webdetail")
    public @ResponseBody
    WebProductDetailResp findByIdWeb(AppProductDetailReq req) {
        WebProductDetailResp msg = new WebProductDetailResp();
        if(0 >= req.getProduct_id()) {
            msg.setCode(1);
            msg.setResult("请求消息无效");
        }
        try {
            msg = userProductService.getAppDetailWeb(req.getProduct_id());
        } catch (Exception e) {
            LOG.warn("get produce detail msg", e);
            msg.setCode(1);
            msg.setResult("查询失败");
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
            String filename = GeneratorUUID.getRadomUUID() + getPicExtFilename(file.getOriginalFilename());
            String url = getServerURL(request, "static/img/", filename);
            String path = request.getServletContext().getRealPath("/") + "static/img/" + filename;
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

    private String getPicExtFilename(String filename) {
        String ext = "";
        int p = filename.lastIndexOf(".");
        if (p > 0) {
            ext = filename.substring(p);
        }
        return ext;
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
