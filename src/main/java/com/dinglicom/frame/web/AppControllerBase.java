/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.web;

import com.dinglicom.frame.sys.domain.AppRequestBase;
import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.SysUserAccount;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysTokenService;

/**
 *
 * @author panzhen
 */
public class AppControllerBase {

    public UserInfo validateToken(SysTokenService sysTokenService, AppRequestBase req, BaseMsgBean msg) {
       return validateToken(sysTokenService, msg, req.getUid(), req.getToken());
    }

    public UserInfo validateToken(SysTokenService sysTokenService, BaseMsgBean msg, long uid, String token) {
        UserInfo userInfo = null;
        try {
            userInfo = sysTokenService.findValidTokenAndUserInfo(token, uid);
            if (null == userInfo) {
                msg.setCode(1);
                msg.setResult("无相关用户信息");
            }
        } catch (Exception ex) {
            msg.setCode(1);
            msg.setResult("无有效权限");
        }
        return userInfo;
    }
    
    
    public SysUserAccount validateTokenAccount(SysTokenService sysTokenService, AppRequestBase req, BaseMsgBean msg) {
        SysUserAccount sysUserAccount = null;
        try {
            sysUserAccount = sysTokenService.findValidTokenAndSysUserAccount(req.getToken(), req.getUid());
            if (null == sysUserAccount) {
                msg.setCode(1);
                msg.setResult("无相关用户信息");
            }
        } catch (Exception ex) {
            msg.setCode(1);
            msg.setResult("无有效权限");
        }
        return sysUserAccount;
    }
}
