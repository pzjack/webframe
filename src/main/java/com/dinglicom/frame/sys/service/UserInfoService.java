/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.service;

import com.dinglicom.frame.sys.domain.AdminAllUserInfoResp;
import com.dinglicom.frame.sys.domain.AdminGetAllUserReq;
import com.dinglicom.frame.sys.domain.AdminReqBase;
import com.dinglicom.frame.sys.domain.AdminUserInfoReq;
import com.dinglicom.frame.sys.domain.AdminUserInfoResp;
import com.dinglicom.frame.sys.domain.AppPageReqBase;
import com.dinglicom.frame.sys.domain.AppUserInfoPage;
import com.dinglicom.frame.sys.domain.AppUserReq;
import com.dinglicom.frame.sys.domain.CustomerDetailResp;
import com.dinglicom.frame.sys.domain.CustomerPhoneReq;
import com.dinglicom.frame.sys.domain.UserDetailMsg;
import com.dinglicom.frame.sys.domain.UserInfoGetResp;
import com.dinglicom.frame.sys.domain.WorkerQueryCustomResp;
import com.dinglicom.frame.sys.domain.WorkerUpdateCustomerReq;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.SysUserAccount;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.order.domain.OrderReq;
import com.dinglicom.salesman.domain.StationUpldateReq;
import java.util.List;

/**
 *
 * @author panzhen
 */
public interface UserInfoService {
    final static String USER_ROLE_CUSTOMER = "CONSUMER";//订户
    final static String USER_ROLE_MILKMAN = "MILKMAN";//送奶工
    final static String USER_ROLE_DEALER = "DEALER";//经销商
    final static String USER_ROLE_STATION = "STATION";//奶站
    final static String USER_ROLE_SALESMAN = "SALESMAN";//业务员
    final static String USER_ROLE_MANAGER = "MANAGER";//部门主管
    final static String USER_ROLE_CHIEF = "CHIEF";//销售总监
    final static String USER_ROLE_ADMINISTRATOR = "ADMINISTRATOR";//管理员
    UserInfo save(UserInfo userInfo);
    void updateUserInfo(UserInfo userInfo);
    UserInfo read(long id);
    UserInfo update(UserInfo userinfo);
    AppUserInfoPage findByWorkerQuery(AppUserReq workerReq);
    AppUserInfoPage findByWorkerPhone(CustomerPhoneReq customerPhoneReq);
    WorkerQueryCustomResp findAllCustomByWorker(UserInfo worker, AppPageReqBase req);
    /**
     * 获取用户的所属组织机构
     * @param userinfo
     * @return 
     */
    SysOranizagion getWorkerStation(UserInfo userinfo);
    
    CustomerDetailResp findUserInfoById(Long customerId);
    /**
     * 查找奶站的送奶工，如果没有送奶工，随机取一个该奶站的员工
     * @param nz
     * @return 
     */
    UserInfo findNaizhaiWorkers(SysOranizagion nz);
    
    /**
     * 更新订户地址信息，通过未登录订单附加消息
     * @param customer
     * @param req
     * @return 
     */
    UserInfo updateUserInfoBynologinOrder(UserInfo customer, OrderReq req);
    
    
    /**
     * 分页显示送奶工
     * @param nzmanager
     * @param req
     * @return 
     */
    WorkerQueryCustomResp findWorkerPage(UserInfo nzmanager, AppPageReqBase req);
    /**
     * 显示用户详情
     * @param customerId
     * @return 
     */
    UserDetailMsg findWorkerById(Long customerId);
    
    /**
     * 修改用户信息
     * @param customer 
     * @return  
     */
    UserInfo updateUserInfo(WorkerUpdateCustomerReq customer);
    
    /**
     * 系统管理员请求所有用户信息
     * @param req
     * @return 
     */
    AdminUserInfoResp findUserInfoPage(AdminReqBase req);
    
    /**
     * 系统管理员添加用户
     * @param req 
     * @param admin 
     * @return  
     */
    UserInfo addUserByAdmin(AdminUserInfoReq req, UserInfo admin);
    
    /**
     * 系统管理员修改用户信息
     * @param req
     * @param user 
     */
    void updateUserByAdmin(AdminUserInfoReq req, UserInfo user);
    
    /**
     * 系统管理员删除用户信息
     * @param uid
     */
    void deleteUserByAdmin(long uid);
    
    /**
     * web管理界面获取所有用户信息列表，可能的查询条件用户角色
     * @param req
     * @param user
     * @return 
     */
    AdminAllUserInfoResp findAllUserPage(AdminGetAllUserReq req, UserInfo user);
    
    /**
     * 根据用户id信息获取用户详情消息
     * @param req
     * @return 
     */
    AdminAllUserInfoResp findByUserId(AdminUserInfoReq req);
    
    /**
     * 业务员新增奶站和经销商信息，增加用户及账号信息
     * @param station
     * @param req
     * @param salesman
     * @return 
     */
    UserInfo addStationUser(SysOranizagion station, StationUpldateReq req, UserInfo salesman);
    
    /**
     * 业务员修改奶站用户及密码信息
     * @param sysUserAccount
     * @param oldpwd
     * @param newpwd 
     */
    void updateUserPwd(SysUserAccount sysUserAccount, String oldpwd, String newpwd);
    
    List<UserInfo> findByOrg(SysOranizagion org);
    
    SysUserAccount findByOrgAndUserType(Long orgid, String usertype);
    
    UserInfo findNaizhaiManager(SysOranizagion nz);
    
    /**
     * 查找所有百度ID
     * @return 
     */
    List<String> findAllBaiduIds();
    
    /**
     * 我的信息获取
     * @param user
     * @return 
     */
    UserInfoGetResp getMyInfo(UserInfo user);
}
