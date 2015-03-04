/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.service;

import com.dinglicom.frame.sys.entity.Permission;
import com.dinglicom.frame.sys.entity.SysRole;
import java.util.Collection;
import java.util.Set;

/**
 *
 * @author panzhen
 */
public interface SysRoleService {
    SysRole save(SysRole sysRole);
    SysRole read(long id);
    void delete(long id);
    void delete(SysRole id);
    Iterable<SysRole> getAll();
    long count();
    Collection<Permission> findPermissionBySysRole(SysRole sysRole);
    Set<String> getPermissionString(Collection<Permission> permissions);
    Collection<Permission> findPermissionBySysRole(Iterable<SysRole> sysRoles);
}
