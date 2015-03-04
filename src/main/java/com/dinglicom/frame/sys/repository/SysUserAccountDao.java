/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.repository;

import com.dinglicom.frame.sys.entity.SysRole;
import com.dinglicom.frame.sys.entity.SysUserAccount;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
//@CacheConfig(cacheNames = {"authorizationCache", "SysUser"})
public interface SysUserAccountDao extends CrudRepository<SysUserAccount, Long> {

//    @Cacheable(value="authorizationCache", key="#account")
//    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
//    @CachePut(value = {"authorizationCache", "SysUser"},key = "#account")
    SysUserAccount findByAccount(String account);
    
    @Modifying
    @Query("delete from SysUserAccount a where a.account = :account")
    void deleteByAccount(@Param("account")String account);
    
    @Query("from SysUserAccount a where a.userInfo.id = :userInfoId")
    List<SysUserAccount> findByUserInfoId(@Param("userInfoId")Long userInfoId);
    
//    @Query("select r from SysRole r join r.sysUserAccounts a where a.id = :id")
    @Query("select distinct roles from SysUserAccount a where a = :sysUserAccount")
    Collection<SysRole> findSysUserAccountRolesBySysUserAccount(@Param("sysUserAccount")SysUserAccount sysUserAccount);
    
    @Query("from SysUserAccount a where a.userInfo.userType=:usertype and a.userInfo.org.id=:orgid and a.userInfo.signDelete=:signDelete")
    List<SysUserAccount> findByUsertypeAndUserorgid(@Param("orgid")Long orgid, @Param("usertype")String usertype, @Param(value = "signDelete") boolean signDelete);
}
