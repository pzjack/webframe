/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.reportnum.web;

import com.dinglicom.export.view.ExcelReportDetails;
import com.dinglicom.export.view.ExcelView;
import com.dinglicom.export.view.LineExcelView;
import com.dinglicom.frame.sys.domain.AdminReqBase;
import com.dinglicom.frame.sys.domain.AppRequestBase;
import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.sys.util.DateUtil;
import com.dinglicom.frame.web.AppControllerBase;
import com.dinglicom.order.domain.WebQueryOrderitemReq;
import com.dinglicom.reportnum.demain.LineDistributionQuery;
import com.dinglicom.reportnum.demain.LineResp;
import com.dinglicom.reportnum.demain.ReportDetailReq;
import com.dinglicom.reportnum.demain.ReportDetailRespItem;
import com.dinglicom.reportnum.demain.ReportNumberPostReq;
import com.dinglicom.reportnum.demain.ReportnumberTimeReq;
import com.dinglicom.reportnum.demain.WebEverydayorgListResp;
import com.dinglicom.reportnum.demain.WebReportNumberReq;
import com.dinglicom.reportnum.demain.WebReportlistReq;
import com.dinglicom.reportnum.demain.WebReportlistResp;
import com.dinglicom.reportnum.demain.WebReportnumberCountDetail;
import com.dinglicom.reportnum.demain.WebReportnumberCountResp;
import com.dinglicom.reportnum.demain.WebReportnumberDetailReq;
import com.dinglicom.reportnum.entity.ReportnumberTime;
import com.dinglicom.reportnum.service.EveryDayEveryOrgReportService;
import com.dinglicom.reportnum.service.ReportSubscribeNumberService;
import com.dinglicom.reportnum.service.ReportnumberTimeService;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author panzhen
 */
@Controller
@RequestMapping(value = "/api/v1/report")
public class ReportSubscribeNumberController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(ReportSubscribeNumberController.class);
    @Resource
    private SysTokenService sysTokenService;
    @Resource
    private ReportSubscribeNumberService reportSubscribeNumberService;
    @Resource
    private ReportnumberTimeService reportnumberTimeService;
    @Resource
    private EveryDayEveryOrgReportService everyDayEveryOrgReportService;

    /**
     * 根据订单条目信息计算报量
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/onedayreport")
    public @ResponseBody
    BaseMsgBean doOnedayReport(AppRequestBase req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo nzmanager = validateToken(sysTokenService, req, msg);
        if (null == nzmanager || 0 == req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = reportSubscribeNumberService.caculateOnedayReportNumber(nzmanager, Calendar.getInstance());
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("caculate one day report number:", e);
            msg.setCode(1);
            msg.setResult("失败");
            return msg;
        }
        return msg;
    }

    /**
     * 保存报量
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/savereport")
    public @ResponseBody
    BaseMsgBean saveOnedayReport(@RequestBody ReportNumberPostReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo nzmanager = validateToken(sysTokenService, req, msg);
        if (null == nzmanager || 0 == req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            reportSubscribeNumberService.saveOnedayReportNumber(req, nzmanager, Calendar.getInstance());
            msg.setCode(0);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("caculate one day report number:", e);
            msg.setCode(1);
            msg.setResult("失败");
            return msg;
        }
        return msg;
    }

    /**
     * 报量时间设置
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/settime")
    public @ResponseBody
    BaseMsgBean setReportDeadTime(ReportnumberTimeReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Set report time,mid({}),token({}),report number dead time({})", req.getMid(), req.getToken(), req.getDead_time());
        if (null == admin || 0 == req.getMid() || null == req.getDead_time()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            ReportnumberTime rt = reportnumberTimeService.get();
            if (null == rt) {
                rt = new ReportnumberTime();
            }
            rt.setTime(req.getDead_time());
            reportnumberTimeService.save(rt);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Get ower feed record fail.", e);
            msg.setCode(1);
            msg.setResult("保存失败");
            return msg;
        }
        return msg;
    }

    /**
     * 报量列表获取
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/reportlist")
    public @ResponseBody
    BaseMsgBean getReportnumbList(WebReportNumberReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getPage() || 0 >= req.getNum()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            WebEverydayorgListResp resp = everyDayEveryOrgReportService.getReportnumberList(req);
            ReportnumberTime rt = reportnumberTimeService.get();
            if (null != rt) {
                resp.setDead_time(rt.getTime());
            }
            msg = resp;
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Get report number list fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
            return msg;
        }
        return msg;
    }

    /**
     * 报量列表查询
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/reportquery")
    public @ResponseBody
    BaseMsgBean queryReportnumb(WebQueryOrderitemReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = everyDayEveryOrgReportService.queryEveryDayOrgReport(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Query report number fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
            return msg;
        }
        return msg;
    }

    /**
     * 报量详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/reportdetail")
    public @ResponseBody
    BaseMsgBean queryReportnumb(WebReportnumberDetailReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getReport_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = reportSubscribeNumberService.getEveryDetailById(req.getReport_id());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Query report number fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
            return msg;
        }
        return msg;
    }

    /**
     * 报量出货
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/ship")
    public @ResponseBody
    BaseMsgBean doShip(WebReportnumberDetailReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getReport_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            everyDayEveryOrgReportService.doShipByid(req.getReport_id(), admin);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Query report number fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
            return msg;
        }
        return msg;
    }

    /**
     * 报量每日统计
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/reportcount")
    public @ResponseBody
    BaseMsgBean getReportCount(AdminReqBase req) {
        WebReportnumberCountResp msg = new WebReportnumberCountResp();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            WebReportnumberCountDetail d = everyDayEveryOrgReportService.countByDate(Calendar.getInstance());
            msg.setData(d);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Query report number fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
            return msg;
        }
        return msg;
    }

    @RequestMapping(value = "/createorgreport")
    public @ResponseBody
    BaseMsgBean createEveryreportnumber(AdminReqBase req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }

        try {
            everyDayEveryOrgReportService.doCreateReportnumberNextday();
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Create every report number fail.", e);
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
    @RequestMapping(value = "/reportsmplist")
    public @ResponseBody
    BaseMsgBean queryReportSamplelist(WebReportlistReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Query report list.", req.getDate());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getPage() || 0 >= req.getNum() || null == req.getDate()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }

        try {
            msg = everyDayEveryOrgReportService.queryReportlist(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Query report list fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
            return msg;
        }
        return msg;
    }

    /**
     * 报量列表获取
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/exportreportlist")
    public ModelAndView exportReportSamplelist(WebReportlistReq req) {
        WebReportlistResp msg = null;
        if (req.getPage() <= 0) {
            req.setPage(1);
        }
        if (req.getNum() <= 0) {
            req.setNum(1000);
        }
        Calendar c = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        if (req.getYear() <= 0) {
            req.setYear(DateUtil.getYear(c));
        }
        sb.append(req.getYear()).append("-");
        if (req.getMonth() <= 0) {
            req.setMonth(DateUtil.getMonth(c));
        }
        sb.append(req.getMonth()).append("-");
        if (req.getDay() <= 0) {
            req.setDay(DateUtil.getDay(c));
        }
        sb.append(req.getDay());
        try {
            msg = everyDayEveryOrgReportService.queryReportlist(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Query report list fail.", e);
        }

        Map model = new HashMap();
        model.put("reportlist", msg);
        model.put("reportdate", sb.toString());
        return new ModelAndView(new ExcelView(), model);
    }

    /**
     * 线路报表excel导出
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/exportlinelist")
    public ModelAndView exportLinelist(LineDistributionQuery req) {
        LineResp msg = null;
        if (req.getPage() <= 0) {
            req.setPage(1);
        }
        if (req.getNum() <= 0) {
            req.setNum(1000);
        }
        if (null == req.getTimetype()) {
            req.setTimetype("day");
        }
        if (null == req.getDate()) {
            req.setDate(DateUtil.formatToDay(new Date()));
        }

        try {
            msg = everyDayEveryOrgReportService.queryLinelist(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Query Line list fail.", e);
        }

        Map model = new HashMap();
        model.put("reportdate", req.getDate());
        model.put("linereportlist", msg);
        return new ModelAndView(new LineExcelView(), model);
    }

    /**
     * 查询报量明细
     * @param req
     * @return
     */
    @RequestMapping(value = "/reportdetails")
    public @ResponseBody
    BaseMsgBean queryReportDetails(ReportDetailReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Query report details.", req.getDate());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getPage() || 0 >= req.getNum() || null == req.getDate()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }

        try {
            msg = reportSubscribeNumberService.findReportDetails(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Query report details fail.", e);
            msg.setCode(1);
            msg.setResult("失败");
            return msg;
        }
        return msg;
    }
    
    
    /**
     * 查询报量明细excel导出
     * @param req
     * @return
     */
    @RequestMapping(value = "/exportreportdetails")
    public ModelAndView exportReportDetails(ReportDetailReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        LOG.info("Export query report details.", req.getDate());
        if (null == admin || 0 >= req.getMid() || null == req.getDate()) {
            return null;
        }
        List<ReportDetailRespItem> data = null;
        try {
            data = reportSubscribeNumberService.findReportDetailAll(req);
        } catch (Exception e) {
            LOG.warn("Export query report list details.", e);
        }
        if(null == data) {
            return null;
        }
        
        Map model = new HashMap();
        model.put("reportdate", req.getDate());
        model.put("reportnumberdetails", data);
        return new ModelAndView(new ExcelReportDetails(), model);
    }
}
