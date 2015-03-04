/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.app.entity;

import com.dinglicom.frame.entity.EntityExt;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author panzhen
 */
@Entity
@Table(name = "app_update")
public class AppUpdate extends EntityExt implements Serializable {
    private static final long serialVersionUID = 1L;
    private String version;
    private Integer versioncode;
    private String info;
    private String type;
    private String url;
    private Boolean forceupdate = Boolean.FALSE;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AppUpdate)) {
            return false;
        }
        AppUpdate other = (AppUpdate) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.dinglicom.app.entity.AppUpdate[ id=" + id + " ]";
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the forceupdate
     */
    public Boolean getForceupdate() {
        return forceupdate;
    }

    /**
     * @param forceupdate the forceupdate to set
     */
    public void setForceupdate(Boolean forceupdate) {
        this.forceupdate = forceupdate;
    }

    /**
     * @return the versioncode
     */
    public Integer getVersioncode() {
        return versioncode;
    }

    /**
     * @param versioncode the versioncode to set
     */
    public void setVersioncode(Integer versioncode) {
        this.versioncode = versioncode;
    }

}
