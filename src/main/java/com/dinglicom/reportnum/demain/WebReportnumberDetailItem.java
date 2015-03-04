/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

/**
 *
 * @author panzhen
 */
public class WebReportnumberDetailItem {
    private String product_name;
    private long report_num;
    
    public WebReportnumberDetailItem() {}
    public WebReportnumberDetailItem(String product_name, long report_num) {
        this.product_name = product_name;
        this.report_num = report_num;
    }

    /**
     * @return the product_name
     */
    public String getProduct_name() {
        return product_name;
    }

    /**
     * @param product_name the product_name to set
     */
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    /**
     * @return the report_num
     */
    public long getReport_num() {
        return report_num;
    }

    /**
     * @param report_num the report_num to set
     */
    public void setReport_num(long report_num) {
        this.report_num = report_num;
    }
    
}
