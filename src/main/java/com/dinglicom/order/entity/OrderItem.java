/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.entity;

import com.dinglicom.frame.entity.EntityExt;
import com.dinglicom.product.entity.UserProduct;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author panzhen
 */
@Entity
@Table(name = "order_item")
public class OrderItem extends EntityExt implements Serializable {
    private UserOrder order;
    private String orderNo;
    private UserProduct product;
    private String productname;//商品名称
    private String productsmallpic;//商品小图片
    private int productnum = 0;//产品总数量，应该配送的数量
    private double productPrice = 0;//商品单价
    private double productTotalPrice = 0;//商品总价
    private int distributionNum = 0;//每天配送数量
    private String distributionTarget;//配送目标，自提或到家
    private String distributionType;//配送方式，全天、仅周末、仅工作日
    private int distributionPeriod = 0;//配送周期，单位月
    private Date firstDistributionDate;//起送日期
    private Date endDistributionDate;//结束日期日期
    private int totaldistributionnum = 0;//生成配送单数量
    private int completenum = 0;//完成配送的数量
//    private String itemstate;//状态同订单状态(当一个订单有多个订单条目时，订单条目有可能 先处于完成状态)

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
     * @return the orderNo
     */
    @Column(name = "order_no", length=20)
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
     * @return the productPrice
     */
    @Column(name = "product_price")
    public double getProductPrice() {
        return productPrice;
    }

    /**
     * @param productPrice the productPrice to set
     */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return the productTotalPrice
     */
    @Column(name = "product_total_price")
    public double getProductTotalPrice() {
        return productTotalPrice;
    }

    /**
     * @param productTotalPrice the productTotalPrice to set
     */
    public void setProductTotalPrice(double productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    /**
     * @return the distributionNum
     */
    @Column(name = "distribution_num")
    public int getDistributionNum() {
        return distributionNum;
    }

    /**
     * @param distributionNum the distributionNum to set
     */
    public void setDistributionNum(int distributionNum) {
        this.distributionNum = distributionNum;
    }

    /**
     * @return the distributionTarget
     */
    @Column(name = "distribution_target")
    public String getDistributionTarget() {
        return distributionTarget;
    }

    /**
     * @param distributionTarget the distributionTarget to set
     */
    public void setDistributionTarget(String distributionTarget) {
        this.distributionTarget = distributionTarget;
    }

    /**
     * @return the distributionType
     */
    @Column(name = "distribution_type", length=4)
    public String getDistributionType() {
        return distributionType;
    }

    /**
     * @param distributionType the distributionType to set
     */
    public void setDistributionType(String distributionType) {
        this.distributionType = distributionType;
    }

    /**
     * @return the distributionPeriod
     */
    @Column(name = "distribution_period", length=4)
    public int getDistributionPeriod() {
        return distributionPeriod;
    }

    /**
     * @param distributionPeriod the distributionPeriod to set
     */
    public void setDistributionPeriod(int distributionPeriod) {
        this.distributionPeriod = distributionPeriod;
    }

    /**
     * @return the firstDistributionDate
     */
    @Column(name = "first_distribution_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getFirstDistributionDate() {
        return firstDistributionDate;
    }

    /**
     * @param firstDistributionDate the firstDistributionDate to set
     */
    public void setFirstDistributionDate(Date firstDistributionDate) {
        this.firstDistributionDate = firstDistributionDate;
    }

    /**
     * @return the endDistributionDate
     */
    @Column(name = "end_distribution_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getEndDistributionDate() {
        return endDistributionDate;
    }

    /**
     * @param endDistributionDate the endDistributionDate to set
     */
    public void setEndDistributionDate(Date endDistributionDate) {
        this.endDistributionDate = endDistributionDate;
    }

    /**
     * @return the productnum
     */
    public int getProductnum() {
        return productnum;
    }

    /**
     * @param productnum the productnum to set
     */
    public void setProductnum(int productnum) {
        this.productnum = productnum;
    }

    /**
     * @return the completenum
     */
    public int getCompletenum() {
        return completenum;
    }

    /**
     * @param completenum the completenum to set
     */
    public void setCompletenum(int completenum) {
        this.completenum = completenum;
    }

    /**
     * @return the productname
     */
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
     * @return the productsmallpic
     */
    public String getProductsmallpic() {
        return productsmallpic;
    }

    /**
     * @param productsmallpic the productsmallpic to set
     */
    public void setProductsmallpic(String productsmallpic) {
        this.productsmallpic = productsmallpic;
    }

//    /**
//     * @return the itemstate
//     */
//    public String getItemstate() {
//        return itemstate;
//    }
//
//    /**
//     * @param itemstate the itemstate to set
//     */
//    public void setItemstate(String itemstate) {
//        this.itemstate = itemstate;
//    }

    /**
     * @return the totaldistributionnum
     */
    public int getTotaldistributionnum() {
        return totaldistributionnum;
    }

    /**
     * @param totaldistributionnum the totaldistributionnum to set
     */
    public void setTotaldistributionnum(int totaldistributionnum) {
        this.totaldistributionnum = totaldistributionnum;
    }
}
