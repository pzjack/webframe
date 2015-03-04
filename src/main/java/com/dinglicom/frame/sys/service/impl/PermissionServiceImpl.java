/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.service.impl;

import com.dinglicom.frame.sys.entity.Permission;
import com.dinglicom.frame.sys.repository.PermissionDao;
import com.dinglicom.frame.sys.service.PermissionService;
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
@CacheConfig(cacheNames = {"permissionCache"})
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionDao permissionDao;

    @Override
    @CachePut(value = "permissionCache", key = "#permission.id")
    public Permission save(Permission permission) {
       return permissionDao.save(permission);
    }

    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public Iterable<Permission> findAll() {
        return permissionDao.findAll();
    }

    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public Permission read(Long id) {
        return permissionDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
       return permissionDao.count();
    }

    @Override
    @CacheEvict(value = "permissionCache", key = "#id")
    public void delete(Long id) {
        permissionDao.delete(id);
    }

    @Override
    @CacheEvict(value = "permissionCache", key = "#permission.id")
    public void delete(Permission permission) {
        permissionDao.delete(permission);
    }
}