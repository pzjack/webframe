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
public class AboutusResp extends BaseMsgBean {

    private AboutusItemResp data;

    /**
     * @return the data
     */
    public AboutusItemResp getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(AboutusItemResp data) {
        this.data = data;
    }
}
