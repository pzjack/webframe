/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.salesman.domain;

/**
 *
 * @author panzhen
 */
public class ProductSaleSampleRespItem {
    
    private Long product_id;
    private String name;
    private Long sale_num;
    
    public ProductSaleSampleRespItem() {}
    public ProductSaleSampleRespItem(Long pid, String productname, Long reportnum) {
        this.product_id = pid;
        this.name = productname;
        this.sale_num = reportnum;
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the sale_num
     */
    public Long getSale_num() {
        return sale_num;
    }

    /**
     * @param sale_num the sale_num to set
     */
    public void setSale_num(Long sale_num) {
        this.sale_num = sale_num;
    }

}
