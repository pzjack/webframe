/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.domain;

import com.dinglicom.frame.sys.domain.AppRequestBase;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author panzhen
 */
public class OrderReq extends AppRequestBase {
    private long product_id;
    private long user_id;
    private String user_name;
    private String distribution_target;//配送目标，自提或到家配送方式：delivery 配送上门self 奶站自取
    private long distribution_target_id;//配送机构ID
    private String distribution_type;//配送方式，全天、仅周末、仅工作日
    private int distribution_period;//配送周期，单位月
    private int distribution_num;//每天配送数量
    private String order_desc;//订单留言
    private String order_origin;//订单来源，app、网页
    private boolean user_proxy;//是否代订
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date start_date;//起送日期
    private String province;
    private String city;
    private String region;
    private String address;
    private String tel;

    /**
     * @return the product_id
     */
    public long getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    /**
     * @return the user_id
     */
    public long getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
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
     * @return the distribution_period
     */
    public int getDistribution_period() {
        return distribution_period;
    }

    /**
     * @param distribution_period the distribution_period to set
     */
    public void setDistribution_period(int distribution_period) {
        this.distribution_period = distribution_period;
    }

    /**
     * @return the distribution_num
     */
    public int getDistribution_num() {
        return distribution_num;
    }

    /**
     * @param distribution_num the distribution_num to set
     */
    public void setDistribution_num(int distribution_num) {
        this.distribution_num = distribution_num;
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
     * @return the order_origin
     */
    public String getOrder_origin() {
        return order_origin;
    }

    /**
     * @param order_origin the order_origin to set
     */
    public void setOrder_origin(String order_origin) {
        this.order_origin = order_origin;
    }

    /**
     * @return the user_proxy
     */
    public boolean isUser_proxy() {
        return user_proxy;
    }

    /**
     * @param user_proxy the user_proxy to set
     */
    public void setUser_proxy(boolean user_proxy) {
        this.user_proxy = user_proxy;
    }

    /**
     * @return the distribution_target_id
     */
    public long getDistribution_target_id() {
        return distribution_target_id;
    }

    /**
     * @param distribution_target_id the distribution_target_id to set
     */
    public void setDistribution_target_id(long distribution_target_id) {
        this.distribution_target_id = distribution_target_id;
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
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
}
