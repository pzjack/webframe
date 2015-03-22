/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.service;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.reportnum.demain.LineDataTmp;
import com.dinglicom.reportnum.demain.ReportNumberPostReq;
import com.dinglicom.reportnum.demain.WebReportnumberDetailResp;
import com.dinglicom.reportnum.entity.ReportSubscribeNumber;
import java.util.Calendar;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface ReportSubscribeNumberService {
//    final static String REPORT_STATE_UNREPORT = "NORMAL";//未报量
//    final static String REPORT_STATE_REPORTTED = "DONE";//已报量
//    final static String REPORT_STATE_SHIPPED = "SHIPPED";//已出货
    
    ReportSubscribeNumber save(ReportSubscribeNumber reportSubscribeNumber);
    
    Iterable<ReportSubscribeNumber> save(List<ReportSubscribeNumber> reportSubscribeNumbers);
    
    List<ReportSubscribeNumber> findByEveryid(long everyId);
    
    /**
     * 计算当日报量
     * @param nzmanager
     * @param c
     * @return 
     */
    BaseMsgBean caculateOnedayReportNumber(UserInfo nzmanager, Calendar c);
    
    /**
     * 提交报量
     * @param req
     * @param nzmanager
     * @param c 
     */
    void saveOnedayReportNumber(ReportNumberPostReq req, UserInfo nzmanager, Calendar c);
    
    /**
     * 查找每日报量详情
     * @param everyId
     * @return 
     */
    WebReportnumberDetailResp getEveryDetailById(long everyId);
    
    /**
     * 奶站年的线路送货查询
     * @param page
     * @param year
     * @param orgname
     * @return 
     */
    Page<LineDataTmp> queryLinebyYear(Pageable page, Integer year, String orgname);
    
    /**
     * 年的线路送货查询
     * @param page
     * @param year
     * @return 
     */
    Page<LineDataTmp> queryLinebyYear(Pageable page, Integer year);
    
    /**
     * 任意一天奶站的线路送货明细
     * @param page
     * @param year
     * @param month
     * @param day
     * @param orgname
     * @return 
     */
    Page<LineDataTmp> queryLinebyYearmonthday(Pageable page, Integer year, Integer month, Integer day, String orgname);
    
    /**
     * 任意一天线路送货明细
     * @param page
     * @param year
     * @param month
     * @param day
     * @return 
     */
    Page<LineDataTmp> queryLinebyYearmonthday(Pageable page, Integer year,Integer month, Integer day);
    
    /**
     * 查询特定经销商某一天的报量明细
     * @param dealerId
     * @param year
     * @param month
     * @param day
     * @return 
     */
    List<ReportSubscribeNumber> findByDealerAndDate(Long dealerId, Integer year,Integer month, Integer day);
    
    
    
    /**
     * 线下报量报表
     * @param year
     * @param month
     * @param day
     * @return 
     */
    List<LineDataTmp> queryLinebyday(Integer year, Integer month, Integer day);
}
