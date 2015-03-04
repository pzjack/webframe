/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dispatch.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class TaskDetailResp extends BaseMsgBean {
    private String name;
    private String mobile_phone;
    private List<TaskDetailItem> data;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the mobile_phone
     */
    public String getMobile_phone() {
        return mobile_phone;
    }

    /**
     * @param mobile_phone the mobile_phone to set
     */
    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    /**
     * @return the data
     */
    public List<TaskDetailItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<TaskDetailItem> data) {
        this.data = data;
    }
}
