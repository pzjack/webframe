/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.app.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class AppupdatelistResp extends BaseMsgBean {
    private Long total_num;
    private List<AppListItem> data;

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

    /**
     * @return the data
     */
    public List<AppListItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<AppListItem> data) {
        this.data = data;
    }
}