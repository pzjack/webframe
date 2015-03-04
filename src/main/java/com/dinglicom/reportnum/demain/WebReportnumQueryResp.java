/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class WebReportnumQueryResp extends BaseMsgBean {
    private List<WebReportNumberQueryItemResp> data;

    /**
     * @return the data
     */
    public List<WebReportNumberQueryItemResp> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<WebReportNumberQueryItemResp> data) {
        this.data = data;
    }
}
