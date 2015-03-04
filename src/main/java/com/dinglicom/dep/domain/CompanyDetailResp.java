/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dep.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;

/**
 *
 * @author panzhen
 */
public class CompanyDetailResp  extends BaseMsgBean {
    private CompanyDetail data;

    /**
     * @return the data
     */
    public CompanyDetail getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(CompanyDetail data) {
        this.data = data;
    }
}
