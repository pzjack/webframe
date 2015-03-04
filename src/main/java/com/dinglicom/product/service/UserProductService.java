/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.product.service;

import com.dinglicom.product.domain.AppProductDetail;
import com.dinglicom.product.domain.AppUserProduct;
import com.dinglicom.product.domain.PageUserProductResult;
import com.dinglicom.product.domain.ProductItem;
import com.dinglicom.product.domain.WebProductDetailResp;
import com.dinglicom.product.domain.WebUserProduct;
import com.dinglicom.product.entity.UserProduct;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author panzhen
 */
public interface UserProductService {
    public final static int P_VIEW_ALL = 1;
    public final static int P_VIEW_REPORT = 5;
    public final static int P_TYPE_GERERAL = 1;
    public final static int P_TYPE_LOW = 5;
    UserProduct read(long id);
    UserProduct update(UserProduct userProduct);
    UserProduct save(UserProduct userProduct);
    Page<AppUserProduct> findByDefinePage(int page, int size);
//    Page<UserProduct> findByPage(int pagesize, int pagenumber);
//    Iterable<UserProduct> findByPage();
    
    List<AppUserProduct> coverAppOut(Page<UserProduct> page);
    
    /**
     * get 商品详情 By App
     * @param id
     * @return 
     */
    AppProductDetail getAppDetail(long id);
    /**
     * 获取商品详情by web
     * @param id
     * @return 
     */
    WebProductDetailResp getAppDetailWeb(long id);
    
    List<ProductItem> findAllProduct();
    
    List<UserProduct> getAll();
    
    Page<WebUserProduct> findByAllPage(int page, int size);
}
