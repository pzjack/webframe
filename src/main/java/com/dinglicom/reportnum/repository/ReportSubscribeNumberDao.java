/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.reportnum.repository;

import com.dinglicom.reportnum.demain.AutoReportLastNum;
import com.dinglicom.reportnum.demain.LineDataTmp;
import com.dinglicom.reportnum.demain.ReportNumberItemResp;
import com.dinglicom.reportnum.demain.WebReportnumberDetailItem;
import com.dinglicom.reportnum.entity.ReportSubscribeNumber;
import com.dinglicom.salesman.domain.ProductSaleSampleRespItem;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface ReportSubscribeNumberDao extends PagingAndSortingRepository<ReportSubscribeNumber, Long> {

    @Query("select count(*) from ReportSubscribeNumber a where a.org.id=:orgid and a.year=:year and a.month=:month and a.day=:day")
    int findByYearAndMonthAndDay(@Param(value = "orgid") long orgid, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "day") int day);

    @Query("select new com.dinglicom.reportnum.demain.ReportNumberItemResp(a.product.id, a.productname, a.distrutenum, a.minusnum, a.plusnum) from ReportSubscribeNumber a where a.org.id=:orgid and a.year=:year and a.month=:month and a.day=:day")
    List<ReportNumberItemResp> findByReportNumYearmonthday(@Param(value = "orgid") long orgid, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "day") int day);

    @Query("select new com.dinglicom.reportnum.demain.AutoReportLastNum(a.product.id, a.reportnum) from ReportSubscribeNumber a where a.org.id=:orgid and a.year=:year and a.month=:month and a.day=:day")
    List<AutoReportLastNum> findByAutoLastReportNumYearmonthday(@Param(value = "orgid") long orgid, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "day") int day);

    List<ReportSubscribeNumber> findByReportno(String reportno);

    @Query("select new com.dinglicom.reportnum.demain.WebReportnumberDetailItem(a.productname, a.reportnum) from ReportSubscribeNumber a where a.everyDayEveryOrgReport.id=:everyId")
    List<WebReportnumberDetailItem> findByEveryDayEveryOrgReport(@Param(value = "everyId") long everyId);

    @Query(" from ReportSubscribeNumber a where a.everyDayEveryOrgReport.id=:everyId")
    List<ReportSubscribeNumber> findByeveryDayEveryOrgReportId(@Param(value = "everyId") long everyId);
    
    
    @Query(" from ReportSubscribeNumber a where a.dealer.id=:dealerid and a.year=:year and a.month=:month and a.day=:day")
    List<ReportSubscribeNumber> findByDealerYearmonthday(@Param(value = "dealerid") Long dealerid, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "day") int day);

    //以下部分统计业务员接口中各个产品的销售情况
    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.id in (select b.id from SysOranizagion b where b.userinfo.id=:salesid and b.signDelete=:signDelete) and a.year=:year and a.month=:month group by a.product,a.productname")
    Page<ProductSaleSampleRespItem> queryProductSampleByMonth(Pageable page, @Param(value = "salesid") long salesid, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "signDelete") Boolean signDelete);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.id in (select b.id from SysOranizagion b where b.userinfo.id=:salesid and b.signDelete=:signDelete) and a.year=:year and a.month=:month")
    Long queryProductSampleByMonth(@Param(value = "salesid") long salesid, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.id in (select b.id from SysOranizagion b where b.userinfo.id=:salesid and b.signDelete=:signDelete) and a.year=:year and a.quarter=:quarter group by a.product,a.productname")
    Page<ProductSaleSampleRespItem> queryProductSampleByQuarter(Pageable page, @Param(value = "salesid") long salesid, @Param(value = "year") int year, @Param(value = "quarter") int quarter, @Param(value = "signDelete") Boolean signDelete);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.id in (select b.id from SysOranizagion b where b.userinfo.id=:salesid and b.signDelete=:signDelete) and a.year=:year and a.quarter=:quarter")
    Long queryProductSampleByQuarter(@Param(value = "salesid") long salesid, @Param(value = "year") int year, @Param(value = "quarter") int quarter, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.id in (select b.id from SysOranizagion b where b.userinfo.id=:salesid and b.signDelete=:signDelete) and a.year=:year group by a.product,a.productname")
    Page<ProductSaleSampleRespItem> queryProductSampleByYear(Pageable page, @Param(value = "salesid") long salesid, @Param(value = "year") int year, @Param(value = "signDelete") Boolean signDelete);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.id in (select b.id from SysOranizagion b where b.userinfo.id=:salesid and b.signDelete=:signDelete) and a.year=:year")
    Long queryProductSampleByYear(@Param(value = "salesid") long salesid, @Param(value = "year") int year, @Param(value = "signDelete") Boolean signDelete);

    //以下部分统计业务员接口中各个奶站或者经销商的销量情况
    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.id =:orgid and a.year=:year and a.month=:month and a.day=:day group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryOrgDetailsSampleByDay(@Param(value = "orgid") long orgid, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "day") int day);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.id =:orgid and a.year=:year and a.month=:month and a.day=:day")
    Long queryOrgDetailsSampleByDayNumber(@Param(value = "orgid") long orgid, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "day") int day);
    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.id =:orgid and a.year=:year and a.month=:month group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryOrgDetailsSampleByMonth(@Param(value = "orgid") long orgid, @Param(value = "year") int year, @Param(value = "month") int month);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.id =:orgid and a.year=:year and a.month=:month")
    Long queryOrgDetailsSampleByMonthNumber(@Param(value = "orgid") long orgid, @Param(value = "year") int year, @Param(value = "month") int month);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.id =:orgid and a.year=:year and a.quarter=:quarter group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryOrgDetailsSampleByQuarter(@Param(value = "orgid") long orgid, @Param(value = "year") int year, @Param(value = "quarter") int quarter);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.id =:orgid and a.year=:year and a.quarter=:quarter")
    Long queryOrgDetailsSampleByQuarterNumber(@Param(value = "orgid") long orgid, @Param(value = "year") int year, @Param(value = "quarter") int quarter);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.id =:orgid and a.year=:year group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryOrgDetailsSampleByYear(@Param(value = "orgid") long orgid, @Param(value = "year") int year);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.id =:orgid and a.year=:year")
    Long queryOrgDetailsSampleByYearNumber(@Param(value = "orgid") long orgid, @Param(value = "year") int year);

    //以下部分统计销售部门产品的销量情况
    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.id in (select b.id from SysOranizagion b where b.userinfo.org.id=:depid and b.signDelete=:signDelete) and a.year=:year and a.month=:month group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProductDepByMonth(@Param(value = "depid") long depid, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "signDelete") Boolean signDelete);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.id in (select b.id from SysOranizagion b where b.userinfo.org.id=:depid and b.signDelete=:signDelete) and a.year=:year and a.month=:month")
    Long queryProductDepSampleByMonth(@Param(value = "depid") long depid, @Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.id in (select b.id from SysOranizagion b where b.userinfo.org.id=:depid and b.signDelete=:signDelete) and a.year=:year and a.quarter=:quarter group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProductDepByQuater(@Param(value = "depid") long depid, @Param(value = "year") int year, @Param(value = "quarter") int quarter, @Param(value = "signDelete") Boolean signDelete);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.id in (select b.id from SysOranizagion b where b.userinfo.org.id=:depid and b.signDelete=:signDelete) and a.year=:year and a.quarter=:quarter")
    Long queryProductDepSampleByQuater(@Param(value = "depid") long depid, @Param(value = "year") int year, @Param(value = "quarter") int quarter, @Param(value = "signDelete") Boolean signDelete);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.id in (select b.id from SysOranizagion b where b.userinfo.org.id=:depid and b.signDelete=:signDelete) and a.year=:year group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProductDepByYear(@Param(value = "depid") long depid, @Param(value = "year") int year, @Param(value = "signDelete") Boolean signDelete);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.id in (select b.id from SysOranizagion b where b.userinfo.org.id=:depid and b.signDelete=:signDelete) and a.year=:year")
    Long queryProductDepSampleByYear(@Param(value = "depid") long depid, @Param(value = "year") int year, @Param(value = "signDelete") Boolean signDelete);

    //以下部分统计销售部门接口中各个业务员的销量情况
    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.userinfo.id =:salesid and a.year=:year and a.month=:month group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryDepSalemanByMonth(@Param(value = "salesid") long salesid, @Param(value = "year") int year, @Param(value = "month") int month);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.userinfo.id =:salesid and a.year=:year and a.month=:month")
    Long queryDepSalemanByMonthNumber(@Param(value = "salesid") long orgid, @Param(value = "year") int year, @Param(value = "month") int month);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.userinfo.id =:salesid and a.year=:year and a.quarter=:quarter group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryDepSalemanByQuarter(@Param(value = "salesid") long orgid, @Param(value = "year") int year, @Param(value = "quarter") int quarter);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.userinfo.id =:salesid and a.year=:year and a.quarter=:quarter")
    Long queryDepSalemanByQuarterNumber(@Param(value = "salesid") long orgid, @Param(value = "year") int year, @Param(value = "quarter") int quarter);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.userinfo.id =:salesid and a.year=:year group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryDepSalemanByYear(@Param(value = "salesid") long orgid, @Param(value = "year") int year);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.userinfo.id =:salesid and a.year=:year")
    Long queryDepSalemanByYearNumber(@Param(value = "salesid") long orgid, @Param(value = "year") int year);

    //公司所有销量统计
    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year and a.month=:month group by a.product,a.productname order by rptnm desc")
    List<ProductSaleSampleRespItem> queryProductAllByMonth(@Param(value = "year") int year, @Param(value = "month") int month);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year and a.month=:month and a.org.parent.id=:depId group by a.product,a.productname order by rptnm desc")
    List<ProductSaleSampleRespItem> queryProductDepByMonth(@Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "depId") Long depId);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year and a.month=:month and a.org.userinfo.id=:salesmanId group by a.product,a.productname order by rptnm desc")
    List<ProductSaleSampleRespItem> queryProductSalesmanByMonth(@Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "salesmanId") Long salesmanId);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year and a.month=:month and a.org.dealer.id=:dealerId group by a.product,a.productname order by rptnm desc")
    List<ProductSaleSampleRespItem> queryProductDealerByMonth(@Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "dealerId") Long dealerId);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year and a.month=:month and a.org.id=:stationId group by a.product,a.productname order by rptnm desc")
    List<ProductSaleSampleRespItem> queryProductStationByMonth(@Param(value = "year") int year, @Param(value = "month") int month, @Param(value = "stationId") Long stationId);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.year=:year and a.month=:month")
    Long queryProductAllSampleByMonth(@Param(value = "year") int year, @Param(value = "month") int month);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year and a.quarter=:quarter group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProducAllByQuater(@Param(value = "year") int year, @Param(value = "quarter") int quarter);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year and a.quarter=:quarter and a.org.parent.id=:depId group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProducDepByQuater(@Param(value = "year") int year, @Param(value = "quarter") int quarter, @Param(value = "depId") Long depId);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year and a.quarter=:quarter and a.org.userinfo.id=:salesmanId group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProducSalesmanByQuater(@Param(value = "year") int year, @Param(value = "quarter") int quarter, @Param(value = "salesmanId") Long salesmanId);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year and a.quarter=:quarter and a.org.dealer.id=:dealerId group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProducDealerByQuater(@Param(value = "year") int year, @Param(value = "quarter") int quarter, @Param(value = "dealerId") Long dealerId);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year and a.quarter=:quarter and a.org.id=:stationId group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProducStationByQuater(@Param(value = "year") int year, @Param(value = "quarter") int quarter, @Param(value = "stationId") Long stationId);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.year=:year and a.quarter=:quarter")
    Long queryProductAllSampleByQuater(@Param(value = "year") int year, @Param(value = "quarter") int quarter);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProductAllByYear(@Param(value = "year") int year);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year and a.org.parent.id=:depId group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProductDepByYear(@Param(value = "year") int year, @Param(value = "depId") Long depId);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year and a.org.userinfo.id=:salesmanId group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProductSalesmanByYear(@Param(value = "year") int year, @Param(value = "salesmanId") Long salesmanId);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year and a.org.dealer.id=:dealerId group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProductDealerByYear(@Param(value = "year") int year, @Param(value = "dealerId") Long dealerId);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum) as rptnm) from ReportSubscribeNumber a where a.year=:year and a.org.id=:stationId group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProductStationByYear(@Param(value = "year") int year, @Param(value = "stationId") Long stationId);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.year=:year")
    Long queryProductAllSampleByYear(@Param(value = "year") int year);

    //公司统计某个销售部门的个产品的销售情况
    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.userinfo.org.id =:depid and a.year=:year and a.month=:month group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProductByDepMonth(@Param(value = "depid") long depid, @Param(value = "year") int year, @Param(value = "month") int month);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.userinfo.org.id =:depid and a.year=:year and a.month=:month")
    Long queryProductSampleByDepMonth(@Param(value = "depid") long depid, @Param(value = "year") int year, @Param(value = "month") int month);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.userinfo.org.id =:depid and a.year=:year and a.quarter=:quarter group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProductByDepQuater(@Param(value = "depid") long depid, @Param(value = "year") int year, @Param(value = "quarter") int quarter);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.userinfo.org.id =:depid and a.year=:year and a.quarter=:quarter")
    Long queryProductSampleByDepQuater(@Param(value = "depid") long depid, @Param(value = "year") int year, @Param(value = "quarter") int quarter);

    @Query("select new com.dinglicom.salesman.domain.ProductSaleSampleRespItem(a.product.id, a.productname, sum(reportnum)) from ReportSubscribeNumber a where a.org.userinfo.org.id =:depid and a.year=:year group by a.product,a.productname")
    List<ProductSaleSampleRespItem> queryProductByDepYear(@Param(value = "depid") long depid, @Param(value = "year") int year);

    @Query("select sum(reportnum) from ReportSubscribeNumber a where a.org.userinfo.org.id =:depid and a.year=:year")
    Long queryProductSampleByDepYear(@Param(value = "depid") long depid, @Param(value = "year") int year);
    
    
    //经销商报量查询
    @Query("select new com.dinglicom.reportnum.demain.LineDataTmp(a.org.id, a.org.name, a.product.id, a.product.shortname, a.product.producttype, sum(a.reportnum)) from ReportSubscribeNumber a where a.org.name like :orgname and a.year=:year group by a.year,a.org,a.product")
    Page<LineDataTmp> queryLinebyYear(Pageable page, @Param(value = "year") Integer year, @Param(value = "orgname")String orgname);
    @Query("select new com.dinglicom.reportnum.demain.LineDataTmp(a.org.id, a.org.name, a.product.id, a.product.shortDesc, a.product.producttype, sum(a.reportnum)) from ReportSubscribeNumber a where a.year=:year group by a.year,a.org,a.product")
    Page<LineDataTmp> queryLinebyYear(Pageable page, @Param(value = "year") Integer year);
    
    //线路数据
    @Query("select new com.dinglicom.reportnum.demain.LineDataTmp(a.org.id, a.org.name, a.product.id, a.product.shortname, a.product.producttype, sum(a.reportnum)) from ReportSubscribeNumber a where a.org.name like :orgname and a.year=:year and a.month=:month and a.day=:day group by a.year,a.org,a.product")
    Page<LineDataTmp> queryLinebyYearmonthday(Pageable page, @Param(value = "year") Integer year, @Param(value = "month") Integer month, @Param(value = "day") Integer day, @Param(value = "orgname")String orgname);
    @Query("select new com.dinglicom.reportnum.demain.LineDataTmp(a.org.id, a.org.name, a.product.id, a.product.shortname, a.product.producttype, sum(a.reportnum)) from ReportSubscribeNumber a where a.year=:year and a.month=:month and a.day=:day group by a.year,a.org,a.product")
    Page<LineDataTmp> queryLinebyYearmonthday(Pageable page, @Param(value = "year") Integer year,@Param(value = "month") Integer month, @Param(value = "day") Integer day);
    
    @Query("select new com.dinglicom.reportnum.demain.LineDataTmp(a.org.id, a.org.name, a.org.dealer_name, a.product.id, a.product.shortname, a.product.producttype, sum(a.reportnum)) from ReportSubscribeNumber a where a.year=:year and a.month=:month and a.day=:day group by a.year,a.org,a.product")
    List<LineDataTmp> queryLinebyYearmonthday(@Param(value = "year") Integer year, @Param(value = "month") Integer month, @Param(value = "day") Integer day);
    
}
