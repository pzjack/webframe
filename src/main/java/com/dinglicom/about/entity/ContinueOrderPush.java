/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.entity;

import com.dinglicom.frame.entity.EntityExt;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author panzhen
 */
@Entity
@Table(name = "continue_order_push")
public class ContinueOrderPush  extends EntityExt implements Serializable {
    private Integer days;

    /**
     * @return the days
     */
    public Integer getDays() {
        return days;
    }

    /**
     * @param days the days to set
     */
    public void setDays(Integer days) {
        this.days = days;
    }
}
