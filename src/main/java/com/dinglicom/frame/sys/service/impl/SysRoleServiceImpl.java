/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.service.impl;

import com.dinglicom.frame.sys.entity.Permission;
import com.dinglicom.frame.sys.entity.SysRole;
import com.dinglicom.frame.sys.repository.SysRoleDao;
import com.dinglicom.frame.sys.service.SysRoleService;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
@CacheConfig(cacheNames = {"sysRoleCache"})
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    @CachePut(value = "sysRoleCache", key = "#sysRole.id")
    public SysRole save(SysRole sysRole) {
        return sysRoleDao.save(sysRole);
    }

    @Override
    @Cacheable
    public SysRole read(long id) {
        return sysRoleDao.findOne(id);
    }

    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public Iterable<SysRole> getAll() {
        return sysRoleDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return sysRoleDao.count();
    }
    
    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public Collection<Permission> findPermissionBySysRole(SysRole sysRole) {
        return sysRoleDao.findSysUserAccountRolesBySysUserAccount(sysRole);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Collection<Permission> findPermissionBySysRole(Iterable<SysRole> sysRoles) {
        Collection<Permission> collection = null;
        if(null != sysRoles) {
            Iterator<SysRole> it = sysRoles.iterator();
            while(it.hasNext()) {
                SysRole sysRole = it.next();
                if(null == collection) {
                    collection = findPermissionBySysRole(sysRole);
                }
                collection.addAll(findPermissionBySysRole(sysRole));
            }
        }
        return collection;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> getPermissionString(Collection<Permission> permissions) {
        Set<String> pms = new HashSet<String>();
        if(null != permissions) {
            Iterator<Permission> it = permissions.iterator();
            while(it.hasNext()) {
                Permission p = it.next();
                pms.add(p.getPermission());
            }
        }
        return pms;
    }

    @Override
    @CacheEvict(value = "sysRoleCache", key = "#id")
    public void delete(long id) {
        sysRoleDao.delete(id);
    }

    @Override
    @CacheEvict(value = "sysRoleCache", key = "#sysRole.id")
    public void delete(SysRole sysRole) {
        sysRoleDao.delete(sysRole);
    }
}