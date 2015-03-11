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

package com.dinglicom.dealerdispathing.repository;

import com.dinglicom.dealerdispathing.domain.DealerDispatchOnedayItem;
import com.dinglicom.dealerdispathing.entity.DealerDispatchItem;
import java.util.Date;
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
public interface DealerDispatchItemDao extends PagingAndSortingRepository<DealerDispatchItem, Long>, JpaSpecificationExecutor<DealerDispatchItem> {
    @Query("from DealerDispatchItem a where a.dealer.id=:dealerId and a.dispathingdate between :minDate and :maxDate")
    List<DealerDispatchItem> findByDetailByDealerDay(@Param(value = "dealerId") Long dealerId, @Param(value = "minDate") Date minDate, @Param(value = "maxDate") Date maxDate);
    
    List<DealerDispatchItem> findByDispatingno(String dispatingno);

    @Query("select count(*) from DealerDispatchItem a where a.dealer.id=:dealerId and a.dispathstate !=:dstate and a.dispathingdate between :minDate and :maxDate")
    int countByDealer(@Param(value = "dealerId") Long dealerId, @Param(value = "dstate") String dstate, @Param(value = "minDate") Date minDate, @Param(value = "maxDate") Date maxDate);
    
    @Query("select distinct new com.dinglicom.dealerdispathing.domain.DealerDispatchOnedayItem(dispatingno, stationname, stationmanager, stationphone, stationaddress) from DealerDispatchItem a where a.dealer.id=:dealerId and a.dispathstate=:dstate and a.dispathingdate between :minDate and :maxDate")
    Page<DealerDispatchOnedayItem> findByDealerOnedayStation(Pageable page, @Param(value = "dealerId") Long dealerId, @Param(value = "dstate") String dstate, @Param(value = "minDate") Date minDate, @Param(value = "maxDate") Date maxDate);

}
