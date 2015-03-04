/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.service;

import com.dinglicom.frame.sys.entity.Permission;

/**
 *
 * @author panzhen
 */
public interface PermissionService {
    Permission read(Long id);
    void delete(Long id);
    void delete(Permission permission);
    Permission save(Permission permission);
    Iterable<Permission> findAll();
    long count();
}
