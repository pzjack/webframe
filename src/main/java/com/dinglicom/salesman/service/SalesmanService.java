/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.salesman.service;

import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.salesman.domain.DepsaleResp;
import com.dinglicom.salesman.domain.OrgSaleDetailSampleReq;
import com.dinglicom.salesman.domain.OrgSaleSampleReq;
import com.dinglicom.salesman.domain.OrgSaleSampleResp;
import com.dinglicom.salesman.domain.ProductSaleSampleReq;
import com.dinglicom.salesman.domain.ProductSaleSampleResp;

/**
 *
 * @author panzhen
 */
public interface SalesmanService {

    /**
     * 销售员各个商品的销售情况
     * @param req
     * @param salesman
     * @return 
     */
    ProductSaleSampleResp queryByProductsample(ProductSaleSampleReq req, UserInfo salesman);

    /**
     * 业务员按照奶站/经销商统计
     * @param req
     * @param salesman
     * @return 
     */
    OrgSaleSampleResp queryByOrgsample(OrgSaleSampleReq req, UserInfo salesman);

    /**
     * 业务员查看具体奶站/经销商产品销售情况
     * @param req
     * @return 
     */
    ProductSaleSampleResp queryByOrgDetailsample(OrgSaleDetailSampleReq req);

    /**
     * 销售部门按照产品统计
     * @param req
     * @param depAdmin
     * @return 
     */
    ProductSaleSampleResp queryByDepProductsample(ProductSaleSampleReq req, UserInfo depAdmin);
    
    /**
     * 销售部分按照销售员销售业绩统计
     * @param req
     * @param depman
     * @return 
     */
    DepsaleResp queryDepBySalesman(OrgSaleSampleReq req, UserInfo depman);
    
    /**
     * 具体销售员销售量统计
     * @param req
     * @return 
     */
    ProductSaleSampleResp queryDepBySalemansample(OrgSaleDetailSampleReq req);
    
    /**
     * 公司所有商品分月、季度、年度的销量统计
     * @param req
     * @return 
     */
    ProductSaleSampleResp queryByAllProductsample(ProductSaleSampleReq req);
    
    /**
     * 按照销售部门统计销量情况
     * @param req
     * @return 
     */
    DepsaleResp querySaleManagerSample(OrgSaleSampleReq req);
    
    /**
     * 销售部门各产品销售情况
     * @param req
     * @return 
     */
    ProductSaleSampleResp querySaleManagerDetailSample(OrgSaleDetailSampleReq req);
}
