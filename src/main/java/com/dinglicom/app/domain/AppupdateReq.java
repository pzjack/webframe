/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.app.domain;

import com.dinglicom.frame.sys.domain.AdminReqBase;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author panzhen
 */
public class AppupdateReq extends AdminReqBase {
    private Long pid;
    private String versionName;
    private Integer versionCode;
    private String info;
    private String type;
    private String url;
    private Boolean forceUpdate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time;
    
    public AppupdateReq() {}
    public AppupdateReq(Long pid, String version, Integer versionCode, String info, String type, String url, Boolean forceUpdate, Date time) {
        this.pid = pid;
        this.versionName = version;
        this.versionCode = versionCode;
        this.info = info;
        this.type = type;
        this.url = url;
        this.forceUpdate = forceUpdate;
        this.time = time;
    }

    /**
     * @return the pid
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(Long pid) {
        this.pid = pid;
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
     * @return the forceUpdate
     */
    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    /**
     * @param forceUpdate the forceUpdate to set
     */
    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the versionName
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * @param versionName the versionName to set
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    /**
     * @return the versionCode
     */
    public Integer getVersionCode() {
        return versionCode;
    }

    /**
     * @param versionCode the versionCode to set
     */
    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }
}
