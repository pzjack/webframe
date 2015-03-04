/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.salesample.domain;

/**
 *
 * @author panzhen
 */
public class WebSaleSampleItem {
    private Long uid;
    private Integer rank;
    private String name;
    private Long amount;
    private String manager;
    private String tel;
    
    public WebSaleSampleItem () {}
    public WebSaleSampleItem (Long amount) {
        this.amount = amount;
    }
    
    public WebSaleSampleItem(Long uid, String name, String tel, Long amount) {
        this.uid = uid;
        this.name = name;
        this.tel = tel;
        this.amount = amount;
    }
    
    public WebSaleSampleItem(Long uid, Long amount) {
        this.uid = uid;
        this.amount = amount;
    }

    public WebSaleSampleItem(Long orgid, Long uid, String manager, String tel) {
        this.uid = uid;
        this.manager = manager;
        this.tel = tel;
        this.amount = orgid;
    }
    
        public WebSaleSampleItem(Long amount, Long orgid, String name, String manager, String tel) {
        this.amount = amount;
        this.uid = orgid;
        this.name = name;
        this.manager = manager;
        this.tel = tel;
    }

    /**
     * @return the uid
     */
    public Long getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * @return the rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(Integer rank) {
        this.rank = rank;
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
     * @return the amount
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Long amount) {
        this.amount = amount;
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
}
