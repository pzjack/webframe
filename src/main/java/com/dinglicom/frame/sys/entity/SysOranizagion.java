/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.entity;

import com.dinglicom.frame.entity.EntityExt;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author panzhen
 */
@Entity
@Table(name = "sys_oranizagion")
public class SysOranizagion extends EntityExt implements java.io.Serializable {
    private SysOranizagion parent;
    private String type;
    private String name;
    private String code;
    private String desc;
    private String phone;
    private String postcode;
    private String address;
    private String state;
    private UserInfo userinfo;
    private String responsible_man;
    private String responsible_phone;
    private SysOranizagion province;
    private SysOranizagion city;
    private SysOranizagion region;
    private String province_name;
    private String city_name;
    private String region_name;

    /**
     * @return the parent
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    public SysOranizagion getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(SysOranizagion parent) {
        this.parent = parent;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the desc
     */
    @Column(name = "description")
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
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode the postcode to set
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
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
     * @return the state
     */
    @Column(name = "sys_state")
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the userinfo
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserInfo getUserinfo() {
        return userinfo;
    }

    /**
     * @param userinfo the userinfo to set
     */
    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    /**
     * @return the responsible_man
     */
    public String getResponsible_man() {
        return responsible_man;
    }

    /**
     * @param responsible_man the responsible_man to set
     */
    public void setResponsible_man(String responsible_man) {
        this.responsible_man = responsible_man;
    }

    /**
     * @return the responsible_phone
     */
    public String getResponsible_phone() {
        return responsible_phone;
    }

    /**
     * @param responsible_phone the responsible_phone to set
     */
    public void setResponsible_phone(String responsible_phone) {
        this.responsible_phone = responsible_phone;
    }

    /**
     * @return the province
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    public SysOranizagion getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(SysOranizagion province) {
        this.province = province;
    }

    /**
     * @return the city
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    public SysOranizagion getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(SysOranizagion city) {
        this.city = city;
    }

    /**
     * @return the region
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    public SysOranizagion getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(SysOranizagion region) {
        this.region = region;
    }

    /**
     * @return the province_name
     */
    public String getProvince_name() {
        return province_name;
    }

    /**
     * @param province_name the province_name to set
     */
    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    /**
     * @return the city_name
     */
    public String getCity_name() {
        return city_name;
    }

    /**
     * @param city_name the city_name to set
     */
    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    /**
     * @return the region_name
     */
    public String getRegion_name() {
        return region_name;
    }

    /**
     * @param region_name the region_name to set
     */
    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
}
