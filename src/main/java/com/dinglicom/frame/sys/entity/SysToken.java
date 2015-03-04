/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.entity;

import com.dinglicom.frame.entity.IdEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author panzhen
 */
@Entity
@Table(name = "sys_token")
public class SysToken extends IdEntity implements java.io.Serializable {
    private String token;
    private long begintime;
    private String role;
    private SysUserAccount sysUserAccount;
    private UserInfo userInfo;

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the begintime
     */
    public long getBegintime() {
        return begintime;
    }

    /**
     * @param begintime the begintime to set
     */
    public void setBegintime(long begintime) {
        this.begintime = begintime;
    }

    /**
     * @return the role
     */
    @Column(name = "role_name", length=30)
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the sysUserAccount
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    public SysUserAccount getSysUserAccount() {
        return sysUserAccount;
    }

    /**
     * @param sysUserAccount the sysUserAccount to set
     */
    public void setSysUserAccount(SysUserAccount sysUserAccount) {
        this.sysUserAccount = sysUserAccount;
    }

    /**
     * @return the userInfo
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info_id")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * @param userInfo the userInfo to set
     */
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
