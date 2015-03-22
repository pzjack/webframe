/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.service;

import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.order.domain.WebQueryOrderitemReq;
import com.dinglicom.reportnum.demain.LineDataTmp;
import com.dinglicom.reportnum.demain.LineDistributionQuery;
import com.dinglicom.reportnum.demain.LineResp;
import com.dinglicom.reportnum.demain.WebEverydayorgListResp;
import com.dinglicom.reportnum.demain.WebReportNumberReq;
import com.dinglicom.reportnum.demain.WebReportlistReq;
import com.dinglicom.reportnum.demain.WebReportlistResp;
import com.dinglicom.reportnum.demain.WebReportnumQueryResp;
import com.dinglicom.reportnum.demain.WebReportnumberCountDetail;
import com.dinglicom.reportnum.entity.EveryDayEveryOrgReport;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author panzhen
 */
public interface EveryDayEveryOrgReportService {
    
    final static String REPORT_STATE_UNREPORT = "unreport";//未报量
    final static String REPORT_STATE_REPORTTED = "reported";//已报量
    final static String REPORT_STATE_EXPIRED = "expired";//报量已截止
    final static String REPORT_STATE_SHIPPED = "shipped";//已出货

    EveryDayEveryOrgReport save(EveryDayEveryOrgReport everyDayEveryOrgReport);
    
    Iterable<EveryDayEveryOrgReport> save(List<EveryDayEveryOrgReport> list);
    
    /**
     * 获取报量列表
     * @param req
     * @return 
     */
    WebEverydayorgListResp getReportnumberList(WebReportNumberReq req);
    
    /**
     * 查询每日报量的单位情况
     * @param req
     * @return 
     */
    WebReportnumQueryResp queryEveryDayOrgReport(WebQueryOrderitemReq req);
    
    /**
     * 报量出货
     * @param id
     * @param admin 
     */
    void doShipByid(long id, UserInfo admin);
    
    /**
     * 统计指定日志的报量情况
     * @param c
     * @return 
     */
    WebReportnumberCountDetail countByDate(Calendar c);
    
    /**
     * 查找指定单位某一天的每日报量是否生成
     * @param orgId
     * @param year
     * @param month
     * @param day
     * @return 
     */
    EveryDayEveryOrgReport findByOrgidAndDate(long orgId, int year, int month, int day);
    
    /**
     * 创建指定时间的每日报量信息
     * @param c 
     */
    void createReportnumberByWorkerstation(Calendar c);
    
    /**
     * 创建下一天的报量信息
     */
    void doCreateReportnumberNextday();
    
    /**
     * 创建单位每日报量对象
     * @param nz
     * @param year
     * @param month
     * @param day
     * @return 
     */
    EveryDayEveryOrgReport createEveryReportnumber(SysOranizagion nz, int year, int month, int day);
    
    /**
     * web端查询报量统计列表
     * @param req
     * @return 
     */
    WebReportlistResp queryReportlist(WebReportlistReq req);
    
    /**
     * 查找当前报量的总计量
     * @param id
     * @return 
     */
    Long findReportnumTotalnumById(long id);
    
    /**
     * 查询线下报量报表
     * @param req
     * @return 
     */
    LineResp queryLinelist(LineDistributionQuery req);
}
