/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.service.impl;

import com.dinglicom.order.entity.UserAddress;
import com.dinglicom.order.repository.UserAddressDao;
import com.dinglicom.order.service.UserAddressService;
import java.util.Iterator;
import javax.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
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
@CacheConfig(cacheNames = {"dataCache"})
public class UserAddressServiceImpl implements UserAddressService {

    @Resource
    private UserAddressDao userAddressDao;
    @Override
    @Transactional(readOnly = true)
    @Cacheable
    public UserAddress read(long id) {
        return userAddressDao.findOne(id);
    }

    @Override
    @Cacheable
    public UserAddress save(UserAddress userAddress) {
        return userAddressDao.save(userAddress);
    }

    @Override
    @CacheEvict
    public void delete(UserAddress userAddress) {
        userAddressDao.delete(userAddress);
    }

    @Override
    @CacheEvict
    public void delete(long id) {
        userAddressDao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<UserAddress> findByUserId(Long userId) {
        Page<UserAddress> page = userAddressDao.findByUserInfoId(new PageRequest(0, 20), false, userId);
        return page.getContent();
    }

    @Override
    public UserAddress findFirstByUserId(Long userId) {
        Iterator<UserAddress> it = findByUserId(userId).iterator();
        if(it.hasNext()) {
            return it.next();
        }
        return null;
    }
}