/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.entity;

import com.dinglicom.frame.entity.IdEntity;
import static com.dinglicom.frame.sys.service.SysUserAccountService.NORMAL;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;


/**
 *
 * @author panzhen
 */
//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY,region="authorizationCache")
@Cacheable(true)
@Entity
@Table(name = "sys_account")
public class SysUserAccount extends IdEntity  implements java.io.Serializable {
    private String nickname;
    private String account;
    private String password;
    private String salt;
    private Collection<SysRole> roles;
    private UserInfo userInfo;
    private String zhState = NORMAL;
    
    public SysUserAccount() {
    }
    
    public SysUserAccount(long id) {
        this.id = id;
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
     * @return the account
     */
    @NotBlank
    @Column(name="account", nullable=false, length=60)
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
     * @return the password
     */
    @NotBlank
    @JsonIgnore
    @Column(name="password", nullable=false, length=60)
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @return the zhState
     */
    @Column(name = "zh_state")
    public String getZhState() {
        return zhState;
    }

    /**
     * @param zhState the zhState to set
     */
    public void setZhState(String zhState) {
        this.zhState = zhState;
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
    /**
     * @return the roles
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_users_roles", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Collection<SysRole> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(Collection<SysRole> roles) {
        this.roles = roles;
    }
    
    public String credentialsSalt() {
        return getAccount() + salt;
    }
    
    public Set<String> stringRoles() {
        Set<String> stringRoles = new HashSet<>();
        if (null != getRoles()) {
            Iterator<SysRole> it = getRoles().iterator();
            while (it.hasNext()) {
                SysRole role = it.next();
                stringRoles.add(role.getRole());
            }
        }
        return stringRoles;
    }
    
    public Set<String> stringPermissions() {
       Set<String> permissions = new HashSet<>();
        if (null != getRoles()) {
            Iterator<SysRole> it = getRoles().iterator();
            while (it.hasNext()) {
                SysRole role = it.next();
                if (null != role.getPermissions()) {
                    Iterator<Permission> itp = role.getPermissions().iterator();
                    while (itp.hasNext()) {
                        Permission permission = itp.next();
                        permissions.add(permission.getPermission());
                    }
                }
            }
        }
        return permissions;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUserAccount user = (SysUserAccount) o;
        return !(id != null ? !id.equals(user.id) : user.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ",nickname='" + getNickname() + "'" +
                ", account='" + getAccount() + "'" +
                ", password='" + password + "'" +
                ", salt='" + salt + "'" +
                ", state=" + zhState +
                '}';
    }
}