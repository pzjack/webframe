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
public class DepsaleRespItem {
    private Long user_id;
    private String realname;
    private Long sale_num;
    
    public DepsaleRespItem () {}
    public DepsaleRespItem(Long user_id, String realname, Long sale_num) {
        this.user_id = user_id;
        this.realname = realname;
        this.sale_num = sale_num;
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
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname the realname to set
     */
    public void setRealname(String realname) {
        this.realname = realname;
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
}
