/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.domain;

/**
 *
 * @author panzhen
 */
public class AboutusItemResp {
    protected String order_tel;//订购热线
    protected String complaint_tel;//投诉热线
    protected String official_site;//官方网站
    protected String order_site;//订购网站
    protected String wechat;//官方微信号
    protected String email;//企业邮箱
    protected String address;//公司地址

    public AboutusItemResp() {}
    public AboutusItemResp(String order_tel, String complaint_tel, String official_site, String order_site, String wechat, String email, String address) {
        this.order_tel = order_tel;
        this.complaint_tel = complaint_tel;
        this.official_site = official_site;
        this.order_site = order_site;
        this.wechat = wechat;
        this.email = email;
        this.address = address;
    }
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
