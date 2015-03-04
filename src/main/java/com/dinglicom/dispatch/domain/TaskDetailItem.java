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
public class TaskDetailItem {
    private long product_id;
    private String product_name;
    private int distribution_num;
    
    public TaskDetailItem() {}
    public TaskDetailItem (long product_id, String product_name, int distribution_num) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.distribution_num = distribution_num;
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
     * @return the distribution_num
     */
    public int getDistribution_num() {
        return distribution_num;
    }

    /**
     * @param distribution_num the distribution_num to set
     */
    public void setDistribution_num(int distribution_num) {
        this.distribution_num = distribution_num;
    }
}
