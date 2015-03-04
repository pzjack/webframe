/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.domain;

import com.dinglicom.frame.sys.domain.AppPageReqBase;

/**
 *
 * @author panzhen
 */
public class FeedQueryReq extends AppPageReqBase {

    private long mid;
    private Integer status;

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
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}
