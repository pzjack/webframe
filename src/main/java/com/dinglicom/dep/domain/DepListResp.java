/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dep.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class DepListResp extends BaseMsgBean {
    private List<DepItem> data;

    /**
     * @return the data
     */
    public List<DepItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<DepItem> data) {
        this.data = data;
    }
}
