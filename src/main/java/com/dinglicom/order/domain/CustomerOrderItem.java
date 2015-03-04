/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.domain;

import com.dinglicom.order.service.UserOrderService;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author panzhen
 */
public class CustomerOrderItem {
    private String order_no;
    private String small_pic;
    private Double product_total_price;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date start_date;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date end_date;
    private Long product_id;
    private String product_name;
    private String distribution_target;
    private String distribution_type;
    private Integer distribution_num;
    private Integer distribution_period;
    private Boolean user_pay;
    private Boolean normal_pause_distribution;
    private Integer send_num;
    private Integer left_num;
    private String order_status;
    private String order_desc;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date order_time;
    
    public CustomerOrderItem () {}
    public CustomerOrderItem(Long productId, String productSmallPic, String productName, String orderNo, Boolean userPay, String orderState, String distributionTarget, String distributionType, Integer distributionPeriod, Integer distributionnum, Integer totalnum, Integer completenum, Double productTotalPrice, Date firstDistributionDate, Date endDistriDate, String orderdesc, Date creatdate) {
        this.product_id = productId;
        this.small_pic = productSmallPic;
        this.product_name = productName;
        
        this.order_no = orderNo;
        this.user_pay = userPay;
        this.distribution_target = distributionTarget;
        this.distribution_type = distributionType;
        this.distribution_period = distributionPeriod;
        this.distribution_num = distributionnum;
        this.product_total_price = productTotalPrice;
        normal_pause_distribution = null != orderState && UserOrderService.ORDER_STATE_PAUSE.equals(orderState);
        this.start_date = firstDistributionDate;
        this.end_date = endDistriDate;
        this.send_num = completenum;
        this.left_num = totalnum - this.send_num;
        this.order_status = orderState;
        this.order_desc = orderdesc;
        this.order_time = creatdate;
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
     * @return the small_pic
     */
    public String getSmall_pic() {
        return small_pic;
    }

    /**
     * @param small_pic the small_pic to set
     */
    public void setSmall_pic(String small_pic) {
        this.small_pic = small_pic;
    }

    /**
     * @return the product_total_price
     */
    public Double getProduct_total_price() {
        return product_total_price;
    }

    /**
     * @param product_total_price the product_total_price to set
     */
    public void setProduct_total_price(Double product_total_price) {
        this.product_total_price = product_total_price;
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
     * @return the product_id
     */
    public Long getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
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
     * @return the user_pay
     */
    public Boolean isUser_pay() {
        return getUser_pay();
    }

    /**
     * @param user_pay the user_pay to set
     */
    public void setUser_pay(Boolean user_pay) {
        this.user_pay = user_pay;
    }

    /**
     * @return the normal_pause_distribution
     */
    public Boolean isNormal_pause_distribution() {
        return getNormal_pause_distribution();
    }

    /**
     * @param normal_pause_distribution the normal_pause_distribution to set
     */
    public void setNormal_pause_distribution(Boolean normal_pause_distribution) {
        this.normal_pause_distribution = normal_pause_distribution;
    }

    /**
     * @return the send_num
     */
    public Integer getSend_num() {
        return send_num;
    }

    /**
     * @param send_num the send_num to set
     */
    public void setSend_num(Integer send_num) {
        this.send_num = send_num;
    }

    /**
     * @return the left_num
     */
    public Integer getLeft_num() {
        return left_num;
    }

    /**
     * @param left_num the left_num to set
     */
    public void setLeft_num(Integer left_num) {
        this.left_num = left_num;
    }

    /**
     * @return the order_status
     */
    public String getOrder_status() {
        return order_status;
    }

    /**
     * @param order_status the order_status to set
     */
    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    /**
     * @return the order_desc
     */
    public String getOrder_desc() {
        return order_desc;
    }

    /**
     * @param order_desc the order_desc to set
     */
    public void setOrder_desc(String order_desc) {
        this.order_desc = order_desc;
    }

    /**
     * @return the user_pay
     */
    public Boolean getUser_pay() {
        return user_pay;
    }

    /**
     * @return the normal_pause_distribution
     */
    public Boolean getNormal_pause_distribution() {
        return normal_pause_distribution;
    }

    /**
     * @return the order_time
     */
    public Date getOrder_time() {
        return order_time;
    }

    /**
     * @param order_time the order_time to set
     */
    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }
}
