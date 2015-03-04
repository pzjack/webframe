/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.product.domain;

/**
 *
 * @author panzhen
 */
public class AppUserProduct {

    protected Long product_id;
    protected String product_name;
    protected Double weight;
    protected Double price;
    protected String small_pic;
    protected String big_pic;
    public AppUserProduct(){}
    public AppUserProduct(Long id, String name, String small_pic, String big_pic, Double weight, Double price){
        this.product_id = id;
        this.product_name = name;
        this.weight = weight;
        this.price = price;
        this.small_pic = small_pic;
        this.big_pic = big_pic;
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
     * @return the weight
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return the small_pic
     */
    public String getSmall_pic() {
        return small_pic;
    }

    /**
     * @param small_pic the small_pic to set
     */
    public void setSmall_pic(String small_pic) {
        this.small_pic = small_pic;
    }

    /**
     * @return the big_pic
     */
    public String getBig_pic() {
        return big_pic;
    }

    /**
     * @param big_pic the big_pic to set
     */
    public void setBig_pic(String big_pic) {
        this.big_pic = big_pic;
    }
}
