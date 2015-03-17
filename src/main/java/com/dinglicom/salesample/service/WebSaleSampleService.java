/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.salesample.service;

import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.salesample.domain.WebProductSaleReq;
import com.dinglicom.salesample.domain.WebProductSaleResp;
import com.dinglicom.salesample.domain.WebRoleuserProductReq;
import com.dinglicom.salesample.domain.WebSaleSampleQueryResp;
import com.dinglicom.salesample.domain.WebSaleSampleReq;
import com.dinglicom.salesample.domain.WebSaleSampleResp;

/**
 *
 * @author panzhen
 */
public interface WebSaleSampleService {

    /**
     * 管理端按照时间范围统计所有商品销售情况
     * @param req
     * @param user
     * @return 
     */
    WebProductSaleResp queryByAllProductsample(WebProductSaleReq req, UserInfo user);
    
    /**
     * web管理端按照角色和时间统计各个角色的销售排名情况
     * @param req
     * @param user
     * @return 
     */
    WebSaleSampleResp queryByRolesample(WebSaleSampleReq req, UserInfo user);
    
    /**
     * web管理端按照角色和时间段统计商品的销量情况
     * @param req
     * @return 
     */
    WebProductSaleResp queryByRoledetail(WebRoleuserProductReq req);
    
    /**
     * web管理端更加用户姓名和电话信息查询统计符合条件用户角色销售总量
     * @param req
     * @return 
     */
    WebSaleSampleQueryResp queryByQueryfieldsample(WebSaleSampleReq req);
}
