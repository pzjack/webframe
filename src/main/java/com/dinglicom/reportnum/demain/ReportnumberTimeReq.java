/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

import com.dinglicom.frame.sys.domain.AdminReqBase;

/**
 *
 * @author panzhen
 */
public class ReportnumberTimeReq extends AdminReqBase {
    private String dead_time;

    /**
     * @return the dead_time
     */
    public String getDead_time() {
        return dead_time;
    }

    /**
     * @param dead_time the dead_time to set
     */
    public void setDead_time(String dead_time) {
        this.dead_time = dead_time;
    }
}