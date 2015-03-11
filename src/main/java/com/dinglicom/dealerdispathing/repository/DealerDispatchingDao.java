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

import com.dinglicom.dealerdispathing.entity.DealerDispatching;
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
public interface DealerDispatchingDao extends PagingAndSortingRepository<DealerDispatching, Long>, JpaSpecificationExecutor<DealerDispatching> {

    @Query("select count(*) from DealerDispatching a where a.dealer.id =:dealerId and a.dispathingdate between :minDate and :maxDate")
    long countDealerDataByDate(@Param(value = "dealerId") Long dealerId, @Param(value = "minDate") Date minDate, @Param(value = "maxDate") Date maxDate);
    
    List<DealerDispatching> findByDispatingno(String dispatingno);
    
    @Query("from DealerDispatching a where a.dealer.id=:dealerId and a.dispathingdate between :minDate and :maxDate")
    List<DealerDispatching> findByDealerDay(@Param(value = "dealerId") Long dealerId, @Param(value = "minDate") Date minDate, @Param(value = "maxDate") Date maxDate);
    
    
    @Query("from DealerDispatching a where a.dispathstate=:dstate and a.dispathingdate between :minDate and :maxDate")
    Page<DealerDispatching> findByDispathingdateBetween(Pageable page, @Param(value = "dstate") String dstate, @Param(value = "minDate") Date minDate, @Param(value = "maxDate") Date maxDate);
}
