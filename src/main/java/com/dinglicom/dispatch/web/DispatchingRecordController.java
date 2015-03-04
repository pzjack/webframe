/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.dispatch.web;

import com.dinglicom.dispatch.domain.DispathingTaskReq;
import com.dinglicom.dispatch.domain.DispathingTaskResp;
import com.dinglicom.dispatch.domain.TaskDetailReq;
import com.dinglicom.dispatch.domain.TaskDetailResp;
import com.dinglicom.dispatch.domain.TaskStatisticsResp;
import com.dinglicom.dispatch.service.DispatchingRecordService;
import com.dinglicom.frame.sys.domain.AppRequestBase;
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
@RequestMapping(value = "/api/v1/dispatch")
public class DispatchingRecordController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(DispatchingRecordController.class);
    @Resource
    protected SysTokenService sysTokenService;
    @Resource
    private DispatchingRecordService dispatchingRecordService;

    /**
     * 送奶工当天任务查询
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/page")
    public @ResponseBody
    DispathingTaskResp findByPage(DispathingTaskReq req) {
        DispathingTaskResp msg = new DispathingTaskResp();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 == req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = dispatchingRecordService.findCurDayTask(req, worker);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("worker current day task page", e);
            msg.setCode(1);
            msg.setResult("查询失败");
            return msg;
        }
        return msg;
    }

    /**
     * 送奶工当天任务查询
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/detail")
    public @ResponseBody
    TaskDetailResp findByDispatchDetail(TaskDetailReq req) {
        TaskDetailResp msg = new TaskDetailResp();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 == req.getUid() || null == req.getOrder_no()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = dispatchingRecordService.findByOrderNoDetails(req);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("worker current day task detail", e);
            msg.setCode(1);
            msg.setResult("查询失败");
            return msg;
        }
        return msg;
    }

    /**
     * 送奶工当天任务统计
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/statistics")
    public @ResponseBody
    TaskStatisticsResp findCurrentDayStatistics(AppRequestBase req) {
        TaskStatisticsResp msg = new TaskStatisticsResp();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 == req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = dispatchingRecordService.findStatisticsNum(worker);
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("worker current day task statistics", e);
            msg.setCode(1);
            msg.setResult("查询失败");
            return msg;
        }
        return msg;
    }

    /**
     * 完成订单配送任务
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/complete")
    public @ResponseBody
    BaseMsgBean completeOrderTask(TaskDetailReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo worker = validateToken(sysTokenService, req, msg);
        if (null == worker || 0 == req.getUid() || null == req.getOrder_no()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            dispatchingRecordService.completeDispachingOrder(worker, req.getOrder_no());
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("worker order no[" + req.getOrder_no() + "]complete:", e);
            msg.setCode(1);
            msg.setResult("完成失败");
            return msg;
        }
        return msg;
    }
    /**
     * 手动触发创建配送任务
     * @return 
     */
    @RequestMapping(value = "/createdispatching")
    public @ResponseBody
    BaseMsgBean createWorkstationDispatchingTask() {
        BaseMsgBean msg = new BaseMsgBean();
        try {
            dispatchingRecordService.createDispatchingTaskByWorderOrg();
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("create diapatching task fail by naizhai:", e);
            msg.setCode(1);
            msg.setResult("完成失败");
        }
        return msg;
    }
}
