/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.service;

import com.dinglicom.frame.sys.entity.SysToken;
import com.dinglicom.frame.sys.entity.SysUserAccount;
import com.dinglicom.frame.sys.entity.UserInfo;
import java.util.List;

/**
 *
 * @author panzhen
 */
public interface SysTokenService {

    SysToken createToken(SysUserAccount sysUserAccount, String token);

    SysToken findToken(String token);

    boolean findValidToken(String token, long uid);

    UserInfo findValidTokenAndUserInfo(String token, long uid);

    SysUserAccount findValidTokenAndSysUserAccount(String token, long uid);

    SysToken save(SysToken sysToken);

    void deleteByBegintime(long begintime);
    
    void deleteByAccountId(long accountid);

    List<SysToken> findByUserid(long userid);

    void delete(SysToken systoken);

    void delete(List<SysToken> systokens);

    void deleteByUserid(long userid);
    
    /**
     * 查找登录情况
     * @param account
     * @return 
     */
    int findByAccout(String account);
}
