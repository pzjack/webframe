/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dispatch.domain;

import com.dinglicom.frame.sys.domain.AppRequestBase;

/**
 *
 * @author panzhen
 */
public class TaskDetailReq extends AppRequestBase {
    private String order_no;

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
