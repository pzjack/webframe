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
public class WebReportlistResult {
    private Long id;
    private String orgname;
    private Long amount;
    private Long productid;
    private String productname;
    private Long productnum;
    private Double total_price;
    
    public WebReportlistResult() {}
    public WebReportlistResult(Long id, String orgname, Long amount, Long productid, String productname, Long productnum) {
        this.id = id;
        this.orgname = orgname;
        this.amount = amount;
        this.productid = productid;
        this.productname = productname;
        this.productnum = productnum;
        this.total_price = total_price;
    }

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
     * @return the orgname
     */
    public String getOrgname() {
        return orgname;
    }

    /**
     * @param orgname the orgname to set
     */
    public void setOrgname(String orgname) {
        this.orgname = orgname;
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
     * @return the productname
     */
    public String getProductname() {
        return productname;
    }

    /**
     * @param productname the productname to set
     */
    public void setProductname(String productname) {
        this.productname = productname;
    }

    /**
     * @return the productnum
     */
    public Long getProductnum() {
        return productnum;
    }

    /**
     * @param productnum the productnum to set
     */
    public void setProductnum(Long productnum) {
        this.productnum = productnum;
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
}
