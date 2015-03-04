/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

import java.util.List;

/**
 *
 * @author panzhen
 */
public class WebReportlistItemResp {
    private String org_name;
    private Long  total_amount;
    private Double total_price;
    private List<WebReportlistDetailItem> detail;

    /**
     * @return the org_name
     */
    public String getOrg_name() {
        return org_name;
    }

    /**
     * @param org_name the org_name to set
     */
    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    /**
     * @return the total_amount
     */
    public Long getTotal_amount() {
        return total_amount;
    }

    /**
     * @param total_amount the total_amount to set
     */
    public void setTotal_amount(Long total_amount) {
        this.total_amount = total_amount;
    }

    /**
     * @return the detail
     */
    public List<WebReportlistDetailItem> getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(List<WebReportlistDetailItem> detail) {
        this.detail = detail;
    }

    /**
     * @return the total_price
     */
    public Double getTotal_price() {
        return total_price;
    }

    /**
     * @param total_price the total_price to set
     */
    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }
}
