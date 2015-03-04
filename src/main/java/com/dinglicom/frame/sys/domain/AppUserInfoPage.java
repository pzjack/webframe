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
public class AppUserInfoPage extends BaseMsgBean {

    protected List<AppUserBeanBase> data;

    /**
     * @return the data
     */
    public List<AppUserBeanBase> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<AppUserBeanBase> data) {
        this.data = data;
    }
}
