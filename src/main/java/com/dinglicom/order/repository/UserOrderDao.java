/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.order.repository;

import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.order.domain.CustomerOrderItem;
import com.dinglicom.order.domain.PayItem;
import com.dinglicom.order.domain.UserOrderPageItemResp;
import com.dinglicom.order.domain.WebOrderItemResp;
import com.dinglicom.order.entity.UserOrder;
import com.dinglicom.reportnum.demain.ReportNumberItemResp;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface UserOrderDao extends PagingAndSortingRepository<UserOrder, Long>, JpaSpecificationExecutor<UserOrder> {

    @Query("select count(*) from UserOrder a where a.user.id=:userId and a.orderState < :orderState and a.signDelete=:signDelete")
    long findByUserAndOrderStateLess(@Param(value = "userId") long userId, @Param(value = "orderState") String orderState, @Param(value = "signDelete") Boolean signDelete);

    @Query("select count(*) from UserOrder a where a.user.id=:userId and a.orderState >= :orderState and a.signDelete=:signDelete")
    long findByUserAndOrderStateGreateEqual(@Param(value = "userId") long userId, @Param(value = "orderState") String orderState, @Param(value = "signDelete") Boolean signDelete);

    @Query("from UserOrder a where a.orderNo = :orderNo")
    UserOrder findByOrderno(@Param(value = "orderNo") String orderNo);

    @Query("select count(*) from UserOrder a where a.orderState = :orderState and a.signDelete = :signDelete")
    long getCountByOrderState(@Param(value = "orderState") String orderState, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.order.domain.CustomerOrderItem(a.product.id, a.productsmallpic, a.productname, a.orderNo, a.userPay, a.orderState, a.distributionTarget, a.distributionType, a.distributionPeriod, a.distributionNum, a.productnum, a.completenum, a.productTotalPrice, a.firstDistributionDate, a.endDistributionDate,a.orderDesc, a.createDate)  from UserOrder a where a.user.id=:userId and a.orderState<:state and a.signDelete=:signDelete")
    List<CustomerOrderItem> findByCustomerByWorkerAndLessComplete(@Param(value = "userId") long userId, @Param(value = "state") String state, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.order.domain.CustomerOrderItem(a.product.id, a.productsmallpic, a.productname, a.orderNo, a.userPay, a.orderState, a.distributionTarget, a.distributionType, a.distributionPeriod, a.distributionNum, a.productnum, a.completenum, a.productTotalPrice, a.firstDistributionDate, a.endDistributionDate,a.orderDesc, a.createDate)  from UserOrder a where a.orderNo=:orderno and a.signDelete=:signDelete")
    List<CustomerOrderItem> findOrderDetailsByOrderno(@Param(value = "orderno") String orderno, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.order.domain.UserOrderPageItemResp(a.orderNo, a.orderState, a.productname, a.firstDistributionDate, a.endDistributionDate, a.productnum, a.completenum)  from UserOrder a where a.user.id=:userId and a.signDelete=:signDelete")
    Page<UserOrderPageItemResp> findOrderItemsByCustomer(Pageable page, @Param(value = "userId") long userId, @Param(value = "signDelete") Boolean signDelete);

    @Query("from UserOrder a where a.org=:org and a.orderState < :orderState and a.totaldistributionnum < a.productnum and a.signDelete=:signDelete and a.firstDistributionDate <= :begindate")
    List<UserOrder> findAllDispatchItem(@Param(value = "org") SysOranizagion nz, @Param(value = "orderState") String orderState, @Param(value = "begindate") Date begindate, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.reportnum.demain.ReportNumberItemResp(a.product.id, a.productname, sum(a.distributionNum)) from UserOrder a where a.distributionType in (:workday,:every) and a.totaldistributionnum < a.productnum and a.org.id=:orgid and a.orderState <:orderState and a.firstDistributionDate <= :begindate and a.signDelete=:signDelete group by a.product,a.productname")
    List<ReportNumberItemResp> getWorkDayReportNumber(@Param(value = "workday") String workday, @Param(value = "every") String every, @Param(value = "orgid") long orgid, @Param(value = "orderState") String orderState, @Param(value = "begindate") Date begindate, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.order.domain.WebOrderItemResp(a.orderNo, a.createDate, a.productname, a.userProxy, a.orderOrigin, a.consigneename, a.consigneephone, a.productTotalPrice, a.confirmtime, a.pausetime, a.pausedays, a.canceltime, a.completetime, a.distributionTarget, a.firstDistributionDate, a.milkmanname) from UserOrder a where a.signDelete = :signDelete")
    Page<WebOrderItemResp> getAllOrderItem(Pageable page, @Param(value = "signDelete") Boolean signDelete);
    
    @Query("select new com.dinglicom.order.domain.WebOrderItemResp(a.orderNo, a.createDate, a.productname, a.userProxy, a.orderOrigin, a.consigneename, a.consigneephone, a.productTotalPrice, a.confirmtime, a.pausetime, a.pausedays, a.canceltime, a.completetime, a.distributionTarget, a.firstDistributionDate, a.milkmanname) from UserOrder a where a.orderState = :orderState and a.signDelete = :signDelete order by a.id desc")
    Page<WebOrderItemResp> getAllOrderItem(Pageable page, @Param(value = "orderState") String orderState, @Param(value = "signDelete") Boolean signDelete);
    
    
    @Query("select new com.dinglicom.order.domain.WebOrderItemResp(a.orderNo, a.createDate, a.productname, a.userProxy, a.orderOrigin, a.consigneename, a.consigneephone, a.productTotalPrice, a.confirmtime, a.pausetime, a.pausedays, a.canceltime, a.completetime, a.distributionTarget, a.firstDistributionDate, a.milkmanname) from UserOrder a where a.orderState in (:orderState1, :orderState2) and a.signDelete = :signDelete order by a.id desc")
    Page<WebOrderItemResp> getAllOrderItem(Pageable page, @Param(value = "orderState1") String orderState1, @Param(value = "orderState2") String orderState2, @Param(value = "signDelete") Boolean signDelete);
    
    @Query("select new com.dinglicom.order.domain.WebOrderItemResp(a.orderNo, a.createDate, a.productname, a.userProxy, a.orderOrigin, a.consigneename, a.consigneephone, a.productTotalPrice, a.confirmtime, a.pausetime, a.pausedays, a.canceltime, a.completetime, a.distributionTarget, a.firstDistributionDate, a.milkmanname) from UserOrder a where (a.orderNo like :query or a.consigneename like :query  or a.consigneephone like :query) and a.signDelete = :signDelete")
    List<WebOrderItemResp> queryAllOrderItem(@Param(value = "query") String query, @Param(value = "signDelete") Boolean signDelete);
    
    @Query("select new com.dinglicom.order.domain.PayItem(id, consigneename, consigneephone, consigneeaddress, productname, productnum, createDate, firstDistributionDate, endDistributionDate, productTotalPrice, payname, paytime) from UserOrder a where a.orderState>=:orderstate and a.userPay=:userPay and a.signDelete = :signDelete")
    Page<PayItem> queryOrderPay(Pageable page, @Param(value = "orderstate") String orderstate, @Param(value = "userPay") Boolean userPay, @Param(value = "signDelete") Boolean signDelete);
}
