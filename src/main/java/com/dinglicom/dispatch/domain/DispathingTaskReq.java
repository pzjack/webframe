/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dispatch.domain;

import com.dinglicom.frame.sys.domain.AppPageReqBase;

/**
 *
 * @author panzhen
 */
public class DispathingTaskReq extends AppPageReqBase {
    private String task_status;

    /**
     * @return the task_status
     */
    public String getTask_status() {
        return task_status;
    }

    /**
     * @param task_status the task_status to set
     */
    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }
}
