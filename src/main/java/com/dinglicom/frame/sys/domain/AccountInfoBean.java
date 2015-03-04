/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.domain;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author panzhen
 */
public class AccountInfoBean implements Serializable {
    private String account;
    private String pwd;
    private String salt;
    private String nickname;
    private String state;
    private long userId;
    private String userType;
    private Set<String> roles;
    private Set<String> permissions;

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the roles
     */
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    /**
     * @return the permissions
     */
    public Set<String> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public String toStringRoles() {
        StringBuilder sb = new StringBuilder();
        if(null != this.getRoles()) {
            Iterator<String> it = this.getRoles().iterator();
            int i = 0;
            while(it.hasNext()) {
                if(i > 0) {
                    sb.append(",");
                }
                sb.append(it.next());
                i++;
            }
        }
        return sb.toString();
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     * @return 
     */
    @Override
    public String toString() {
        return getAccount();
    }

    /**
     * 重载hashCode,只计算loginName;
     * @return 
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getAccount());
    }

    /**
     * 重载equals,只计算loginName;
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AccountInfoBean other = (AccountInfoBean) obj;
        if (getAccount() == null) {
            if (other.getAccount() != null) {
                return false;
            }
        } else if (!account.equals(other.account)) {
            return false;
        }
        return true;
    }
}
