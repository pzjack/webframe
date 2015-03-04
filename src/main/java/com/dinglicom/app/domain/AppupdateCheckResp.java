/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.app.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;

/**
 *
 * @author panzhen
 */
public class AppupdateCheckResp extends BaseMsgBean {

    private Integer curVersionCode;
    private Integer newVersionCode;
    private String info;
    private String url;
    private Boolean forceUpdate;
    
    public AppupdateCheckResp() {}

    public AppupdateCheckResp(Integer versionCode, String info, String url, Boolean forceUpdate) {
        this.newVersionCode = versionCode;
        this.info = info;
        this.url = url;
        this.forceUpdate = forceUpdate;
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
}
