/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.service.impl;

import com.dinglicom.frame.sys.domain.AccountInfoBean;
import com.dinglicom.frame.sys.domain.AppAccountBean;
import com.dinglicom.frame.sys.domain.UserInfoUpdateReq;
import com.dinglicom.frame.sys.domain.WorkerCustomAddBean;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.SysRole;
import com.dinglicom.frame.sys.entity.SysToken;
import com.dinglicom.frame.sys.entity.SysUserAccount;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.repository.SysUserAccountDao;
import com.dinglicom.frame.sys.service.PasswordUtil;
import com.dinglicom.frame.sys.service.SysOranizagionService;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.sys.service.SysUserAccountService;
import com.dinglicom.frame.sys.service.UserInfoService;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
//@CacheConfig(cacheNames = {"sysUserAccountCache"})
public class SysUserAccountServiceImpl implements SysUserAccountService {

    @Resource
    private SysUserAccountDao sysUserDao;
    @Resource
    private PasswordUtil passwordUtil;
    @Resource
    private SysTokenService sysTokenService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private SysOranizagionService sysOranizagionService;

    /**
     * @param account
     * @return
     */
    @Override
//    @Cacheable(value = "sysUserAccountCache", key = "#account")
    @Transactional(readOnly = true)
    public SysUserAccount findByAccount(String account) {
        return sysUserDao.findByAccount(account);
    }

    @Override
//    @CacheEvict(value = "sysUserAccountCache", allEntries = true)
    public void delete(SysUserAccount sysUserAccount) {
        sysUserDao.delete(sysUserAccount);
    }

//    @CacheEvict(value = "sysUserAccountCache", key = "#sysUserAccount.account", allEntries = true)
//    @CacheEvict(value = "sysUserAccountCache", allEntries = true)
    public void deleteByAccount(String account) {
        sysUserDao.deleteByAccount(account);
    }

    @Override
//    @CacheEvict(value = "sysUserAccountCache", allEntries = true)
    public void delete(List<SysUserAccount> sysUserAccounts) {
        sysUserDao.delete(sysUserAccounts);
    }

//    @CacheEvict(value = "sysUserAccountCache", key = "#sysUserAccount.account")
    public void update(SysUserAccount sysUserAccount) {
        sysUserDao.save(sysUserAccount);
    }

    /**
     * 查找账号详细信息
     *
     * @param account
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public AccountInfoBean findUserLongInfo(String account) {
        SysUserAccount sysUser = findByAccount(account);
        AccountInfoBean accountInfo = new AccountInfoBean();
        if(null == sysUser) {
            return null;
        }
        accountInfo.setAccount(sysUser.getAccount());
        accountInfo.setPwd(sysUser.getPassword());
        accountInfo.setSalt(sysUser.credentialsSalt());
        accountInfo.setNickname(sysUser.getNickname());
        accountInfo.setRoles(sysUser.stringRoles());
        accountInfo.setState(sysUser.getZhState());
        accountInfo.setPermissions(sysUser.stringPermissions());
        if (null != sysUser.getUserInfo()) {
            accountInfo.setUserId(sysUser.getUserInfo().getId());
            accountInfo.setUserType(sysUser.getUserInfo().getUserType());
        }
        return accountInfo;
    }

    /**
     * @param sysUserAccount
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<SysRole> findRoles(SysUserAccount sysUserAccount) {
        return sysUserDao.findSysUserAccountRolesBySysUserAccount(sysUserAccount);
    }

    /**
     *
     * @param sysUserAccount
     * @return
     */
    @Override
//    @CachePut(value = "sysUserAccountCache", key = "#sysUserAccount.account")
    public SysUserAccount save(SysUserAccount sysUserAccount) {
        sysUserAccount.setZhState(NORMAL);
        passwordUtil.encryptPassword(sysUserAccount);
        return sysUserDao.save(sysUserAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> getRoleString(Collection<SysRole> sysUserAccountRoles) {
        Set<String> roles = new HashSet<>();
        if (null != sysUserAccountRoles) {
            Iterator<SysRole> it = sysUserAccountRoles.iterator();
            while (it.hasNext()) {
                SysRole r = it.next();
                roles.add(r.getRole());
            }
        }
        return roles;
    }

    /**
     * 登录用户token生成
     *
     * @param account
     * @param token
     * @return
     */
    @Override
    public SysToken createToken(String account, String token) {
        SysUserAccount sysUserAccount = findByAccount(account);
        return sysTokenService.createToken(sysUserAccount, token);
    }

    /**
     * 用户注册
     *
     * @param appAccountBean
     * @return
     */
    @Override
    public SysUserAccount createUserAccountByApp(AppAccountBean appAccountBean) {
        SysUserAccount old = findByAccount(appAccountBean.getAccount());
        if (null != old) {
            throw new RuntimeException("账号已经存在");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserType(UserInfoService.USER_ROLE_CUSTOMER);
        userInfo.setAccount(appAccountBean.getAccount());
        userInfoService.save(userInfo);
        SysUserAccount sysUserAccount = new SysUserAccount();
        sysUserAccount.setAccount(appAccountBean.getAccount());
        sysUserAccount.setPassword(appAccountBean.getPwd());
        sysUserAccount.setUserInfo(userInfo);
        return save(sysUserAccount);
    }

    /**
     * 送奶工添加订户
     *
     * @param customer
     * @param worker
     */
    @Override
    public void createUserAccountByWorker(WorkerCustomAddBean customer, UserInfo worker) {
        SysUserAccount account = findByAccount(customer.getMobile_phone());
        if (null != account) {
            throw new RuntimeException("账号已经存在");
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setOrg(worker.getOrg());
        userInfo.setOrgname(worker.getOrgname());
        userInfo.setRealname(customer.getName());
        userInfo.setPhone(customer.getMobile_phone());
//        userInfo.setProvincename(customer.getProvince());
//        userInfo.setCityname(customer.getCity());
//        userInfo.setRegionname(customer.getRegion());
        userInfo.setProvince(worker.getProvince());
        userInfo.setProvincename(worker.getProvincename());
        userInfo.setCity(worker.getCity());
        userInfo.setCityname(worker.getCityname());
        userInfo.setRegion(worker.getRegion());
        userInfo.setRegionname(worker.getRegionname());
        userInfo.setAccount(customer.getMobile_phone());

        userInfo.setAddress(customer.getAddress());
        userInfo.setUserType(UserInfoService.USER_ROLE_CUSTOMER);
        userInfoService.save(userInfo);

        SysUserAccount sysUserAccount = new SysUserAccount();
        sysUserAccount.setAccount(customer.getMobile_phone());
        sysUserAccount.setPassword(customer.getPwd());
        sysUserAccount.setUserInfo(userInfo);
        save(sysUserAccount);
    }

    @Override
    public void addWorker(WorkerCustomAddBean worker, UserInfo nzmanager) {
        SysUserAccount account = findByAccount(worker.getMobile_phone());
        if (null != account) {
            throw new RuntimeException("账号已经存在");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setOrg(nzmanager.getOrg());
        userInfo.setOrgname(nzmanager.getOrgname());
        userInfo.setRealname(worker.getName());
        userInfo.setPhone(worker.getMobile_phone());
//        userInfo.setProvincename(worker.getProvince());
//        userInfo.setCityname(worker.getCity());
//        userInfo.setRegionname(worker.getRegion());
//        userInfo.setAddress(worker.getAddress());
        userInfo.setProvince(nzmanager.getProvince());
        userInfo.setProvincename(nzmanager.getProvincename());
        userInfo.setCity(nzmanager.getCity());
        userInfo.setCityname(nzmanager.getCityname());
        userInfo.setRegion(nzmanager.getRegion());
        userInfo.setRegionname(nzmanager.getRegionname());
        userInfo.setAddress(nzmanager.getAddress());
        userInfo.setUserType(UserInfoService.USER_ROLE_MILKMAN);
        userInfoService.save(userInfo);

        SysUserAccount sysUserAccount = new SysUserAccount();
        sysUserAccount.setAccount(worker.getMobile_phone());
        sysUserAccount.setPassword(worker.getPwd());
        sysUserAccount.setUserInfo(userInfo);
        save(sysUserAccount);
    }

    /**
     * 用户自己修改自己信息
     *
     * @param customer
     * @param req
     */
    @Override
    public void updateUserAccountByWorker(UserInfo customer, UserInfoUpdateReq req) {
        if (null != req.getRealname() && !req.getRealname().isEmpty() && !req.getRealname().equals(customer.getRealname())) {
            customer.setRealname(req.getRealname());
        }
        if (null != req.getTel() && !req.getTel().isEmpty() && !req.getTel().equals(customer.getPhone())) {
            customer.setPhone(req.getTel());
        }
        if (null != req.getAddress() && !req.getAddress().isEmpty() && !req.getAddress().equals(customer.getAddress())) {
            customer.setAddress(req.getAddress());
        }
        if (null != req.getPid() && req.getPid() > 0) {
            SysOranizagion province = sysOranizagionService.read(req.getPid());
            if (null == province) {
                throw new RuntimeException("Not find province.");
            }
            customer.setProvince(province);
            customer.setProvincename(province.getName());
        }
        if (null != req.getCid() && req.getCid() > 0) {
            SysOranizagion city = sysOranizagionService.read(req.getCid());
            if (null == city) {
                throw new RuntimeException("Not find city.");
            }
            customer.setCity(city);
            customer.setCityname(city.getName());
        }
        if (null != req.getRid() && req.getRid() > 0) {
            SysOranizagion region = sysOranizagionService.read(req.getRid());
            if (null == region) {
                throw new RuntimeException("Not find region.");
            }
            customer.setRegion(region);
            customer.setRegionname(region.getName());
        }
        if (null != req.getManager() && !req.getManager().isEmpty()) {
            if (UserInfoService.USER_ROLE_MANAGER.equals(customer.getUserType()) || UserInfoService.USER_ROLE_STATION.equals(customer.getUserType())) {
                SysOranizagion org = customer.getOrg();
                if (null != org) {
                    org.setResponsible_man(req.getManager());
                    sysOranizagionService.save(org);
                }
            }
        }

        userInfoService.updateUserInfo(customer);

        if (null != req.getPwd() && !req.getPwd().isEmpty()) {
            SysUserAccount sysUserAccount = findAccountByUserInfo(req.getUid());
            updatePwd(sysUserAccount, req.getOld_pwd(), req.getPwd());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SysUserAccount findAccountByUserInfo(long uid) {
        List<SysUserAccount> list = sysUserDao.findByUserInfoId(uid);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void updatePwd(SysUserAccount sysUserAccount, String oldPwd, String newpwd) {
//        if(oldPwd != null && oldPwd.equals(sysUserAccount.getPassword())) {
//            throw new RuntimeException("password error.");
//        }
        if (null != newpwd) {
            updateAccountPwd(sysUserAccount, newpwd);
            update(sysUserAccount);
        }
    }

    private SysUserAccount updateAccountPwd(SysUserAccount sysUserAccount, String newpwd) {
        String encodePwd = passwordUtil.getEncryptPassword(newpwd, sysUserAccount.getAccount(), sysUserAccount.getSalt());
        sysUserAccount.setPassword(encodePwd);
        return sysUserAccount;
    }

    /**
     * 送奶工删除订户信息 ;
     *
     * @param userid
     */
    @Override
//    @CacheEvict
    public void deleteByUserId(long userid) {
        UserInfo userInfo = userInfoService.read(userid);
        if (null == userInfo) {
            return;
        }
        if (UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(userInfo.getUserType())) {
            SysOranizagion station = userInfo.getOrg();
            if (null != station) {
                station.setSignDelete(Boolean.TRUE);
            }
        }
        userInfo.setSignDelete(Boolean.TRUE);
        userInfoService.update(userInfo);

        sysTokenService.deleteByUserid(userid);
        List<SysUserAccount> list = sysUserDao.findByUserInfoId(userid);
        if (null != list && list.size() > 0) {
            for (SysUserAccount a : list) {
                delete(a);
            }
        }
    }

    /**
     * 根据用户的组织及用户类型信息查询用户账号
     *
     * @param orgId
     * @param usertype
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysUserAccount> findByOrgAndUsertype(Long orgId, String usertype) {
        return sysUserDao.findByUsertypeAndUserorgid(orgId, usertype, Boolean.FALSE);
    }
}
