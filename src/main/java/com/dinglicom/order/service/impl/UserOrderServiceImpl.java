/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.order.service.impl;

import com.dinglicom.frame.sys.domain.CustomeDetailReq;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysOranizagionService;
import com.dinglicom.frame.sys.service.UserInfoService;
import com.dinglicom.frame.sys.util.DateUtil;
import com.dinglicom.order.domain.CustomerOrderItem;
import com.dinglicom.order.domain.CustomerOrderResp;
import com.dinglicom.order.domain.OrderDetailResp;
import com.dinglicom.order.domain.OrderReq;
import com.dinglicom.order.domain.PayConfirmReq;
import com.dinglicom.order.domain.PayItem;
import com.dinglicom.order.domain.PayReq;
import com.dinglicom.order.domain.PayResp;
import com.dinglicom.order.domain.UserOrderPageItemResp;
import com.dinglicom.order.domain.UserOrderPageResp;
import com.dinglicom.order.domain.WebOrderCounter;
import com.dinglicom.order.domain.WebOrderCounterResp;
import com.dinglicom.order.domain.WebOrderItemResp;
import com.dinglicom.order.domain.WebOrderitemAllResp;
import com.dinglicom.order.domain.WebOrderitemReq;
import com.dinglicom.order.domain.WebOrderitemqueryDetailResp;
import com.dinglicom.order.domain.WebQueryOrderitemReq;
import com.dinglicom.order.domain.WebQueryOrderitemRespBase;
import com.dinglicom.order.entity.OrderChangeRecord;
import com.dinglicom.order.entity.UserOrder;
import com.dinglicom.order.repository.UserOrderDao;
import com.dinglicom.order.service.OrderChangeRecordService;
import com.dinglicom.order.service.UserOrderService;
import com.dinglicom.product.entity.UserProduct;
import com.dinglicom.product.service.UserProductService;
import com.dinglicom.reportnum.demain.ReportNumberItemResp;
import com.dinglicom.reportnum.entity.ReportnumberTime;
import com.dinglicom.reportnum.service.ReportnumberTimeService;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
@CacheConfig(cacheNames = {"dataCache"})
public class UserOrderServiceImpl implements UserOrderService {

    @Resource
    private UserOrderDao userOrderDao;
//    @Resource
//    private OrderItemService orderItemService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserProductService userProductService;
    @Resource
    private OrderChangeRecordService orderChangeRecordService;
    @Resource
    private SysOranizagionService sysOranizagionService;
    @Resource
    private ReportnumberTimeService reportnumberTimeService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable
    public UserOrder read(long id) {
        return userOrderDao.findOne(id);
    }

    @Override
    @Cacheable
    public UserOrder save(UserOrder userOrder) {
        return userOrderDao.save(userOrder);
    }

    @Override
    @Cacheable
    public Iterable<UserOrder> save(Iterable<UserOrder> userOrders) {
        return userOrderDao.save(userOrders);
    }

    @Override
    @CacheEvict
    public void delete(UserOrder userOrder) {
        userOrderDao.delete(userOrder);
    }

    @Override
    @CacheEvict
    public void delete(long id) {
        userOrderDao.delete(id);
    }

    @Override
    public UserOrder doCreateOrder(UserInfo worker, OrderReq orderReq) {
        UserInfo customer = userInfoService.read(orderReq.getUser_id());
        if (null == customer) {
            throw new RuntimeException("无收货人信息:" + orderReq.getUser_name());
        }
        UserProduct userProduct = userProductService.read(orderReq.getProduct_id());
        if (null == userProduct) {
            throw new RuntimeException("无商品信息:" + orderReq.getProduct_id());
        }
        //代订人角色验证
        //......
//        OrderItem orderItem = setOrderAttributeByProductAndReq(orderReq, userProduct);
        UserOrder order = createOrder(orderReq, customer, worker.getOrg());
        setOrderAttributeByProductAndReq(orderReq.getStart_date(), order, userProduct);
        order.setUserProxy(true);//代订
        order.setProxyname(worker.getRealname());
        order = userOrderDao.save(order);
        String orderNo = createOrderNo(order.getId());
        order.setOrderNo(orderNo);
        userOrderDao.save(order);
        return order;
    }

    @Override
    public UserOrder doCreateOrderSelf(UserInfo customer, OrderReq orderReq) {
        if (null == customer) {
            throw new RuntimeException("无收货人信息:" + orderReq.getUser_name());
        }
        SysOranizagion workerstation;
        if (orderReq.getDistribution_target_id() > 0) {//
            workerstation = sysOranizagionService.read(orderReq.getDistribution_target_id());
        } else {
            workerstation = customer.getOrg();
        }
        if (null == workerstation) {
            throw new RuntimeException("无奶站或者配送方信息:" + orderReq.getDistribution_target_id());
        }
        UserProduct userProduct = userProductService.read(orderReq.getProduct_id());
        if (null == userProduct) {
            throw new RuntimeException("无商品信息:" + orderReq.getProduct_id());
        }

        UserOrder order = createOrder(orderReq, customer, workerstation);
        setOrderAttributeByProductAndReq(orderReq.getStart_date(), order, userProduct);
        order.setUserProxy(false);
        order = userOrderDao.save(order);
        String orderNo = createOrderNo(order.getId());
        order.setOrderNo(orderNo);
        userOrderDao.save(order);
        return order;
    }

    private UserOrder createOrder(OrderReq orderReq, UserInfo customer, SysOranizagion workerstation) {
        UserOrder order = new UserOrder();
//        order.setOrderNo(orderNo);
        order.setOrderDesc(orderReq.getOrder_desc());
        order.setOrderOrigin(orderReq.getOrder_origin());
        order.setDistributionNum(orderReq.getDistribution_num());
        order.setDistributionPeriod(orderReq.getDistribution_period());
        order.setDistributionTarget(orderReq.getDistribution_target());
//        order.setAddress(userAddress);
        order.setOrderState(ORDER_STATE_CONFIRM);//confirm代订直接到以确认状态
        order.setUserPay(false);//默认未支付
        if (null != customer.getId() && customer.getId() > 0) {//注册用户
            order.setUser(customer);
        }
        order.setConsigneename(customer.getRealname());
        order.setConsigneephone(customer.getPhone());
        order.setConsigneeaddress(customer.getAddress());

        order.setOrg(workerstation);
        order.setOrgmame(workerstation.getName());

        if (null == orderReq.getDistribution_type()) {
            order.setDistributionType(DISTRIBUTION_TYPE_EVERY);
        } else {
            switch (orderReq.getDistribution_type()) {
                case DISTRIBUTION_TYPE_WORK://工作日
                    order.setDistributionType(DISTRIBUTION_TYPE_WORK);
                    break;
                case DISTRIBUTION_TYPE_END://周末
                    order.setDistributionType(DISTRIBUTION_TYPE_END);
                    break;
                default://每天
                    order.setDistributionType(DISTRIBUTION_TYPE_EVERY);
            }
        }
        return order;
    }

    private void setOrderAttributeByProductAndReq(Date firstDate, UserOrder order, UserProduct userProduct) {
//        OrderItem order = new OrderItem();
        order.setProduct(userProduct);
        if (null != order.getDistributionTarget()) {
            switch (order.getDistributionTarget()) {
                case ORDER_DELIVERY_TARGET_SELF://do it yourself//到奶站自提
                    order.setProductPrice(userProduct.getPrice());
                    break;
                default://send to home//送货上门
                    order.setProductPrice(userProduct.getHomePrice());
                    break;
            }
            order.setProductTotalPrice(order.getProductPrice() * order.getDistributionPeriod());
        } else {
            order.setDistributionTarget(ORDER_DELIVERY_TARGET_SELF);
            order.setProductPrice(userProduct.getPrice());
            order.setProductTotalPrice(userProduct.getPrice() * order.getDistributionPeriod());
        }
        Calendar c = Calendar.getInstance();
        if (null == firstDate) {
            firstDate = DateUtil.getAfterCronDay(c, 2);
        }
        Calendar fc = Calendar.getInstance();
        fc.setTime(firstDate);
        fc.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
        fc.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
        fc.set(Calendar.SECOND, 59);
        c = DateUtil.getOneDayMaxtime(c);
        if (fc.before(c)) {
            throw new RuntimeException("起送时间不能等于或者早于当天！");
        }
        ReportnumberTime pt = reportnumberTimeService.get();
        if(null != pt && null != pt.getTime()) {
            int p = pt.getTime().indexOf(":");
            if(p > 0 && p + 1 < pt.getTime().length()) {
                int h = Integer.valueOf(pt.getTime().substring(0, p));
                int m = Integer.valueOf(pt.getTime().substring(p + 1));
                if(fc.get(Calendar.HOUR_OF_DAY) >= h && fc.get(Calendar.MINUTE) > m) {
                    c.setTimeInMillis(DateUtil.getAfterDay(c, 2).getTime());
                    c = DateUtil.getOneDayMintime(c);
                    if(fc.before(c)) {
                        throw new RuntimeException("在报量截止时间之后提交的订单，起送时间不能早于后天！");
                    }
                }
            }
        }
        c.setTime(firstDate);
        c = DateUtil.getOneDayMintime(c);
        caculateOrderDate(order, c, userProduct.getProductnum());
        order.setProductname(userProduct.getName());
        order.setProductsmallpic(userProduct.getSmallPic());
        order.setProductnum(userProduct.getProductnum() * order.getDistributionPeriod() * order.getDistributionNum());
    }

    public void caculateOrderDate(UserOrder order, Calendar c, int productnum) {
        switch (order.getDistributionType()) {
            case DISTRIBUTION_TYPE_WORK://工作日
                order.setFirstDistributionDate(DateUtil.getLastWorkday(c).getTime());
                order.setEndDistributionDate(DateUtil.getAfterWorkDay(c, order.getDistributionPeriod() * productnum));
                break;
            case DISTRIBUTION_TYPE_END://周末
                order.setFirstDistributionDate(DateUtil.getLastWorkEndday(c).getTime());
                order.setEndDistributionDate(DateUtil.getAfterWorkEndDay(c, order.getDistributionPeriod() * productnum));
                break;
            default://每天
                order.setDistributionType(DISTRIBUTION_TYPE_EVERY);
                order.setFirstDistributionDate(c.getTime());
                order.setEndDistributionDate(DateUtil.getAfterDay(c, order.getDistributionPeriod() * productnum));
        }
    }

    private String createOrderNo(long orderId) {
        Calendar c = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(c.get(Calendar.YEAR));
        sb.append(formatNumber(c.get(Calendar.MONTH) + 1, 2, "0"));
        sb.append(formatNumber(orderId, 14, "0"));
        return sb.toString();
    }

    private String formatNumber(long num, int length, String formatString) {
        StringBuilder sb = new StringBuilder();
        String numString = "" + num;
        for (int i = numString.length(); i < length; i++) {
            sb.append(formatString);
        }
        sb.append(num);
        return sb.toString();
    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(final int page, final int size) {
        return new PageRequest(page - 1, size);
    }

    @Override
    @Transactional(readOnly = true)
    public long findNotCompleteOrderCount(long userId) {
        return userOrderDao.findByUserAndOrderStateLess(userId, ORDER_STATE_COMPLETE, Boolean.FALSE);
    }

    @Override
    @Transactional(readOnly = true)
    public long findCompleteOrderCount(long userId) {
        return userOrderDao.findByUserAndOrderStateGreateEqual(userId, ORDER_STATE_COMPLETE, Boolean.FALSE);
    }

    @Override
    public void stopOrder(UserInfo userInfo, String orderNo) throws IOException {
        UserOrder order = userOrderDao.findByOrderno(orderNo);
        if (null == order) {
            throw new IOException("订单不存在");
        }
        if (order.getOrderState().compareTo("300") >= 0) {
            throw new IOException("当前订单状态不能终止");
        }
        OrderChangeRecord orderChangeRecord = new OrderChangeRecord();
        orderChangeRecord.setState(OrderChangeRecordService.ORDER_STOP);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        orderChangeRecord.setBeginTime(c.getTime());
        orderChangeRecord.setOptUser(userInfo);
        orderChangeRecord.setOptUserName(userInfo.getRealname());
        orderChangeRecord.setOrderNo(orderNo);
        orderChangeRecord.setOrder(order);
        orderChangeRecordService.save(orderChangeRecord);
        order.setOrderState(ORDER_STATE_STOP);
    }

    @Override
    public void pauseOrder(UserInfo userInfo, String orderNo, int days) throws IOException {
        UserOrder order = userOrderDao.findByOrderno(orderNo);
        if (null == order) {
            throw new RuntimeException("订单不存在");
        }
        if (order.getOrderState().compareTo(ORDER_STATE_PAUSE) >= 0) {
            throw new RuntimeException("当前订单状态不能终止");
        }

        Date nextday = DateUtil.getNextDay();
        OrderChangeRecord orderChangeRecord = new OrderChangeRecord();
        orderChangeRecord.setState(OrderChangeRecordService.ORDER_PAUSE);
        orderChangeRecord.setPausestate(OrderChangeRecordService.PAUSE_AMONT);
        orderChangeRecord.setEndTime(DateUtil.getAfterDay(nextday, days));
        orderChangeRecord.setBeginTime(nextday);
        orderChangeRecord.setPauseDay(days);
        orderChangeRecord.setOptUser(userInfo);
        orderChangeRecord.setOptUserName(userInfo.getRealname());
        orderChangeRecord.setOrderNo(orderNo);
        orderChangeRecord.setOrder(order);
        orderChangeRecordService.save(orderChangeRecord);

        order.setPausedays(days);
        order.setPausename(userInfo.getRealname());
        order.setPausetime(new Date());
        order.setOrderState(ORDER_STATE_PAUSE);
    }

    @Override
    public void continueOrder(UserOrder srcOrder, UserInfo user, Long productId) {
        UserOrder order = createOrderByOrder(srcOrder, productId);
        if (!user.getId().equals(srcOrder.getUser().getId())) {//代订
            order.setProxyname(user.getRealname());
            order.setUserProxy(true);
        }
        order = userOrderDao.save(order);
        String orderNo = createOrderNo(order.getId());
        order.setOrderNo(orderNo);
        userOrderDao.save(order);
    }

    private UserOrder createOrderByOrder(UserOrder srcOrder, Long productId) {
        UserOrder order = new UserOrder();
        order.setContinueorderno(srcOrder.getOrderNo());
        order.setOrderDesc("续订自订单号：" + srcOrder.getOrderNo());
        order.setOrderOrigin(srcOrder.getOrderOrigin());

        order.setOrderState(UserOrderService.ORDER_STATE_CONFIRM);

        order.setOrg(srcOrder.getOrg());
        order.setOrgmame(srcOrder.getOrgmame());

        order.setUser(srcOrder.getUser());
        order.setConsigneename(srcOrder.getConsigneename());
        order.setConsigneephone(srcOrder.getConsigneephone());
        order.setConsigneeaddress(srcOrder.getConsigneeaddress());
        order.setUserPay(false);
//        order.setUserPay(true);

        order.setDistributionPeriod(1);//续订只能续订一个月
        order.setDistributionNum(srcOrder.getDistributionNum());

        if (null != productId && !srcOrder.getProduct().getId().equals(productId)) {//更换产品
            UserProduct product = userProductService.read(productId);
            if (null != product) {
                order.setProduct(product);
                order.setProductname(product.getName());
                order.setProductsmallpic(product.getSmallPic());
                if (UserOrderService.ORDER_DELIVERY_TARGET_SELF.equals(order.getDistributionTarget())) {
                    order.setProductPrice(product.getPrice());
                } else {
                    order.setProductPrice(product.getHomePrice());
                }
                order.setProductTotalPrice(order.getProductPrice() * order.getDistributionNum());
            }
        } else {
            order.setProduct(srcOrder.getProduct());
            order.setProductname(srcOrder.getProductname());
            order.setProductsmallpic(srcOrder.getProductsmallpic());
            order.setProductPrice(srcOrder.getProductPrice());
            order.setProductTotalPrice(order.getProductPrice() * order.getDistributionNum());
        }
        order.setProductnum(srcOrder.getProductnum());

        order.setDistributionTarget(srcOrder.getDistributionTarget());
        order.setDistributionType(srcOrder.getDistributionType());

//        item.setItemstate(order.getOrderState());
        switch (srcOrder.getDistributionType()) {
            case DISTRIBUTION_TYPE_WORK:
                order.setFirstDistributionDate(DateUtil.getLastWorkday(srcOrder.getEndDistributionDate()));
                order.setEndDistributionDate(DateUtil.getAfterWorkDay(srcOrder.getEndDistributionDate(), order.getDistributionPeriod() * order.getProductnum()));
                break;
            case DISTRIBUTION_TYPE_END://周末
                order.setFirstDistributionDate(DateUtil.getLastWorkEndday(srcOrder.getEndDistributionDate()));
                order.setEndDistributionDate(DateUtil.getAfterWorkDay(srcOrder.getEndDistributionDate(), order.getDistributionPeriod() * order.getProductnum()));
                break;
            default:
                order.setFirstDistributionDate(DateUtil.getCronDayMinDate(srcOrder.getEndDistributionDate()));
                order.setEndDistributionDate(DateUtil.getAfterDay(srcOrder.getEndDistributionDate(), order.getDistributionPeriod() * order.getProductnum()));
        }
        return order;
    }

    @Override
    public UserOrder findByOrderNo(String orderNo) {
        return userOrderDao.findByOrderno(orderNo);
    }

    @Override
    public void confirmOrder(String orderNo, UserInfo user) {
        UserOrder order = userOrderDao.findByOrderno(orderNo);
        if (null == order) {
            throw new RuntimeException("No found any order by order_no:" + orderNo);
        }
        if (!UserOrderService.ORDER_STATE_SUBMIT.equals(order.getOrderState())) {
            throw new RuntimeException("Current order state is error.");
        }
        order.setOrderState(UserOrderService.ORDER_STATE_CONFIRM);
        order.setConfirmname(user.getRealname());
        order.setConfirmtime(new Date());

        userOrderDao.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public WebOrderCounterResp findWebOrdercounter(UserInfo user) {
        WebOrderCounterResp resp = new WebOrderCounterResp();
        WebOrderCounter counter = new WebOrderCounter();
        resp.setData(counter);
        if(UserInfoService.USER_ROLE_ADMINISTRATOR.equals(user.getUserType())) {
            counter.setInit(userOrderDao.getCountByOrderState(UserOrderService.ORDER_STATE_SUBMIT, Boolean.FALSE));
            counter.setConfirm(userOrderDao.getCountByOrderState(UserOrderService.ORDER_STATE_CONFIRM, Boolean.FALSE));
            counter.setIng(userOrderDao.getCountByOrderState(UserOrderService.ORDER_STATE_DISPATCHING, Boolean.FALSE));
            counter.setPause(userOrderDao.getCountByOrderState(UserOrderService.ORDER_STATE_PAUSE, Boolean.FALSE));
            counter.setCancel(userOrderDao.getCountByOrderState(UserOrderService.ORDER_STATE_STOP, Boolean.FALSE));
            counter.setDone(userOrderDao.getCountByOrderState(UserOrderService.ORDER_STATE_COMPLETE, Boolean.FALSE) + userOrderDao.getCountByOrderState(UserOrderService.ORDER_STATE_TERMINAT, Boolean.FALSE));
        } else if(UserInfoService.USER_ROLE_STATION.equals(user.getUserType())) {
            Long stationId = user.getOrg().getId();
            counter.setInit(userOrderDao.getCountByOrderState(stationId, UserOrderService.ORDER_STATE_SUBMIT, Boolean.FALSE));
            counter.setConfirm(userOrderDao.getCountByOrderState(stationId, UserOrderService.ORDER_STATE_CONFIRM, Boolean.FALSE));
            counter.setIng(userOrderDao.getCountByOrderState(stationId, UserOrderService.ORDER_STATE_DISPATCHING, Boolean.FALSE));
            counter.setPause(userOrderDao.getCountByOrderState(stationId, UserOrderService.ORDER_STATE_PAUSE, Boolean.FALSE));
            counter.setCancel(userOrderDao.getCountByOrderState(stationId, UserOrderService.ORDER_STATE_STOP, Boolean.FALSE));
            counter.setDone(userOrderDao.getCountByOrderState(stationId, UserOrderService.ORDER_STATE_COMPLETE, Boolean.FALSE) + userOrderDao.getCountByOrderState(stationId, UserOrderService.ORDER_STATE_TERMINAT, Boolean.FALSE));
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public WebOrderitemqueryDetailResp getOrderDetailByOrderno(String orderNo) {
        UserOrder order = findByOrderNo(orderNo);
        WebOrderitemqueryDetailResp resp = new WebOrderitemqueryDetailResp();
        if (null != order) {
            copyPropertyFromOrderitem(order, resp);
            return resp;
        }
        return resp;
    }

    private void copyPropertyFromOrderitem(UserOrder src, WebOrderitemqueryDetailResp dest) {
        dest.setDistribution_name(src.getOrgmame());
        dest.setDistribution_num(src.getDistributionNum());
        dest.setDistribution_period(src.getDistributionPeriod());
        dest.setDistribution_target(src.getDistributionTarget());
        dest.setDistribution_type(src.getDistributionType());
        dest.setEnd_date(src.getEndDistributionDate());
        dest.setLeft_num((long) src.getProductnum() - src.getCompletenum());
        dest.setOrder_no(src.getOrderNo());
        dest.setOrigin(src.getOrderOrigin());
        dest.setProduct_name(src.getProductname());
        dest.setProduct_price(src.getProductPrice());
        dest.setProxy(src.isUserProxy());
        dest.setProxy_user(src.getProxyname());
        dest.setSend_num((long) src.getCompletenum());
        dest.setStart_date(src.getFirstDistributionDate());
        dest.setTel(src.getConsigneephone());
        dest.setTime(src.getCreateDate());
        dest.setTotal_price(src.getProductTotalPrice());
        dest.setUser(src.getConsigneename());

        dest.setStatus(src.getOrderState());
        dest.setConfirm_time(src.getConfirmtime());
        dest.setPause_time(src.getPausetime());
        dest.setPause_days(src.getPausedays());
        dest.setCancel_time(src.getCanceltime());
        dest.setDone_time(src.getCompletetime());
        dest.setDesc(src.getOrderDesc());
        dest.setAddress(src.getConsigneeaddress());
        dest.setDelivery_man(src.getMilkmanname());
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerOrderResp findCustomerOrderItem(CustomeDetailReq req, UserInfo customer) {
        CustomerOrderResp resp = new CustomerOrderResp();
        resp.setUser_id(customer.getId());
        resp.setUser_name(customer.getRealname());

        List<CustomerOrderItem> data = userOrderDao.findByCustomerByWorkerAndLessComplete(customer.getId(), UserOrderService.ORDER_STATE_COMPLETE, Boolean.FALSE);
        resp.setData(data);
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailResp findOrderDetails(UserInfo customer, String orderno) {
        OrderDetailResp resp = new OrderDetailResp();
        resp.setUser_id(customer.getId());
        resp.setUser_name(customer.getRealname());

        List<CustomerOrderItem> data = userOrderDao.findOrderDetailsByOrderno(orderno, Boolean.FALSE);
        if (null != data && data.size() > 0) {
            resp.setData(data.get(0));
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public UserOrderPageResp findCustomerOrderItem(long userId, int pagenum, int pagesize) {
        UserOrderPageResp resp = new UserOrderPageResp();
        Page<UserOrderPageItemResp> page = userOrderDao.findOrderItemsByCustomer(buildPageRequest(pagenum, pagesize), userId, Boolean.FALSE);
        resp.setData(page.getContent());
        resp.setTotal_page(page.getTotalPages());
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserOrder> findAllDispatchingItem(SysOranizagion nz, Date beginDate) {
        return userOrderDao.findAllDispatchItem(nz, UserOrderService.ORDER_STATE_PAUSE, beginDate, Boolean.FALSE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportNumberItemResp> findWorkdayReportNumberByWorkstation(SysOranizagion workstation, Date beginDate) {
        return userOrderDao.getWorkDayReportNumber(UserOrderService.DISTRIBUTION_TYPE_WORK, UserOrderService.DISTRIBUTION_TYPE_EVERY, workstation.getId(), UserOrderService.ORDER_STATE_PAUSE, beginDate, Boolean.FALSE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportNumberItemResp> findWorkendReportNumberByWorkstation(SysOranizagion workstation, Date beginDate) {
        return userOrderDao.getWorkDayReportNumber(UserOrderService.DISTRIBUTION_TYPE_END, UserOrderService.DISTRIBUTION_TYPE_EVERY, workstation.getId(), UserOrderService.ORDER_STATE_PAUSE, beginDate, Boolean.FALSE);
    }

    @Override
    @Transactional(readOnly = true)
    public WebQueryOrderitemRespBase queryWebOrderitem(WebQueryOrderitemReq req) {
        WebQueryOrderitemRespBase resp = new WebQueryOrderitemRespBase();
        resp.setData(userOrderDao.queryAllOrderItem("%" + req.getQuery() + "%", Boolean.FALSE));
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public WebOrderitemAllResp findWebOrderitem(WebOrderitemReq req, UserInfo user) {
        WebOrderitemAllResp resp = new WebOrderitemAllResp();

        Page<WebOrderItemResp> page = null;
        if(UserInfoService.USER_ROLE_ADMINISTRATOR.equalsIgnoreCase(user.getUserType())) {
            if (null != req.getStatus()) {
                if (UserOrderService.ORDER_STATE_COMPLETE.equals(req.getStatus())) {
                    page = userOrderDao.getAllOrderItem(buildPageRequest(req.getPage(), req.getNum()), req.getStatus(), UserOrderService.ORDER_STATE_TERMINAT, Boolean.FALSE);
                } else {
                    page = userOrderDao.getAllOrderItem(buildPageRequest(req.getPage(), req.getNum()), req.getStatus(), Boolean.FALSE);
                }
            } else {
                page = userOrderDao.getAllOrderItem(buildPageRequest(req.getPage(), req.getNum()), Boolean.FALSE);
            }
        } else if(UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(user.getUserType())) {
            Long stationId = user.getOrg().getId();
            if (null != req.getStatus()) {
                if (UserOrderService.ORDER_STATE_COMPLETE.equals(req.getStatus())) {
                    page = userOrderDao.getAllOrderItem(buildPageRequest(req.getPage(), req.getNum()), stationId, req.getStatus(), UserOrderService.ORDER_STATE_TERMINAT, Boolean.FALSE);
                } else {
                    page = userOrderDao.getAllOrderItem(buildPageRequest(req.getPage(), req.getNum()), stationId, req.getStatus(), Boolean.FALSE);
                }
            } else {
                page = userOrderDao.getAllOrderItem(buildPageRequest(req.getPage(), req.getNum()), stationId, Boolean.FALSE);
            }
        }
        if (null != page) {
            resp.setData(page.getContent());
            resp.setTotal_page(page.getTotalPages());
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public PayResp findOrderPayInfo(PayReq req) {
        PayResp resp = new PayResp();
        Page<PayItem> page;
        if (null != req.getStatus() && "ing".equalsIgnoreCase(req.getStatus())) {
            page = userOrderDao.queryOrderPay(buildPageRequest(req.getPage(), req.getNum()), UserOrderService.ORDER_STATE_COMPLETE, Boolean.FALSE, Boolean.FALSE);
        } else {
            page = userOrderDao.queryOrderPay(buildPageRequest(req.getPage(), req.getNum()), UserOrderService.ORDER_STATE_STOP, Boolean.TRUE, Boolean.FALSE);
        }
        if (null != page && null != page.getContent()) {
            resp.setTotal_num(page.getTotalElements());
            resp.setData(page.getContent());
        }
        return resp;
    }

    @Override
    public void doOrderPayment(PayConfirmReq req) {
        UserOrder order = userOrderDao.findOne(req.getId());
        order.setPayname(req.getPayee());
        order.setPaytime(new Date());
        order.setUserPay(Boolean.TRUE);
//        if(UserOrderService.ORDER_STATE_COMPLETE.equals(order.getOrderState())) {
        order.setOrderState(UserOrderService.ORDER_STATE_TERMINAT);
//        } else 
        userOrderDao.save(order);
    }
}
