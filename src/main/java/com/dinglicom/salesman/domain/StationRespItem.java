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
public class StationRespItem {

    private Long user_id;
    private String name;
    private String manager;
    private String mobile_phone;
    
    public StationRespItem () {}
    public StationRespItem(Long id, String name, String manager, String phone) {
        this.user_id = id;
        this.name = name;
        this.manager = manager;
        this.mobile_phone = phone;
    }

    /**
     * @return the user_id
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
     * @return the manager
     */
    public String getManager() {
        return manager;
    }

    /**
     * @param manager the manager to set
     */
    public void setManager(String manager) {
        this.manager = manager;
    }

    /**
     * @return the mobile_phone
     */
    public String getMobile_phone() {
        return mobile_phone;
    }

    /**
     * @param mobile_phone the mobile_phone to set
     */
    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }
}
