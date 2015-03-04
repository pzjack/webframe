/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.service;

import com.dinglicom.frame.sys.domain.AccountInfoBean;
import com.dinglicom.frame.sys.domain.AppAccountBean;
import com.dinglicom.frame.sys.domain.UserInfoUpdateReq;
import com.dinglicom.frame.sys.domain.WorkerCustomAddBean;
import com.dinglicom.frame.sys.entity.SysRole;
import com.dinglicom.frame.sys.entity.SysToken;
import com.dinglicom.frame.sys.entity.SysUserAccount;
import com.dinglicom.frame.sys.entity.UserInfo;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 *
 * @author panzhen
 */
public interface SysUserAccountService {
    
    final static String NORMAL = "00";//正常
    final static String FREEZE = "10";//冻结
    final static String DELETE = "30";//删除
    SysUserAccount findByAccount(String account);
    
    SysUserAccount save(SysUserAccount sysUserAccount);
    
    void delete(SysUserAccount sysUserAccount);
    
    void delete(List<SysUserAccount> sysUserAccounts);
    
    Collection<SysRole> findRoles(SysUserAccount sysUserAccount);
    
    Set<String> getRoleString( Collection<SysRole> sysUserAccountRoles);
    
    AccountInfoBean findUserLongInfo(String account);
    
    SysToken createToken(String account, String token);
    
    SysUserAccount createUserAccountByApp(AppAccountBean appAccountBean);
    
    SysUserAccount findAccountByUserInfo(long uid);
    
    void updatePwd(SysUserAccount sysUserAccount, String oldPwd, String newpwd);
    
    /**
     * 送奶工添加订户
     * @param customer
     * @param worker 
     */
    void createUserAccountByWorker(WorkerCustomAddBean customer, UserInfo worker);
    /**
     * 用户自己修改自己信息
     * @param worker
     * @param customer 
     */
    void updateUserAccountByWorker(UserInfo worker, UserInfoUpdateReq customer);
    /**
     * 送奶工删除订户
     * @param userid 
     */
    void deleteByUserId(long userid);
    
    /**
     * 添加送奶工
     * @param worker
     * @param nzmanager 
     */
    void addWorker(WorkerCustomAddBean worker, UserInfo nzmanager);
    
    /**
     * 根据用户的组织及用户类型信息查询用户账号
     * @param orgId
     * @param usertype
     * @return 
     */
    List<SysUserAccount> findByOrgAndUsertype(Long orgId, String usertype);
}