/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dispatch.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class TaskStatisticsResp extends BaseMsgBean {
    private long total_send_num = 0;
    private long total_left_num = 0;
    private List<TaskStatisticsItem> data = new ArrayList<TaskStatisticsItem>();

    public TaskStatisticsResp() {}
    public TaskStatisticsResp(long total_send_num, long total_confirm_num) {
        this.total_send_num = total_confirm_num;
        this.total_left_num = total_send_num - total_confirm_num;
    }
    /**
     * @return the total_send_num
     */
    public long getTotal_send_num() {
        return total_send_num;
    }

    /**
     * @param total_send_num the total_send_num to set
     */
    public void setTotal_send_num(long total_send_num) {
        this.total_send_num = total_send_num;
    }

    /**
     * @return the total_left_num
     */
    public long getTotal_left_num() {
        return total_left_num;
    }

    /**
     * @param total_left_num the total_left_num to set
     */
    public void setTotal_left_num(long total_left_num) {
        this.total_left_num = total_left_num;
    }

    /**
     * @return the data
     */
    public List<TaskStatisticsItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<TaskStatisticsItem> data) {
        this.data = data;
    }

}
