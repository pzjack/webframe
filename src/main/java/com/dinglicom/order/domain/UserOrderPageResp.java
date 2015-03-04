/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class UserOrderPageResp extends BaseMsgBean {

    private int total_page;
    private List<UserOrderPageItemResp> data;

    /**
     * @return the total_page
     */
    public int getTotal_page() {
        return total_page;
    }

    /**
     * @param total_page the total_page to set
     */
    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    /**
     * @return the data
     */
    public List<UserOrderPageItemResp> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<UserOrderPageItemResp> data) {
        this.data = data;
    }
}
