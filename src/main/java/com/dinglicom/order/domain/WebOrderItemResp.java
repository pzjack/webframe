/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author panzhen
 */
public class WebOrderItemResp {

    private String order_no;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date time;
    private String product_name;
    private boolean proxy;
    private String origin;
    private String user;
    private String tel;
    private double total_price;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date confirm_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date pause_time;
    private Integer pause_days;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date cancel_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date done_time;
    private String distribution_target;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date start_date;
    private String delivery_man;

    public WebOrderItemResp() {
    }

    public WebOrderItemResp(String order_no, Date time, String product_name, boolean proxy,
            String origin, String user, String tel, double total_price, Date confirm_time,
            Date pause_time, Integer pause_days, Date cancel_time, Date done_time, String distribution_target, Date startdate, String milkmanname) {
        this.order_no = order_no;
        this.time = time;
        this.product_name = product_name;
        this.proxy = proxy;
        this.origin = origin;
        this.user = user;
        this.tel = tel;
        this.total_price = total_price;
        this.confirm_time = confirm_time;
        this.pause_time = pause_time;
        this.pause_days = pause_days;
        this.cancel_time = cancel_time;
        this.done_time = done_time;
        this.distribution_target = distribution_target;
        this.start_date = startdate;
        this.delivery_man = milkmanname;
    }

    /**
     * @return the order_no
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * @param order_no the order_no to set
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the product_name
     */
    public String getProduct_name() {
        return product_name;
    }

    /**
     * @param product_name the product_name to set
     */
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    /**
     * @return the proxy
     */
    public boolean getProxy() {
        return isProxy();
    }

    /**
     * @param proxy the proxy to set
     */
    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }

    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the total_price
     */
    public double getTotal_price() {
        return total_price;
    }

    /**
     * @param total_price the total_price to set
     */
    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    /**
     * @return the proxy
     */
    public boolean isProxy() {
        return proxy;
    }

    /**
     * @return the confirm_time
     */
    public Date getConfirm_time() {
        return confirm_time;
    }

    /**
     * @param confirm_time the confirm_time to set
     */
    public void setConfirm_time(Date confirm_time) {
        this.confirm_time = confirm_time;
    }

    /**
     * @return the pause_time
     */
    public Date getPause_time() {
        return pause_time;
    }

    /**
     * @param pause_time the pause_time to set
     */
    public void setPause_time(Date pause_time) {
        this.pause_time = pause_time;
    }

    /**
     * @return the pause_days
     */
    public Integer getPause_days() {
        return pause_days;
    }

    /**
     * @param pause_days the pause_days to set
     */
    public void setPause_days(Integer pause_days) {
        this.pause_days = pause_days;
    }

    /**
     * @return the cancel_time
     */
    public Date getCancel_time() {
        return cancel_time;
    }

    /**
     * @param cancel_time the cancel_time to set
     */
    public void setCancel_time(Date cancel_time) {
        this.cancel_time = cancel_time;
    }

    /**
     * @return the done_time
     */
    public Date getDone_time() {
        return done_time;
    }

    /**
     * @param done_time the done_time to set
     */
    public void setDone_time(Date done_time) {
        this.done_time = done_time;
    }

    /**
     * @return the distribution_target
     */
    public String getDistribution_target() {
        return distribution_target;
    }

    /**
     * @param distribution_target the distribution_target to set
     */
    public void setDistribution_target(String distribution_target) {
        this.distribution_target = distribution_target;
    }

    /**
     * @return the start_date
     */
    public Date getStart_date() {
        return start_date;
    }

    /**
     * @param start_date the start_date to set
     */
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    /**
     * @return the delivery_man
     */
    public String getDelivery_man() {
        return delivery_man;
    }

    /**
     * @param delivery_man the delivery_man to set
     */
    public void setDelivery_man(String delivery_man) {
        this.delivery_man = delivery_man;
    }
}
