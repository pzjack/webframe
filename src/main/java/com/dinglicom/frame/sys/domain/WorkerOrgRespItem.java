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
public class WorkerOrgRespItem {
    private long shop_id;
    private String shop_name;
    private String address;
    
    public WorkerOrgRespItem() {} 
    public WorkerOrgRespItem(long id, String name, String address) {
        shop_id = id;
        shop_name = name;
        this.address = address;
    }

    /**
     * @return the shop_id
     */
    public long getShop_id() {
        return shop_id;
    }

    /**
     * @param shop_id the shop_id to set
     */
    public void setShop_id(long shop_id) {
        this.shop_id = shop_id;
    }

    /**
     * @return the shop_name
     */
    public String getShop_name() {
        return shop_name;
    }

    /**
     * @param shop_name the shop_name to set
     */
    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
