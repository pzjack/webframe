/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.web;

import com.dinglicom.frame.sys.entity.Permission;
import com.dinglicom.frame.sys.entity.SysRole;
import com.dinglicom.frame.sys.service.PermissionService;
import com.dinglicom.frame.sys.service.SysRoleService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author panzhen
 */
@Controller
@RequestMapping(value = "/api/v1/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private PermissionService permissionService;

    @RequestMapping(value = "/save")
    public @ResponseBody
    SysRole save(@RequestBody SysRole sysRole) {
        return sysRoleService.save(sysRole);
    }

    @RequestMapping(value = "/addpms")
    public @ResponseBody
    SysRole addPermisses(@RequestBody SysRole sysRole) {
        SysRole role = sysRoleService.read(sysRole.getId());
        Iterable<Permission> its = permissionService.findAll();
        Iterator<Permission> it = its.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (2 == i) {
                break;
            }
            role.addPermission(it.next());
            i++;
        }
        return sysRoleService.save(role);
    }

    @RequestMapping(value = "/deletepms")
    public @ResponseBody
    SysRole deletePermisses(@RequestBody SysRole sysRole) {
        SysRole role = sysRoleService.read(sysRole.getId());
        Collection<Permission> cln = role.getPermissions();
        if (null != cln) {
            List<Permission> deleteList = new ArrayList<Permission>();
            Iterator<Permission> it = cln.iterator();
            while (it.hasNext()) {
                Permission p = it.next();
                if (p.getId() > 2L) {
                    deleteList.add(p);
                }
            }
            for (Permission p : deleteList) {
                role.removePermission(p);
            }
        }

        return sysRoleService.save(role);
    }
}
