/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.app.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author panzhen
 */
public class AppcheckItem {
    private Integer curVersionCode;
    private Integer newVersionCode;
    private String info;
    private String url;
    private Boolean forceUpdate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time;
    public AppcheckItem() {}
    
    public AppcheckItem(Integer versionCode, String info, String url, Boolean forceUpdate) {
        this.newVersionCode = versionCode;
        this.info = info;
        this.url = url;
        this.forceUpdate = forceUpdate;
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
     * @return the curVersionCode
     */
    public Integer getCurVersionCode() {
        return curVersionCode;
    }

    /**
     * @param curVersionCode the curVersionCode to set
     */
    public void setCurVersionCode(Integer curVersionCode) {
        this.curVersionCode = curVersionCode;
    }

    /**
     * @return the newVersionCode
     */
    public Integer getNewVersionCode() {
        return newVersionCode;
    }

    /**
     * @param newVersionCode the newVersionCode to set
     */
    public void setNewVersionCode(Integer newVersionCode) {
        this.newVersionCode = newVersionCode;
    }
}
