/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.service;

import com.dinglicom.order.entity.OrderChangeRecord;
import java.util.Date;
import java.util.List;

/**
 *
 * @author panzhen
 */
public interface OrderChangeRecordService {
    final static int ORDER_STOP = 1;
    final static int ORDER_PAUSE = 0;
    final static int PAUSE_AMONT = 0;
    final static int PAUSE_END = 1;
    OrderChangeRecord save(OrderChangeRecord orderChangeRecord);
    /**
     * 查找所有已经到期的暂停的订单
     * @param enddate
     * @return 
     */
    List<OrderChangeRecord> findAllPauseOrder(Date enddate);
    
    /**
     * 将到期的暂停的订单恢复成配送中状态，同时重新计算配送结束日期
     * @param enddate 
     */
    void doEndPauseOrder(Date enddate);
}
