/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

import java.util.Date;

/**
 *
 * @author panzhen
 */
public class WebReportNumberQueryItemResp extends WebEverydayorgItemResp {

    private String status;
    public WebReportNumberQueryItemResp(){}
    public WebReportNumberQueryItemResp(long report_id, String status, Date report_time, Date ship_time, String org_name, String user_name, String tel) {
        super(report_id, report_time, ship_time, org_name, user_name, tel);
        this.status = status;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
