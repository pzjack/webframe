/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.service;

import com.dinglicom.frame.sys.domain.CustomeDetailReq;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.order.domain.CustomerOrderResp;
import com.dinglicom.order.domain.OrderDetailResp;
import com.dinglicom.order.domain.OrderReq;
import com.dinglicom.order.domain.PayConfirmReq;
import com.dinglicom.order.domain.PayReq;
import com.dinglicom.order.domain.PayResp;
import com.dinglicom.order.domain.UserOrderPageResp;
import com.dinglicom.order.domain.WebOrderCounterResp;
import com.dinglicom.order.domain.WebOrderitemAllResp;
import com.dinglicom.order.domain.WebOrderitemReq;
import com.dinglicom.order.domain.WebOrderitemqueryDetailResp;
import com.dinglicom.order.domain.WebQueryOrderitemReq;
import com.dinglicom.order.domain.WebQueryOrderitemRespBase;
import com.dinglicom.order.entity.UserOrder;
import com.dinglicom.reportnum.demain.ReportNumberItemResp;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author panzhen
 */
public interface UserOrderService {
    final static String ORDER_STATE_SUBMIT = "000";//提交未确认
    final static String ORDER_STATE_CONFIRM = "100";//已确认
    final static String ORDER_STATE_DISPATCHING = "140";//配送中
    final static String ORDER_STATE_PAUSE = "180";//停止暂停
//    final static String ORDER_STATE_CONTINUE = "260";//续订的订单
    final static String ORDER_STATE_COMPLETE = "300";//配送完成
    final static String ORDER_STATE_STOP = "350";//订单停止(可能配送未完成)
    final static String ORDER_STATE_TERMINAT = "400";//终结,可能正常完成(配送完成、付款完成)，也可能被用户终结(比如送货未完成阶段，用户不想继续)。
    
    final static String ORDER_DELIVERY_TARGET_SELF = "self";//到奶站自取
    final static String ORDER_DELIVERY_TARGET_HOME = "delivery";//送货上门
    final static String ORDER_DELIVERY_TARGET_STATION = "station";//送奶工送货上门
    
    final static String DISTRIBUTION_TYPE_EVERY = "everyday";//每天配送
    final static String DISTRIBUTION_TYPE_WORK = "workday";//仅工作日配送
    final static String DISTRIBUTION_TYPE_END = "wenkend";//仅周末配送
    
    UserOrder read(long id);
    UserOrder save(UserOrder userOrder);
    Iterable<UserOrder> save(Iterable<UserOrder> userOrders);
    void delete(UserOrder userOrder);
    void delete(long id);
    /**
     * 生成订单
     * @param worker
     * @param orderReq
     * @return 
     */
    UserOrder doCreateOrder(UserInfo worker, OrderReq orderReq);
    
    /**
     * 由登录用户或非登录用户产生订单
     * @param customer
     * @param orderReq
     * @return 
     */
    UserOrder doCreateOrderSelf(UserInfo customer, OrderReq orderReq);
    
    /**
     * 按照订单号查询
     * @param orderNo
     * @return 
     */
    UserOrder findByOrderNo(String orderNo);
    
    /**
     * 用户未完成订单的数量
     * @param userId
     * @return 
     */
    long findNotCompleteOrderCount(long userId);
    
    /**
     * 用户已经完成订单的数量
     * @param userId
     * @return 
     */
    long findCompleteOrderCount(long userId);
    
    /**
     * 终止订单
     * @param userInfo
     * @param orderNo
     * @throws IOException 
     */
    void stopOrder(UserInfo userInfo, String orderNo) throws IOException;
    
    /**
     * 暂停订单配送
     * @param userInfo
     * @param orderNo
     * @param days
     * @throws IOException 
     */
    void pauseOrder(UserInfo userInfo, String orderNo, int days) throws IOException;
    
    /**
     * 续订订单
     * @param srcOrder
     * @param user
     * @param productId 
     */
    void continueOrder(UserOrder srcOrder, UserInfo user, Long productId);
    
    /**
     * 订单确认
     * @param orderNo
     * @param user 
     */
    void confirmOrder(String orderNo, UserInfo user);
    
    /**
     * web端订单统计
     * @return 
     */
    WebOrderCounterResp findWebOrdercounter();
    
    /**
     * web端获取订单详情
     * @param orderNo
     * @return 
     */
    WebOrderitemqueryDetailResp getOrderDetailByOrderno(String orderNo);
    
    /**
     * 查询订户的所有订单信息
     * @param req
     * @param customer
     * @return 
     */
    CustomerOrderResp findCustomerOrderItem(CustomeDetailReq req, UserInfo customer);
    
    /**
     * 订单详情
     * @param customer
     * @param orderno
     * @return 
     */
    OrderDetailResp findOrderDetails(UserInfo customer, String orderno);
    
    /**
     * 用户分页查询自己订单数据
     * @param userId
     * @param pagenum
     * @param pagesize
     * @return 
     */
    UserOrderPageResp findCustomerOrderItem(long userId, int pagenum, int pagesize);
    
    /**
     * 查找所有待配送的订单项目
     * @param beginDate
     * @return 
     */
    List<UserOrder> findAllDispatchingItem(SysOranizagion nz, Date beginDate);
    /**
     * 计算工作日每日报量
     * @param workstation
     * @param beginDate
     * @return 
     */
    List<ReportNumberItemResp> findWorkdayReportNumberByWorkstation(SysOranizagion workstation, Date beginDate);
    /**
     * 计算周末每日报量
     * @param workstation
     * @param beginDate
     * @return 
     */
    List<ReportNumberItemResp> findWorkendReportNumberByWorkstation(SysOranizagion workstation, Date beginDate);
    
    /**
     * web端分页查询所有订单信息
     * @param req
     * @return 
     */
    WebOrderitemAllResp findWebOrderitem(WebOrderitemReq req);
    
    /**
     * web端查询订单
     * @param req
     * @return 
     */
    WebQueryOrderitemRespBase queryWebOrderitem(WebQueryOrderitemReq req);
    
    /**
     * 查询是否完成支付的订单
     * @param req
     * @return 
     */
    PayResp findOrderPayInfo(PayReq req);
    
    /**
     * 完成支付
     * @param req 
     */
    void doOrderPayment(PayConfirmReq req);
}
