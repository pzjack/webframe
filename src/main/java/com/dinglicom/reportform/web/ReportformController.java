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

package com.dinglicom.reportform.web;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.web.AppControllerBase;
import com.dinglicom.reportform.domain.ReportformReq;
import com.dinglicom.reportform.service.ReportFormService;
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
@RequestMapping(value = "/api/v1/reportform")
public class ReportformController extends AppControllerBase {
    private final static Logger LOG = LoggerFactory.getLogger(ReportformController.class);
    @Resource
    private SysTokenService sysTokenService;
    @Resource
    private ReportFormService reportFormService;

    
    /**
     * 查询报量统计列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/line")
    public @ResponseBody
    BaseMsgBean queryLineReportform(ReportformReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Query Line report form.parma:date={}", req.getDate());
        if (null == admin || 0 >= req.getMid() || null == req.getDate()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }

        try {
            msg = reportFormService.queryLinelist(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Query Line report form fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
            return msg;
        }
        return msg;
    }
    
    
    /**
     * 查询报量统计列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/station")
    public @ResponseBody
    BaseMsgBean queryStationReportform(ReportformReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Query Staion report form.parma:date={}", req.getDate());
        if (null == admin || 0 >= req.getMid() || null == req.getDate()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }

        try {
            msg = reportFormService.queryStationlist(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Query Staion report form fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
            return msg;
        }
        return msg;
    }
}
