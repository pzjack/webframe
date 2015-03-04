/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.product.domain.ProductItem;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class WebReportlistResp extends BaseMsgBean {
    private long total_num;
    private List<ProductItem> products;
    private List<WebReportlistItemResp> data;

    /**
     * @return the data
     */
    public List<WebReportlistItemResp> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<WebReportlistItemResp> data) {
        this.data = data;
    }

    /**
     * @return the total_num
     */
    public long getTotal_num() {
        return total_num;
    }

    /**
     * @param total_num the total_num to set
     */
    public void setTotal_num(long total_num) {
        this.total_num = total_num;
    }

    /**
     * @return the products
     */
    public List<ProductItem> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<ProductItem> products) {
        this.products = products;
    }
}
