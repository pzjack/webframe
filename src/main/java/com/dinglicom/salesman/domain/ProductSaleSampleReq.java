/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.salesman.domain;

import com.dinglicom.frame.sys.domain.AppPageReqBase;

/**
 *
 * @author panzhen
 */
public class ProductSaleSampleReq extends AppPageReqBase {
    protected String type;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
