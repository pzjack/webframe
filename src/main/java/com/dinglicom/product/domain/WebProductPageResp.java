/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.product.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class WebProductPageResp extends BaseMsgBean {

    private List<WebUserProduct> data;

    /**
     * @return the data
     */
    public List<WebUserProduct> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<WebUserProduct> data) {
        this.data = data;
    }
}
