/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

import com.dinglicom.frame.sys.domain.AdminReqBase;

/**
 *
 * @author panzhen
 */
public class WebReportnumberDetailReq  extends AdminReqBase {
    private long report_id;

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
}
