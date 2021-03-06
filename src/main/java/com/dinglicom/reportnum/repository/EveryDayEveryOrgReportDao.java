/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.reportnum.repository;

import com.dinglicom.reportnum.demain.WebEverydayorgItemResp;
import com.dinglicom.reportnum.demain.WebReportNumberQueryItemResp;
import com.dinglicom.reportnum.demain.WebReportlistResult;
import com.dinglicom.reportnum.entity.EveryDayEveryOrgReport;
import com.dinglicom.salesample.domain.WebSaleSampleItem;
import com.dinglicom.salesman.domain.DepsaleRespItem;
import com.dinglicom.salesman.domain.OrgSaleSampleRespItem;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface EveryDayEveryOrgReportDao extends PagingAndSortingRepository<EveryDayEveryOrgReport, Long>, JpaSpecificationExecutor<EveryDayEveryOrgReport> {

//    @Query("select new com.dinglicom.reportnum.demain.WebEverydayorgItemResp(id, reporttime, shiptime, orgname, responsibleman, responsiblephone) from EveryDayEveryOrgReport a where a.year=:year and a.month=:month and a.day=:day and a.signDelete=:signDelete")
//    Page<WebEverydayorgItemResp> findByDate(Pageable page, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "day") int day, @Param(value = "signDelete") Boolean signDelete);
    @Query("select new com.dinglicom.reportnum.demain.WebEverydayorgItemResp(id, reporttime, shiptime, orgname, responsibleman, responsiblephone) from EveryDayEveryOrgReport a where a.year = :year and a.month = :month and a.day = :day and a.signDelete=:signDelete")
    Page<WebEverydayorgItemResp> findList(Pageable page, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "day") int day, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.reportnum.demain.WebEverydayorgItemResp(id, reporttime, shiptime, orgname, responsibleman, responsiblephone) from EveryDayEveryOrgReport a where a.orgtype =:orgtype and a.signDelete=:signDelete")
    Page<WebEverydayorgItemResp> findByOrgtype(Pageable page, @Param(value = "orgtype") String orgtype, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.reportnum.demain.WebEverydayorgItemResp(id, reporttime, shiptime, orgname, responsibleman, responsiblephone) from EveryDayEveryOrgReport a where a.year = :year and a.month = :month and a.day = :day and a.reportstate =:reportstate and a.signDelete=:signDelete")
    Page<WebEverydayorgItemResp> findByReportstate(Pageable page, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "day") int day, @Param(value = "reportstate") String reportstate, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.reportnum.demain.WebEverydayorgItemResp(id, reporttime, shiptime, orgname, responsibleman, responsiblephone) from EveryDayEveryOrgReport a where a.orgtype =:orgtype and a.reportstate =:reportstate and a.signDelete=:signDelete")
    Page<WebEverydayorgItemResp> findByOrgtypeAndReportstate(Pageable page, @Param(value = "orgtype") String orgtype, @Param(value = "reportstate") String reportstate, @Param(value = "signDelete") Boolean signDelete);

    @Query("select sum(a.totalnum) from EveryDayEveryOrgReport a where a.year = :year and a.month = :month and a.day = :day and a.signDelete=:signDelete")
    Long findAllReportnum(@Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "day") int day, @Param(value = "signDelete") Boolean signDelete);

    @Query("select sum(a.totalnum) from EveryDayEveryOrgReport a where a.orgtype =:orgtype and a.signDelete=:signDelete")
    Long findAllReportnumOrgType(@Param(value = "orgtype") String orgtype, @Param(value = "signDelete") Boolean signDelete);

    @Query("select sum(a.totalnum) from EveryDayEveryOrgReport a where a.year = :year and a.month = :month and a.day = :day and a.reportstate =:reportstate and a.signDelete=:signDelete")
    Long findAllReportnumState(@Param(value = "reportstate") String reportstate, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "day") int day, @Param(value = "signDelete") Boolean signDelete);

    @Query("select sum(a.totalnum) from EveryDayEveryOrgReport a where a.orgtype =:orgtype and a.reportstate =:reportstate and a.signDelete=:signDelete")
    Long findAllReportnum(@Param(value = "orgtype") String orgtype, @Param(value = "reportstate") String reportstate, @Param(value = "signDelete") Boolean signDelete);

    @Query("select totalnum from EveryDayEveryOrgReport a where a.id=:id")
    Long findReportnumtotalnumById(@Param(value = "id") long id);

    @Query("select new com.dinglicom.reportnum.demain.WebReportNumberQueryItemResp(id, reportstate, reporttime, shiptime, orgname, responsibleman, responsiblephone) from EveryDayEveryOrgReport a where a.signDelete=:signDelete")
    Page<WebReportNumberQueryItemResp> query(Pageable page, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.reportnum.demain.WebReportNumberQueryItemResp(id, reportstate, reporttime, shiptime, orgname, responsibleman, responsiblephone) from EveryDayEveryOrgReport a where a.orgname = :orgname and a.signDelete=:signDelete")
    Page<WebReportNumberQueryItemResp> queryByOrgname(Pageable page, @Param(value = "orgname") String orgname, @Param(value = "signDelete") Boolean signDelete);

    @Query("select count(*) from EveryDayEveryOrgReport a where a.year=:year and a.month=:month and a.day=:day and a.reportstate =:reportstate and a.signDelete=:signDelete")
    long countByReportstate(@Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "day") int day, @Param(value = "reportstate") String reportstate, @Param(value = "signDelete") Boolean signDelete);

    @Query("from EveryDayEveryOrgReport a where a.org.id=:orgid and a.year=:year and a.month=:month and a.day=:day")
    List<EveryDayEveryOrgReport> findByDateAndOrgid(@Param(value = "orgid") long orgid, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "day") int day);

    //@Query("select new com.dinglicom.reportnum.demain.WebReportlistResult(a.org.id, a.orgname, a.totalnum, b.product.id, b.productname, b.reportnum) from ReportSubscribeNumber b  right join b.everyDayEveryOrgReport a  where a.year = :year and a.month = :month and a.day = :day and  a.signDelete=:signDelete")
    @Query("select new com.dinglicom.reportnum.demain.WebReportlistResult(b.org.id, b.orgname, b.everyDayEveryOrgReport.totalnum, b.product.id, b.productname, b.reportnum) from ReportSubscribeNumber b  where b.year = :year and b.month = :month and b.day = :day and b.org.id in (:orgIds) and  b.signDelete=:signDelete")
    List<WebReportlistResult> queryReportlist(@Param(value = "year") Integer year, @Param(value = "month") Integer month, @Param(value = "day") Integer day, @Param(value = "orgIds") List<Long> orgIds, @Param(value = "signDelete") Boolean signDelete);
    
    @Query("select distinct b.org.id from ReportSubscribeNumber b  where b.year = :year and b.month = :month and b.day = :day and  b.signDelete=:signDelete")
    Page<Long> queryReportlistOrgPage(Pageable page, @Param(value = "year") Integer year, @Param(value = "month") Integer month, @Param(value = "day") Integer day, @Param(value = "signDelete") Boolean signDelete);

//    @Query("select new com.dinglicom.reportnum.demain.WebReportlistResult(a.org.id, a.orgname, a.totalnum, b.productname, b.reportnum) from ReportSubscribeNumber b right join b.everyDayEveryOrgReport a  where a.orgtype=:orgtype and a.year = :year and a.month = :month and a.day = :day and  a.signDelete=:signDelete")
//    Page<WebReportlistResult> queryReportlist(Pageable page, @Param(value = "orgtype") String orgtype, @Param(value = "year") Integer year, @Param(value = "month") Integer month, @Param(value = "day") Integer day, @Param(value = "signDelete") Boolean signDelete);

    //以下部分，业务员按照单位统计销量情况
    @Query("select new com.dinglicom.salesman.domain.OrgSaleSampleRespItem(a.org.id, a.orgname, a.responsibleman, sum(totalnum), a.orgtype) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesid and a.year=:year and a.month=:month and a.day=:day group by a.org,a.orgname,a.responsibleman,a.orgtype order by sum(totalnum) desc")
    Page<OrgSaleSampleRespItem> queryOrgSampleByDay(Pageable page, @Param(value = "salesid") long salesid, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "day") int day);

//    @Query("select new com.dinglicom.salesman.domain.OrgSaleSampleRespItem(a.org.id, a.orgname, a.responsibleman, sum(totalnum), a.orgtype) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesid and a.org.signDelete=:signDelete and a.orgtype=:type and a.year=:year and a.month=:month group by a.org,a.orgname,a.responsibleman,a.orgtype")
//    Page<OrgSaleSampleRespItem> queryOrgSampleByMonth(Pageable page, @Param(value = "salesid") long salesid, @Param(value = "type") String type, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.salesman.domain.OrgSaleSampleRespItem(a.org.id, a.orgname, a.responsibleman, sum(totalnum), a.orgtype) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesid and a.year=:year and a.month=:month group by a.org,a.orgname,a.responsibleman,a.orgtype order by sum(totalnum) desc")
    Page<OrgSaleSampleRespItem> queryOrgSampleByMonth(Pageable page, @Param(value = "salesid") long salesid, @Param(value = "year") int year, @Param(value = "month") int month);

//    @Query("select new com.dinglicom.salesman.domain.OrgSaleSampleRespItem(a.org.id, a.orgname, a.responsibleman, sum(totalnum), a.orgtype) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesid and a.org.signDelete=:signDelete and a.orgtype=:type and a.year=:year and a.quarter=:quarter group by a.org,a.orgname,a.responsibleman,a.orgtype")
//    Page<OrgSaleSampleRespItem> queryOrgSampleByQuarter(Pageable page, @Param(value = "salesid") long salesid, @Param(value = "type") String type, @Param(value = "year") int year, @Param(value = "quarter") int quarter, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.salesman.domain.OrgSaleSampleRespItem(a.org.id, a.orgname, a.responsibleman, sum(totalnum), a.orgtype) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesid and a.year=:year and a.quarter=:quarter group by a.org,a.orgname,a.responsibleman,a.orgtype order by sum(totalnum) desc")
    Page<OrgSaleSampleRespItem> queryOrgSampleByQuarter(Pageable page, @Param(value = "salesid") long salesid, @Param(value = "year") int year, @Param(value = "quarter") int quarter);

//    @Query("select new com.dinglicom.salesman.domain.OrgSaleSampleRespItem(a.org.id, a.orgname, a.responsibleman, sum(totalnum), a.orgtype) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesid and a.org.signDelete=:signDelete and a.orgtype=:type and a.year=:year group by a.org,a.orgname,a.responsibleman,a.orgtype")
//    Page<OrgSaleSampleRespItem> queryOrgSampleByYear(Pageable page, @Param(value = "salesid") long salesid, @Param(value = "type") String type, @Param(value = "year") int year, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.salesman.domain.OrgSaleSampleRespItem(a.org.id, a.orgname, a.responsibleman, sum(totalnum), a.orgtype) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesid and a.year=:year group by a.org,a.orgname,a.responsibleman,a.orgtype order by sum(totalnum) desc")
    Page<OrgSaleSampleRespItem> queryOrgSampleByYear(Pageable page, @Param(value = "salesid") long salesid, @Param(value = "year") int year);

    //以下部分统计销售部门各销售员销售业绩
    @Query("select new com.dinglicom.salesman.domain.DepsaleRespItem(a.org.userinfo.id, a.org.userinfo.realname, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo.org.id=:depid and a.org.userinfo.signDelete=:signDelete and a.year=:year and a.month=:month group by a.org.userinfo")
    List<DepsaleRespItem> queryDepOrgSampleByMonth(@Param(value = "depid") long depid, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.salesman.domain.DepsaleRespItem(a.org.userinfo.id, a.org.userinfo.realname, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo.org.id=:depid and a.org.userinfo.signDelete=:signDelete and a.year=:year and a.quarter=:quarter group by a.org.userinfo")
    List<DepsaleRespItem> queryDepOrgSampleByQuater(@Param(value = "depid") long depid, @Param(value = "year") int year, @Param(value = "quarter") int quarter, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.salesman.domain.DepsaleRespItem(a.org.userinfo.id, a.org.userinfo.realname, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo.org.id=:depid and a.org.userinfo.signDelete=:signDelete and a.year=:year group by a.org.userinfo")
    List<DepsaleRespItem> queryDepOrgSampleByYear(@Param(value = "depid") long depid, @Param(value = "year") int year, @Param(value = "signDelete") Boolean signDelete);

    //以下部分统计各个销售部门销售业绩
    @Query("select new com.dinglicom.salesman.domain.DepsaleRespItem(a.org.userinfo.org.id, a.org.userinfo.org.responsible_man, sum(totalnum)) from EveryDayEveryOrgReport a where  a.year=:year and a.month=:month group by a.org.userinfo.org")
    List<DepsaleRespItem> querySaleManagerSampleByMonth(@Param(value = "year") int year, @Param(value = "month") int month);

    @Query("select new com.dinglicom.salesman.domain.DepsaleRespItem(a.org.userinfo.org.id, a.org.userinfo.org.responsible_man, sum(totalnum)) from EveryDayEveryOrgReport a where  a.year=:year and a.month=:quarter group by a.org.userinfo.org")
    List<DepsaleRespItem> querySaleManagerSampleByQuater(@Param(value = "year") int year, @Param(value = "quarter") int quarter);

    @Query("select new com.dinglicom.salesman.domain.DepsaleRespItem(a.org.userinfo.org.id, a.org.userinfo.org.responsible_man, sum(totalnum)) from EveryDayEveryOrgReport a where  a.year=:year group by a.org.userinfo.org")
    List<DepsaleRespItem> querySaleManagerSampleByYear(@Param(value = "year") int year);

    //跨部门统计所有销售人员销售情况
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.year=:year and a.month=:month group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllSalemanByAdminMonth(Pageable page, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.year=:year and a.quarter=:quarter group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllSalemanByAdminQuater(Pageable page, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.year=:year group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllSalemanByAdminYear(Pageable page, @Param(value = "year") int year);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.parent.id=:depId and a.year=:year and a.month=:month group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllSalemanByDepMonth(Pageable page, @Param(value = "depId")Long depId, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.parent.id=:depId and a.year=:year and a.quarter=:quarter group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllSalemanByDepQuater(Pageable page, @Param(value = "depId")Long depId, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.parent.id=:depId and a.year=:year group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllSalemanByDepYear(Pageable page, @Param(value = "depId")Long depId, @Param(value = "year") int year);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesId and a.year=:year and a.month=:month group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllSalemanBySalesmanMonth(Pageable page, @Param(value = "salesId")Long salesId, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesId and a.year=:year and a.quarter=:quarter group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllSalemanBySalesmanQuater(Pageable page, @Param(value = "salesId")Long salesId, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesId and a.year=:year group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllSalemanBySalesmanYear(Pageable page, @Param(value = "salesId")Long salesId, @Param(value = "year") int year);

    
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesmanId and a.year=:year and a.month=:month group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllSalemanByMonth(Pageable page, @Param(value = "salesmanId")Long salesmanId, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesmanId and a.year=:year and a.quarter=:quarter group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllSalemanByQuater(Pageable page, @Param(value = "salesmanId")Long salesmanId, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesmanId and a.year=:year group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllSalemanByYear(Pageable page, @Param(value = "salesmanId")Long salesmanId, @Param(value = "year") int year);

    //跨部门统计所有奶站销售情况
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.year=:year and a.month=:month group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByAdminMonth(Pageable page, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.year=:year and a.quarter=:quarter group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByAdminQuater(Pageable page, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.year=:year group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByAdminYear(Pageable page, @Param(value = "year") int year);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.parent.id=:depId and a.year=:year and a.month=:month group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByDepMonth(Pageable page, @Param(value = "depId")Long depId, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.parent.id=:depId and  a.year=:year and a.quarter=:quarter group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByDepQuater(Pageable page, @Param(value = "depId")Long depId, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.parent.id=:depId and  a.year=:year group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByDepYear(Pageable page, @Param(value = "depId")Long depId, @Param(value = "year") int year);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesId and a.year=:year and a.month=:month group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationBySalesmanMonth(Pageable page, @Param(value = "salesId")Long salesId, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesId and  a.year=:year and a.quarter=:quarter group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationBySalesmanQuater(Pageable page, @Param(value = "salesId")Long salesId, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesId and  a.year=:year group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationBySalesmanYear(Pageable page, @Param(value = "salesId")Long salesId, @Param(value = "year") int year);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.dealer.id=:darlerId and a.year=:year and a.month=:month group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByDarlerMonth(Pageable page, @Param(value = "darlerId")Long darlerId, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.dealer.id=:darlerId and  a.year=:year and a.quarter=:quarter group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByDarlerQuater(Pageable page, @Param(value = "darlerId")Long darlerId, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.dealer.id=:darlerId and  a.year=:year group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByDarlerYear(Pageable page, @Param(value = "darlerId")Long darlerId, @Param(value = "year") int year);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.id=:stationId and a.year=:year and a.month=:month group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByStationMonth(Pageable page, @Param(value = "stationId")Long stationId, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.id=:stationId and  a.year=:year and a.quarter=:quarter group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByStationQuater(Pageable page, @Param(value = "stationId")Long stationId, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.id=:stationId and  a.year=:year group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByStationYear(Pageable page, @Param(value = "stationId")Long stationId, @Param(value = "year") int year);

//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.id:=stationId and a.year=:year and a.month=:month group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByMonth(Pageable page, @Param(value = "stationId")Long stationId, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.id:=stationId and  a.year=:year and a.quarter=:quarter group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByQuater(Pageable page, @Param(value = "stationId")Long stationId, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.id:=stationId and  a.year=:year group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllStationByYear(Pageable page, @Param(value = "stationId")Long stationId, @Param(value = "year") int year);

    //跨部门统计所有经销商销售情况
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone) from EveryDayEveryOrgReport a where a.year=:year and a.month=:month group by a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDarlerByAdminMonth(Pageable page, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone) from EveryDayEveryOrgReport a where a.year=:year and a.quarter=:quarter group by a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDarlerByAdminQuater(Pageable page, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone) from EveryDayEveryOrgReport a where a.year=:year group by a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDarlerByAdminYear(Pageable page, @Param(value = "year") int year);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone) from EveryDayEveryOrgReport a where a.org.parent.id=:depId and a.year=:year and a.month=:month group by a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDarlerByDepMonth(Pageable page, @Param(value = "depId")Long depId, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone) from EveryDayEveryOrgReport a where a.org.parent.id=:depId and  a.year=:year and a.quarter=:quarter group by a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDarlerByDepQuater(Pageable page, @Param(value = "depId")Long depId, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone) from EveryDayEveryOrgReport a where a.org.parent.id=:depId and  a.year=:year group by a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDarlerByDepYear(Pageable page, @Param(value = "depId")Long depId, @Param(value = "year") int year);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesId and a.year=:year and a.month=:month group by a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDarlerBySalesmanMonth(Pageable page, @Param(value = "salesId")Long salesId, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesId and  a.year=:year and a.quarter=:quarter group by a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDarlerBySalesmanQuater(Pageable page, @Param(value = "salesId")Long salesId, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone) from EveryDayEveryOrgReport a where a.org.userinfo.id=:salesId and  a.year=:year group by a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDarlerBySalesmanYear(Pageable page, @Param(value = "salesId")Long salesId, @Param(value = "year") int year);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone) from EveryDayEveryOrgReport a where a.org.dealer.id=:darlerId and a.year=:year and a.month=:month group by a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDarlerByDarlerMonth(Pageable page, @Param(value = "darlerId")Long darlerId, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone) from EveryDayEveryOrgReport a where a.org.dealer.id=:darlerId and  a.year=:year and a.quarter=:quarter group by a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDarlerByDarlerQuater(Pageable page, @Param(value = "darlerId")Long darlerId, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone) from EveryDayEveryOrgReport a where a.org.dealer.id=:darlerId and  a.year=:year group by a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone  order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDarlerByDarlerYear(Pageable page, @Param(value = "darlerId")Long darlerId, @Param(value = "year") int year);

    //以下部分统计所有销售部门各自的销售业绩
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.userinfo.org.id, a.org.userinfo.org.name, a.org.userinfo.org.responsible_man, a.org.userinfo.org.responsible_phone) from EveryDayEveryOrgReport a where a.year=:year and a.month=:month group by a.org.userinfo.org.id, a.org.userinfo.org.name, a.org.userinfo.org.responsible_man, a.org.userinfo.org.responsible_phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDepByAdminMonth(Pageable page, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.userinfo.org.id, a.org.userinfo.org.name, a.org.userinfo.org.responsible_man, a.org.userinfo.org.responsible_phone) from EveryDayEveryOrgReport a where a.year=:year and a.quarter=:quarter group by a.org.userinfo.org.id, a.org.userinfo.org.name, a.org.userinfo.org.responsible_man, a.org.userinfo.org.responsible_phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDepByAdminQuater(Pageable page, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.userinfo.org.id, a.org.userinfo.org.name, a.org.userinfo.org.responsible_man, a.org.userinfo.org.responsible_phone) from EveryDayEveryOrgReport a where a.year=:year group by a.org.userinfo.org.id, a.org.userinfo.org.name, a.org.userinfo.org.responsible_man, a.org.userinfo.org.responsible_phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDepByAdminYear(Pageable page, @Param(value = "year") int year);
//    
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.userinfo.org.id, a.org.userinfo.org.name, a.org.userinfo.org.responsible_man, a.org.userinfo.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.parent.id=:depId and a.year=:year and a.month=:month group by a.org.userinfo.org.id, a.org.userinfo.org.name, a.org.userinfo.org.responsible_man, a.org.userinfo.org.responsible_phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDepByDepMonth(Pageable page, @Param(value = "depId")Long depId, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.userinfo.org.id, a.org.userinfo.org.name, a.org.userinfo.org.responsible_man, a.org.userinfo.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.parent.id=:depId and a.year=:year and a.quarter=:quarter group by a.org.userinfo.org.id, a.org.userinfo.org.name, a.org.userinfo.org.responsible_man, a.org.userinfo.org.responsible_phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDepByDepQuater(Pageable page, @Param(value = "depId")Long depId, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.userinfo.org.id, a.org.userinfo.org.name, a.org.userinfo.org.responsible_man, a.org.userinfo.org.responsible_phone) from EveryDayEveryOrgReport a where a.org.parent.id=:depId and a.year=:year group by a.org.userinfo.org.id, a.org.userinfo.org.name, a.org.userinfo.org.responsible_man, a.org.userinfo.org.responsible_phone order by sum(totalnum) desc")
//    Page<WebSaleSampleItem> queryAllDepByDepYear(Pageable page, @Param(value = "depId")Long depId, @Param(value = "year") int year);
    
    //以下部分统计公司所有销售业绩
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum)) from EveryDayEveryOrgReport a where a.year=:year and a.month=:month")
//    Page<WebSaleSampleItem> queryAllComByAdminMonth(Pageable page, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum)) from EveryDayEveryOrgReport a where a.year=:year and a.quarter=:quarter")
//    Page<WebSaleSampleItem> queryAllComByAdminQuater(Pageable page, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum)) from EveryDayEveryOrgReport a where a.year=:year")
//    Page<WebSaleSampleItem> queryAllComByAdminYear(Pageable page, @Param(value = "year") int year);
    
    
    
    //跨部门查询根据名称 统计所有销售人员销售情况
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo is not null and (a.org.userinfo.realname =:name or a.org.userinfo.phone=:name) and a.year=:year and a.month=:month group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone")
//    Page<WebSaleSampleItem> queryFieldSalemanByMonth(Pageable page,  @Param(value = "name")String name, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo is not null and (a.org.userinfo.realname =:name or a.org.userinfo.phone=:name) and a.year=:year and a.quarter=:quarter group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone")
//    Page<WebSaleSampleItem> queryFieldSalemanByQuater(Pageable page, @Param(value = "name")String name, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo is not null and (a.org.userinfo.realname =:name or a.org.userinfo.phone=:name) and a.year=:year group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone")
//    Page<WebSaleSampleItem> queryFieldSalemanByYear(Pageable page, @Param(value = "name")String name, @Param(value = "year") int year);
    
    
    //跨部门查询根据名称 统计所有奶站/经销商销售情况
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.id, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.id in :orgids and a.year=:year and a.month=:month group by a.org")
//    Page<WebSaleSampleItem> queryFieldStationByMonth(Pageable page, @Param(value = "orgids") List<Long> orgids, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.id, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.id in :orgids  and a.year=:year and a.quarter=:quarter group by a.org")
//    Page<WebSaleSampleItem> queryFieldStationByQuater(Pageable page, @Param(value = "orgids") List<Long> orgids, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.id, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.id in :orgids  and a.year=:year group by a.org")
//    Page<WebSaleSampleItem> queryFieldStationByYear(Pageable page, @Param(value = "orgids") List<Long> orgids, @Param(value = "year") int year);
    
    
    
    //以下部分查询根据名称 统计所有销售部门各自的销售业绩
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.org.id, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo.org.id in :orgids and a.year=:year and a.month=:month group by a.org.userinfo.org")
//    Page<WebSaleSampleItem> queryFieldDepByMonth(Pageable page, @Param(value = "orgids") List<Long> orgids, @Param(value = "year") int year, @Param(value = "month") int month);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.org.id, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo.org.id in :orgids and a.year=:year and a.quarter=:quarter group by a.org.userinfo.org")
//    Page<WebSaleSampleItem> queryFieldDepByQuater(Pageable page, @Param(value = "orgids") List<Long> orgids, @Param(value = "year") int year, @Param(value = "quarter") int quarter);
//
//    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.org.id, sum(totalnum)) from EveryDayEveryOrgReport a where a.org.userinfo.org.id in :orgids and a.year=:year group by a.org.userinfo.org")
//    Page<WebSaleSampleItem> queryFieldDepByYear(Pageable page, @Param(value = "orgids") List<Long> orgids, @Param(value = "year") int year);
}
