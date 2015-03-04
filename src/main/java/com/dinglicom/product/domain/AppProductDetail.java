/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.product.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;

/**
 * APP商品请求应答结果详情
 * @author panzhen
 */
public class AppProductDetail  extends BaseMsgBean {
    protected long product_id;
    protected String product_name;
    protected String big_pic;
    protected String short_desc;
    protected String brand;
    protected double weight;
    protected String model;
    protected double price;
    protected double home_price;

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

}
