/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author panzhen
 */
public class UserOrderPageItemResp {
    private String order_no;
    private String order_status;
    private String product_name;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date start_date;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date end_date;
    private int send_num;
    private int left_num;
    
    public UserOrderPageItemResp() {}
    public UserOrderPageItemResp(String orderno, String orderstate, String productname, Date begintime, Date endtime, int totals, int complete) {
        this.order_no = orderno;
        this.order_status = orderstate;
        this.product_name = productname;
        this.start_date = begintime;
        this.end_date = endtime;
        this.send_num = complete;
        this.left_num = totals - complete;
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
     * @return the order_status
     */
    public String getOrder_status() {
        return order_status;
    }

    /**
     * @param order_status the order_status to set
     */
    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    /**
     * @return the product_name
     */
    public String getProduct_name() {
        return product_name;
    }

    /**
     * @param product_name the product_name to set
     */
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    /**
     * @return the start_date
     */
    public Date getStart_date() {
        return start_date;
    }

    /**
     * @param start_date the start_date to set
     */
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    /**
     * @return the end_date
     */
    public Date getEnd_date() {
        return end_date;
    }

    /**
     * @param end_date the end_date to set
     */
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    /**
     * @return the send_num
     */
    public long getSend_num() {
        return send_num;
    }

    /**
     * @param send_num the send_num to set
     */
    public void setSend_num(int send_num) {
        this.send_num = send_num;
    }

    /**
     * @return the left_num
     */
    public long getLeft_num() {
        return left_num;
    }

    /**
     * @param left_num the left_num to set
     */
    public void setLeft_num(int left_num) {
        this.left_num = left_num;
    }
}
