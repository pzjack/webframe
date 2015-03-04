/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.repository;

import com.dinglicom.order.entity.UserAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * 用户地址DAO
 * @author panzhen
 */
public interface UserAddressDao  extends PagingAndSortingRepository<UserAddress, Long>, JpaSpecificationExecutor<UserAddress>  {
    @Query("from UserAddress a where a.signDelete=:signDelete")
    Page<UserAddress> findBySignDelete(Pageable page, @Param(value="signDelete")Boolean signDelete);
    @Query("from UserAddress a where a.signDelete=:signDelete and a.address like :address")
    Page<UserAddress> findByAddress(Pageable page, @Param(value="signDelete")Boolean signDelete, @Param(value="address")String address);
    @Query("from UserAddress a where a.signDelete=:signDelete and a.mobilePhone like :mobilePhone")
    Page<UserAddress> findByMobilePhone(Pageable page, @Param(value="signDelete")Boolean signDelete, @Param(value="mobilePhone")String mobilePhone);
    @Query("from UserAddress a where a.signDelete=:signDelete and a.userInfo.id = :userInfoId")
    Page<UserAddress> findByUserInfoId(Pageable page, @Param(value="signDelete")Boolean signDelete, @Param(value="userInfoId")long userInfoId);
    
}
