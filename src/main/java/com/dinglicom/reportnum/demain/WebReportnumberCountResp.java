/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;

/**
 *
 * @author panzhen
 */
public class WebReportnumberCountResp extends BaseMsgBean {
    private WebReportnumberCountDetail data;

    /**
     * @return the data
     */
    public WebReportnumberCountDetail getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(WebReportnumberCountDetail data) {
        this.data = data;
    }
}
