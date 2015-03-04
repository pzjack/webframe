/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.entity;

import com.dinglicom.frame.entity.EntityExt;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author panzhen
 */
@Entity
@Table(name = "order_change_record")
public class OrderChangeRecord extends EntityExt implements Serializable {
    private String orderNo;//订单号
    private UserOrder order;//订单
    private int state;//退订或者暂停
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginTime;//开始生效时间
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;//结束生效时间
    private int pauseDay;//暂停时间（多少天）
    private String desc;//暂停说明
    private UserInfo optUser;//执行退订或者暂停的用户
    private String optUserName;//执行退订或者暂停的用户名
    private int pausestate;//暂停状态，暂停中，暂停结束

    /**
     * @return the orderNo
     */
    @Column(name = "order_no", length=30)
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the order
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    public UserOrder getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(UserOrder order) {
        this.order = order;
    }

    /**
     * @return the state
     */
    @Column(name = "f_state")
    public int getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @return the beginTime
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "begin_time")
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * @param beginTime the beginTime to set
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * @return the endTime
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the pauseDay
     */
    @Column(name = "pause_day")
    public int getPauseDay() {
        return pauseDay;
    }

    /**
     * @param pauseDay the pauseDay to set
     */
    public void setPauseDay(int pauseDay) {
        this.pauseDay = pauseDay;
    }

    /**
     * @return the desc
     */
    @Column(name = "f_desc")
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the optUser
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opt_user_id")
    public UserInfo getOptUser() {
        return optUser;
    }

    /**
     * @param optUser the optUser to set
     */
    public void setOptUser(UserInfo optUser) {
        this.optUser = optUser;
    }

    /**
     * @return the optUserName
     */
    @Column(name = "opt_user_name")
    public String getOptUserName() {
        return optUserName;
    }

    /**
     * @param optUserName the optUserName to set
     */
    public void setOptUserName(String optUserName) {
        this.optUserName = optUserName;
    }

    /**
     * @return the pausestate
     */
    @Column(name = "pause_state")
    public int getPausestate() {
        return pausestate;
    }

    /**
     * @param pausestate the pausestate to set
     */
    public void setPausestate(int pausestate) {
        this.pausestate = pausestate;
    }
}
