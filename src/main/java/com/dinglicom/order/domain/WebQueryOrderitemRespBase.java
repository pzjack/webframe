/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class WebQueryOrderitemRespBase extends BaseMsgBean {
    protected List<WebOrderItemResp> data;


    /**
     * @return the data
     */
    public List<WebOrderItemResp> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<WebOrderItemResp> data) {
        this.data = data;
    }
}
