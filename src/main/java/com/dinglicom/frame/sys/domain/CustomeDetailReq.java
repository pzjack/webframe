/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.domain;

/**
 *
 * @author panzhen
 */
public class CustomeDetailReq extends AppRequestBase {
    private long user_id;
    private String order_no;

    /**
     * @return the user_id
     */
    public long getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(long user_id) {
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
}