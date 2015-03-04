/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author panzhen
 */
public class WebOrderitemqueryDetailResp extends BaseMsgBean {
    private String order_no;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date time;
    private String product_name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date start_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date end_date;
    private Long send_num;
    private Long left_num;
    private String distribution_target;
    private String distribution_name;
    private String distribution_type;
    private Integer distribution_num;
    private Integer distribution_period;
    private Boolean proxy;
    private String proxy_user;
    private String origin;
    private String user;
    private String tel;
    private Double total_price;
    private Double product_price;
    
    private String status;
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
    private String desc;
    private String address;
    private String delivery_man; 

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
     * @return the end_date
     */
    public Date getEnd_date() {
        return end_date;
    }

    /**
     * @param end_date the end_date to set
     */
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    /**
     * @return the send_num
     */
    public Long getSend_num() {
        return send_num;
    }

    /**
     * @param send_num the send_num to set
     */
    public void setSend_num(Long send_num) {
        this.send_num = send_num;
    }

    /**
     * @return the left_num
     */
    public Long getLeft_num() {
        return left_num;
    }

    /**
     * @param left_num the left_num to set
     */
    public void setLeft_num(Long left_num) {
        this.left_num = left_num;
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
     * @return the distribution_name
     */
    public String getDistribution_name() {
        return distribution_name;
    }

    /**
     * @param distribution_name the distribution_name to set
     */
    public void setDistribution_name(String distribution_name) {
        this.distribution_name = distribution_name;
    }

    /**
     * @return the distribution_type
     */
    public String getDistribution_type() {
        return distribution_type;
    }

    /**
     * @param distribution_type the distribution_type to set
     */
    public void setDistribution_type(String distribution_type) {
        this.distribution_type = distribution_type;
    }

    /**
     * @return the distribution_num
     */
    public Integer getDistribution_num() {
        return distribution_num;
    }

    /**
     * @param distribution_num the distribution_num to set
     */
    public void setDistribution_num(Integer distribution_num) {
        this.distribution_num = distribution_num;
    }

    /**
     * @return the distribution_period
     */
    public Integer getDistribution_period() {
        return distribution_period;
    }

    /**
     * @param distribution_period the distribution_period to set
     */
    public void setDistribution_period(Integer distribution_period) {
        this.distribution_period = distribution_period;
    }

    /**
     * @return the proxy
     */
    public Boolean isProxy() {
        return proxy;
    }

    /**
     * @param proxy the proxy to set
     */
    public void setProxy(Boolean proxy) {
        this.proxy = proxy;
    }

    /**
     * @return the proxy_user
     */
    public String getProxy_user() {
        return proxy_user;
    }

    /**
     * @param proxy_user the proxy_user to set
     */
    public void setProxy_user(String proxy_user) {
        this.proxy_user = proxy_user;
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
    public Double getTotal_price() {
        return total_price;
    }

    /**
     * @param total_price the total_price to set
     */
    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    /**
     * @return the product_price
     */
    public Double getProduct_price() {
        return product_price;
    }

    /**
     * @param product_price the product_price to set
     */
    public void setProduct_price(Double product_price) {
        this.product_price = product_price;
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

    /**
     * @return the desc
     */
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
}