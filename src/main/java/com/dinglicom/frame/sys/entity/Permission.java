/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.entity;

import com.dinglicom.frame.entity.IdEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author panzhen
 */
@Entity
@Table(name = "sys_permissions")
public class Permission extends IdEntity implements java.io.Serializable {
    private String permission; //权限标识 程序中判断使用,如"user:create"
    private String description; //权限描述,UI界面显示使用
    private Boolean available = Boolean.FALSE; //是否可用,如果不可用将不会添加给用户
//    private Collection<SysRole> roles;

    public Permission() {
    }

    public Permission(String permission, String description, Boolean available) {
        this.permission = permission;
        this.description = description;
        this.available = available;
    }

    /**
     * @return the permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * @param permission the permission to set
     */
    public void setPermission(String permission) {
        this.permission = permission;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Permission p = (Permission) o;

        return !(id != null ? !id.equals(p.id) : p.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Permission{"
                + "id=" + id
                + ", permission='" + permission + '\''
                + ", description='" + description + '\''
                + ", available=" + available
                + '}';
    }

//    /**
//     * @return the roles
//     */
//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
//    public Collection<SysRole> getRoles() {
//        return roles;
//    }
//
//    /**
//     * @param roles the roles to set
//     */
//    public void setRoles(Collection<SysRole> roles) {
//        this.roles = roles;
//    }
}
