/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.order.service.impl;

import com.dinglicom.frame.sys.util.DateUtil;
import com.dinglicom.order.entity.OrderChangeRecord;
import com.dinglicom.order.entity.UserOrder;
import com.dinglicom.order.repository.OrderChangeRecordDao;
import com.dinglicom.order.service.OrderChangeRecordService;
import com.dinglicom.order.service.UserOrderService;
import static com.dinglicom.order.service.UserOrderService.DISTRIBUTION_TYPE_END;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
public class OrderChangeRecordServiceImpl implements OrderChangeRecordService {

    @Resource
    private OrderChangeRecordDao orderChangeRecordDao;
    @Resource
    private UserOrderService userOrderService;

    @Override
    public OrderChangeRecord save(OrderChangeRecord orderChangeRecord) {
        return orderChangeRecordDao.save(orderChangeRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderChangeRecord> findAllPauseOrder(Date enddate) {
        return orderChangeRecordDao.findByPauseamont(enddate, PAUSE_AMONT, Boolean.FALSE);
    }

    @Override
    public void doEndPauseOrder(Date enddate) {
        List<OrderChangeRecord> pauselist = findAllPauseOrder(enddate);
        List<UserOrder> orders = new ArrayList<UserOrder>();
        for (OrderChangeRecord c : pauselist) {
            c.setPausestate(PAUSE_END);
            UserOrder order = c.getOrder();
            orders.add(order);
            if (null != order) {
                if (order.getDistributionNum() >= order.getProductnum()) {
                    order.setOrderState(UserOrderService.ORDER_STATE_COMPLETE);
                } else {
                    order.setOrderState(UserOrderService.ORDER_STATE_DISPATCHING);
                }
                switch (order.getDistributionType()) {
                    case UserOrderService.DISTRIBUTION_TYPE_EVERY:
                        order.setEndDistributionDate(DateUtil.getAfterDay(enddate, (order.getProductnum() - order.getTotaldistributionnum()) / order.getDistributionNum()));
                        break;
                    case DISTRIBUTION_TYPE_END://周末
                        order.setEndDistributionDate(DateUtil.getAfterWorkEndDay(enddate, (order.getProductnum() - order.getTotaldistributionnum()) / order.getDistributionNum()));
                        break;
                    case UserOrderService.DISTRIBUTION_TYPE_WORK:
                        Date firstworkday = DateUtil.getLastWorkday(enddate);
                        order.setEndDistributionDate(DateUtil.getAfterWorkDay(firstworkday, (order.getProductnum() - order.getTotaldistributionnum()) / order.getDistributionNum()));
                        break;
                }
            }
        }
        if (pauselist.size() > 0) {
            orderChangeRecordDao.save(pauselist);
            if (orders.size() > 0) {
                userOrderService.save(orders);
            }
        }
    }
}
