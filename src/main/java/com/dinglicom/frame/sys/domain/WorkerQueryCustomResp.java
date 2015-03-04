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
public class WorkerQueryCustomResp extends AppUserInfoPage {
    private int total_page;

    /**
     * @return the total_page
     */
    public int getTotal_page() {
        return total_page;
    }

    /**
     * @param total_page the total_page to set
     */
    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }
}
