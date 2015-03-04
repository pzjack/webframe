/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dispatch.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 * 送奶工任务查询结果
 * @author panzhen
 */
public class DispathingTaskResp extends BaseMsgBean {
    private long total_page;
    private List<DispathingTaskItem> data;

    /**
     * @return the total_page
     */
    public long getTotal_page() {
        return total_page;
    }

    /**
     * @param total_page the total_page to set
     */
    public void setTotal_page(long total_page) {
        this.total_page = total_page;
    }

    /**
     * @return the data
     */
    public List<DispathingTaskItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<DispathingTaskItem> data) {
        this.data = data;
    }
}
