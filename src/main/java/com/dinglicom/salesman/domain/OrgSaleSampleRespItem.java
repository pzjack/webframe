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
public class OrgSaleSampleRespItem {
    private Long user_id;
    private String name;
    private String manager;
    private Long sale_num;
    private String role;
    
    public OrgSaleSampleRespItem () {}
    public OrgSaleSampleRespItem (Long oid, String orgname, String manager, Long salenum, String type) {
        this.user_id = oid;
        this.name = orgname;
        this.manager = manager;
        this.sale_num = salenum;
        this.role = type;
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
     * @return the sale_num
     */
    public Long getSale_num() {
        return sale_num;
    }

    /**
     * @param sale_num the sale_num to set
     */
    public void setSale_num(Long sale_num) {
        this.sale_num = sale_num;
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
