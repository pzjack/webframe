/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.order.service.impl;

import com.dinglicom.order.entity.OrderItem;
import com.dinglicom.order.repository.OrderItemDao;
import com.dinglicom.order.service.OrderItemService;
import javax.annotation.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
//@CacheConfig(cacheNames = {"dataCache"})
public class OrderItemServiceImpl implements OrderItemService {

//    @Resource
//    private OrderItemDao orderItemDao;
//
//    @Override
//    @Transactional(readOnly = true)
////    @Cacheable
//    public OrderItem read(long id) {
//        return orderItemDao.findOne(id);
//    }
//
//    @Override
////    @Cacheable
//    public OrderItem save(OrderItem orderItem) {
//        return orderItemDao.save(orderItem);
//    }
//
//    @Override
////    @Cacheable
//    public Iterable<OrderItem> save(Iterable<OrderItem> orderItems) {
//        return orderItemDao.save(orderItems);
//    }
//
//    @Override
////    @CacheEvict
//    public void delete(OrderItem orderItem) {
//        orderItemDao.delete(orderItem);
//    }
//
//    @Override
////    @CacheEvict
//    public void delete(long id) {
//        orderItemDao.delete(id);
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<OrderItem> findByOrderNo(String orderNo) {
//        return orderItemDao.findByOrderNo(orderNo, Boolean.FALSE);
//    }

//    @Override
//    public void updateOrderItemState(String orderNo, String state) {
//        int c = orderItemDao.updateOrderItemState(orderNo, state, Boolean.FALSE);
//        if (c <= 0) {
//            throw new RuntimeException("订单号[" + orderNo + "]没有对应的条目被修改状态[" + state + "]");
//        }
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public CustomerOrderResp findCustomerOrderItem(CustomeDetailReq req, UserInfo customer) {
//        CustomerOrderResp resp = new CustomerOrderResp();
//        resp.setUser_id(customer.getId());
//        resp.setUser_name(customer.getRealname());
//
//        List<CustomerOrderItem> data = orderItemDao.findByCustomerByWorkerAndLessComplete(customer.getId(), UserOrderService.ORDER_STATE_COMPLETE, Boolean.FALSE);
//        resp.setData(data);
//        return resp;
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public OrderDetailResp findOrderDetails(UserInfo customer, String orderno) {
//        OrderDetailResp resp = new OrderDetailResp();
//        resp.setUser_id(customer.getId());
//        resp.setUser_name(customer.getRealname());
//
//        List<CustomerOrderItem> data = orderItemDao.findOrderDetailsByOrderno(orderno, Boolean.FALSE);
//        if (null != data && data.size() > 0) {
//            resp.setData(data.get(0));
//        }
//        return resp;
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public UserOrderPageResp findCustomerOrderItem(long userId, int pagenum, int pagesize) {
//        UserOrderPageResp resp = new UserOrderPageResp();
//        Page<UserOrderPageItemResp> page = orderItemDao.findOrderItemsByCustomer(buildPageRequest(pagenum, pagesize), userId, Boolean.FALSE);
//        resp.setData(page.getContent());
//        resp.setTotal_page(page.getTotalPages());
//        return resp;
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<OrderItem> findAllDispatchingItem(Date beginDate) {
//        return orderItemDao.findAllDispatchItem(UserOrderService.ORDER_STATE_PAUSE, beginDate, Boolean.FALSE);
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<ReportNumberItemResp> findWorkdayReportNumberByWorkstation(SysOranizagion workstation, Date beginDate) {
//        return orderItemDao.getWorkDayReportNumber(UserOrderService.DISTRIBUTION_TYPE_WORK, UserOrderService.DISTRIBUTION_TYPE_EVERY, workstation.getId(), UserOrderService.ORDER_STATE_PAUSE, beginDate, Boolean.FALSE);
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<ReportNumberItemResp> findWorkendReportNumberByWorkstation(SysOranizagion workstation, Date beginDate) {
//        return orderItemDao.getWorkDayReportNumber(UserOrderService.DISTRIBUTION_TYPE_END, UserOrderService.DISTRIBUTION_TYPE_EVERY, workstation.getId(), UserOrderService.ORDER_STATE_PAUSE, beginDate, Boolean.FALSE);
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public WebOrderitemAllResp findWebOrderitem(WebOrderitemReq req) {
//        WebOrderitemAllResp resp = new WebOrderitemAllResp();
////        WebOrderCounter counter = new WebOrderCounter();
////        resp.setStat_num(counter);
////        counter.setInit(orderItemDao.getCountByOrderState(UserOrderService.ORDER_STATE_SUBMIT, Boolean.FALSE));
////        counter.setConfirm(orderItemDao.getCountByOrderState(UserOrderService.ORDER_STATE_CONFIRM, Boolean.FALSE));
////        counter.setIng(orderItemDao.getCountByOrderState(UserOrderService.ORDER_STATE_DISPATCHING, Boolean.FALSE));
////        counter.setPause(orderItemDao.getCountByOrderState(UserOrderService.ORDER_STATE_PAUSE, Boolean.FALSE));
////        counter.setCancel(orderItemDao.getCountByOrderState(UserOrderService.ORDER_STATE_STOP, Boolean.FALSE));
////        counter.setDone(orderItemDao.getCountByOrderState(UserOrderService.ORDER_STATE_COMPLETE, Boolean.FALSE) + orderItemDao.getCountByOrderState(UserOrderService.ORDER_STATE_TERMINAT, Boolean.FALSE));
//
//        Page<WebOrderItemResp> page;
//        if (null != req.getStatus()) {
//            page = orderItemDao.getAllOrderItem(buildPageRequest(req.getPage(), req.getNum()), req.getStatus(), Boolean.FALSE);
//        } else {
//            page = orderItemDao.getAllOrderItem(buildPageRequest(req.getPage(), req.getNum()), Boolean.FALSE);
//        }
//        if (null != page) {
//            resp.setData(page.getContent());
//            resp.setTotal_page(page.getTotalPages());
//        }
//        return resp;
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public WebQueryOrderitemRespBase queryWebOrderitem(WebQueryOrderitemReq req) {
//        WebQueryOrderitemRespBase resp = new WebQueryOrderitemRespBase();
//        resp.setData(orderItemDao.queryAllOrderItem("%" + req.getQuery() + "%", Boolean.FALSE));
//        return resp;
//    }

//    @Override
//    @Transactional(readOnly = true)
//    public WebOrderitemqueryDetailResp getOrderDetailByOrderno(String orderNo) {
//        List<OrderItem> items = findByOrderNo(orderNo);
//        if (null != items && items.size() > 0) {
//            WebOrderitemqueryDetailResp resp = new WebOrderitemqueryDetailResp();
//            copyPropertyFromOrderitem(items.get(0), resp);
//            return resp;
//        }
//        return null;
//    }

//    private void copyPropertyFromOrderitem(OrderItem src, WebOrderitemqueryDetailResp dest) {
//        UserOrder order = src.getOrder();
//        dest.setDistribution_name(order.getOrgmame());
//        dest.setDistribution_num(src.getDistributionNum());
//        dest.setDistribution_period(src.getDistributionPeriod());
//        dest.setDistribution_target(src.getDistributionTarget());
//        dest.setDistribution_type(src.getDistributionType());
//        dest.setEnd_date(src.getEndDistributionDate());
//        dest.setLeft_num(src.getProductnum() - src.getCompletenum());
//        dest.setOrder_no(src.getOrderNo());
//        dest.setOrigin(order.getOrderOrigin());
//        dest.setProduct_name(src.getProductname());
//        dest.setProduct_price(src.getProductPrice());
//        dest.setProxy(order.isUserProxy());
//        dest.setProxy_user(order.getProxyname());
//        dest.setSend_num(src.getCompletenum());
//        dest.setStart_date(src.getFirstDistributionDate());
//        dest.setTel(order.getConsigneephone());
//        dest.setTime(src.getCreateDate());
//        dest.setTotal_price(src.getProductTotalPrice());
//        dest.setUser(order.getConsigneename());
//        
//        dest.setStatus(order.getOrderState());
//        dest.setConfirm_time(order.getConfirmtime());
//        dest.setPause_time(order.getPausetime());
//        dest.setPause_days(order.getPausedays());
//        dest.setCancel_time(order.getCanceltime());
//        dest.setDone_time(order.getCompletetime());
//    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(final int page, final int size) {
        return new PageRequest(page - 1, size);
    }
}
