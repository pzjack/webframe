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
public class DlvryPageItem {

    private long did;
    private String name;
    private String manager;
    private String tel;
    private String desc;
    private String province;
    private String city;
    private String region;
    
    public DlvryPageItem() {}
    public DlvryPageItem(long did, String name, String manager, String tel, String desc, String province, String city, String region) {
        this.did = did;
        this.name = name;
        this.manager = manager;
        this.tel = tel;
        this.desc = desc;
        this.province = province;
        this.city = city;
        this.region = region;
    }

    /**
     * @return the did
     */
    public long getDid() {
        return did;
    }

    /**
     * @param did the did to set
     */
    public void setDid(long did) {
        this.did = did;
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
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
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
}
