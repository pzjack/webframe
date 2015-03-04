/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.domain;

/**
 *
 * @author panzhen
 */
public class BaiduReq extends AppRequestBase {
    private String baiduId;

    /**
     * @return the baiduId
     */
    public String getBaiduId() {
        return baiduId;
    }

    /**
     * @param baiduId the baiduId to set
     */
    public void setBaiduId(String baiduId) {
        this.baiduId = baiduId;
    }
}
