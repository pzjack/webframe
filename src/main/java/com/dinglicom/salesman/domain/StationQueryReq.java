/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.salesman.domain;

import com.dinglicom.frame.sys.domain.AppPageReqBase;

/**
 *
 * @author panzhen
 */
public class StationQueryReq extends AppPageReqBase {
    private String role;

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