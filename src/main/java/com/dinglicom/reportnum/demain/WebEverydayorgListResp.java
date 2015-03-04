/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class WebEverydayorgListResp extends BaseMsgBean {
    private Long total_num;
    private String dead_time;
    private List<WebEverydayorgItemResp> data;

    /**
     * @return the dead_time
     */
    public String getDead_time() {
        return dead_time;
    }

    /**
     * @param dead_time the dead_time to set
     */
    public void setDead_time(String dead_time) {
        this.dead_time = dead_time;
    }

    /**
     * @return the data
     */
    public List<WebEverydayorgItemResp> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<WebEverydayorgItemResp> data) {
        this.data = data;
    }

    /**
     * @return the total_num
     */
    public Long getTotal_num() {
        return total_num;
    }

    /**
     * @param total_num the total_num to set
     */
    public void setTotal_num(Long total_num) {
        this.total_num = total_num;
    }
}
