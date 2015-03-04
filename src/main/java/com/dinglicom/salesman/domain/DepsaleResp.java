/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.salesman.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class DepsaleResp extends BaseMsgBean {
    private List<DepsaleRespItem> data;

    /**
     * @return the data
     */
    public List<DepsaleRespItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<DepsaleRespItem> data) {
        this.data = data;
    }
}
