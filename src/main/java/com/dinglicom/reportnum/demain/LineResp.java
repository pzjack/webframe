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
public class LineResp extends BaseMsgBean {
    private long total_num;
    private List<LineProductItem> general_type;
    private List<LineProductItem> low_type;
    
    private List<LineDataItem> data;

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
     * @return the general_type
     */
    public List<LineProductItem> getGeneral_type() {
        return general_type;
    }

    /**
     * @param general_type the general_type to set
     */
    public void setGeneral_type(List<LineProductItem> general_type) {
        this.general_type = general_type;
    }

    /**
     * @return the low_type
     */
    public List<LineProductItem> getLow_type() {
        return low_type;
    }

    /**
     * @param low_type the low_type to set
     */
    public void setLow_type(List<LineProductItem> low_type) {
        this.low_type = low_type;
    }

    /**
     * @return the data
     */
    public List<LineDataItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<LineDataItem> data) {
        this.data = data;
    }
    
}
