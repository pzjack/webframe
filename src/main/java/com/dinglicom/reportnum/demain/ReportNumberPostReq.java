/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

import com.dinglicom.frame.sys.domain.AppRequestBase;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class ReportNumberPostReq extends AppRequestBase {
    private long total_amount;
    private List<ReportNumberItemResp> data;

    /**
     * @return the total_amount
     */
    public long getTotal_amount() {
        return total_amount;
    }

    /**
     * @param total_amount the total_amount to set
     */
    public void setTotal_amount(long total_amount) {
        this.total_amount = total_amount;
    }

    /**
     * @return the data
     */
    public List<ReportNumberItemResp> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<ReportNumberItemResp> data) {
        this.data = data;
    }
}
