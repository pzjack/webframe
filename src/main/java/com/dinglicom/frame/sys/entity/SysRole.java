/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.entity;

import com.dinglicom.frame.entity.IdEntity;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author panzhen
 */
@Entity
@Table(name = "sys_roles")
public class SysRole extends IdEntity implements java.io.Serializable {

    private String role; //角色标识 程序中判断使用,如"admin"
    private String description; //角色描述,UI界面显示使用
    private Boolean available = Boolean.FALSE; //是否可用,如果不可用将不会添加给用户
    private Collection<Permission> permissions;
//    private Collection<SysUserAccount> sysUserAccounts;

    public SysRole() {
    }

    public SysRole(String role, String description, Boolean available) {
        this.role = role;
        this.description = description;
        this.available = available;
    }

    /**
     * @return the role
     */
    @Column(name = "role_name")
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the available
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * @param available the available to set
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

//    /**
//     * @return the sysUserAccounts
//     */
//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
//    public Collection<SysUserAccount> getSysUserAccounts() {
//        return sysUserAccounts;
//    }
//
//    /**
//     * @param sysUserAccounts the sysUserAccounts to set
//     */
//    public void setSysUserAccounts(Collection<SysUserAccount> sysUserAccounts) {
//        this.sysUserAccounts = sysUserAccounts;
//    }

    /**
     * @return the permissions
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "sys_roles_permissions", 
            joinColumns = @JoinColumn(name = "role_id"), 
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    public Collection<Permission> getPermissions() {
        return permissions;
    }
    
    public void addPermission(Permission permission) {
        if(null == permissions) {
            permissions = new HashSet<>();
        }
        if(!permissions.contains(permission)) {
            permissions.add(permission);
        }
    }
    
    public void removePermission(Permission permission) {
         if(null != permissions) {
             if(permissions.contains(permission)) {
                 permissions.remove(permission);
             }
         }
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(Collection<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysRole roleTmp = (SysRole) o;

        return !(id != null ? !id.equals(roleTmp.id) : roleTmp.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SysRole{"
                + "id=" + id
                + ", role='" + role + '\''
                + ", description='" + description + '\''
                + ", available=" + available
                + '}';
    }
}
