/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.domain;

import com.dinglicom.frame.sys.domain.AppRequestBase;

/**
 *
 * @author panzhen
 */
public class OrderStateChangeReq extends AppRequestBase {
    private Long user_id;
    private String order_no;
    private Integer stop_days;
    private Long product_id;

    /**
     * @return the user_id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the order_no
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * @param order_no the order_no to set
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    /**
     * @return the stop_days
     */
    public Integer getStop_days() {
        return stop_days;
    }

    /**
     * @param stop_days the stop_days to set
     */
    public void setStop_days(Integer stop_days) {
        this.stop_days = stop_days;
    }

    /**
     * @return the product_id
     */
    public Long getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }
}
