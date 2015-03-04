/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.order.repository;

import com.dinglicom.order.domain.CustomerOrderItem;
import com.dinglicom.order.domain.UserOrderPageItemResp;
import com.dinglicom.order.domain.WebOrderItemResp;
import com.dinglicom.order.entity.OrderItem;
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
public interface OrderItemDao extends PagingAndSortingRepository<OrderItem, Long>, JpaSpecificationExecutor<OrderItem> {    
//    @Query("from OrderItem a where a.orderNo=:orderNo and a.signDelete=:signDelete")
//    List<OrderItem> findByOrderNo(@Param(value = "orderNo") String orderNo, @Param(value = "signDelete") Boolean signDelete);

//    @Query("select new com.dinglicom.order.domain.CustomerOrderItem(a.product.id, a.product.smallPic, a.product.name, a.order.orderNo, a.order.userPay, a.order.orderState, a.distributionTarget, a.distributionType, a.distributionPeriod, a.distributionNum, a.productnum, a.completenum, a.productTotalPrice, a.firstDistributionDate, a.endDistributionDate,a.order.orderDesc)  from OrderItem a where a.order.user.id=:userId and a.order.orderState<:state and a.signDelete=:signDelete")
//    List<CustomerOrderItem> findByCustomerByWorkerAndLessComplete(@Param(value="userId")long userId, @Param(value="state")String state, @Param(value="signDelete")Boolean signDelete);

//    @Query("select new com.dinglicom.order.domain.CustomerOrderItem(a.product.id, a.product.smallPic, a.product.name, a.order.orderNo, a.order.userPay, a.order.orderState, a.distributionTarget, a.distributionType, a.distributionPeriod, a.distributionNum, a.productnum, a.completenum, a.productTotalPrice, a.firstDistributionDate, a.endDistributionDate,a.order.orderDesc)  from OrderItem a where a.orderNo=:orderno and a.signDelete=:signDelete")
//    List<CustomerOrderItem> findOrderDetailsByOrderno(@Param(value="orderno")String orderno, @Param(value="signDelete")Boolean signDelete);
    
//    @Query("select new com.dinglicom.order.domain.UserOrderPageItemResp(a.orderNo, a.order.orderState, a.productname, a.firstDistributionDate, a.endDistributionDate, a.productnum, a.completenum)  from OrderItem a where a.order.user.id=:userId and a.signDelete=:signDelete")
//    Page<UserOrderPageItemResp> findOrderItemsByCustomer(Pageable page, @Param(value="userId")long userId, @Param(value="signDelete")Boolean signDelete);
    
//    @Query("from OrderItem a where a.order.orderState < :orderState and a.totaldistributionnum < a.productnum and a.signDelete=:signDelete and a.firstDistributionDate <= :begindate")
//    List<OrderItem> findAllDispatchItem(@Param(value="orderState")String orderState, @Param(value="begindate")Date begindate, @Param(value="signDelete")Boolean signDelete );
  
//    @Query("select new com.dinglicom.reportnum.demain.ReportNumberItemResp(a.product.id, a.productname, sum(a.distributionNum)) from OrderItem a where a.distributionType in (:workday,:every) and a.totaldistributionnum < a.productnum and a.order.org.id=:orgid and a.order.orderState <:orderState and a.firstDistributionDate >= :begindate and a.signDelete=:signDelete group by a.product,a.productname")
//    List<ReportNumberItemResp> getWorkDayReportNumber(@Param(value="workday")String workday, @Param(value="every")String every, @Param(value="orgid")long orgid, @Param(value="orderState")String orderState, @Param(value="begindate")Date begindate, @Param(value = "signDelete") Boolean signDelete);
    
//    @Query("select new com.dinglicom.order.domain.WebOrderItemResp(a.orderNo, a.createDate, a.productname, a.order.userProxy, a.order.orderOrigin, a.order.consigneename, a.order.consigneephone, a.productTotalPrice, a.order.confirmtime, a.order.pausetime, a.order.pausedays, a.order.canceltime, a.order.completetime) from OrderItem a where a.signDelete = :signDelete")
//    Page<WebOrderItemResp> getAllOrderItem(Pageable page, @Param(value = "signDelete") Boolean signDelete);
    
//    @Query("select new com.dinglicom.order.domain.WebOrderItemResp(a.orderNo, a.createDate, a.productname, a.order.userProxy, a.order.orderOrigin, a.order.consigneename, a.order.consigneephone, a.productTotalPrice, a.order.confirmtime, a.order.pausetime, a.order.pausedays, a.order.canceltime, a.order.completetime) from OrderItem a where a.order.orderState = :orderState and a.signDelete = :signDelete")
//    Page<WebOrderItemResp> getAllOrderItem(Pageable page, @Param(value = "orderState") String orderState, @Param(value = "signDelete") Boolean signDelete);
    
//    @Query("select new com.dinglicom.order.domain.WebOrderItemResp(a.orderNo, a.createDate, a.productname, a.order.userProxy, a.order.orderOrigin, a.order.consigneename, a.order.consigneephone, a.productTotalPrice, a.order.confirmtime, a.order.pausetime, a.order.pausedays, a.order.canceltime, a.order.completetime) from OrderItem a where (a.orderNo like :query or a.order.consigneename like :query  or a.order.consigneephone like :query) and a.signDelete = :signDelete")
//    List<WebOrderItemResp> queryAllOrderItem(@Param(value = "query") String query, @Param(value = "signDelete") Boolean signDelete);
}
