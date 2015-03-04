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
public class WebUserProduct extends AppUserProduct {
    private String short_name;
    private Integer product_type;
    private Integer view_range;
    
    public WebUserProduct() {}
    public WebUserProduct(Long id, String name, String shortname, String small_pic, String big_pic, Double weight, Double price, Integer productType, Integer viewRange) {
        super(id, name, small_pic, big_pic, weight, price);
        this.short_name = shortname;
        this.product_type = productType;
        this.view_range = viewRange;
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

    /**
     * @return the product_type
     */
    public Integer getProduct_type() {
        return product_type;
    }

    /**
     * @param product_type the product_type to set
     */
    public void setProduct_type(Integer product_type) {
        this.product_type = product_type;
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
}
