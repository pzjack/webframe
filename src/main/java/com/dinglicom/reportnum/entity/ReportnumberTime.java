/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.entity;

import com.dinglicom.frame.entity.EntityExt;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author panzhen
 */
@Entity
@Table(name = "report_number_time")
public class ReportnumberTime extends EntityExt implements Serializable {
    private String time;

    /**
     * @return the time
     */
    @Column(name = "f_time")
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }
}
