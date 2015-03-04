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
public class ReportNumberItemResp {
    private long product_id;
    private String name;
    private long amount;
    private long minus;
    private long plus;
    
    public ReportNumberItemResp() {};
    public ReportNumberItemResp(long product_id, String name, long amount) {
        this.product_id = product_id;
        this.name = name;
        this.amount = amount;
    }
    public ReportNumberItemResp(long product_id, String name, long amount, long minus, long plus) {
        this.product_id = product_id;
        this.name = name;
        this.amount = amount;
        this.minus = minus;
        this.plus = plus;
    }

    /**
     * @return the product_id
     */
    public long getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(long product_id) {
        this.product_id = product_id;
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
     * @return the amount
     */
    public long getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * @return the minus
     */
    public long getMinus() {
        return minus;
    }

    /**
     * @param minus the minus to set
     */
    public void setMinus(long minus) {
        this.minus = minus;
    }

    /**
     * @return the plus
     */
    public long getPlus() {
        return plus;
    }

    /**
     * @param plus the plus to set
     */
    public void setPlus(long plus) {
        this.plus = plus;
    }
}
