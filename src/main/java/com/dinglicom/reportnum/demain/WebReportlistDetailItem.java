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
public class WebReportlistDetailItem {
    private Long pid;
    private Long  report_num;
    
    public WebReportlistDetailItem() {}
    public WebReportlistDetailItem(Long pid, Long  report_num) {
        this.pid = pid;
        this.report_num = report_num;
    }

    /**
     * @return the report_num
     */
    public Long getReport_num() {
        return report_num;
    }

    /**
     * @param report_num the report_num to set
     */
    public void setReport_num(Long report_num) {
        this.report_num = report_num;
    }

    /**
     * @return the pid
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }
}
