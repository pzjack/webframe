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
package com.dinglicom.dealerdispathing.web;

import com.dinglicom.dealerdispathing.domain.DealerDispatchResp;
import com.dinglicom.dealerdispathing.domain.DealerDispatchingReq;
import com.dinglicom.dealerdispathing.domain.DealerDispathReq;
import com.dinglicom.dealerdispathing.domain.DealerOnedayTaskReq;
import com.dinglicom.dealerdispathing.domain.DealerOperatorTaskReq;
import com.dinglicom.dealerdispathing.service.DealerDispatchServcie;
import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.web.AppControllerBase;
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
@RequestMapping(value = "/api/v1/dealer")
public class DealerDispathController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(DealerDispathController.class);
    @Resource
    private SysTokenService sysTokenService;
    @Resource
    private DealerDispatchServcie dealerDispatchServcie;

    /**
     * 获取配送列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/page")
    public @ResponseBody
    DealerDispatchResp pageList(DealerDispathReq req) {
        DealerDispatchResp msg = new DealerDispatchResp();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getPage() || 0 >= req.getNum() || null == req.getStatus()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = dealerDispatchServcie.queryReportlist(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Dealer query dispatching list page fail:", e);
            msg.setCode(1);
            msg.setResult("查询失败");
            return msg;
        }
        return msg;
    }

    @RequestMapping(value = "/ship")
    public @ResponseBody
    BaseMsgBean doship(DealerDispatchingReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || null == req.getId()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            dealerDispatchServcie.doShip(req.getId(), admin);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Dealer do ship fail:", e);
            msg.setCode(1);
            msg.setResult("出货失败");
            return msg;
        }
        return msg;
    }

    @RequestMapping(value = "/dispathing")
    public @ResponseBody
    BaseMsgBean doDispathing(DealerOperatorTaskReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo dealer = validateToken(sysTokenService, msg, req.getUid(), req.getToken());
        if (null == dealer || 0 >= req.getUid() || null == req.getId()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            dealerDispatchServcie.doDispatching(req.getId(), dealer);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Dealer do complement fail:", e);
            msg.setCode(1);
            msg.setResult("完成配送失败");
            return msg;
        }
        return msg;
    }

    @RequestMapping(value = "/tasks")
    public @ResponseBody
    BaseMsgBean getTasks(DealerOnedayTaskReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo dealer = validateToken(sysTokenService, msg, req.getUid(), req.getToken());
        if (null == dealer || 0 >= req.getUid()  || 0 >= req.getPage() || 0 >= req.getNum() || null == req.getStatus()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = dealerDispatchServcie.getCurDayTasks(req, dealer);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Dealer get current day task fail:", e);
            msg.setCode(1);
            msg.setResult("获取当天任务失败");
            return msg;
        }
        return msg;
    }
}
