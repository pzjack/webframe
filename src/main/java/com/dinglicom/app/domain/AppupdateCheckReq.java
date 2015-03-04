/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.app.domain;

/**
 *
 * @author panzhen
 */
public class AppupdateCheckReq {
    private Integer curVersionCode;
    private String type;

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
}