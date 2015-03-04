/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.salesample.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.salesman.domain.ProductSaleSampleRespItem;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class WebProductSaleResp extends BaseMsgBean {
    private List<ProductSaleSampleRespItem> data;

    /**
     * @return the data
     */
    public List<ProductSaleSampleRespItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<ProductSaleSampleRespItem> data) {
        this.data = data;
    }

}
