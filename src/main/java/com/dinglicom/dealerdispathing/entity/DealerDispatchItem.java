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
@Table(name = "dealer_dispatch_item_detail")
public class DealerDispatchItem  extends EntityExt implements Serializable {
    private SysOranizagion dealer;//经销商
    private String dealername;
    private String dealermanager;
    private SysOranizagion workstation;//奶站
    private String stationname;
    private String stationmanager;
    private String stationphone;
    private String stationaddress;
    
    private DealerDispatching dealerDispatching;
    private String dispathstate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dispathingdate;
    
    private UserProduct product;//产品
    private String productname;//产品名称
    private Integer producttype;//产品分类
    private Long distrutenum;//配送报量

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
     * @return the workstation
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_station_id")
    public SysOranizagion getWorkstation() {
        return workstation;
    }

    /**
     * @param workstation the workstation to set
     */
    public void setWorkstation(SysOranizagion workstation) {
        this.workstation = workstation;
    }

    /**
     * @return the stationname
     */
    @Column(name = "station_name")
    public String getStationname() {
        return stationname;
    }

    /**
     * @param stationname the stationname to set
     */
    public void setStationname(String stationname) {
        this.stationname = stationname;
    }

    /**
     * @return the stationmanager
     */
    @Column(name = "station_manager")
    public String getStationmanager() {
        return stationmanager;
    }

    /**
     * @param stationmanager the stationmanager to set
     */
    public void setStationmanager(String stationmanager) {
        this.stationmanager = stationmanager;
    }

    /**
     * @return the stationphone
     */
    @Column(name = "station_phone")
    public String getStationphone() {
        return stationphone;
    }

    /**
     * @param stationphone the stationphone to set
     */
    public void setStationphone(String stationphone) {
        this.stationphone = stationphone;
    }

    /**
     * @return the stationaddress
     */
    @Column(name = "station_address")
    public String getStationaddress() {
        return stationaddress;
    }

    /**
     * @param stationaddress the stationaddress to set
     */
    public void setStationaddress(String stationaddress) {
        this.stationaddress = stationaddress;
    }

    /**
     * @return the dealerDispatching
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_dispatching_id")
    public DealerDispatching getDealerDispatching() {
        return dealerDispatching;
    }

    /**
     * @param dealerDispatching the dealerDispatching to set
     */
    public void setDealerDispatching(DealerDispatching dealerDispatching) {
        this.dealerDispatching = dealerDispatching;
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
     * @return the distrutenum
     */
    @Column(name = "distrute_num")
    public Long getDistrutenum() {
        return distrutenum;
    }

    /**
     * @param distrutenum the distrutenum to set
     */
    public void setDistrutenum(Long distrutenum) {
        this.distrutenum = distrutenum;
    }
}
