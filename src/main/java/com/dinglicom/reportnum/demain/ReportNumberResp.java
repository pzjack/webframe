/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author panzhen
 */
public class ReportNumberResp extends BaseMsgBean {
    @DateTimeFormat(pattern = "HH:mm")
//    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private String expired_time;
    private Long total_amount;
    private String status;
    private List<ReportNumberItemResp> data;

    /**
     * @return the expired_time
     */
    public String getExpired_time() {
        return expired_time;
    }

    /**
     * @param expired_time the expired_time to set
     */
    public void setExpired_time(String expired_time) {
        this.expired_time = expired_time;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the data
     */
    public List<ReportNumberItemResp> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<ReportNumberItemResp> data) {
        this.data = data;
    }

    /**
     * @return the total_amount
     */
    public Long getTotal_amount() {
        return total_amount;
    }

    /**
     * @param total_amount the total_amount to set
     */
    public void setTotal_amount(Long total_amount) {
        this.total_amount = total_amount;
    }
}
