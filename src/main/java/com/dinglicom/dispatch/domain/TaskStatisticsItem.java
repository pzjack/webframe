/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dispatch.domain;

/**
 *
 * @author panzhen
 */
public class TaskStatisticsItem {
    private long product_id;
    private String product_name;
    private long product_send_num;
    private long product_left_num;
    
    public TaskStatisticsItem() {}
    public TaskStatisticsItem(long product_id, String product_name, long product_send_num, long product_confirm_num) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_send_num = product_confirm_num;
        this.product_left_num = product_send_num - product_confirm_num;
    }

    /**
     * @return the product_id
     */
    public long getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(long product_id) {
        this.product_id = product_id;
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
     * @return the product_send_num
     */
    public long getProduct_send_num() {
        return product_send_num;
    }

    /**
     * @param product_send_num the product_send_num to set
     */
    public void setProduct_send_num(long product_send_num) {
        this.product_send_num = product_send_num;
    }

    /**
     * @return the product_left_num
     */
    public long getProduct_left_num() {
        return product_left_num;
    }

    /**
     * @param product_left_num the product_left_num to set
     */
    public void setProduct_left_num(long product_left_num) {
        this.product_left_num = product_left_num;
    }
}