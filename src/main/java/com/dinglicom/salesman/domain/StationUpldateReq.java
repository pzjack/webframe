/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.salesman.domain;

import com.dinglicom.frame.sys.domain.AppRequestBase;

/**
 * 奶站或者经销商新增或者编辑对象
 * @author panzhen
 */
public class StationUpldateReq extends AppRequestBase {

    private Long user_id;
    private String name;
    private String manager;
    private String mobile_phone;
    private Long province;
    private Long city;
    private Long region;
    private String address;
    private String pwd;
    private String role;

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

    /**
     * @return the province
     */
    public Long getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(Long province) {
        this.province = province;
    }

    /**
     * @return the city
     */
    public Long getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(Long city) {
        this.city = city;
    }

    /**
     * @return the region
     */
    public Long getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(Long region) {
        this.region = region;
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

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }
}
