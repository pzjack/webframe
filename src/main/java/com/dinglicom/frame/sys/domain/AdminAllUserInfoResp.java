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
public class AdminAllUserInfoResp extends BaseMsgBean {
    private long total_num;
    private List<UserInfoItem> data;

    /**
     * @return the total_num
     */
    public long getTotal_num() {
        return total_num;
    }

    /**
     * @param total_num the total_num to set
     */
    public void setTotal_num(long total_num) {
        this.total_num = total_num;
    }
    /**
     * @return the data
     */
    public List<UserInfoItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<UserInfoItem> data) {
        this.data = data;
    }
}
