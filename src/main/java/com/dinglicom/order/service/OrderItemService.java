/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.service;

/**
 *
 * @author panzhen
 */
public interface OrderItemService {
//    OrderItem read(long id);
//    OrderItem save(OrderItem orderItem);
//    Iterable<OrderItem> save(Iterable<OrderItem> orderItems);
//    void delete(OrderItem orderItem);
//    void delete(long id);
//    List<OrderItem> findByOrderNo(String orderNo);
//    /*findOrderDetailsstomerOrderResp findCustomerOrderItem(CustomeDetailReq req, UserInfo customer);
    
    /**
     * 用户分页查询自己订单数据
     * @param userId
     * @param pagenum
     * @param pagesize
     * @return 
     */
//    UserOrderPageResp findCustomerOrderItem(long userId, int pagenum, int pagesize);
    
    /**
     * 查找所有待配送的订单项目
     * @param beginDate
     * @return 
     */
//    List<OrderItem> findAllDispatchingItem(Date beginDate);
    
//    /**
//     * 订单对应的项目的状态的同步变化
//     * @param orderNo
//     * @param state 
//     */
//    void updateOrderItemState(String orderNo, String state);
    
    /**
     * 订单详情
     * @param customer
     * @param orderno
     * @return 
     */
//    OrderDetailResp findOrderDetails(UserInfo customer, String orderno);
    
    
    /**
     * 计算工作日每日报量
     * @param workstation
     * @param beginDate
     * @return 
     */
//    List<ReportNumberItemResp> findWorkdayReportNumberByWorkstation(SysOranizagion workstation, Date beginDate);
 
    /**
     * 计算周末每日报量
     * @param workstation
     * @param beginDate
     * @return 
     */
//    List<ReportNumberItemResp> findWorkendReportNumberByWorkstation(SysOranizagion workstation, Date beginDate);
    
    /**
     * web端分页查询所有订单信息
     * @param req
     * @return 
     */
//    WebOrderitemAllResp findWebOrderitem(WebOrderitemReq req);
    
    /**
     * web端查询订单
     * @param req
     * @return 
     */
//    WebQueryOrderitemRespBase queryWebOrderitem(WebQueryOrderitemReq req);
    
    /**
     * web端获取订单详情
     * @param orderNo
     * @return 
     */
//    WebOrderitemqueryDetailResp getOrderDetailByOrderno(String orderNo);
}
