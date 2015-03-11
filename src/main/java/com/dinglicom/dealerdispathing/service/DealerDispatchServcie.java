/*
 * Copyright 2015 Jack.Alexander
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dinglicom.dealerdispathing.service;

import com.dinglicom.dealerdispathing.domain.DealerDispatchOnedayResp;
import com.dinglicom.dealerdispathing.domain.DealerDispatchResp;
import com.dinglicom.dealerdispathing.domain.DealerDispathReq;
import com.dinglicom.dealerdispathing.domain.DealerOnedayTaskReq;
import com.dinglicom.frame.sys.entity.UserInfo;

/**
 *
 * @author panzhen
 */
public interface DealerDispatchServcie {
    final static String DISPATH_STATE_NONE = "none";//未出货
    final static String DISPATH_STATE_INT = "ing";//未配送
    final static String DISPATH_STATE_DONE = "done";//已配送
    /**
     * 管理端获取经销商配送列表
     * @param req
     * @return 
     */
    DealerDispatchResp queryReportlist(DealerDispathReq req);
    
    /**
     * 出货接口
     * @param id 
     * @param darler 
     */
    void doShip(String id, UserInfo darler);
    
    /**
     * 经销商今日任务接口
     * @param req
     * @param darler
     * @return 
     */
    DealerDispatchOnedayResp getCurDayTasks(DealerOnedayTaskReq req, UserInfo darler);
    
    /**
     * 完成配送
     * @param id
     * @param darler 
     */
    void doDispatching(String id, UserInfo darler);
}
