/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.service;

import com.dinglicom.order.entity.UserAddress;

/**
 *
 * @author panzhen
 */
public interface UserAddressService {
    UserAddress read(long id);
    UserAddress save(UserAddress userAddress);
    void delete(UserAddress userAddress);
    void delete(long id);
    Iterable<UserAddress> findByUserId(Long userId);
    UserAddress findFirstByUserId(Long userId);
}
