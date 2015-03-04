/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.domain;

import com.dinglicom.frame.sys.domain.AdminReqBase;

/**
 *
 * @author panzhen
 */
public class PayConfirmReq extends AdminReqBase {
    private Long id;
    private String payee;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the payee
     */
    public String getPayee() {
        return payee;
    }

    /**
     * @param payee the payee to set
     */
    public void setPayee(String payee) {
        this.payee = payee;
    }
}
