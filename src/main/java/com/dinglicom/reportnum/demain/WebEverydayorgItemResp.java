/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author panzhen
 */
public class WebEverydayorgItemResp {
    protected long report_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date report_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date ship_time;
    protected String org_name;
    protected String user_name;
    protected String tel;
    
    public WebEverydayorgItemResp() {}
    public WebEverydayorgItemResp(long report_id, Date report_time, Date ship_time, String org_name, String user_name, String tel) {
        this.report_id = report_id;
        this.report_time = report_time;
        this.ship_time = ship_time;
        this.org_name = org_name;
        this.user_name = user_name;
        this.tel = tel;
    }

    /**
     * @return the report_id
     */
    public long getReport_id() {
        return report_id;
    }

    /**
     * @param report_id the report_id to set
     */
    public void setReport_id(long report_id) {
        this.report_id = report_id;
    }

    /**
     * @return the report_time
     */
    public Date getReport_time() {
        return report_time;
    }

    /**
     * @param report_time the report_time to set
     */
    public void setReport_time(Date report_time) {
        this.report_time = report_time;
    }

    /**
     * @return the ship_time
     */
    public Date getShip_time() {
        return ship_time;
    }

    /**
     * @param ship_time the ship_time to set
     */
    public void setShip_time(Date ship_time) {
        this.ship_time = ship_time;
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
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }
}