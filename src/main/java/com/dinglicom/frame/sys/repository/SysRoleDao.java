/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.repository;

import com.dinglicom.frame.sys.entity.Permission;
import com.dinglicom.frame.sys.entity.SysRole;
import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface SysRoleDao  extends CrudRepository<SysRole, Long> {
    
//    @Query("select p from Permission p join p.roles a where a.id = :id")
    @Query("select distinct a.permissions from SysRole a where a = :sysRole")
    Collection<Permission> findSysUserAccountRolesBySysUserAccount(@Param("sysRole")SysRole sysRole);
}
