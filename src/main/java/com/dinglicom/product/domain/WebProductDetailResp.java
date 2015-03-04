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
public class WebProductDetailResp extends AppProductDetail {
    private String small_pic;
    private Integer type_desc = 1;
    private Integer view_range = 1;
    private String short_name;

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