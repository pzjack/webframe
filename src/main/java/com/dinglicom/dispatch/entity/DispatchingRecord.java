/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dispatch.entity;

import com.dinglicom.frame.entity.EntityExt;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.order.entity.UserOrder;
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
@Table(name = "dispatching_record")
public class DispatchingRecord extends EntityExt implements Serializable {
    private int year;//年
    private int quarter;//季度
    private int month;//月
    private int day;//日
    private String orderNo;//订单号
    private UserOrder order;//订单
    private UserProduct product;
//    private OrderItem orderItem;//订单条目
    private String productName;//产品名称
//    private String productSmallPic;//产品小图片路径
    private int dispatchingNum;//每天应该配送数量
    private double productPrice;//商品单价
    private String dispatchingTarget;//配送目标，自提或到家
    private String dispatchingType;//配送方式，全天、仅周末、仅工作日
    private UserInfo consignee;//收货人
    private String consigneeName;//收货人姓名
    private String phone;//收货人联系电话
    private String address;//收货人详细地址
    //以下是配送信息
    private SysOranizagion dispatchingOrg;//配送奶站(经销商)
    private String dispatchingOrgName;//配送奶站名称
    private UserInfo dispatchingWorker;//送奶工
    private String dispatchingWorkerName;//送奶工姓名
    private String dispatchingWorkerPhone;//送奶工联系电话
    //收货确认人及时间
    private int confirmNum;//确认数量
    private String confirmName;//确认人
    private Date confirmDate;//确认时间
    //第三方配送信息
    private SysOranizagion delivery;//配送方
    private String deliverymame;//配送方名称

    public DispatchingRecord() {};
    public DispatchingRecord(String consigneeName, String phone, long productId, String productName, int dispatchingNum) {
        this.consigneeName = consigneeName;
        this.phone = phone;
        this.product = new UserProduct();
        this.product.setId(productId);
        this.productName = productName;
        this.dispatchingNum = dispatchingNum;
    }
    /**
     * @return the year
     */
    @Column(name = "f_year")
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the quarter
     */
    @Column(name = "f_quarter")
    public int getQuarter() {
        return quarter;
    }

    /**
     * @param quarter the quarter to set
     */
    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    /**
     * @return the month
     */
    @Column(name = "f_month")
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return the day
     */
    @Column(name = "f_day")
    public int getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * @return the orderNo
     */
    @Column(name = "order_no")
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
     * @return the productName
     */
    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
//    /**
//     * 
//     * @return the productSmallPic
//     */
//    @Column(name = "product_small_pic")
//    public String getProductSmallPic() {
//        return productSmallPic;
//    }
//    
//    /**
//     * 
//     * @param productSmallPic  the productSmallPic to set
//     */
//    public void setProductSmallPic(String productSmallPic) {
//        this.productSmallPic = productSmallPic;
//    }

    /**
     * @return the dispatchingNum
     */
    @Column(name = "dispatching_num")
    public int getDispatchingNum() {
        return dispatchingNum;
    }

    /**
     * @param dispatchingNum the dispatchingNum to set
     */
    public void setDispatchingNum(int dispatchingNum) {
        this.dispatchingNum = dispatchingNum;
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
     * @return the dispatchingTarget
     */
    @Column(name = "dispatching_target")
    public String getDispatchingTarget() {
        return dispatchingTarget;
    }

    /**
     * @param dispatchingTarget the dispatchingTarget to set
     */
    public void setDispatchingTarget(String dispatchingTarget) {
        this.dispatchingTarget = dispatchingTarget;
    }

    /**
     * @return the dispatchingType
     */
    @Column(name = "dispatching_type")
    public String getDispatchingType() {
        return dispatchingType;
    }

    /**
     * @param dispatchingType the dispatchingType to set
     */
    public void setDispatchingType(String dispatchingType) {
        this.dispatchingType = dispatchingType;
    }

    /**
     * @return the consignee
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consignee_id")
    public UserInfo getConsignee() {
        return consignee;
    }

    /**
     * @param consignee the consignee to set
     */
    public void setConsignee(UserInfo consignee) {
        this.consignee = consignee;
    }

    /**
     * @return the consigneeName
     */
    @Column(name = "consignee_name")
    public String getConsigneeName() {
        return consigneeName;
    }

    /**
     * @param consigneeName the consigneeName to set
     */
    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
     * @return the dispatchingOrg
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispatching_org_id")
    public SysOranizagion getDispatchingOrg() {
        return dispatchingOrg;
    }

    /**
     * @param dispatchingOrg the dispatchingOrg to set
     */
    public void setDispatchingOrg(SysOranizagion dispatchingOrg) {
        this.dispatchingOrg = dispatchingOrg;
    }

    /**
     * @return the dispatchingOrgName
     */
    @Column(name = "dispatching_org_name")
    public String getDispatchingOrgName() {
        return dispatchingOrgName;
    }

    /**
     * @param dispatchingOrgName the dispatchingOrgName to set
     */
    public void setDispatchingOrgName(String dispatchingOrgName) {
        this.dispatchingOrgName = dispatchingOrgName;
    }

    /**
     * @return the dispatchingWorker
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispatching_worker_id")
    public UserInfo getDispatchingWorker() {
        return dispatchingWorker;
    }

    /**
     * @param dispatchingWorker the dispatchingWorker to set
     */
    public void setDispatchingWorker(UserInfo dispatchingWorker) {
        this.dispatchingWorker = dispatchingWorker;
    }

    /**
     * @return the dispatchingWorkerName
     */
    @Column(name = "dispatching_norker_name")
    public String getDispatchingWorkerName() {
        return dispatchingWorkerName;
    }

    /**
     * @param dispatchingWorkerName the dispatchingWorkerName to set
     */
    public void setDispatchingWorkerName(String dispatchingWorkerName) {
        this.dispatchingWorkerName = dispatchingWorkerName;
    }

    /**
     * @return the dispatchingWorkerPhone
     */
    @Column(name = "dispatching_norker_phone")
    public String getDispatchingWorkerPhone() {
        return dispatchingWorkerPhone;
    }

    /**
     * @param dispatchingWorkerPhone the dispatchingWorkerPhone to set
     */
    public void setDispatchingWorkerPhone(String dispatchingWorkerPhone) {
        this.dispatchingWorkerPhone = dispatchingWorkerPhone;
    }

    /**
     * @return the confirmNum
     */
    @Column(name = "confirm_num")
    public int getConfirmNum() {
        return confirmNum;
    }

    /**
     * @param confirmNum the confirmNum to set
     */
    public void setConfirmNum(int confirmNum) {
        this.confirmNum = confirmNum;
    }

    /**
     * @return the confirmName
     */
    @Column(name = "confirm_name")
    public String getConfirmName() {
        return confirmName;
    }

    /**
     * @param confirmName the confirmName to set
     */
    public void setConfirmName(String confirmName) {
        this.confirmName = confirmName;
    }

    /**
     * @return the confirmDate
     */
    @Column(name = "confirm_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getConfirmDate() {
        return confirmDate;
    }

    /**
     * @param confirmDate the confirmDate to set
     */
    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    /**
     * @return the delivery
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    public SysOranizagion getDelivery() {
        return delivery;
    }

    /**
     * @param delivery the delivery to set
     */
    public void setDelivery(SysOranizagion delivery) {
        this.delivery = delivery;
    }

    /**
     * @return the deliverymame
     */
    public String getDeliverymame() {
        return deliverymame;
    }

    /**
     * @param deliverymame the deliverymame to set
     */
    public void setDeliverymame(String deliverymame) {
        this.deliverymame = deliverymame;
    }
}