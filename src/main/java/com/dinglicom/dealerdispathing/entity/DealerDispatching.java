/*
 * Copyright 2015 Jack.Alexander
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dinglicom.dealerdispathing.entity;

import com.dinglicom.frame.entity.EntityExt;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.product.entity.UserProduct;
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
@Table(name = "dealer_dispatch_record")
public class DealerDispatching  extends EntityExt implements Serializable {
    private SysOranizagion dealer;
    private String dealername;
    private String dealermanager;
    private String dispathstate;
    private UserProduct product;
    private String productname;
    private Integer producttype;
    private Long productnum;
    private UserInfo user;
    private String username;
    private String dispatingno;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dispathingdate;
    
    /**
     * @return the dealer
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_id")
    public SysOranizagion getDealer() {
        return dealer;
    }

    /**
     * @param dealer the dealer to set
     */
    public void setDealer(SysOranizagion dealer) {
        this.dealer = dealer;
    }

    /**
     * @return the dealername
     */
    @Column(name = "dealer_name")
    public String getDealername() {
        return dealername;
    }

    /**
     * @param dealername the dealername to set
     */
    public void setDealername(String dealername) {
        this.dealername = dealername;
    }

    /**
     * @return the dispathstate
     */
    @Column(name = "dispath_state")
    public String getDispathstate() {
        return dispathstate;
    }

    /**
     * @param dispathstate the dispathstate to set
     */
    public void setDispathstate(String dispathstate) {
        this.dispathstate = dispathstate;
    }

    /**
     * @return the dealermanager
     */
    @Column(name = "dealer_manager")
    public String getDealermanager() {
        return dealermanager;
    }

    /**
     * @param dealermanager the dealermanager to set
     */
    public void setDealermanager(String dealermanager) {
        this.dealermanager = dealermanager;
    }

    /**
     * @return the product
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    public UserProduct getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(UserProduct product) {
        this.product = product;
    }

    /**
     * @return the productname
     */
    @Column(name = "product_name")
    public String getProductname() {
        return productname;
    }

    /**
     * @param productname the productname to set
     */
    public void setProductname(String productname) {
        this.productname = productname;
    }

    /**
     * @return the producttype
     */
    @Column(name = "product_type")
    public Integer getProducttype() {
        return producttype;
    }

    /**
     * @param producttype the producttype to set
     */
    public void setProducttype(Integer producttype) {
        this.producttype = producttype;
    }

    /**
     * @return the productnum
     */
    @Column(name = "product_num")
    public Long getProductnum() {
        return productnum;
    }

    /**
     * @param productnum the productnum to set
     */
    public void setProductnum(Long productnum) {
        this.productnum = productnum;
    }

    /**
     * @return the user
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserInfo getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserInfo user) {
        this.user = user;
    }

    /**
     * @return the username
     */
    @Column(name = "user_name")
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the dispathingdate
     */
    @Column(name = "dispathing_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getDispathingdate() {
        return dispathingdate;
    }

    /**
     * @param dispathingdate the dispathingdate to set
     */
    public void setDispathingdate(Date dispathingdate) {
        this.dispathingdate = dispathingdate;
    }

    /**
     * @return the dispatingno
     */
    @Column(name = "dispating_no")
    public String getDispatingno() {
        return dispatingno;
    }

    /**
     * @param dispatingno the dispatingno to set
     */
    public void setDispatingno(String dispatingno) {
        this.dispatingno = dispatingno;
    }
}
