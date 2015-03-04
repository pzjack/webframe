/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.domain;

import java.util.List;

/**
 *
 * @author panzhen
 */
public class RegionResp extends BaseMsgBean {
    private List<RegionRespItem> data;

    /**
     * @return the data
     */
    public List<RegionRespItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<RegionRespItem> data) {
        this.data = data;
    }
}
