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
public class UserDetailMsg extends BaseMsgBean {
    private long user_id;
    private String name;
    private String mobile_phone;
    private Long pid;
    private String province;
    private Long cid;
    private String city;
    private Long rid;
    private String region;
    private String address;

    /**
     * @return the user_id
     */
    public long getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(long user_id) {
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
    public String getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
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
     * @return the pid
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * @return the cid
     */
    public Long getCid() {
        return cid;
    }

    /**
     * @param cid the cid to set
     */
    public void setCid(Long cid) {
        this.cid = cid;
    }

    /**
     * @return the rid
     */
    public Long getRid() {
        return rid;
    }

    /**
     * @param rid the rid to set
     */
    public void setRid(Long rid) {
        this.rid = rid;
    }
}
