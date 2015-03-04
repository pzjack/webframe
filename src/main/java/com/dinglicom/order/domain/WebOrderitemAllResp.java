/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.domain;

/**
 *
 * @author panzhen
 */
public class WebOrderitemAllResp extends WebQueryOrderitemRespBase {
    private long total_page;
//    private WebOrderCounter stat_num;

    /**
     * @return the total_page
     */
    public long getTotal_page() {
        return total_page;
    }

    /**
     * @param total_page the total_page to set
     */
    public void setTotal_page(long total_page) {
        this.total_page = total_page;
    }
}
