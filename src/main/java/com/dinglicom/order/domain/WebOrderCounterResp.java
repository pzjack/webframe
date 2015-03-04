/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;

/**
 *
 * @author panzhen
 */
public class WebOrderCounterResp extends BaseMsgBean {
    private WebOrderCounter data;

    /**
     * @return the data
     */
    public WebOrderCounter getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(WebOrderCounter data) {
        this.data = data;
    }
}
