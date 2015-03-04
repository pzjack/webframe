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
public class AdminReqBase {
    protected long mid;
    protected String token;

    /**
     * @return the mid
     */
    public long getMid() {
        return mid;
    }

    /**
     * @param mid the mid to set
     */
    public void setMid(long mid) {
        this.mid = mid;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
}
