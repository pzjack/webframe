/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.product.domain;

import com.dinglicom.frame.sys.domain.AdminReqBase;

/**
 *
 * @author panzhen
 */
public class AddProductReq extends AdminReqBase {

    private long product_id;
    private String product_name;
    private String big_pic;
    private String small_pic;
    private String short_desc;
    private double weight;
    private String brand;
    private String model;
    private double price;
    private double home_price;
    private Integer type_desc = 1;
    private Integer view_range = 1;
    private String short_name;

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
     * @return the short_desc
     */
    public String getShort_desc() {
        return short_desc;
    }

    /**
     * @param short_desc the short_desc to set
     */
    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    /**
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the home_price
     */
    public double getHome_price() {
        return home_price;
    }

    /**
     * @param home_price the home_price to set
     */
    public void setHome_price(double home_price) {
        this.home_price = home_price;
    }

    /**
     * @return the type_desc
     */
    public Integer getType_desc() {
        return type_desc;
    }

    /**
     * @param type_desc the type_desc to set
     */
    public void setType_desc(Integer type_desc) {
        this.type_desc = type_desc;
    }

    /**
     * @return the view_range
     */
    public Integer getView_range() {
        return view_range;
    }

    /**
     * @param view_range the view_range to set
     */
    public void setView_range(Integer view_range) {
        this.view_range = view_range;
    }

    /**
     * @return the short_name
     */
    public String getShort_name() {
        return short_name;
    }

    /**
     * @param short_name the short_name to set
     */
    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }
}
