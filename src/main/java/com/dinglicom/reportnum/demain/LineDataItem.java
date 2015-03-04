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
public class LineDataItem {
    private Long org_id;
    private String org_name;
    private Long total_amount;
    private List<LineProductNum> general_type;
    private List<LineProductNum> low_type;
    private String line;
    private String reciver;
    private String business;
    private String outer;
    private String report_region;

    /**
     * @return the org_id
     */
    public Long getOrg_id() {
        return org_id;
    }

    /**
     * @param org_id the org_id to set
     */
    public void setOrg_id(Long org_id) {
        this.org_id = org_id;
    }

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
     * @return the general_type
     */
    public List<LineProductNum> getGeneral_type() {
        return general_type;
    }

    /**
     * @param general_type the general_type to set
     */
    public void setGeneral_type(List<LineProductNum> general_type) {
        this.general_type = general_type;
    }

    /**
     * @return the low_type
     */
    public List<LineProductNum> getLow_type() {
        return low_type;
    }

    /**
     * @param low_type the low_type to set
     */
    public void setLow_type(List<LineProductNum> low_type) {
        this.low_type = low_type;
    }

    /**
     * @return the line
     */
    public String getLine() {
        return line;
    }

    /**
     * @param line the line to set
     */
    public void setLine(String line) {
        this.line = line;
    }

    /**
     * @return the reciver
     */
    public String getReciver() {
        return reciver;
    }

    /**
     * @param reciver the reciver to set
     */
    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    /**
     * @return the business
     */
    public String getBusiness() {
        return business;
    }

    /**
     * @param business the business to set
     */
    public void setBusiness(String business) {
        this.business = business;
    }

    /**
     * @return the outer
     */
    public String getOuter() {
        return outer;
    }

    /**
     * @param outer the outer to set
     */
    public void setOuter(String outer) {
        this.outer = outer;
    }

    /**
     * @return the report_region
     */
    public String getReport_region() {
        return report_region;
    }

    /**
     * @param report_region the report_region to set
     */
    public void setReport_region(String report_region) {
        this.report_region = report_region;
    }
}
