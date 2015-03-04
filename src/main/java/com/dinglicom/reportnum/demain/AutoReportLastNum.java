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
public class AutoReportLastNum {
    private Long productid;
    private Long reportnum;
//    private Long minusnum;//减少报量（退订报量）
//    private Long plusnum;//增加报量
    
    public AutoReportLastNum() {}
    public AutoReportLastNum(Long productid, Long reportnum) {
        this.productid = productid;
        this.reportnum = reportnum;
    }

    /**
     * @return the productid
     */
    public Long getProductid() {
        return productid;
    }

    /**
     * @param productid the productid to set
     */
    public void setProductid(Long productid) {
        this.productid = productid;
    }

    /**
     * @return the reportnum
     */
    public Long getReportnum() {
        return reportnum;
    }

    /**
     * @param reportnum the reportnum to set
     */
    public void setReportnum(Long reportnum) {
        this.reportnum = reportnum;
    }
}
