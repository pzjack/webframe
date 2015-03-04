/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.web;

import com.dinglicom.frame.sys.entity.Permission;
import com.dinglicom.frame.sys.service.PermissionService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author panzhen
 */
@Controller
@RequestMapping(value = "/api/v1/pmss")
public class PermissionController {
    private final static Logger LOG = LoggerFactory.getLogger(PermissionController.class);
    @Resource
    private PermissionService permissionService;
    
    @RequestMapping(value = "/save")
    public @ResponseBody Permission save(@RequestBody Permission permission) {
        return permissionService.save(permission);
    }
}
