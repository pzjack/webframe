/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.repository;

import com.dinglicom.order.entity.OrderChangeRecord;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface OrderChangeRecordDao extends PagingAndSortingRepository<OrderChangeRecord, Long>, JpaSpecificationExecutor<OrderChangeRecord> {

     @Query("from OrderChangeRecord a where a.pausestate=:pausestate and a.endTime >= :endTime and a.signDelete=:signDelete")
    List<OrderChangeRecord> findByPauseamont(@Param(value = "endTime") Date endTime, @Param(value = "pausestate") int pausestate, @Param(value = "signDelete") Boolean signDelete);
}
