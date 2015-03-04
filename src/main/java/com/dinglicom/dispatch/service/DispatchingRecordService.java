/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dispatch.service;

import com.dinglicom.dispatch.domain.DispathingTaskReq;
import com.dinglicom.dispatch.domain.DispathingTaskResp;
import com.dinglicom.dispatch.domain.TaskDetailReq;
import com.dinglicom.dispatch.domain.TaskDetailResp;
import com.dinglicom.dispatch.domain.TaskStatisticsResp;
import com.dinglicom.dispatch.entity.DispatchingRecord;
import com.dinglicom.frame.sys.entity.UserInfo;
import java.io.IOException;

/**
 *
 * @author panzhen
 */
public interface DispatchingRecordService {
    final static String DISPATCHING_NOT_COMPELTE = "left";
    final static String DISPATCHING_COMPELTE = "done";
    DispatchingRecord read(long id);
    DispatchingRecord save(DispatchingRecord dispatchingRecord);
    /**
     * 查询送奶工当日的任务
     * @param req
     * @param user
     * @return 
     */
    DispathingTaskResp findCurDayTask(DispathingTaskReq req, UserInfo user);
    /**
     * 查询订单详情
     * @param req
     * @return 
     */
    TaskDetailResp findByOrderNoDetails(TaskDetailReq req);
    
    /**
     * 统计送奶工任务数据
     * @param user
     * @return 
     */
    TaskStatisticsResp findStatisticsNum(UserInfo user);
    /**
     * 送奶工完成配送
     * @param worker
     * @param orderNo
     * @throws IOException 
     */
    void completeDispachingOrder(UserInfo worker, String orderNo) throws IOException;
    
    /**
     * 获取奶站，并逐个按照奶站创建配送单
     */
    void createDispatchingTaskByWorderOrg();
}
