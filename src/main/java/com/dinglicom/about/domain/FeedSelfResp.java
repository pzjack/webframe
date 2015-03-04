/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.about.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class FeedSelfResp extends BaseMsgBean {

    private List<FeedSelfRespItem> data;

    /**
     * @return the data
     */
    public List<FeedSelfRespItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<FeedSelfRespItem> data) {
        this.data = data;
    }
}
