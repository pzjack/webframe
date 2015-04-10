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

package com.dinglicom.reportform.service;

import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.reportform.domain.LineResp;
import com.dinglicom.reportform.domain.ReportformReq;
import com.dinglicom.reportform.domain.SalemanResp;
import com.dinglicom.reportform.domain.StationResp;

/**
 *
 * @author panzhen
 */
public interface ReportFormService {

    /**
     * 查询某一天的报量报表
     * 输出中包含线路信息
     * @param req
     * @param admin
     * @return 
     */
    LineResp queryLinelist(ReportformReq req, UserInfo admin);
    
    /**
     * 查询某一天的报量报表
     * 不包含线路数量
     * @param req
     * @param admin
     * @return 
     */
    StationResp queryStationlist(ReportformReq req, UserInfo admin);
    
    /**
     * 查询销售员销售情况
     * @param req
     * @param admin
     * @return 
     */
    SalemanResp querySalemanlist(ReportformReq req, UserInfo admin);
}
