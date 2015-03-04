/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dep.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;

/**
 *
 * @author panzhen
 */
public class DepaddResp extends BaseMsgBean {
    private Long did;

    /**
     * @return the did
     */
    public Long getDid() {
        return did;
    }

    /**
     * @param did the did to set
     */
    public void setDid(Long did) {
        this.did = did;
    }
}
