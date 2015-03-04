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
public class AppupdateResp extends BaseMsgBean {
    private Long pid;

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
}
