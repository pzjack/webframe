/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.domain;

import com.dinglicom.frame.sys.domain.AdminReqBase;

/**
 *
 * @author panzhen
 */
public class OrderDelvReq extends AdminReqBase {
    private Long delivery_uid;
    private String order_no;

    /**
     * @return the delivery_uid
     */
    public Long getDelivery_uid() {
        return delivery_uid;
    }

    /**
     * @param delivery_uid the delivery_uid to set
     */
    public void setDelivery_uid(Long delivery_uid) {
        this.delivery_uid = delivery_uid;
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
}
