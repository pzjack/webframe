/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.order.web;

import com.dinglicom.frame.sys.domain.AdminReqBase;
import com.dinglicom.frame.sys.domain.AppPageReqBase;
import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.domain.CustomeDetailReq;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.sys.service.UserInfoService;
import com.dinglicom.frame.web.AppControllerBase;
import com.dinglicom.order.domain.OrderCreateResp;
import com.dinglicom.order.domain.OrderDelvReq;
import com.dinglicom.order.domain.OrderReq;
import com.dinglicom.order.domain.OrderStateChangeReq;
import com.dinglicom.order.domain.PayConfirmReq;
import com.dinglicom.order.domain.PayReq;
import com.dinglicom.order.domain.WebOrdergetReq;
import com.dinglicom.order.domain.WebOrderitemReq;
import com.dinglicom.order.domain.WebQueryOrderitemReq;
import com.dinglicom.order.entity.UserOrder;
import com.dinglicom.order.service.UserOrderService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author panzhen
 */
@Controller
@RequestMapping(value = "/api/v1/order")
public class UserOrderController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(UserOrderController.class);

    @Resource
    private SysTokenService sysTokenService;
    @Resource
    private UserOrderService userOrderService;
    @Resource
    private UserInfoService userInfoService;

    /**
     * 送奶工代用户创建订单
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/create")
    public @ResponseBody
    BaseMsgBean save(OrderReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 >= req.getUid() || 0 >= req.getProduct_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            userOrderService.doCreateOrder(worker, req);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("create order", e);
            msg.setCode(1);
            msg.setResult("生成订单失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 用户登录后自己创建订单
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/lgncreateorder")
    public @ResponseBody
    BaseMsgBean createOrderSelf(OrderReq req) {
        OrderCreateResp msg = new OrderCreateResp();
        UserInfo customer = validateToken(sysTokenService, req, msg);
        if (null == customer || 0 >= req.getUid() || 0 >= req.getProduct_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            UserOrder order = userOrderService.doCreateOrderSelf(customer, req);
            if (null != order) {
                msg.setOrder_no(order.getOrderNo());
            }
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("create order", e);
            msg.setCode(1);
            msg.setResult("生成订单失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 未登录用户创建订单
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/createorder")
    public @ResponseBody
    BaseMsgBean createOrderSelfNoLogin(OrderReq req) {
        OrderCreateResp msg = new OrderCreateResp();
        if (0 >= req.getDistribution_target_id() || null == req.getUser_name() || null == req.getTel() || null == req.getAddress() || 0 >= req.getProduct_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        UserInfo customer = new UserInfo();
        customer.setAddress(req.getAddress());
        customer.setPhone(req.getTel());
        customer.setRealname(req.getUser_name());

        try {
            UserOrder order = userOrderService.doCreateOrderSelf(customer, req);
            if (null != order) {
                msg.setOrder_no(order.getOrderNo());
            }
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("create order", e);
            msg.setCode(1);
            msg.setResult("生成订单失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 送奶工查找订户订单详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/customeritem")
    public @ResponseBody
    BaseMsgBean findCustomerOrderItems(CustomeDetailReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 >= req.getUid() || 0 >= req.getUser_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        UserInfo customer = userInfoService.read(req.getUser_id());
        if (null == customer) {
            msg.setCode(1);
            msg.setResult("不存在用户user_id=" + req.getUser_id());
            return msg;
        }
        try {
            msg = userOrderService.findCustomerOrderItem(req, customer);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("find user order", e);
            msg.setCode(1);
            msg.setResult("生成订单失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 用户分页获取自己订单列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/sfcustomeritem")
    public @ResponseBody
    BaseMsgBean findSelfOrderItems(AppPageReqBase req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo customer = validateToken(sysTokenService, req, msg);
        if (null == customer || 0 >= req.getUid() || 0 >= req.getNum() || 0 >= req.getPage()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userOrderService.findCustomerOrderItem(customer.getId(), req.getPage(), req.getNum());
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("find user order", e);
            msg.setCode(1);
            msg.setResult("生成订单失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 用户查找订单详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/orderdetail")
    public @ResponseBody
    BaseMsgBean findOrderDetails(CustomeDetailReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo customer = validateToken(sysTokenService, req, msg);
        if (null == customer || 0 >= req.getUid() || null == req.getOrder_no()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userOrderService.findOrderDetails(customer, req.getOrder_no());
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("find user order", e);
            msg.setCode(1);
            msg.setResult("生成订单失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 送奶工终止用户订单
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/stoporder")
    public @ResponseBody
    BaseMsgBean stopCustomerOrderByWorker(OrderStateChangeReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || null == req.getOrder_no()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            userOrderService.stopOrder(worker, req.getOrder_no());
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("stop user order", e);
            msg.setCode(1);
            msg.setResult("退订订单失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 送奶工暂停用户订单
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/pauseorder")
    public @ResponseBody
    BaseMsgBean pauseCustomerOrderByWorker(OrderStateChangeReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 >= req.getUid() || null == req.getOrder_no()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        if (req.getStop_days() <= 0 || req.getStop_days() > 365) {
            msg.setCode(1);
            msg.setResult("停止期限不符合规定");
            return msg;
        }
        try {
            userOrderService.pauseOrder(worker, req.getOrder_no(), req.getStop_days());
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("停止订单失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * 续订订单
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/continueorder")
    public @ResponseBody
    BaseMsgBean continueOrder(OrderStateChangeReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 >= req.getUid() || null == req.getOrder_no()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        UserOrder order = userOrderService.findByOrderNo(req.getOrder_no());
        if (null == order || null == order.getUser()) {
            msg.setCode(1);
            msg.setResult("该用户订单号不存在");
            return msg;
        }
        try {
            userOrderService.continueOrder(order, worker, req.getProduct_id());
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult("续订订单失败!" + e.getMessage());
            return msg;
        }
        return msg;
    }

    /**
     * web端获取订单列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/orderitempage")
    public @ResponseBody
    BaseMsgBean getAllOrderitem(WebOrderitemReq req) {
        BaseMsgBean msg = new BaseMsgBean();

        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Web get all order items,mid({}),token({})", req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getPage() || 0 >= req.getNum()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userOrderService.findWebOrderitem(req, admin);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Web get all order items fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
        }
        return msg;
    }

    /**
     * web端查询订单列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/orderitemquery")
    public @ResponseBody
    BaseMsgBean queryOrderitem(WebQueryOrderitemReq req) {
        BaseMsgBean msg = new BaseMsgBean();

        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Web query order items,mid({}),token({}), key({})", req.getMid(), req.getToken(), req.getQuery());
        if (null == admin || 0 >= req.getMid() || null == req.getQuery()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userOrderService.queryWebOrderitem(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Web query order items fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
        }
        return msg;
    }

    /**
     * web端获取订单详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/orderitemget")
    public @ResponseBody
    BaseMsgBean getOrderitemByOrderNo(WebOrdergetReq req) {
        BaseMsgBean msg = new BaseMsgBean();

        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Web query order items,mid({}),token({}), order_no({})", req.getMid(), req.getToken(), req.getOrder_no());
        if (null == admin || 0 >= req.getMid() || null == req.getOrder_no()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userOrderService.getOrderDetailByOrderno(req.getOrder_no());
            
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Web query order items fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
        }
        return msg;
    }

    /**
     * web确认订单
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/orderitemconfirm")
    public @ResponseBody
    BaseMsgBean doConfirm(WebOrdergetReq req) {
        BaseMsgBean msg = new BaseMsgBean();

        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Order confirm ,mid({}),token({}), order_no({})", req.getMid(), req.getToken(), req.getOrder_no());
        if (null == admin || 0 >= req.getMid() || null == req.getOrder_no()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            userOrderService.confirmOrder(req.getOrder_no(), admin);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Order confirm fail.", e);
            msg.setCode(1);
            msg.setResult("失败:" + e.getMessage());
        }
        return msg;
    }

    /**
     * web订单类别统计
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/ordercounter")
    public @ResponseBody
    BaseMsgBean getOrderCount(AdminReqBase req) {
        BaseMsgBean msg = new BaseMsgBean();

        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Order confirm ,mid({}),token({})", req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userOrderService.findWebOrdercounter(admin);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Order confirm fail.", e);
            msg.setCode(1);
            msg.setResult("失败:" + e.getMessage());
        }
        return msg;
    }

    /**
     * web端获取是否已经支付的订单信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/paypage")
    public @ResponseBody
    BaseMsgBean findPayOrder(PayReq req) {
        BaseMsgBean msg = new BaseMsgBean();

        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Web get all order pay info,mid({}),token({})", req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getPage() || 0 >= req.getNum()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = userOrderService.findOrderPayInfo(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Web get all order pay info fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
        }
        return msg;
    }

    /**
     * web端确认支付成功
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/payconfirm")
    public @ResponseBody
    BaseMsgBean doPayConfirm(PayConfirmReq req) {
        BaseMsgBean msg = new BaseMsgBean();

        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Confirm order pay complete,mid({}),token({}), order id({}), payee({})", req.getMid(), req.getToken(), req.getId(), req.getPayee());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getId() || null == req.getPayee()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg.setResult("成功");
            userOrderService.doOrderPayment(req);
        } catch (Exception e) {
            LOG.warn("Confirm order pay complete, fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
        }
        return msg;
    }

    /**
     * 订单删除
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/delete")
    public @ResponseBody
    BaseMsgBean delete(WebOrdergetReq req) {
        BaseMsgBean msg = new BaseMsgBean();

        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Delete order,mid({}),token({}), order no({})", req.getMid(), req.getToken(), req.getOrder_no());
        if (null == admin || 0 >= req.getMid() || null == req.getOrder_no()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            UserOrder order = userOrderService.findByOrderNo(req.getOrder_no());
            if (null == order || !UserOrderService.ORDER_STATE_STOP.equals(order.getOrderState())) {
                msg.setCode(1);
                msg.setResult("订单不存在或者当前订单的状态不能被删除.");
            } else {
                order.setSignDelete(Boolean.TRUE);
                msg.setResult("成功");
                userOrderService.save(order);
            }
        } catch (Exception e) {
            LOG.warn("Delete order fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
        }
        return msg;
    }

    /**
     * 订单指派送奶工
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/dlvr")
    public @ResponseBody
    BaseMsgBean diliveryToMilkman(OrderDelvReq req) {
        BaseMsgBean msg = new BaseMsgBean();

        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Order diliveryToMilkman, mid({}),token({}), order no({}), milkman id({})", req.getMid(), req.getToken(), req.getOrder_no(), req.getDelivery_uid());
        if (null == admin || 0 >= req.getMid() || null == req.getOrder_no() || null == req.getDelivery_uid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            UserInfo worker = userInfoService.read(req.getDelivery_uid());
            if (null == worker || !UserInfoService.USER_ROLE_MILKMAN.equals(worker.getUserType())) {
                msg.setCode(1);
                msg.setResult("送奶工不存在或者指定送奶工的角色不正确.");
                return msg;
            }
            UserOrder order = userOrderService.findByOrderNo(req.getOrder_no());
            if (null == order || !UserOrderService.ORDER_STATE_CONFIRM.equals(order.getOrderState()) || !UserOrderService.ORDER_DELIVERY_TARGET_STATION.equals(order.getDistributionTarget())) {
                msg.setCode(1);
                msg.setResult("订单不存在或者当前订单的状态不能被指派.");
            } else {
                order.setSignDelete(Boolean.TRUE);
                order.setOrderState(UserOrderService.ORDER_STATE_DISPATCHING);
                order.setMilkman(worker);
                order.setMilkmanname(worker.getRealname());
                userOrderService.save(order);
                msg.setResult("成功");
            }
        } catch (Exception e) {
            LOG.warn("Order diliveryToMilkman fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
        }
        return msg;
    }
}
