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
public class OrgSaleSampleResp extends BaseMsgBean {

    private List<OrgSaleSampleRespItem> data;

    /**
     * @return the data
     */
    public List<OrgSaleSampleRespItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<OrgSaleSampleRespItem> data) {
        this.data = data;
    }
}
