/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.salesman.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class ProductSaleSampleResp extends BaseMsgBean {
    private Long total_sales;
    private List<ProductSaleSampleRespItem> data;

    /**
     * @return the total_sales
     */
    public Long getTotal_sales() {
        return total_sales;
    }

    /**
     * @param total_sales the total_sales to set
     */
    public void setTotal_sales(Long total_sales) {
        this.total_sales = total_sales;
    }

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
