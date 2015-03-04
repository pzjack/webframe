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
public class CustomerDetailResp extends UserDetailMsg {
    private long unfinished_order;
    private long finished_order;

    /**
     * @return the unfinished_order
     */
    public long getUnfinished_order() {
        return unfinished_order;
    }

    /**
     * @param unfinished_order the unfinished_order to set
     */
    public void setUnfinished_order(long unfinished_order) {
        this.unfinished_order = unfinished_order;
    }

    /**
     * @return the finished_order
     */
    public long getFinished_order() {
        return finished_order;
    }

    /**
     * @param finished_order the finished_order to set
     */
    public void setFinished_order(long finished_order) {
        this.finished_order = finished_order;
    }
}
