/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.service.impl;

import com.dinglicom.frame.sys.domain.AdminAllUserInfoResp;
import com.dinglicom.frame.sys.domain.AdminGetAllUserReq;
import com.dinglicom.frame.sys.domain.AdminReqBase;
import com.dinglicom.frame.sys.domain.AdminUserInfoReq;
import com.dinglicom.frame.sys.domain.AdminUserInfoResp;
import com.dinglicom.frame.sys.domain.AdminUserInfoRespItem;
import com.dinglicom.frame.sys.domain.AppPageReqBase;
import com.dinglicom.frame.sys.domain.AppUserBeanBase;
import com.dinglicom.frame.sys.domain.AppUserInfoPage;
import com.dinglicom.frame.sys.domain.AppUserReq;
import com.dinglicom.frame.sys.domain.CustomerDetailResp;
import com.dinglicom.frame.sys.domain.CustomerPhoneReq;
import com.dinglicom.frame.sys.domain.UserDetailMsg;
import com.dinglicom.frame.sys.domain.UserInfoGetResp;
import com.dinglicom.frame.sys.domain.UserInfoItem;
import com.dinglicom.frame.sys.domain.WorkerQueryCustomResp;
import com.dinglicom.frame.sys.domain.WorkerUpdateCustomerReq;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.SysUserAccount;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.repository.UserInfoDao;
import com.dinglicom.frame.sys.service.SysOranizagionService;
import com.dinglicom.frame.sys.service.SysUserAccountService;
import com.dinglicom.frame.sys.service.UserInfoService;
import com.dinglicom.order.domain.OrderReq;
import com.dinglicom.order.service.UserOrderService;
import com.dinglicom.salesman.domain.StationUpldateReq;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
@CacheConfig(cacheNames = {"userInfoCache"})
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private UserOrderService userOrderService;
    @Resource
    private SysUserAccountService sysUserAccountService;
    @Resource
    private SysOranizagionService sysOranizagionService;

    @Override
    @CachePut(value = "userInfoCache", key = "#userInfo.id")
    public UserInfo save(UserInfo userInfo) {
        return userInfoDao.save(userInfo);
    }

    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public UserInfo read(long id) {
        return userInfoDao.findOne(id);
    }

    @Override
    @CacheEvict
    public UserInfo update(UserInfo userinfo) {
        userinfo.setSignDelete(Boolean.TRUE);
        return save(userinfo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserInfo> findByOrg(SysOranizagion org) {
        return userInfoDao.findUserByOrg(org, Boolean.FALSE);
    }

    @Override
    @Transactional(readOnly = true)
    public SysOranizagion getWorkerStation(UserInfo userinfo) {
        return userinfo.getOrg();
    }

    @Override
    @Transactional(readOnly = true)
    public AppUserInfoPage findByWorkerQuery(AppUserReq workerReq) {
        AppUserInfoPage p = new AppUserInfoPage();
        p.setData(userInfoDao.findWorkerQueryString(workerReq.getRole(), workerReq.getQuery(), false));
        return p;
    }

    @Override
    @Transactional(readOnly = true)
    public AppUserInfoPage findByWorkerPhone(CustomerPhoneReq customerPhoneReq) {
        AppUserInfoPage p = new AppUserInfoPage();
        p.setData(userInfoDao.findWorkerQueryString(customerPhoneReq.getRole(), customerPhoneReq.getQuery(), false));
        return p;
    }

    @Override
    @Transactional(readOnly = true)
    public WorkerQueryCustomResp findAllCustomByWorker(UserInfo worker, AppPageReqBase req) {
        WorkerQueryCustomResp resp = new WorkerQueryCustomResp();
        Page<AppUserBeanBase> page = userInfoDao.findWorkerQueryCustomPage(new PageRequest(req.getPage() - 1, req.getNum()), worker.getOrg().getId(), USER_ROLE_CUSTOMER, false);
        resp.setTotal_page(page.getTotalPages());
        resp.setData(page.getContent());
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public WorkerQueryCustomResp findWorkerPage(UserInfo nzmanager, AppPageReqBase req) {
        WorkerQueryCustomResp resp = new WorkerQueryCustomResp();
        Page<AppUserBeanBase> page = userInfoDao.findWorkerQueryCustomPage(new PageRequest(req.getPage() - 1, req.getNum()), nzmanager.getOrg().getId(), USER_ROLE_MILKMAN, false);
        resp.setTotal_page(page.getTotalPages());
        resp.setData(page.getContent());
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public AdminUserInfoResp findUserInfoPage(AdminReqBase req) {
        AdminUserInfoResp resp = new AdminUserInfoResp();
        Page<AdminUserInfoRespItem> page = userInfoDao.findUserInfoPage(new PageRequest(0, 20), Boolean.FALSE);
//        resp.setTotal_page(page.getTotalPages());
        resp.setData(page.getContent());
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDetailResp findUserInfoById(Long customerId) {
        UserInfo customer = userInfoDao.findOne(customerId);
        CustomerDetailResp resp = new CustomerDetailResp();
        resp.setUser_id(customer.getId());
        resp.setName(customer.getRealname());
        resp.setMobile_phone(customer.getPhone());
        if (null != customer.getProvince()) {
            resp.setPid(customer.getProvince().getId());
        }
        resp.setProvince(customer.getProvincename());
        if (null != customer.getCity()) {
            resp.setCid(customer.getCity().getId());
        }
        resp.setCity(customer.getCityname());
        if (null != customer.getRegion()) {
            resp.setRid(customer.getRegion().getId());
        }
        resp.setRegion(customer.getRegionname());
        resp.setAddress(customer.getAddress());
        long notCompleteNum = userOrderService.findNotCompleteOrderCount(customer.getId());
        long completeNum = userOrderService.findCompleteOrderCount(customer.getId());

        resp.setFinished_order(completeNum);
        resp.setUnfinished_order(notCompleteNum);

        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailMsg findWorkerById(Long workerId) {
        UserInfo customer = userInfoDao.findOne(workerId);
        UserDetailMsg resp = new UserDetailMsg();
        resp.setUser_id(customer.getId());
        resp.setName(customer.getRealname());
        resp.setMobile_phone(customer.getPhone());
        if (null != customer.getProvince()) {
            resp.setPid(customer.getProvince().getId());
        }
        resp.setProvince(customer.getProvincename());
        if (null != customer.getCity()) {
            resp.setCid(customer.getCity().getId());
        }
        resp.setCity(customer.getCityname());
        if (null != customer.getRegion()) {
            resp.setRid(customer.getRegion().getId());
        }
        resp.setRegion(customer.getRegionname());
        resp.setAddress(customer.getAddress());

        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfo findNaizhaiWorkers(SysOranizagion nz) {
        List<UserInfo> list = userInfoDao.findUserByOrgAndRole(nz, UserInfoService.USER_ROLE_MILKMAN, Boolean.FALSE);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfo findNaizhaiManager(SysOranizagion nz) {
        List<UserInfo> list = userInfoDao.findUserByOrgAndRole(nz, UserInfoService.USER_ROLE_STATION, Boolean.FALSE);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public UserInfo updateUserInfoBynologinOrder(UserInfo customer, OrderReq req) {
        customer.setProvincename(req.getProvince());
        customer.setCityname(req.getCity());
        customer.setRegionname(req.getRegion());
        customer.setAddress(req.getAddress());
        customer.setPhone(req.getTel());
        customer.setRealname(req.getUser_name());

        return customer;
    }

    @Override
    @CacheEvict
    public void updateUserInfo(UserInfo userInfo) {
        userInfoDao.save(userInfo);
    }

    @Override
    @CacheEvict
    public UserInfo updateUserInfo(WorkerUpdateCustomerReq req) {
        UserInfo userInfo = read(req.getUser_id());
        if (null == userInfo) {
            return null;
        }
        if (null != req.getName() && !req.getName().isEmpty()) {
            userInfo.setRealname(req.getName());
        }
        if (null != req.getMobile_phone() && !req.getMobile_phone().isEmpty() && !req.getMobile_phone().equals(userInfo.getPhone())) {
            throw new RuntimeException("联系电话作为账号信息，不能被修改");
        }

        if (null != req.getAddress() && !req.getAddress().isEmpty()) {
            userInfo.setAddress(req.getAddress());
        }
        if (null != req.getProvince()) {
            SysOranizagion province = sysOranizagionService.read(req.getProvince());
            if (null != province) {
                userInfo.setProvince(province);
                userInfo.setProvincename(province.getName());
            }
        }
        if (null != req.getCity()) {
            SysOranizagion city = sysOranizagionService.read(req.getCity());
            if (null != city) {
                userInfo.setCity(city);
                userInfo.setCityname(city.getName());
            }
        }
        if (null != req.getRegion()) {
            SysOranizagion region = sysOranizagionService.read(req.getRegion());
            if (null != region) {
                userInfo.setRegion(region);
                userInfo.setRegionname(region.getName());
            }
        }
        if (null != req.getPwd() && !req.getPwd().isEmpty()) {
            SysUserAccount sysUserAccount = sysUserAccountService.findAccountByUserInfo(userInfo.getId());
            if (null == sysUserAccount) {
                throw new RuntimeException("账号不存在");
            }
            sysUserAccountService.updatePwd(sysUserAccount, null, req.getPwd());
        }
        return userInfo;
    }

    @Override
    public UserInfo addUserByAdmin(AdminUserInfoReq req) {
        UserInfo user = new UserInfo();
        user.setAddress(req.getAddress());
        user.setBirthday(req.getBirthday());
        user.setDesc(req.getDesc());
        user.setIdNumber(req.getId_number());
        user.setPhone(req.getTel());
        user.setRealname(req.getRealname());
        user.setAccount(req.getAccount());
        if (null != req.getPid() && req.getPid() > 0) {
            SysOranizagion province = sysOranizagionService.read(req.getPid());
            if (null != province) {
                user.setProvince(province);
                user.setProvincename(province.getName());
            }
        }
        if (null != req.getCid() && req.getCid() > 0) {
            SysOranizagion city = sysOranizagionService.read(req.getCid());
            if (null != city) {
                user.setCity(city);
                user.setCityname(city.getName());
            }
        }
        if (null != req.getRid() && req.getRid() > 0) {
            SysOranizagion region = sysOranizagionService.read(req.getRid());
            if (null != region) {
                user.setRegion(region);
                user.setRegionname(region.getName());
            }
        }
        user.setSex(req.getSex());

//        if (null != req.getSup_id() && req.getSup_id() > 0) {
//            UserInfo supermanager = read(req.getSup_id());
//            if (null != supermanager) {
//                user.setManagerid(supermanager.getId());
//                user.setManager(supermanager.getRealname());
//
//                if (null != supermanager.getOrg()) {
//                    user.setOrg(supermanager.getOrg());
//                    user.setOrgname(supermanager.getOrg().getName());
//                }
//                if (null == user.getProvince()) {
//                    user.setProvince(supermanager.getProvince());
//                    user.setProvincename(supermanager.getProvincename());
//                }
//                if (null == user.getCity()) {
//                    user.setCity(supermanager.getCity());
//                    user.setCityname(supermanager.getCityname());
//                }
//                if (null == user.getRegion()) {
//                    user.setRegion(supermanager.getRegion());
//                    user.setRegionname(supermanager.getRegionname());
//                }
//
//                if (null == req.getAddress()) {
//                    user.setAddress(supermanager.getAddress());
//                }
//            } else {
//                SysOranizagion supOrg = sysOranizagionService.read(req.getSup_id());
//                if (null != supOrg) {
//                    user.setOrg(supOrg);
//                    user.setOrgname(supOrg.getName());
//                }
//            }
//        }
        if (UserInfoService.USER_ROLE_CHIEF.equalsIgnoreCase(req.getRole())) {
            user.setUserType(UserInfoService.USER_ROLE_CHIEF);
            List<SysOranizagion> coms = sysOranizagionService.findOrgByType(SysOranizagionService.ORG_TYPE_COM);
            if (null != coms && coms.size() > 0) {
                SysOranizagion com = coms.get(0);
                user.setOrg(com);
                user.setOrgname(com.getName());
                if (null == user.getProvince()) {
                    user.setProvince(com.getProvince());
                    user.setProvincename(com.getProvince_name());
                }
                if (null == user.getCity()) {
                    user.setCity(com.getCity());
                    user.setCityname(com.getCity_name());
                }
                if (null == user.getRegion()) {
                    user.setRegion(com.getRegion());
                    user.setRegionname(com.getRegion_name());
                }

                if (null == req.getAddress()) {
                    user.setAddress(com.getAddress());
                }
            } else {
                throw new RuntimeException("Not found companay info");
            }
        } else if (UserInfoService.USER_ROLE_MANAGER.equalsIgnoreCase(req.getRole())) {
            user.setUserType(UserInfoService.USER_ROLE_MANAGER);
            SysOranizagion dep = sysOranizagionService.read(req.getSup_id());
            if (null != dep) {
                dep.setResponsible_man(user.getRealname());
                dep.setResponsible_phone(user.getPhone());
                sysOranizagionService.save(dep);

                if (null == user.getProvince()) {
                    user.setProvince(dep.getProvince());
                    user.setProvincename(dep.getProvince_name());
                }
                if (null == user.getCity()) {
                    user.setCity(dep.getCity());
                    user.setCityname(dep.getCity_name());
                }
                if (null == user.getRegion()) {
                    user.setRegion(dep.getRegion());
                    user.setRegionname(dep.getRegion_name());
                }

                if (null == req.getAddress()) {
                    user.setAddress(dep.getAddress());
                }
            } else {
                throw new RuntimeException("Not found department info by id:" + req.getSup_id());
            }
        } else if (UserInfoService.USER_ROLE_SALESMAN.equalsIgnoreCase(req.getRole())) {
            user.setUserType(UserInfoService.USER_ROLE_SALESMAN);
            SysOranizagion dep = sysOranizagionService.read(req.getSup_id());
            if (null != dep) {
                dep.setResponsible_man(user.getRealname());
                dep.setResponsible_phone(user.getPhone());
                sysOranizagionService.save(dep);

                if (null == user.getProvince()) {
                    user.setProvince(dep.getProvince());
                    user.setProvincename(dep.getProvince_name());
                }
                if (null == user.getCity()) {
                    user.setCity(dep.getCity());
                    user.setCityname(dep.getCity_name());
                }
                if (null == user.getRegion()) {
                    user.setRegion(dep.getRegion());
                    user.setRegionname(dep.getRegion_name());
                }

                if (null == req.getAddress()) {
                    user.setAddress(dep.getAddress());
                }
            } else {
                throw new RuntimeException("Not found department info by id:" + req.getSup_id());
            }
        } else if (UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(req.getRole())) {
            user.setUserType(UserInfoService.USER_ROLE_STATION);
            user.setNickname(req.getManager());
            SysOranizagion station = new SysOranizagion();
            if (null != req.getSup_id() && req.getSup_id() > 0) {
                UserInfo salsman = read(req.getSup_id());
                if (null != salsman) {
                    user.setManagerid(salsman.getId());
                    user.setManager(salsman.getRealname());
                } else {
                    throw new RuntimeException("Not found salesman info by id:" + req.getDid());
                }
                station.setUserinfo(salsman);
                if (null != salsman.getOrg()) {
                    station.setParent(salsman.getOrg());
                }
            }
            if (null != req.getDid() && req.getDid() > 0) {
                SysOranizagion dealer = sysOranizagionService.read(req.getDid());
                if (null != dealer) {
                    user.setDid(dealer.getId());
                    user.setDname(dealer.getName());

                    station.setDealer(dealer);
                    station.setDealer_name(dealer.getName());
                } else {
                    throw new RuntimeException("Not found dealer info by id:" + req.getDid());
                }
            }

            station.setName(req.getRealname());
            station.setProvince(user.getProvince());
            station.setProvince_name(user.getProvincename());
            station.setCity(user.getCity());
            station.setCity_name(user.getCityname());
            station.setRegion(user.getRegion());
            station.setRegion_name(user.getRegionname());
            station.setResponsible_man(req.getManager());
            station.setResponsible_phone(req.getTel());
            station.setPhone(req.getTel());
            station.setType(SysOranizagionService.ORG_TYPE_NZH);
            station.setAddress(req.getAddress());
            station.setDesc(req.getDesc());
            sysOranizagionService.save(station);

            user.setOrg(station);
            user.setOrgname(station.getName());
        } else if (UserInfoService.USER_ROLE_DEALER.equalsIgnoreCase(req.getRole())) {
            user.setUserType(UserInfoService.USER_ROLE_DEALER);
            user.setNickname(req.getManager());
            SysOranizagion dealer = new SysOranizagion();
            if (null != req.getSup_id() && req.getSup_id() > 0) {
                UserInfo salsman = read(req.getSup_id());
                dealer.setUserinfo(salsman);
                if (null != salsman) {
                    user.setManagerid(salsman.getId());
                    user.setManager(salsman.getRealname());
                } else {
                    throw new RuntimeException("Not found salesman info by id:" + req.getDid());
                }
                if (null != salsman.getOrg()) {
                    dealer.setParent(salsman.getOrg());
                }
            }
            dealer.setName(req.getRealname());

            dealer.setProvince(user.getProvince());
            dealer.setProvince_name(user.getProvincename());
            dealer.setCity(user.getCity());
            dealer.setCity_name(user.getCityname());
            dealer.setRegion(user.getRegion());
            dealer.setRegion_name(user.getRegionname());

            dealer.setResponsible_man(req.getManager());
            dealer.setResponsible_phone(req.getTel());
            dealer.setPhone(req.getTel());

            dealer.setType(SysOranizagionService.ORG_TYPE_DEALER);
            dealer.setAddress(req.getAddress());
            dealer.setDesc(req.getDesc());
            sysOranizagionService.save(dealer);

            user.setOrg(dealer);
            user.setOrgname(dealer.getName());
        } else if (UserInfoService.USER_ROLE_MILKMAN.equalsIgnoreCase(req.getRole())) {
            user.setUserType(UserInfoService.USER_ROLE_MILKMAN);
            SysOranizagion nz = sysOranizagionService.read(req.getSup_id());
            if (null != nz) {
                user.setOrg(nz);
                user.setOrgname(nz.getName());
                UserInfo nzmanager = findNaizhaiManager(nz);
                if(null != nzmanager) {
                    user.setManagerid(nzmanager.getId());
                    user.setManager(nzmanager.getRealname());
                }
                if (null == user.getProvince()) {
                    user.setProvince(nz.getProvince());
                    user.setProvincename(nz.getProvince_name());
                }
                if (null == user.getCity()) {
                    user.setCity(nz.getCity());
                    user.setCityname(nz.getCity_name());
                }
                if (null == user.getRegion()) {
                    user.setRegion(nz.getRegion());
                    user.setRegionname(nz.getRegion_name());
                }
                if (null == user.getAddress()) {
                    user.setAddress(nz.getAddress());
                }
            } else {
                UserInfo nzmanager = read(req.getSup_id());
                if (null != nzmanager && null != nzmanager.getOrg()) {
                    user.setOrg(nzmanager.getOrg());
                    user.setOrgname(nzmanager.getOrg().getName());

                    user.setManagerid(nzmanager.getId());
                    user.setManager(nzmanager.getRealname());

                    if (null == user.getProvince()) {
                        user.setProvince(nzmanager.getOrg().getProvince());
                        user.setProvincename(nzmanager.getOrg().getProvince_name());
                    }
                    if (null == user.getCity()) {
                        user.setCity(nzmanager.getOrg().getCity());
                        user.setCityname(nzmanager.getOrg().getCity_name());
                    }
                    if (null == user.getRegion()) {
                        user.setRegion(nzmanager.getOrg().getRegion());
                        user.setRegionname(nzmanager.getOrg().getRegion_name());
                    }
                    if (null == user.getAddress()) {
                        user.setAddress(nzmanager.getOrg().getAddress());
                    }
                } else {
                    throw new RuntimeException("Not found station info for sup_id:" + req.getSup_id() + " super_name:" + req.getSup_name());
                }
            }
        } else if (UserInfoService.USER_ROLE_CUSTOMER.equalsIgnoreCase(req.getRole())) {
            user.setUserType(UserInfoService.USER_ROLE_CUSTOMER);
            SysOranizagion nz = sysOranizagionService.read(req.getSup_id());
            if (null != nz) {
                user.setOrg(nz);
                user.setOrgname(nz.getName());
                
                UserInfo nzmanager = findNaizhaiManager(nz);
                if(null != nzmanager) {
                    user.setManagerid(nzmanager.getId());
                    user.setManager(nzmanager.getRealname());
                }
            } else {
                UserInfo nzmanager = read(req.getSup_id());
                if (null != nzmanager && null != nzmanager.getOrg()) {
                    user.setOrg(nzmanager.getOrg());
                    user.setOrgname(nzmanager.getOrg().getName());

                    user.setManagerid(nzmanager.getId());
                    user.setManager(nzmanager.getRealname());
                }
            }
        }
        save(user);
        if (null != req.getAccount() && null != req.getPwd()) {
            SysUserAccount old = sysUserAccountService.findByAccount(req.getAccount());
            if (null != old) {
                throw new RuntimeException("账号已经存在");
            }

            SysUserAccount sysUserAccount = new SysUserAccount();
            sysUserAccount.setAccount(req.getAccount());
            sysUserAccount.setPassword(req.getPwd());
            sysUserAccount.setUserInfo(user);
            sysUserAccountService.save(sysUserAccount);
        }
        return user;
    }

    @Override
    public void updateUserByAdmin(AdminUserInfoReq req, UserInfo user) {
        if (null != req.getAddress() && !req.getAddress().isEmpty()) {
            user.setAddress(req.getAddress());
        }
        if (null != req.getBirthday()) {
            user.setBirthday(req.getBirthday());
        }
        if (null != req.getDesc() && !req.getDesc().isEmpty()) {
            user.setDesc(req.getDesc());
        }
        if (null != req.getId_number() && !req.getId_number().isEmpty()) {
            user.setIdNumber(req.getId_number());
        }
        if (null != req.getTel() && !req.getTel().isEmpty()) {
            user.setPhone(req.getTel());
        }

        if (null != req.getPid() && req.getPid() > 0) {
            SysOranizagion province = sysOranizagionService.read(req.getPid());
            if (null != province) {
                user.setProvince(province);
                user.setProvincename(province.getName());
            }
        }
        if (null != req.getCid() && req.getCid() > 0) {
            SysOranizagion city = sysOranizagionService.read(req.getCid());
            if (null != city) {
                user.setCity(city);
                user.setCityname(city.getName());
            }
        }
        if (null != req.getRid() && req.getRid() > 0) {
            SysOranizagion region = sysOranizagionService.read(req.getRid());
            if (null != region) {
                user.setRegion(region);
                user.setRegionname(region.getName());
            }
        }
        if (null != req.getSex() && !req.getSex().isEmpty()) {
            user.setSex(req.getSex());
        }
        if (UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(req.getRole()) || UserInfoService.USER_ROLE_DEALER.equalsIgnoreCase(req.getRole())) {
            if (null != req.getManager()) {
                user.setNickname(req.getManager());
                SysOranizagion org = user.getOrg();
                if (null != org) {
                    org.setResponsible_man(req.getManager());
                    sysOranizagionService.save(org);
                }
            }
        }
        if (null != req.getSup_id() && req.getSup_id() > 0) {
            UserInfo superman = read(req.getSup_id());
            if (null != superman) {
                user.setManagerid(superman.getId());
                user.setManager(superman.getRealname());
            }
        }
        if (null != req.getRole() && !req.getRole().isEmpty()) {
            user.setUserType(req.getRole());
        }
        save(user);

        if (null != req.getPwd() && !req.getPwd().isEmpty()) {
            SysUserAccount sysUserAccount = sysUserAccountService.findAccountByUserInfo(req.getUid());
            if (null == sysUserAccount) {
                throw new RuntimeException("账号不存在");
            }
            sysUserAccountService.updatePwd(sysUserAccount, null, req.getPwd());
        }
    }

    @Override
    public void deleteUserByAdmin(long uid) {
        sysUserAccountService.deleteByUserId(uid);
//        SysUserAccount sysUserAccount = sysUserAccountService.findAccountByUserInfo(req.getUid());
//        if(null != sysUserAccount) {
//            sysUserAccountService.delete(sysUserAccount);
//        }
    }

    @Override
    @Transactional(readOnly = true)
    public AdminAllUserInfoResp findAllUserPage(AdminGetAllUserReq req) {
        AdminAllUserInfoResp resp = new AdminAllUserInfoResp();
        Page<UserInfoItem> page;
        if (null == req.getRole()) {
            page = userInfoDao.findUserAndAccountPage(new PageRequest(req.getPage() - 1, req.getNum()), Boolean.FALSE);
        } else {
            if (UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(req.getRole()) || UserInfoService.USER_ROLE_DEALER.equalsIgnoreCase(req.getRole())) {
                page = userInfoDao.findUserAndAccountStationPage(new PageRequest(req.getPage() - 1, req.getNum()), req.getRole(), Boolean.FALSE);
            } else {
                page = userInfoDao.findUserAndAccountPage(new PageRequest(req.getPage() - 1, req.getNum()), req.getRole(), Boolean.FALSE);
            }
        }
        if (null != page) {
            resp.setTotal_num(page.getTotalElements());
            resp.setData(page.getContent());
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public AdminAllUserInfoResp findByUserId(AdminUserInfoReq req) {
        AdminAllUserInfoResp resp = new AdminAllUserInfoResp();
        resp.setData(userInfoDao.findUserByUserId(req.getUid(), Boolean.FALSE));
        return resp;
    }

    @Override
    public UserInfo addStationUser(SysOranizagion station, StationUpldateReq req, UserInfo salesman) {
        UserInfo user = new UserInfo();
        user.setProvince(station.getProvince());
        user.setProvincename(station.getProvince_name());
        user.setCity(station.getCity());
        user.setCityname(station.getCity_name());
        user.setRegion(station.getRegion());
        user.setRegionname(station.getRegion_name());
        user.setAddress(station.getAddress());
        user.setPhone(station.getPhone());
        user.setUserType(station.getType());
        user.setOrg(station);
        user.setOrgname(station.getName());
        user.setRealname(station.getResponsible_man());
        user.setNickname(req.getManager());
        user.setManagerid(salesman.getId());
        user.setManager(salesman.getRealname());
        save(user);

        if (null != req.getMobile_phone() && null != req.getPwd()) {
            SysUserAccount old = sysUserAccountService.findByAccount(user.getPhone());
            if (null != old) {
                throw new RuntimeException("账号已经存在");
            }

            SysUserAccount sysUserAccount = new SysUserAccount();
            sysUserAccount.setAccount(user.getPhone());
            sysUserAccount.setPassword(req.getPwd());
            sysUserAccount.setUserInfo(user);
            sysUserAccountService.save(sysUserAccount);
        }
        return user;
    }

    @Override
    public void updateUserPwd(SysUserAccount sysUserAccount, String oldpwd, String newpwd) {
        sysUserAccountService.updatePwd(sysUserAccount, oldpwd, newpwd);
    }

    @Override
    @Transactional(readOnly = true)
    public SysUserAccount findByOrgAndUserType(Long orgid, String usertype) {
        List<SysUserAccount> accounts = sysUserAccountService.findByOrgAndUsertype(orgid, usertype);
        if (accounts.size() > 0) {
            return accounts.get(0);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllBaiduIds() {
        return userInfoDao.findAllBaiduid(Boolean.FALSE);
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoGetResp getMyInfo(UserInfo user) {
        UserInfoGetResp resp = new UserInfoGetResp();
        resp.setRole(user.getUserType());
        if (null != user.getProvince()) {
            resp.setPid(user.getProvince().getId());
        }
        resp.setProvince(user.getProvincename());
        if (null != user.getCity()) {
            resp.setCid(user.getCity().getId());
        }
        resp.setCity(user.getCityname());
        if (null != user.getRegion()) {
            resp.setRid(user.getRegion().getId());
        }
        resp.setRegion(user.getRegionname());
        resp.setAddress(user.getAddress());
        resp.setRealname(user.getRealname());
        resp.setSex(user.getSex());
        resp.setTel(user.getPhone());
        resp.setAccount(user.getAccount());
        if (UserInfoService.USER_ROLE_STATION.equals(user.getUserType()) || UserInfoService.USER_ROLE_MANAGER.equals(user.getUserType())) {
            SysOranizagion org = user.getOrg();
            if (null != org) {
                resp.setManager(org.getResponsible_man());
            }
        }
        return resp;
    }
}
