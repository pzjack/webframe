/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.service.impl;

import com.dinglicom.frame.sys.entity.SysToken;
import com.dinglicom.frame.sys.entity.SysUserAccount;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.repository.SysTokenDao;
import com.dinglicom.frame.sys.service.SysTokenService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
@CacheConfig(cacheNames = "tokenCache")
public class SysTokenServiceImpl implements SysTokenService {

    private final static long PRO = 30L * 24L * 3600L * 1000L;
    @Resource
    private SysTokenDao sysTokenDao;

    @Override
    @CacheEvict(value = "tokenCache", key = "#systoken.token")
    public void delete(SysToken systoken) {
        sysTokenDao.delete(systoken);
    }

    @Override
    @CacheEvict(value = "tokenCache", allEntries = true)
    public void delete(List<SysToken> systokens) {
        sysTokenDao.delete(systokens);
    }

    @Override
    public void deleteByUserid(long userid) {
        delete(findByUserid(userid));
    }

    @Override
    public SysToken createToken(SysUserAccount sysUserAccount, String token) {
        SysToken sysToken = new SysToken();
        sysToken.setToken(token);
        sysToken.setBegintime(System.currentTimeMillis());
        sysToken.setSysUserAccount(sysUserAccount);
        sysToken.setUserInfo(sysUserAccount.getUserInfo());
        return sysToken;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "tokenCache", key = "#token")
    public SysToken findToken(String token) {
        return sysTokenDao.findByToken(token);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean findValidToken(String token, long uid) {
        try {
            validateToken(token, uid);
        } catch (RuntimeException ex) {
            return false;
        }
        return true;
    }

    private SysToken validateToken(String token, long uid) {
        SysToken t = findToken(token);
        if (null == t) {
            throw new RuntimeException("没有授权信息");
        }
        UserInfo userInfo = t.getUserInfo();
        if (null == t.getUserInfo()) {
            throw new RuntimeException("没有用户信息");
        }
        if (System.currentTimeMillis() - t.getBegintime() > PRO) {
            throw new RuntimeException("授权信息已经过期");
        }
        if (uid != userInfo.getId()) {
            throw new RuntimeException("没有有效授权信息");
        }
        return t;
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfo findValidTokenAndUserInfo(String token, long uid) {
        SysToken t = validateToken(token, uid);
        if (null != t) {
            return t.getUserInfo();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public SysUserAccount findValidTokenAndSysUserAccount(String token, long uid) {
        SysToken t = validateToken(token, uid);
        if (null != t) {
            return t.getSysUserAccount();
        }
        return null;
    }

    @Override
    @CachePut(value = "tokenCache", key = "#sysToken.token")
    public SysToken save(SysToken sysToken) {
        return sysTokenDao.save(sysToken);
    }

    @Override
    @CacheEvict(value = "tokenCache", allEntries=true)
    public void deleteByAccountId(long accountid) {
        sysTokenDao.deleteByAllAccountId(accountid);
    }

    @Override
    @CacheEvict(value = "tokenCache", allEntries=true)
    public void deleteByBegintime(long begintime) {
        sysTokenDao.deleteByBegintimeLessThan(begintime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysToken> findByUserid(long userid) {
        return sysTokenDao.findByUserInfoId(userid);
    }
    
   @Override
    @Transactional(readOnly = true)
    public int findByAccout(String account) {
        List<SysToken> list = sysTokenDao.findByAccount(account);
        if(null == list || list.size() <= 0) {
            return 0;//未登录
        }
        SysToken t = list.get(0);
        if (System.currentTimeMillis() - t.getBegintime() < PRO) {
            return -1;//已登录，并且未过期
        }
        return 10;
    }
}
