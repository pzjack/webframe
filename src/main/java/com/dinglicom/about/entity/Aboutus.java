/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.entity;

import com.dinglicom.frame.entity.EntityExt;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author panzhen
 */
@Entity
@Table(name = "about_us")
public class Aboutus  extends EntityExt implements Serializable {
    private String order_tel;//订购热线
    private String complaint_tel;//投诉热线
    private String official_site;//官方网站
    private String order_site;//订购网站
    private String wechat;//官方微信号
    private String email;//企业邮箱
    private String address;//公司地址

    /**
     * @return the order_tel
     */
    public String getOrder_tel() {
        return order_tel;
    }

    /**
     * @param order_tel the order_tel to set
     */
    public void setOrder_tel(String order_tel) {
        this.order_tel = order_tel;
    }

    /**
     * @return the complaint_tel
     */
    public String getComplaint_tel() {
        return complaint_tel;
    }

    /**
     * @param complaint_tel the complaint_tel to set
     */
    public void setComplaint_tel(String complaint_tel) {
        this.complaint_tel = complaint_tel;
    }

    /**
     * @return the official_site
     */
    public String getOfficial_site() {
        return official_site;
    }

    /**
     * @param official_site the official_site to set
     */
    public void setOfficial_site(String official_site) {
        this.official_site = official_site;
    }

    /**
     * @return the order_site
     */
    public String getOrder_site() {
        return order_site;
    }

    /**
     * @param order_site the order_site to set
     */
    public void setOrder_site(String order_site) {
        this.order_site = order_site;
    }

    /**
     * @return the wechat
     */
    public String getWechat() {
        return wechat;
    }

    /**
     * @param wechat the wechat to set
     */
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
