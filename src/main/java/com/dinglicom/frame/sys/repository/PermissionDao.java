/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.repository;

import com.dinglicom.frame.sys.entity.Permission;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author panzhen
 */
public interface PermissionDao  extends CrudRepository<Permission, Long> {
    
}
