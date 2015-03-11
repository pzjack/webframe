/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.product.repository;

import com.dinglicom.product.domain.AppUserProduct;
import com.dinglicom.product.entity.UserProduct;
import com.dinglicom.product.domain.ProductItem;
import com.dinglicom.product.domain.WebUserProduct;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface UserProductDao extends PagingAndSortingRepository<UserProduct, Long>, JpaSpecificationExecutor<UserProduct> {
    //, homePrice
    @Query("select new com.dinglicom.product.domain.AppUserProduct(id, name, smallPic, bigPic, weight, price) from UserProduct a where a.viewrange=:viewrange and a.signDelete=:signDelete")
    Page<AppUserProduct> findBySignDelete(Pageable page, @Param(value="viewrange")int viewrange, @Param(value="signDelete")Boolean signDelete);
    
     @Query("select new com.dinglicom.product.domain.WebUserProduct(id, name, shortname, smallPic, bigPic, weight, price, producttype, viewrange) from UserProduct a where a.signDelete=:signDelete")
    Page<WebUserProduct> findByNotDelete(Pageable page,  @Param(value="signDelete")Boolean signDelete);
    
    
    @Query("select new com.dinglicom.product.domain.ProductItem(id, shortname) from UserProduct a where a.signDelete=:signDelete")
    List<ProductItem> findByAllProduct(@Param(value="signDelete")Boolean signDelete);
    
    
    @Query("from UserProduct a where a.signDelete=:signDelete")
    List<UserProduct> findByAll(@Param(value="signDelete")Boolean signDelete);
}
