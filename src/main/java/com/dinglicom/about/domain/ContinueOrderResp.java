/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;

/**
 *
 * @author panzhen
 */
public class ContinueOrderResp extends BaseMsgBean {
    private Integer days;

    /**
     * @return the days
     */
    public Integer getDays() {
        return days;
    }

    /**
     * @param days the days to set
     */
    public void setDays(Integer days) {
        this.days = days;
    }
}
