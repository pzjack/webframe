/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.repository;

import com.dinglicom.frame.sys.entity.SysToken;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface SysTokenDao extends CrudRepository<SysToken, Long> {

    /**
     * 根据token值查询token对象
     * @param token
     * @return 
     */
    public SysToken findByToken(String token);

    @Query("from SysToken a where a.token = :token and a.userInfo.id=:userInfoId")
    public SysToken findByTokenAndUserInfoId(@Param("token")String token, @Param("userInfoId")long userInfoId);
    
    /**
     * 删除小于规定时间的所有数据
     * @param begintime 
     */
    @Modifying
//    @Query("delete SysToken a where a.begintime <= :begintime")//@Param("begintime")
    public void deleteByBegintimeLessThan(long begintime);

    @Modifying
    @Query("delete SysToken a where a.sysUserAccount.id = :accountid")//@Param("begintime")
    public void deleteByAllAccountId(@Param("accountid")long accountid);
    
    @Query("from SysToken a where a.userInfo.id=:userInfoId")
    public List<SysToken> findByUserInfoId(@Param("userInfoId")long userInfoId);
    
    
    @Query("from SysToken a where a.sysUserAccount.account=:account order by a.begintime desc")
    public List<SysToken> findByAccount(@Param("account")String account);
}
