/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.domain;

/**
 *
 * @author panzhen
 */
public class CityAddReq extends AdminReqBase{
    private long pid;
    private long cid;
    private String city;

    /**
     * @return the pid
     */
    public long getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(long pid) {
        this.pid = pid;
    }
    /**
     * @return the cid
     */
    public long getCid() {
        return cid;
    }

    /**
     * @param cid the cid to set
     */
    public void setCid(long cid) {
        this.cid = cid;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }
}
