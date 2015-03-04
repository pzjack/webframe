/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dispatch.repository;

import com.dinglicom.dispatch.domain.DispathingTaskItem;
import com.dinglicom.dispatch.domain.TaskStatisticsItem;
import com.dinglicom.dispatch.domain.TaskStatisticsResp;
import com.dinglicom.dispatch.entity.DispatchingRecord;
import com.dinglicom.frame.sys.entity.UserInfo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface DispatchingRecordDao extends PagingAndSortingRepository<DispatchingRecord, Long>, JpaSpecificationExecutor<DispatchingRecord> {

    @Query("select distinct new com.dinglicom.dispatch.domain.DispathingTaskItem(orderNo, consigneeName, address) from DispatchingRecord a where a.year = :year and a.month = :month and a.day = :day and a.dispatchingWorker=:worker and a.signDelete=:signDelete and a.confirmName IS NULL")
    Page<DispathingTaskItem> findDispathchingTaskNotComplete(Pageable page, 
            @Param(value="year")int year, @Param(value="month")int month, @Param(value="day")int day, 
            @Param(value="worker")UserInfo worker, @Param(value="signDelete")Boolean signDelete);
    
    @Query("select distinct new com.dinglicom.dispatch.domain.DispathingTaskItem(orderNo, consigneeName, address) from DispatchingRecord a where a.year = :year and a.month = :month and a.day = :day and a.dispatchingWorker=:worker and a.signDelete=:signDelete and a.confirmName IS NOT NULL")
    Page<DispathingTaskItem> findDispathchingTaskComplete(Pageable page, 
            @Param(value="year")int year, @Param(value="month")int month, @Param(value="day")int day,
            @Param(value="worker")UserInfo worker, @Param(value="signDelete")Boolean signDelete);

    @Query("select new com.dinglicom.dispatch.entity.DispatchingRecord(a.consigneeName, a.phone, a.product.id, productName, dispatchingNum) from DispatchingRecord a where a.year = :year and a.month = :month and a.day = :day and a.orderNo=:orderNo and a.signDelete=:signDelete")
    List<DispatchingRecord> findDispathchingTaskDetails(@Param(value="orderNo")String orderNo,
            @Param(value="year")int year, @Param(value="month")int month, @Param(value="day")int day,
            @Param(value="signDelete")Boolean signDelete);
    
    @Query("select new com.dinglicom.dispatch.domain.TaskStatisticsResp(SUM(a.dispatchingNum), SUM(a.confirmNum)) from DispatchingRecord a where a.year = :year and a.month = :month and a.day = :day and a.dispatchingOrg.id=:dispatchingOrgId and a.dispatchingWorker.id=:dispatchingWorkerId and a.signDelete=:signDelete group by a.year, a.month, a.day")
    List<TaskStatisticsResp> findWorkerStatistics( 
            @Param(value="dispatchingOrgId")long dispatchingOrgId, @Param(value="dispatchingWorkerId")long dispatchingWorkerId,
            @Param(value="year")int year, @Param(value="month")int month, @Param(value="day")int day,
            @Param(value="signDelete")Boolean signDelete);
    
    
    @Query("select new com.dinglicom.dispatch.domain.TaskStatisticsItem(a.product.id, a.productName, SUM(a.dispatchingNum), SUM(a.confirmNum)) from DispatchingRecord a where a.year = :year and a.month = :month and a.day = :day and a.dispatchingOrg.id=:dispatchingOrgId and a.dispatchingWorker.id=:dispatchingWorkerId and a.signDelete=:signDelete group by a.product.id, a.productName")
    List<TaskStatisticsItem> findWorkerProductStatistics( 
            @Param(value="dispatchingOrgId")long dispatchingOrgId, @Param(value="dispatchingWorkerId")long dispatchingWorkerId,
            @Param(value="year")int year, @Param(value="month")int month, @Param(value="day")int day,
            @Param(value="signDelete")Boolean signDelete);

    @Query("from DispatchingRecord a where a.orderNo=:orderNo and a.dispatchingWorker.id=:dispatchingWorkerId and a.confirmName IS NULL and a.signDelete=:signDelete")
    List<DispatchingRecord> findByWorkerIdAndOrderNo(@Param(value="orderNo")String orderNo,
            @Param(value="dispatchingWorkerId")long dispatchingWorkerId, @Param(value="signDelete")Boolean signDelete);
    
    /**
     * 根据时间查看奶站是否已经生成配送单
     * @param year
     * @param month
     * @param day
     * @param dispatchingOrgId
     * @param signDelete
     * @return 
     */
     @Query("select count(*) from DispatchingRecord a where a.year = :year and a.month = :month and a.day = :day and a.dispatchingOrg.id=:dispatchingOrgId and a.signDelete=:signDelete")
    long findByStationReportCount(@Param(value="year")int year, @Param(value="month")int month, @Param(value="day")int day,
            @Param(value="dispatchingOrgId")long dispatchingOrgId, @Param(value="signDelete")Boolean signDelete);
    
//    @Query("select new com.dinglicom.reportnum.domain.ReportNumberItemResp(a.product.id, a.productName, SUM(a.dispatchingNum), SUM(a.confirmNum)) from DispatchingRecord a where a.year = :year and a.month = :month and a.day = :day and a.dispatchingOrg.id=:dispatchingOrgId and a.dispatchingWorker.id=:dispatchingWorkerId and a.signDelete=:signDelete group by a.product.id, a.productName")
//    List<ReportNumberItemResp> getCurrentDayReportNum(@Param(value="dispatchingOrgId")long dispatchingOrgId,
//            @Param(value="year")int year, @Param(value="month")int month, @Param(value="day")int day,
//            @Param(value="signDelete")Boolean signDelete);
}
