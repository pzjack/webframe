/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.entity;

import com.dinglicom.frame.entity.EntityExt;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
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
@Table(name = "user_order")
public class UserOrder extends EntityExt implements Serializable {
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

    private UserInfo user;//收货人
    private String consigneename;//收货人姓名
    private String consigneephone;//收货人联系电话
    private String consigneeaddress;//收货人详细地址
    
    private SysOranizagion org;//奶站,配送方,经销商
    private String orgmame;//奶站,配送方,经销商名称
    
//    private UserAddress address;
    private String orderNo;//订单编号
    private String orderDesc;//订单留言
    private String orderOrigin;//订单来源，app、网页
    private boolean userProxy;//是否代订
    private boolean userPay;//是否已经支付
    private String orderState;//状态，未确认、已确认、已完成
//    private List<OrderItem> items;//订单项
    private String continueorderno;//续订的订单号，父订单号
    
    private UserInfo milkman;//如果是送奶工送货上门，指定的送奶工的id
    private String milkmanname;//指定送货上门的送奶工的名称
    
    private String proxyname;//代订人姓名
    private String confirmname;//确认人姓名
    private Date confirmtime;//确认时间
    private Date completetime;//完成
    private String payname;//收款人姓名
    private Date paytime;//收款时间
    private Date canceltime;//取消时间
    private String cancelname;//取消人
    private String completename;//完成人
    private String pausename;//暂停人
    private Date pausetime;//暂停时间
    private Integer pausedays;//暂停天数

    

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
     * @return the org
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    public SysOranizagion getOrg() {
        return org;
    }

    /**
     * @param org the org to set
     */
    public void setOrg(SysOranizagion org) {
        this.org = org;
    }

//    /**
//     * @return the address
//     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "address_id")
//    public UserAddress getAddress() {
//        return address;
//    }
//
//    /**
//     * @param address the addressI to set
//     */
//    public void setAddress(UserAddress address) {
//        this.address = address;
//    }

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
     * @return the orderDesc
     */
    @Column(name = "order_desc", length=200)
    public String getOrderDesc() {
        return orderDesc;
    }

    /**
     * @param orderDesc the orderDesc to set
     */
    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    /**
     * @return the orderOrigin
     */
    @Column(name = "order_origin", length=4)
    public String getOrderOrigin() {
        return orderOrigin;
    }

    /**
     * @param orderOrigin the orderOrigin to set
     */
    public void setOrderOrigin(String orderOrigin) {
        this.orderOrigin = orderOrigin;
    }

    /**
     * @return the userProxy
     */
    @Column(name = "user_proxy")
    public boolean isUserProxy() {
        return userProxy;
    }

    /**
     * @param userProxy the userProxy to set
     */
    public void setUserProxy(boolean userProxy) {
        this.userProxy = userProxy;
    }

    /**
     * @return the userPay
     */
    @Column(name = "user_pay")
    public boolean isUserPay() {
        return userPay;
    }

    /**
     * @param userPay the userPay to set
     */
    public void setUserPay(boolean userPay) {
        this.userPay = userPay;
    }

    /**
     * @return the orderState
     */
    @Column(name = "order_state", length=4)
    public String getOrderState() {
        return orderState;
    }

    /**
     * @param orderState the orderState to set
     */
    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

//    /**
//     * @return the items
//     */
//    @OneToMany(fetch = FetchType.LAZY, mappedBy="order")
//    public List<OrderItem> getItems() {
//        return items;
//    }
//
//    /**
//     * @param items the items to set
//     */
//    public void setItems(List<OrderItem> items) {
//        this.items = items;
//    }

    /**
     * @return the consigneename
     */
    public String getConsigneename() {
        return consigneename;
    }

    /**
     * @param consigneename the consigneename to set
     */
    public void setConsigneename(String consigneename) {
        this.consigneename = consigneename;
    }

    /**
     * @return the consigneephone
     */
    public String getConsigneephone() {
        return consigneephone;
    }

    /**
     * @param consigneephone the consigneephone to set
     */
    public void setConsigneephone(String consigneephone) {
        this.consigneephone = consigneephone;
    }

    /**
     * @return the consigneeaddress
     */
    public String getConsigneeaddress() {
        return consigneeaddress;
    }

    /**
     * @param consigneeaddress the consigneeaddress to set
     */
    public void setConsigneeaddress(String consigneeaddress) {
        this.consigneeaddress = consigneeaddress;
    }

    /**
     * @return the orgmame
     */
    public String getOrgmame() {
        return orgmame;
    }

    /**
     * @param orgmame the orgmame to set
     */
    public void setOrgmame(String orgmame) {
        this.orgmame = orgmame;
    }

    /**
     * @return the continueorderno
     */
    public String getContinueorderno() {
        return continueorderno;
    }

    /**
     * @param continueorderno the continueorderno to set
     */
    public void setContinueorderno(String continueorderno) {
        this.continueorderno = continueorderno;
    }

    /**
     * @return the proxyname
     */
    public String getProxyname() {
        return proxyname;
    }

    /**
     * @param proxyname the proxyname to set
     */
    public void setProxyname(String proxyname) {
        this.proxyname = proxyname;
    }

    /**
     * @return the confirmname
     */
    public String getConfirmname() {
        return confirmname;
    }

    /**
     * @param confirmname the confirmname to set
     */
    public void setConfirmname(String confirmname) {
        this.confirmname = confirmname;
    }

    /**
     * @return the confirmtime
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getConfirmtime() {
        return confirmtime;
    }

    /**
     * @param confirmtime the confirmtime to set
     */
    public void setConfirmtime(Date confirmtime) {
        this.confirmtime = confirmtime;
    }

    /**
     * @return the completetime
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getCompletetime() {
        return completetime;
    }

    /**
     * @param completetime the completetime to set
     */
    public void setCompletetime(Date completetime) {
        this.completetime = completetime;
    }

    /**
     * @return the payname
     */
    public String getPayname() {
        return payname;
    }

    /**
     * @param payname the payname to set
     */
    public void setPayname(String payname) {
        this.payname = payname;
    }

    /**
     * @return the paytime
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getPaytime() {
        return paytime;
    }

    /**
     * @param paytime the paytime to set
     */
    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    /**
     * @return the canceltime
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getCanceltime() {
        return canceltime;
    }

    /**
     * @param canceltime the canceltime to set
     */
    public void setCanceltime(Date canceltime) {
        this.canceltime = canceltime;
    }

    /**
     * @return the cancelname
     */
    public String getCancelname() {
        return cancelname;
    }

    /**
     * @param cancelname the cancelname to set
     */
    public void setCancelname(String cancelname) {
        this.cancelname = cancelname;
    }

    /**
     * @return the completename
     */
    public String getCompletename() {
        return completename;
    }

    /**
     * @param completename the completename to set
     */
    public void setCompletename(String completename) {
        this.completename = completename;
    }

    /**
     * @return the pausename
     */
    public String getPausename() {
        return pausename;
    }

    /**
     * @param pausename the pausename to set
     */
    public void setPausename(String pausename) {
        this.pausename = pausename;
    }

    /**
     * @return the pausetime
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getPausetime() {
        return pausetime;
    }

    /**
     * @param pausetime the pausetime to set
     */
    public void setPausetime(Date pausetime) {
        this.pausetime = pausetime;
    }

    /**
     * @return the pausedays
     */
    public Integer getPausedays() {
        return pausedays;
    }

    /**
     * @param pausedays the pausedays to set
     */
    public void setPausedays(Integer pausedays) {
        this.pausedays = pausedays;
    }

    /**
     * @return the milkman
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milkman_id")
    public UserInfo getMilkman() {
        return milkman;
    }

    /**
     * @param milkman the milkman to set
     */
    public void setMilkman(UserInfo milkman) {
        this.milkman = milkman;
    }

    /**
     * @return the milkmanname
     */
    public String getMilkmanname() {
        return milkmanname;
    }

    /**
     * @param milkmanname the milkmanname to set
     */
    public void setMilkmanname(String milkmanname) {
        this.milkmanname = milkmanname;
    }
}
